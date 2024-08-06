package edu.java.translator.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class ClientException extends RuntimeException {

    private final String name;
    private final int code;

    public ClientException(String message, String name, int code) {
        super(message);
        this.name = name;
        this.code = code;
    }
}
