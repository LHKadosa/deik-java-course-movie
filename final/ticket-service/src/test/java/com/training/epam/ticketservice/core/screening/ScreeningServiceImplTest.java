package com.training.epam.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreeningServiceImplTest {

    private static final Movie MOVIE_ENTITY = new Movie("MovieTitle", "MovieGenre", 123);
    private static final Room ROOM_ENTITY = new Room("RoomName", 12, 34);
    private static final Screening ENTITY = new Screening(MOVIE_ENTITY, ROOM_ENTITY, LocalDateTime.parse("2021-03-15 11:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final ScreeningDto DTO = new ScreeningDto.Builder()
            .withMovie(MOVIE_ENTITY)
            .withRoom(ROOM_ENTITY)
            .withStartingTime(LocalDateTime.parse("2021-03-15 11:00",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final ScreeningServiceImpl underTest = new ScreeningServiceImpl(movieRepository, roomRepository, screeningRepository);

    @Test
    void testGetScreeningListShouldReturnTheScreeningWhenItExists() {
        // Given
        when(screeningRepository.findAll()).thenReturn(List.of(ENTITY));
        List<ScreeningDto> expected = List.of(DTO);

        // When
        List<ScreeningDto> actual = underTest.getScreeningList();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetScreeningListShouldReturnEmptyWhenItDoesntExist() {
        when(screeningRepository.findAll()).thenReturn(List.of());
        List<ScreeningDto> expected = List.of();

        List<ScreeningDto> actual = underTest.getScreeningList();

        assertEquals(expected, actual);
    }

    @Test
    void canDeleteScreening() {
        String result = underTest.deleteScreening(DTO.getMovie().toString(), DTO.getRoom().toString(), "2021-03-15 11:00");

        assertEquals(result, "Screening deleted!");
    }
}