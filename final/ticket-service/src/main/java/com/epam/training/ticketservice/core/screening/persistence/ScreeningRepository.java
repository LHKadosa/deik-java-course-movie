package com.epam.training.ticketservice.core.screening.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Integer>{

    Optional<Screening> findByMovieTitleAndRoomNameAndStartingTime(String movie, String room, LocalDateTime startingTime);

    List<Screening> findAllByRoomName(String roomName);
}
