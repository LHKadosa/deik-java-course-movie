package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
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
public class RoomCommand {

    private final RoomService roomService;

    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Room creation")
    public String createRoom(String name, int rows, int columns) {
        roomService.createRoom(name, rows, columns);
        return "Creation was successful!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Room updating")
    public String updateRoom(String name, int rows, int columns) {
        roomService.updateRoom(name, rows, columns);
        return "Updating was successful!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Room deleting")
    public String deleteRoom(String name) {
        roomService.deleteRoom(name);
        return "Deletion was successful!";
    }

    @ShellMethod(key = "list rooms", value = "List the available rooms.")
    public String listRoom() {
        List<RoomDto> roomList = roomService.getRoomList();
        if (roomList.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            roomList.forEach(room -> {
                result.append("Room ")
                        .append(room.getName())
                        .append(" with ")
                        .append(room.getRows() * room.getColumns())
                        .append(" seats, ")
                        .append(room.getRows())
                        .append(" rows and ")
                        .append(room.getColumns())
                        .append(" columns")
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
