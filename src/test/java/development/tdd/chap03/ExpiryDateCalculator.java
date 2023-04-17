package development.tdd.chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonths = payData.getPayAmount() / 10_000;
        if (payData.getFirstBillingDate() != null) {
            // 첫 납부일과 이후 납부일의 일자가 다른 경우를 처리하기 위한 코드

            LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths); // 후보 만료일 구함
            if (payData.getFirstBillingDate().getDayOfMonth() != candidateExp.getDayOfMonth()) { // 첫 납부일의 일자와 후보 만료일의 일자가 다르면
                final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
                if (dayLenOfCandiMon < payData.getFirstBillingDate().getDayOfMonth()) {
                    return candidateExp.withDayOfMonth(dayLenOfCandiMon);
                    // 후보 만료일이 포함된 달의 일자 길이에 따른 만료일 계산 로직 추가
                }
                System.out.println("candidateExp = " + candidateExp);
                return candidateExp.withDayOfMonth(payData.getFirstBillingDate().getDayOfMonth()); // 첫 납부일의 일자를 후보 만료일의 일자로 사용
            }
        }

        return payData.getBillingDate().plusMonths(addedMonths);
    }
}
