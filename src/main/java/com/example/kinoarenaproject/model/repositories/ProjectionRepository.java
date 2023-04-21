package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Movie;
import com.example.kinoarenaproject.model.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Integer> {
    Optional<Projection> findById(int id);

    List<Projection> findByMovie(Optional<Movie> movie);

    @Query(value = "SELECT p.id, p.movie_id, p.hall_id, p.date, p.price, p.start_time FROM projections AS p JOIN halls AS h ON p.hall_id = h.id WHERE h.cinema_id = :cinemaId", nativeQuery = true)
    List<Projection> getProjectionsByCinema(@Param("cinemaId") int cinemaId);
}
