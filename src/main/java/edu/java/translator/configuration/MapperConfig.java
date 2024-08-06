package edu.java.translator.configuration;

import edu.java.translator.dtos.TranslationRequest;
import edu.java.translator.model.Translation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<TranslationRequest, Translation> fromDto =
                mapper.createTypeMap(TranslationRequest.class, Translation.class);
        fromDto.addMapping(TranslationRequest::getSourceText, Translation::setOriginText);
        return mapper;
    }

}
