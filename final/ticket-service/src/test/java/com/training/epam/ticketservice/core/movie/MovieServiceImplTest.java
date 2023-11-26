package com.training.epam.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;

class MovieServiceImplTest {

    private static final Movie ENTITY = new Movie("MovieTitle", "MovieGenre", 123);
    private static final MovieDto DTO = new MovieDto.Builder()
            .withTitle("MovieTitle")
            .withGenre("MovieGenre")
            .withDuration(123)
            .build();

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);

    @Test
    void testGetMovieListShouldReturnTheMovieWhenItExists() {
        // Given
        when(movieRepository.findAll()).thenReturn(List.of(ENTITY));
        List<MovieDto> expected = List.of(DTO);

        // When
        List<MovieDto> actual = underTest.getMovieList();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testCreateMovieShouldStoreTheGivenMovieWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(ENTITY)).thenReturn(ENTITY);

        // When
        underTest.createMovie("MovieTitle", "MovieGenre", 123);

        // Then
        verify(movieRepository).save(ENTITY);
    }

    @Test
    void testUpdateMovieShouldStoreTheGivenMovieWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(ENTITY)).thenReturn(ENTITY);

        // When
        underTest.updateMovie("MovieTitle", "MovieGenre", 4567);

        // Then
        verify(movieRepository).findByTitle("MovieTitle");
    }
}
