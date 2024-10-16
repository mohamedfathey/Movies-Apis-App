package com.moviefilx.movieApi.Service;

import com.moviefilx.movieApi.DTO.MovieDto;
import com.moviefilx.movieApi.Entity.MoviePageResponse;
import com.moviefilx.movieApi.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto , MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId);

    List<MovieDto>getAllMovie();

    MovieDto updateMovie(Integer movieId , MovieDto movieDto , MultipartFile file)throws IOException ;

    String deleteMovie(Integer movieId) throws IOException;

    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber , Integer pageSize);

    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber , Integer pageSize , String sortBy , String dir) ;


}
