package com.epam.training.ticketservice.core.movie.model;

import org.apache.maven.surefire.shade.org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.builder.ToStringStyle;

public class MovieDto {

    private String title;
    private String genre;
    private int duration;

    public MovieDto(String title, String genre, int duration){
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public MovieDto(final Builder builder){
        this.title = builder.title;
        this.genre = builder.genre;
        this.duration = builder.duration;
    }

    public String getTitle(){
        return this.title;
    }

    public String getGenre(){
        return this.genre;
    }

    public int getDuration(){
        return this.duration;
    }

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }

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
            return new MovieDto(this);
        }
    }
}