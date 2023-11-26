package com.epam.training.ticketservice.core.room.model;

import lombok.Value;

public class RoomDto {

    private final String name;
    private final int rows;
    private final int columns;

    public RoomDto(final Builder builder) {
        this.name = builder.name;
        ;
        this.rows = builder.rows;
        ;
        this.columns = builder.columns;
    }

    public String getName() {
        return this.name;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

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
            return new RoomDto(this);
        }
    }
}