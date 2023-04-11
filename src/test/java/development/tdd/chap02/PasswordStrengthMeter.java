package development.tdd.chap02;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s) {
        return PasswordStrength.NORMAL; // 두 번째 테스트만 통과하게 됨
    }
}
