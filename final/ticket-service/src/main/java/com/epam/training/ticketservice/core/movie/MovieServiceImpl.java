package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public void createMovie(String title, String genre, int duration) {
        Movie movie = new Movie(title, genre, duration);
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(String title, String genre, int duration) {
        Optional<Movie> movie = movieRepository.findByTitle(title);
        if (movie.isPresent()) {
            movie.get().setTitle(title);
            movie.get().setGenre(genre);
            movie.get().setDuration(duration);
            movieRepository.save(movie.get());
        }
    }

    @Override
    public void deleteMovie(String title) {
        Optional<Movie> movie = movieRepository.findByTitle(title);
        movie.ifPresent(value -> movieRepository.deleteById(value.getId()));
    }

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll()
                .stream()
                .map(this::createEntityFromDto)
                .toList();
    }

    private MovieDto createEntityFromDto(Movie movie) {
        return MovieDto.builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withDuration(movie.getDuration())
                .build();
    }

}
