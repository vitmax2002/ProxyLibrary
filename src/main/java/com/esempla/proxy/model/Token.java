package com.esempla.proxy.model;

public final class Token {
    public static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Token.token = token;
    }
}
