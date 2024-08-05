package edu.java.translator.controllers;

import edu.java.translator.dtos.Language;
import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/translator")
@Controller
@Slf4j
public class TranslationController {

    private final TranslationService service;

    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView show() {
        ModelAndView mav = new ModelAndView("translator");
        List<Language> languages = service.getSupportedLanguages();
        mav.addObject("languages", languages);
        mav.addObject("request", new TranslationRequest());
        return mav;
    }

    @PostMapping
    public ModelAndView translate(
            @ModelAttribute("request") TranslationRequest request,
            HttpServletRequest httpRequest) {
        ModelAndView mav = new ModelAndView("translator");
        request.setUserAddr(httpRequest.getRemoteAddr());
        mav.addObject("response", service.translate(request));
        return mav;
    }
}
