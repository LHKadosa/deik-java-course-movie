package com.epam.training.ticketservice.core.movie.model;

import lombok.Value;

@Value
public class MovieDto {

    private final String title;
    private final String genre;
    private final int duration;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String title;
        private String genre;
        private int duration;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public MovieDto build() {
            return new MovieDto(title, genre, duration);
        }
    }
}