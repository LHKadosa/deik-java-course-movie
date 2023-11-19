package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void createMovie(String title, String genre, int duration);

    void updateMovie(String title, String genre, int duration);

    void deleteMovie(String title);

    List<MovieDto> getMovieList();
}
