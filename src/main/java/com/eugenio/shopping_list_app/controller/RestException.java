package com.eugenio.shopping_list_app.controller;

public final class RestException {

    public static class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }
    }
}
