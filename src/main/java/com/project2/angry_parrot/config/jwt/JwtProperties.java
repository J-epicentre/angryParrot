package com.project2.angry_parrot.config.jwt;

public interface JwtProperties {
    String SECRET = "jepicentre";
    int EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}
