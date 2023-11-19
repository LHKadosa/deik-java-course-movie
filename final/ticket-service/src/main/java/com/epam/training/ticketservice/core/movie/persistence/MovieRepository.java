package com.epam.training.ticketservice.core.movie.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository  extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitle(String title);
}
