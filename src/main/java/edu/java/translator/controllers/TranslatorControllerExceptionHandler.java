package edu.java.translator.controllers;

import edu.java.translator.dtos.ApiErrorResponse;
import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.exceptions.ApiClientErrorException;
import edu.java.translator.exceptions.ApiServerErrorException;
import edu.java.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class TranslatorControllerExceptionHandler {

    private final TranslationService service;

    public TranslatorControllerExceptionHandler(TranslationService service) {
        this.service = service;
    }

    @ExceptionHandler(ApiClientErrorException.class)
    public ModelAndView handleApiClientError(
            HttpServletRequest req,
            ApiClientErrorException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getDescription(),
                ex.getName(),
                ex.getCode()
        );
        return createModelAndView(Map.of("errorResponse", response));
    }

    @ExceptionHandler(ApiServerErrorException.class)
    public ModelAndView handleApiServerError(
            HttpServletRequest req,
            ApiServerErrorException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getDescription(),
                ex.getName(),
                ex.getCode()
        );
        return createModelAndView(Map.of("errorResponse", response));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValid(
            HttpServletRequest req,
            MethodArgumentNotValidException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                getViolatedFieldsDescription(ex),
                "MethodArgumentNotValidException",
                HttpStatus.BAD_REQUEST.value()
        );
        List<Language> languages = service.getSupportedLanguages();
        return createModelAndView(Map.of(
                "errorResponse", response,
                "languages", languages));
    }

    private ModelAndView createModelAndView(Map<String, ?> map) {
        ModelAndView mav = new ModelAndView("translator");
        mav.addAllObjects(map);
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
