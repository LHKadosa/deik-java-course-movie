package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService{

    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    public String createScreening(String movieString, String roomString, String startingTimeString){
        Optional<Movie> movie = movieRepository.findByTitle(movieString);
        Optional<Room> room = roomRepository.findByName(roomString);
        if(movie.isEmpty() || room.isEmpty()) return "Missing Movie or Room";
        LocalDateTime startingTime = LocalDateTime.parse(startingTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Screening screening = new Screening(movie.get(), room.get(), startingTime);

        List<Screening> allOthers = screeningRepository.findAllByRoomName(roomString);
        for(Screening other: allOthers){
            if(screening.isOverlaping(other,true)){
                if(screening.isOverlaping(other, false))
                    return "There is an overlapping screening";
                else
                    return "This would start in the break period after another screening in this room";
            }
        }

        screeningRepository.save(screening);
        return "Screening created!";
    }

    @Override
    public String deleteScreening(String movieString, String roomString, String startingTimeString) {
        LocalDateTime startingTime = LocalDateTime.parse(startingTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Optional<Screening> screening = screeningRepository.findByMovieTitleAndRoomNameAndStartingTime(movieString,roomString,startingTime);
        screening.ifPresent(value -> screeningRepository.deleteById(value.getId()));
        return "Screening deleted!";
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll()
                .stream()
                .map(this::createEntityFromDto)
                .toList();
    }

    private ScreeningDto createEntityFromDto(Screening screening) {
        return ScreeningDto.builder()
                .withMovie(screening.getMovie())
                .withRoom(screening.getRoom())
                .withStartingTime(screening.getStartingTime())
                .build();
    }
}
