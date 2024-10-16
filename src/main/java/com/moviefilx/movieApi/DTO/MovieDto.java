package com.moviefilx.movieApi.DTO;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Integer movieId;
    @NotBlank(message = "Please Provide movie's title !")
    private String title ;
    @NotBlank(message = "Please Provide movie's director")

    private String director ;
    @NotBlank(message = "Please Provide movie's studio")
    private String studio ;

    private Set<String> movieCast ;

    private Integer releaseYear;
    @NotBlank(message = "Please Provide movie's poster")
    private String poster ;

    @NotBlank(message = "Please Provide movie's poster URL ")

    private String posterUrl ;

}
