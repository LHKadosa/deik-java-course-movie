package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ScreeningDto {

    private final Movie movie;
    private final Room room;
    private final LocalDateTime startingTime;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Movie movie;
        private Room room;
        private LocalDateTime startingTime;

        public Builder withMovie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Builder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder withStartingTime(LocalDateTime startingTime) {
            this.startingTime = startingTime;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movie,room,startingTime);
        }
    }
}
