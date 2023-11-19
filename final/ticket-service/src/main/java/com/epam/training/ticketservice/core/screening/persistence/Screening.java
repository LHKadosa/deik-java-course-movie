package com.epam.training.ticketservice.core.screening.persistence;

import javax.persistence.*;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Screenings")
@Data
@NoArgsConstructor

public class Screening {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Room room;
    private LocalDateTime startingTime;

    public Screening(Movie movie, Room room, LocalDateTime startingTime){
        this.movie = movie;
        this.room = room;
        this.startingTime = startingTime;
    }

    public Screening(ScreeningDto screeningDto){
        this.movie = screeningDto.getMovie();
        this.room = screeningDto.getRoom();
        this.startingTime = screeningDto.getStartingTime();
    }

    public boolean isOverlaping(Screening other, boolean withBreak){
        LocalDateTime start = getStartingTime();
        LocalDateTime end = start.plusMinutes(movie.getDuration() + (withBreak ? 10 : 0));
        LocalDateTime otherStart = other.getStartingTime();
        LocalDateTime otherEnd = otherStart.plusMinutes(other.getMovie().getDuration() + (withBreak ? 10 : 0));

        return !(end.isBefore(otherStart) || start.isAfter(otherEnd));
    }
}
