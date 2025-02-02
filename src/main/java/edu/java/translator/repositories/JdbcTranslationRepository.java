package edu.java.translator.repositories;

import edu.java.translator.model.Translation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTranslationRepository implements TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Translation save(Translation translation) {
        Long id = jdbcTemplate
                .queryForObject(
                        "insert into translation (user_addr, source_lang, target_lang,"
                                + " origin_text, translated_text) "
                                + "values (?, ?, ?, ?, ?) returning id",
                        Long.class,
                        translation.getUserAddr(),
                        translation.getSourceLang(),
                        translation.getTargetLang(),
                        translation.getOriginText(),
                        translation.getTranslatedText());
        translation.setId(id);
        return translation;
    }
}
