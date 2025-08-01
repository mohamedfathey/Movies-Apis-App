package com.moviefilx.movieApi.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviefilx.movieApi.DTO.MovieDto;
import com.moviefilx.movieApi.Entity.MoviePageResponse;
import com.moviefilx.movieApi.Exception.EmptyFileException;
import com.moviefilx.movieApi.Service.MovieService;
import com.moviefilx.movieApi.Utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService ;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,@RequestPart String movieDto ) throws IOException {
    if (file.isEmpty()){
        throw new EmptyFileException("File is empty ! Please sand another file");
    }

    MovieDto dto = convertToMovieDto(movieDto) ;
    return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);

    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto>getMovieHandlerById(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/allMovie")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler(){
        return ResponseEntity.ok(movieService.getAllMovie());
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDto>updateMovieHandler(
                                                          @PathVariable Integer movieId,
                                                          @RequestPart MultipartFile file ,
                                                          @RequestPart String movieDtoObj
                                                        ) throws IOException {
        if (file.isEmpty())file = null;
        MovieDto movieDto = convertToMovieDto(movieDtoObj);
        return ResponseEntity.ok(movieService.updateMovie(movieId,movieDto,file));

    }
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String>deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/allMoviesPage")
    public ResponseEntity<MoviePageResponse> getMovieWithPagination(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE ,required = false) Integer pageSize
            ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber,pageSize));
    }

    @GetMapping("/allMoviesPageSort")
    public ResponseEntity<MoviePageResponse> getMovieWithPaginationSorting(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE ,required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY , required = false) String sortBy ,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String dir
    ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir));
    }







    private MovieDto convertToMovieDto (String movieDtoObj)throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj,MovieDto.class);
    }

}
