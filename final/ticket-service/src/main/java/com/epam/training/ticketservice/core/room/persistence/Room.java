package com.epam.training.ticketservice.core.room.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private int rows;
    private int columns;

    public Room(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

}