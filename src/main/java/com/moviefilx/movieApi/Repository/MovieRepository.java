package com.moviefilx.movieApi.Repository;

import com.moviefilx.movieApi.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
