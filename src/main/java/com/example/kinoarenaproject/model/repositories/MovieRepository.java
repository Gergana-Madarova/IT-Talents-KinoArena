package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Genre;
import com.example.kinoarenaproject.model.entities.Movie;
import com.example.kinoarenaproject.model.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findById(int id);

  //  List<Movie> findByGenre(Optional<Genre> genre);
   // List<Movie> findByGenre(int id);

    @Query(value = "SELECT * FROM movies WHERE genre_id = :genre_id", nativeQuery = true)
    List<Movie> findByGenre(@Param("genre_id") int genre_id);
}
