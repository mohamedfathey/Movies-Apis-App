package com.moviefilx.movieApi.auth.utils;


import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String refreshToken;
}