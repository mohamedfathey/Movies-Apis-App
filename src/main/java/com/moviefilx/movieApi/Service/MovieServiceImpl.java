package com.moviefilx.movieApi.Service;

import com.moviefilx.movieApi.DTO.MovieDto;
import com.moviefilx.movieApi.Entity.Movie;
import com.moviefilx.movieApi.Entity.MoviePageResponse;
import com.moviefilx.movieApi.Exception.FileExistsException;
import com.moviefilx.movieApi.Exception.MovieNotFoundException;
import com.moviefilx.movieApi.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private  MovieRepository movieRepository ;
    @Autowired
    private  FileService fileService;
    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl ;


    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new FileExistsException("File is already exists . Please enter anthor file name");
        }
        String uploadFileName =  fileService.uploadFile(path,file);

        movieDto.setPoster(uploadFileName);

        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        Movie saveMovie = movieRepository.save(movie);

        String posterUrl = baseUrl+ "/file/" +uploadFileName ;

        MovieDto response = new MovieDto(
                saveMovie.getMovieId(),
                saveMovie.getTitle(),
                saveMovie.getDirector(),
                saveMovie.getStudio(),
                saveMovie.getMovieCast(),
                saveMovie.getReleaseYear(),
                saveMovie.getPoster(),
                posterUrl
        );



        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie Not Found with id >>> " + movieId));

        String posterUrl = baseUrl+ "/file/" +movie ;

        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );


        return response;
    }

    @Override
    public List<MovieDto> getAllMovie() {
        List<Movie> movies = movieRepository.findAll() ;
        List<MovieDto>movieDtos = new ArrayList<>();
        for(Movie movie :movies){
            String posterUrl = baseUrl+ "/file/" + movie.getPoster() ;

            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto) ;
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // check if move object exists with give movie
        Movie mv = movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie not fount with is >> "+movieId));
        // update Poster
        String fileName = mv.getPoster();
        if (file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path , file);
        }
        // set movieDto's poster new value
        movieDto.setPoster(fileName);
        // map it to Movie object
        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // save the movie object
        Movie updateMovie = movieRepository.save(movie);
        // generate posterUrl for it
        String posterUrl = baseUrl + "/file/" + fileName ;
        // map to movieDto and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        //check if movie object exists in dataBase
        Movie mv = movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie not fount with id "+movieId));
        Integer id = mv.getMovieId();
        // delete the file associated with object
        Files.deleteIfExists(Paths.get(path+File.separator + mv.getPoster()));
        // delete the movie object
        movieRepository.delete(mv);

        return "Movie delete with id + " + id;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<Movie>moviePages =movieRepository.findAll(pageable);
        List<Movie>movies = moviePages.getContent();
        List<MovieDto>movieDtos = new ArrayList<>();

        for(Movie movie :movies){
            String posterUrl = baseUrl+ "/file/" + movie.getPoster() ;

            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto) ;
        }



        return new MoviePageResponse(movieDtos , pageNumber , pageSize,
                                    moviePages.getTotalElements(),
                                    moviePages.getTotalPages(),
                                    moviePages.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                              : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Movie> moviePages = movieRepository.findAll(pageable) ;
        List<Movie>movies = moviePages.getContent();

        List<MovieDto>  movieDtos = new ArrayList<>();


        for(Movie movie :movies){
            String posterUrl = baseUrl+ "/file/" + movie.getPoster() ;

            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto) ;
        }



        return new MoviePageResponse(movieDtos , pageNumber , pageSize,
                moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast());
    }
}
