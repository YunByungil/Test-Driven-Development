package development.tdd.chap03;

import java.time.LocalDate;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {
        if (payData.getFirstBillingDate() != null) {
            // 첫 납부일과 이후 납부일의 일자가 다른 경우를 처리하기 위한 코드

            LocalDate candidateExp = payData.getBillingDate().plusMonths(1); // 후보 만료일 구함
            if (payData.getFirstBillingDate().getDayOfMonth() != candidateExp.getDayOfMonth()) { // 첫 납부일의 일자와 후보 만료일의 일자가 다르면
                return candidateExp.withDayOfMonth(payData.getFirstBillingDate().getDayOfMonth()); // 첫 납부일의 일자를 후보 만료일의 일자로 사용
            }
        }

        return payData.getBillingDate().plusMonths(1);
    }
}
