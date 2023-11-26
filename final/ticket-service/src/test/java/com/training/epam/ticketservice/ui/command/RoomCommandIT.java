package com.training.epam.ticketservice.ui.command;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes= Application.class)
@SpringBootTest
@ActiveProfiles("it")
public class RoomCommandIT {


    private static final RoomDto ROOM_DTO = new RoomDto.Builder()
            .withName("RoomName")
            .withRows(12)
            .withColumns(34)
            .build();

    @Autowired
    private Shell shell;

    @SpyBean
    private RoomService roomService;

    @Test
    void testMovieListCommandShouldNotTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "delete room RoomName");
        shell.evaluate(() -> "list rooms");

        // Then
        verify(roomService).getRoomList();
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieCreateCommandShouldNotSaveTheMovieWhenUserIsNotLoggedIn() {
        // Given
        shell.evaluate(() -> "sign out");

        // When
        shell.evaluate(() -> "create room RoomName 12 34");

        // Then
        //verify(movieService).createMovie("MovieTitle", "MovieGenre", 123);
        verify(roomService, times(0)).createRoom("RoomName", 12, 34);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieCreateCommandShouldNotSaveTheMovieWhenUserIsNotAdmin() {
        // Given
        shell.evaluate(() -> "sign out");
        shell.evaluate(() -> "user register username4 password4");
        shell.evaluate(() -> "sign in privileged username4 password4");

        // When
        shell.evaluate(() -> "create room RoomName 12 34");

        // Then
        //verify(movieService).createMovie("MovieTitle", "MovieGenre", 123);
        verify(roomService, times(0)).createRoom("RoomName", 12, 34);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testRoomCreateCommandShouldNotSaveTheRoomWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create room RoomName 12 34");

        // Then
        verify(roomService).createRoom("RoomName", 12, 34);
        //assertTrue(roomService.getRoomList().contains(ROOM_DTO));
    }


    @Test
    void testMovieUpdateCommandShouldNotSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create room RoomName 12 34");
        shell.evaluate(() -> "update room RoomName 34 34");

        // Then
        verify(roomService).updateRoom("RoomName", 34, 34);
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieDeleteCommandShouldNotSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create room RoomName 12 34");
        shell.evaluate(() -> "delete room RoomName");

        // Then
        verify(roomService).deleteRoom("RoomName");
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

    @Test
    void testMovieListCommandShouldNotSaveTheMovieWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");

        // When
        shell.evaluate(() -> "create room RoomName 12 34");
        shell.evaluate(() -> "list rooms");

        // Then
        verify(roomService).getRoomList();
        //assertTrue(movieService.getMovieList().contains(MOVIE_DTO));
    }

}
