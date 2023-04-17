package development.tdd.chap03;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ExpiryDateCalculatorTest {
    /*
    매달 비용을 지불해야 사용할 수 있는 유료 서비스
    - 서비스를 사용하려면 매달 1만 원을 선불로 납부한다. 납부일 기준으로 한 달 뒤가 서비스 만료일이 된다.
    - 2개월 이상 요금을 납부할 수 있다.
    - 10만 원을 납부하면 서비스를 1년 제공한다.
     */

    /*
    구현하기 쉬운 것부터 먼저 테스트
    2019년 3월 1일에 1만 원을 납부하면, 만료일은 2019년 4월 1일이 된다.
     */
    @DisplayName("만원_납부하면_한달_뒤가_만료일이_됨")
    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() {
        LocalDate billingDate = LocalDate.of(2019, 3, 1);
        int payAmount = 10_000;

        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate expiryDate = cal.calculateExpiryDate(billingDate, payAmount);

        Assertions.assertThat(LocalDate.of(2019, 4, 1)).isEqualTo(expiryDate);
    }
}
