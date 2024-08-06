package edu.java.translator.exceptions;

public class ClientBadRequestException extends ClientException {

    public ClientBadRequestException(String message, String name, int code) {
        super(message, name, code);
    }
}
