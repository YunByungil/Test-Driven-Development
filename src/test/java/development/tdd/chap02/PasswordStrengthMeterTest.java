package development.tdd.chap02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordStrengthMeterTest {

    @DisplayName("테스트 메서드 생성")
    @Test
    void name() {
    }

    @DisplayName("암호가 모든 조건을 충족, 강도는 강함")
    @Test
    void meetsAllCriteria_Then_Strong() {
        PasswordStrengthMeter meter = new PasswordStrengthMeter();
        PasswordStrength result = meter.meter("ab12!@AB");
        assertThat(PasswordStrength.STRONG).isEqualTo(result);
        PasswordStrength result2 = meter.meter("abc1!Add");
        assertThat(PasswordStrength.STRONG).isEqualTo(result2);
    }
}
