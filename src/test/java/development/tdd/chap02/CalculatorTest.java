package development.tdd.chap02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    @DisplayName("덧셈 기능 테스트")
    @Test
    void plus() {
        int sum = Calculator.plus(1, 2);
        assertThat(3).isEqualTo(sum);
        assertThat(5).isEqualTo(Calculator.plus(4, 1));
    }
}
