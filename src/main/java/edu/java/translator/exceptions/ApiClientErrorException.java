package edu.java.translator.exceptions;

public class ApiClientErrorException extends ApiException {

    public ApiClientErrorException(String description, String name, int code) {
        super(description, name, code);
    }
}
