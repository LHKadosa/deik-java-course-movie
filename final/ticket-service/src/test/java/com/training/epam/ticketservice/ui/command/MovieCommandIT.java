package com.training.epam.ticketservice.ui.command;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes= Application.class)
@SpringBootTest
@ActiveProfiles("it")
public class MovieCommandIT {

    private static final MovieDto MOVIE_DTO = new MovieDto.Builder()
            .withTitle("MovieTitle")
            .withGenre("MovieGenre")
            .withDuration(123)
            .build();

    @Autowired
    private Shell shell;

    @SpyBean
    private MovieService movieService;


    @Test
    void testMovieCreateCommandShouldSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre 123");

        // Then
        verify(movieService).createMovie("MovieTitle", "MovieGenre", 123);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieCreateCommandShouldNotSaveTheMovieWhenUserIsNotLoggedIn() {
        // Given
        shell.evaluate(() -> "sign out");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre 123");

        // Then
        //verify(movieService).createMovie("MovieTitle", "MovieGenre", 123);
        verify(movieService, times(0)).createMovie("MovieTitle", "MovieGenre", 123);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieCreateCommandShouldNotSaveTheMovieWhenUserIsNotAdmin() {
        // Given
        shell.evaluate(() -> "sign out");
        shell.evaluate(() -> "user register username3 password3");
        shell.evaluate(() -> "sign in privileged username3 password3");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre 123");

        // Then
        //verify(movieService).createMovie("MovieTitle", "MovieGenre", 123);
        verify(movieService, times(0)).createMovie("MovieTitle", "MovieGenre", 123);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieUpdateCommandShouldNotSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre1 123");
        shell.evaluate(() -> "update movie MovieTitle MovieGenre2 123");

        // Then
        verify(movieService).updateMovie("MovieTitle", "MovieGenre2", 123);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieDeleteCommandShouldNotSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre 123");
        shell.evaluate(() -> "delete movie MovieTitle");

        // Then
        verify(movieService).deleteMovie("MovieTitle");
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieListCommandShouldNotSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre 123");
        shell.evaluate(() -> "list movies");

        // Then
        verify(movieService).getMovieList();
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieListCommandShouldNotTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "list movies");

        // Then
        verify(movieService).getMovieList();
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }
/*
    @Test
    void testProductCreateCommandShouldNotSaveTheProductWhenNobodyIsLoggedIn() {
        // Given
        // When
        shell.evaluate(() -> "admin product create Tej 100");

        // Then
        verify(productService, times(0)).createProduct(PRODUCT_DTO);
    }

    @Test
    void testProductCreateCommandShouldSaveTheProductWhenAdminIsLoggedIn() {
        // Given
        shell.evaluate(() -> "user login admin admin");

        // When
        shell.evaluate(() -> "admin product create Tej 100");

        // Then
        verify(productService).createProduct(PRODUCT_DTO);
        assertTrue(productService.getProductList().contains(PRODUCT_DTO));
    }*/
}
