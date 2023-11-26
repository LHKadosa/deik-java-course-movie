package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
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
public class MovieCommand {

    private final MovieService movieService;

    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Movie creation")
    public String createMovie(String title, String genre, int duration) {
        try {
            movieService.createMovie(title, genre, duration);
            return "Creation was successful!";
        } catch (Exception e) {
            return "Creation failed!";
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Movie updating")
    public String updateMovie(String title, String genre, int duration) {
        movieService.updateMovie(title, genre, duration);
        return "Updating was successful!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Movie deleting")
    public String deleteMovie(String title) {
        movieService.deleteMovie(title);
        return "Deletion was successful!";
    }

    @ShellMethod(key = "list movies", value = "List the available movies.")
    public String listMovie() {
        List<MovieDto> movieList = movieService.getMovieList();
        if (movieList.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            movieList.forEach(movie -> {
                result.append(movie.getTitle())
                        .append(" (")
                        .append(movie.getGenre())
                        .append(", ")
                        .append(movie.getDuration())
                        .append(" minutes)")
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
