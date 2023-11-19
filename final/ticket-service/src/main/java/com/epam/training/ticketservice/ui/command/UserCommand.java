package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class UserCommand {

    private final UserService userService;

    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        return userService.logout()
                .map(userDto -> userDto + " is logged out!")
                .orElse("You need to login first!");
    }

    @ShellMethod(key = "sign in privileged", value = "User login")
    public String login(String username, String password) {
        return userService.login(username, password)
                .map(userDto -> userDto + " is successfully logged in!")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String print() {
        Optional<UserDto> user = userService.describe();
        if (!user.isEmpty() && user.get().role() == User.Role.ADMIN) {
            return "Signed in with privileged account '" + user.get().username()+"'";
        }

        return userService.describe()
                .map(Record::toString)
                .orElse("You are not signed in");
    }

    @ShellMethod(key = "user register", value = "User registration")
    public String registerUser(String userName, String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }
}