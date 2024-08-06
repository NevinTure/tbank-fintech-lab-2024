package edu.java.translator.exceptions;

public class ProviderInternalServerErrorException extends ProviderException {

    public ProviderInternalServerErrorException(String message, String name, int code) {
        super(message, name, code);
    }
}
