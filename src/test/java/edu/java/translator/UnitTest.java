package edu.java.translator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

    @Test
    public void test() {
        //given
        int a = 1;
        int b = 2;

        //when
        int result = a + b;

        //then
        int expectedResult = 3;
        assertThat(result).isEqualTo(expectedResult);
    }
}
