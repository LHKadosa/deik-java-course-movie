package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand {

    private final ScreeningService screeningService;

    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Screening creation")
    public String createScreening(String movie, String room, String starting) {
        return screeningService.createScreening(movie, room, starting);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Screening deleting")
    public String deleteScreening(String movie, String room, String starting) {
        return screeningService.deleteScreening(movie, room, starting);
    }

    @ShellMethod(key = "list screenings", value = "List the available screenings.")
    public String listScreenings() {
        List<ScreeningDto> screeningList = screeningService.getScreeningList();
        if(screeningList.isEmpty())
            return "There are no screenings";
        else{
            StringBuilder result = new StringBuilder();
            screeningList.forEach(screening -> {
                result.append(screening.getMovie().getTitle())
                        .append(" (")
                        .append(screening.getMovie().getGenre())
                        .append(", ")
                        .append(screening.getMovie().getDuration())
                        .append(" minutes), screened in room ")
                        .append(screening.getRoom().getName())
                        .append(", at ")
                        .append(screening.getStartingTime())
                        .append("\n");
            });
            return result.toString();
        }
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return Availability.unavailable("You are not logged in!");
        } else if (user.get().role() != User.Role.ADMIN) {
            return Availability.unavailable("You are not an admin!");
        }
        return Availability.available();
    }
}
