package com.epam.training.ticketservice.core.room.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository  extends JpaRepository<Room, Integer> {
    Optional<Room> findByName(String name);
}