package edu.java.translator.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProviderException extends RuntimeException {

    private final String name;
    private final int code;

    public ProviderException(String message, String name, int code) {
        super(message);
        this.name = name;
        this.code = code;
    }
}
