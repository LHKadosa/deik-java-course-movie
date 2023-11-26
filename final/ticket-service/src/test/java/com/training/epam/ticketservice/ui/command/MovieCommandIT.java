package com.training.epam.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    private MovieService productService;


    @Test
    void testProductCreateCommandShouldNotSaveTheProductWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create movie MovieTitle MovieGenre 123");

        // Then
        verify(productService, times(0)).createMovie("MovieTitle", "MovieGenre", 123);
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
