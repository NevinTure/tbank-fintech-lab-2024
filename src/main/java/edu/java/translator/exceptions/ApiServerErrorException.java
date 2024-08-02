package edu.java.translator.exceptions;


public class ApiServerErrorException extends ApiException {

    public ApiServerErrorException(String description, String name, int code) {
        super(description, name, code);
    }
}
