package edu.java.translator;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

    @Test
    public void test() {
        URI languages = URI.create("languages");
        System.out.println(languages);
    }
}
