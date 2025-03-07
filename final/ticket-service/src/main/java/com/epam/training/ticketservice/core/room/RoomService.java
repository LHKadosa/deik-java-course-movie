package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;

public interface RoomService {

    void createRoom(String name, int rows, int columns);

    void updateRoom(String name, int rows, int columns);

    void deleteRoom(String name);

    List<RoomDto> getRoomList();
}