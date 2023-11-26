package com.epam.training.ticketservice.core.room.model;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RoomDtoTest {

    private final RoomDto roomDto = RoomDto.builder()
            .withColumns(1)
            .withRows(2)
            .withName("Movie name")
            .build();

    @Test
    void canGetName() {
        assertThat("Movie name").isEqualTo(roomDto.getName());
    }

    @Test
    void canGetColumns() {
        assertThat(1).isEqualTo(roomDto.getColumns());
    }

    @Test
    void canGetRows() {
        assertThat(2).isEqualTo(roomDto.getRows());
    }
}