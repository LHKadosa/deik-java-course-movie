package com.epam.training.ticketservice.core.room.model;

import lombok.Value;

@Value
public class RoomDto {

    private final String name;
    private final int rows;
    private final int columns;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private int rows;
        private int columns;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder withColumns(int columns) {
            this.columns = columns;
            return this;
        }

        public RoomDto build() {
            return new RoomDto(name, rows, columns);
        }
    }
}