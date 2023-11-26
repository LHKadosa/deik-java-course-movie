package com.training.epam.ticketservice.ui.command;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.ui.command.ScreeningCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.shell.Shell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("it")
@ContextConfiguration(classes = {ScreeningCommand.class, Application.class})

class ScreeningCommandIT {

    @Autowired
    private Shell shell;

    @SpyBean
    private ScreeningService screeningService;

    @BeforeEach
    void setUp(){
        reset(screeningService);
        clearAllCaches();
    }

    @Test
    void canCreateScreening(){
        shell.evaluate(() -> "sign in privileged admin admin");

        shell.evaluate(() -> "create screening movie room starting");

        verify(screeningService, times(1)).createScreening("movie", "room", "starting");
    }

    @Test
    void canNotCreateScreening(){
        shell.evaluate(() -> "sign out");

        shell.evaluate(() -> "create screening movie room starting");

        verify(screeningService, times(0)).createScreening("movie", "room", "starting");
    }

    @Test
    void canNotCreateScreeningUser(){
        shell.evaluate(() -> "sign out");
        shell.evaluate(() -> "user register username5 password5");
        shell.evaluate(() -> "sign in privileged username5 password5");

        shell.evaluate(() -> "create screening movie room starting");

        verify(screeningService, times(0)).createScreening("movie", "room", "starting");
    }

    @Test
    void canDeleteScreening(){
        when(screeningService.deleteScreening("movie", "room", "2007-12-03 10:15")).thenReturn("Screening deleted!");

        shell.evaluate(() -> "sign in privileged admin admin");

        shell.evaluate(() -> "delete screening movie room starting");

        verify(screeningService, times(1)).deleteScreening("movie", "room", "starting");
        verify(screeningService).deleteScreening("movie", "room", "starting");
    }

    @Test
    void canNotListScreenings(){
        when(screeningService.getScreeningList()).thenReturn(List.of());
        shell.evaluate(() -> "sign in privileged admin admin");

        shell.evaluate(() -> "create screening movie room starting");

        shell.evaluate(() -> "list screenings");

        verify(screeningService, times(1)).getScreeningList();
        verify(screeningService).getScreeningList();
    }

    @Test
    void canListScreenings(){
        when(screeningService.getScreeningList()).thenReturn(List.of());
        shell.evaluate(() -> "sign in privileged admin admin");

        shell.evaluate(() -> "create movie Sátántangó genre 123");
        shell.evaluate(() -> "create room Pedersoli 12 34");
        shell.evaluate(() -> "create screening Sátántangó Pedersoli \"2021-03-15 11:00\"");

        shell.evaluate(() -> "list screenings");

        verify(screeningService, times(1)).getScreeningList();
        verify(screeningService).getScreeningList();
    }
}