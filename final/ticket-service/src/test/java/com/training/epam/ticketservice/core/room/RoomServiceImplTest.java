package com.training.epam.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RoomServiceImplTest {

    private static final Room ENTITY = new Room("RoomName", 12, 34);
    private static final RoomDto DTO = new RoomDto.Builder()
            .withName("RoomName")
            .withRows(12)
            .withColumns(34)
            .build();

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);

    @Test
    void testGetRoomListShouldReturnTheRoomWhenItExists() {
        // Given
        when(roomRepository.findAll()).thenReturn(List.of(ENTITY));
        List<RoomDto> expected = List.of(DTO);

        // When
        List<RoomDto> actual = underTest.getRoomList();

        // Then
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    void testCreateRoomShouldStoreTheGivenRoomWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(ENTITY)).thenReturn(ENTITY);

        // When
        underTest.createRoom("RoomName", 12, 34);

        // Then
        verify(roomRepository).save(ENTITY);
    }

    @Test
    void testUpdateRoomShouldStoreTheGivenRoomWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(ENTITY)).thenReturn(ENTITY);

        // When
        underTest.updateRoom("RoomName", 12, 34);

        // Then
        verify(roomRepository).findByName("RoomName");
    }
}
