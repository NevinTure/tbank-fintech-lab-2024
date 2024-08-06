package edu.java.translator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

    private String description;
    private String exceptionName;
    private int code;
}
