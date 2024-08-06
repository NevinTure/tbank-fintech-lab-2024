package edu.java.translator.controllers;

import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/translator")
@Controller
@Validated
public class TranslationController {

    private final TranslationService service;

    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView show() {
        return createModelAndView(Map.of());
    }

    @PostMapping
    public ModelAndView translate(
            @ModelAttribute("request") @Valid TranslationRequest request,
            HttpServletRequest httpRequest) {
        request.setUserAddr(Optional
                .ofNullable(httpRequest.getHeader("X-Forwarded-For"))
                .orElseGet(httpRequest::getRemoteAddr));
        return createModelAndView(Map.of("response", service.translate(request)));
    }

    private ModelAndView createModelAndView(Map<String, ?> attributes) {
        ModelAndView mav = new ModelAndView("translator");
        mav.addAllObjects(attributes);
        mav.addObject("request", new TranslationRequest());
        mav.addObject("languages", service.getSupportedLanguages());
        return mav;
    }
}
