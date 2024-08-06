package edu.java.translator.controllers;

import edu.java.translator.dtos.ApiErrorResponse;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.exceptions.ClientBadRequestException;
import edu.java.translator.exceptions.ProviderInternalServerErrorException;
import edu.java.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@SuppressWarnings("MultipleStringLiterals")
public class TranslationControllerExceptionHandler {

    private final TranslationService service;

    public TranslationControllerExceptionHandler(TranslationService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientBadRequestException.class)
    public ModelAndView handleBadRequest(
            HttpServletRequest req,
            ClientBadRequestException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getMessage(),
                ex.getName(),
                ex.getCode()
        );
        return createModelAndView(Map.of("errorResponse", response));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProviderInternalServerErrorException.class)
    public ModelAndView handleInternalServerError(
            HttpServletRequest req,
            ProviderInternalServerErrorException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getMessage(),
                ex.getName(),
                ex.getCode()
        );
        return createModelAndView(Map.of("errorResponse", response));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValid(
            HttpServletRequest req,
            MethodArgumentNotValidException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                getViolatedFieldsDescription(ex),
                "MethodArgumentNotValidException",
                HttpStatus.BAD_REQUEST.value()
        );
        return createModelAndView(Map.of(
                "errorResponse", response,
                "languages", service.getSupportedLanguages()));
    }

    private ModelAndView createModelAndView(Map<String, ?> attributes) {
        ModelAndView mav = new ModelAndView("translator");
        mav.addAllObjects(attributes);
        mav.addObject("request", new TranslationRequest());
        return mav;
    }

    private String getViolatedFieldsDescription(MethodArgumentNotValidException ex) {
        List<String> violatedField = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(v -> ((FieldError) v).getField())
                .toList();
        return String.format("Invalid request params: %s", String.join(", ", violatedField));
    }
}
