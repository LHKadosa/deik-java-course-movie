package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("it")
@ContextConfiguration(classes = {User.class, UserCommand.class, Application.class})
class UserCommandIT {

    @Autowired
    private Shell shell;

    @SpyBean
    private UserService userService;

    @Test
    void canUserRegister() {
        shell.evaluate(() -> "user register username password");

        verify(userService, times(1)).registerUser("username", "password");
    }

    @Test
    void canUserLogin() {
        shell.evaluate(() -> "sign in privileged admin admin");

        verify(userService, times(1)).login("admin", "admin");
    }

    @Test
    void canUserLogOut() {
        shell.evaluate(() -> "sign in privileged admin admin");
        shell.evaluate(() -> "sign out");

        verify(userService, times(1)).logout();
    }

    @Test
    void canUserPrint() {
        shell.evaluate(() -> "sign in privileged admin admin");

        shell.evaluate(() -> "describe account");

        verify(userService, times(2)).describe();
    }

    @Test
    void canUserPrintNoSignIn() {
        shell.evaluate(() -> "sign out");

        shell.evaluate(() -> "describe account");

        verify(userService, times(3)).describe();
    }


    @Test
    void canUserPrintUser() {
        shell.evaluate(() -> "user register username2 password2");
        shell.evaluate(() -> "sign in privileged username2 password2");

        shell.evaluate(() -> "describe account");

        verify(userService, times(3)).describe();
    }
}