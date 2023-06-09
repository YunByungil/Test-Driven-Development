package development.tdd.chap03;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

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

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 4, 1));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 5, 5))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 6, 5));
    }

    /*
    예외 상황 처리, 단순히 한 달 추가로 끝나지 않는 상황이 존재한다.
    - 납부일이 2019-01-31이고 납부액이 1만 원이면 만료일은 2019-02-28이다.
    - 납부일이 2019-05-31이고 납부액이 1만 원이면 만료일은 2019-06-30이다.
    - 납부일이 2020-01-31이고 납부액이 1만 원이면 만료일은 2020-02-29이다.
     */
    @DisplayName("납부일과_한달_뒤_일자가_같지_않음")
    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 4, 1)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 5, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 6, 30)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 2, 29)
        );
    }

    /*
    다음 테스트 선택: 다시 예외 상황
    - 2만 원을 지불하면 만료일이 두 달 뒤가 된다.
    - 3만 원을 지불하면 만료일이 세 달 뒤가 된다.

    - 첫 납부일이 2019-01-31이고 만료되는 2019-02-28에 1만 원을 납부하면 다음 만료일은 2019-03-31이다.
    - 첫 납부일이 2019-01-30이고 만료되는 2019-02-28에 1만 원을 납부하면 다음 만료일은 2019-03-30이다.
    - 첫 납부일이 2019-05-31이고 만료되는 2019-06-30에 1만 원을 납부하면 다음 만료일은 2019-07-31이다.
     */
    @DisplayName("첫 납부일 일자와 만료일 납부일 일자가 같지 않을 때 만료일 계산 테스트")
    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2019, 3, 31));

        // 첫 납부일이 2019-01-30이고 만료되는 2019-02-28에 1만 원을 납부하면 다음 만료일은 2019-03-30이다.
        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 30))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2019, 3, 30));

        // 첫 납부일이 2019-05-31이고 만료되는 2019-06-30에 1만 원을 납부하면 다음 만료일은 2019-07-31이다.
        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 5, 31))
                .billingDate(LocalDate.of(2019, 6, 30))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2019, 7, 31));
    }

    /*
    다음 테스트 선택: 쉬운 테스트
    - 2만 원을 지불하면 만료일이 두 달 뒤가 된다.
    - 3만 원을 지불하면 만료일이 석 달 뒤가 된다.
     */
    @DisplayName("이만원 이상 납부하면 비례해서 만료일 계산")
    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(20_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2019, 5, 1));

        // 3만 원을 납부하는 사례 추가
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019, 6, 1)
        );
    }

    /*
    예외 상황 테스트 추가
    - 첫 납부일이 2019-01-31이고 만료되는 2019-02-28에 2만 원을 납부하면 다음 만료일은 2019-04-30이다.
     */
    @DisplayName("첫 납부일과 만료일 일자가 다를 때 이만 원 이상 납부 사례 추가")
    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 1, 31))
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019, 4, 30)
        );
    }
    
    /*
    10개월 요금을 납부하면 1년 제공
     */
    @DisplayName("10만 원을 납부하면 1년 서비스를 제공하는 테스트")
    @Test
    void 십만원을_납부하면_1년_제공() {
        assertExpiryDate(
            PayData.builder()
                    .billingDate(LocalDate.of(2019, 1, 28))
                    .payAmount(100_000)
                    .build(),
            LocalDate.of(2020, 1, 28)
        );
        // 2019년 1월 28일에 10만 원을 납부하면 2020년 1월 28일이어야 하는데 2019년 11월 28일을 만료일로 계산해서 테스트 실패
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);

        assertThat(realExpiryDate).isEqualTo(expectedExpiryDate);
    }
}
