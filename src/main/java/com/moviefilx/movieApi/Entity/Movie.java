package com.moviefilx.movieApi.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    @NotBlank(message = "Please Provide movie's title !")
    private String title ;
    @NotBlank(message = "Please Provide movie's director")

    private String director ;
    @NotBlank(message = "Please Provide movie's studio")
    private String studio ;
    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String>movieCast ;
    private Integer releaseYear;
    @NotBlank(message = "Please Provide movie's poster")
    private String poster ;


}
