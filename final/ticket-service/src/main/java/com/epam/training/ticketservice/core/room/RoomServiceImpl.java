package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(String name, int rows, int columns) {
        Room room = new Room(name, rows, columns);
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(String name, int rows, int columns) {
        Optional<Room> room = roomRepository.findByName(name);
        if (room.isPresent()) {
            room.get().setName(name);
            room.get().setRows(rows);
            room.get().setColumns(columns);
            roomRepository.save(room.get());
        }
    }

    @Override
    public void deleteRoom(String name) {
        Optional<Room> room = roomRepository.findByName(name);
        room.ifPresent(value -> roomRepository.deleteById(value.getId()));
    }

    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll()
                .stream()
                .map(this::createEntityFromDto)
                .toList();
    }

    private RoomDto createEntityFromDto(Room room) {
        return RoomDto.builder()
                .withName(room.getName())
                .withRows(room.getRows())
                .withColumns(room.getColumns())
                .build();
    }
}