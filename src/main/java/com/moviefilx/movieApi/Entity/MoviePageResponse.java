package com.moviefilx.movieApi.Entity;

import com.moviefilx.movieApi.DTO.MovieDto;

import java.util.List;

public record MoviePageResponse(List<MovieDto>movieDtos,
                                Integer pageNumber,
                                Integer pageSize,
                                long totalElements,
                                int totalPages,

                                boolean isLast
                                ) {

}
