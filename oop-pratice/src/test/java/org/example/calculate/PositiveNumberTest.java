package org.example.calculate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

class PositiveNumberTest {
    @ParameterizedTest
    @ValueSource(doubles = {0.-1})
    void createTest(double value) {
        assertThatCode(()->new PositiveNumber((int) value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0또는 음수를 전달할 수 없습니다.");
    }
}