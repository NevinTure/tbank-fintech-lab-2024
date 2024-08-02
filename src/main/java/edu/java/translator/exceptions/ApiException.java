package edu.java.translator.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class ApiException extends RuntimeException {

    private final String description;
    private final String name;
    private final int code;
}
