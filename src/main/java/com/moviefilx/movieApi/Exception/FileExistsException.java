package com.moviefilx.movieApi.Exception;

public class FileExistsException extends RuntimeException{
    public FileExistsException(String message) {
        super(message);
    }
}
