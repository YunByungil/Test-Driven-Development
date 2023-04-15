package development.tdd.chap02;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s) {

        if (s == null || s.isEmpty()) {
            return PasswordStrength.INVALID;
        }

        int metCount = 0;

        boolean lengthEnough = s.length() >= 8;
        if (lengthEnough) {
            metCount++;
        }

        boolean containsNum = meetsContainingNumberCriteria(s);
        if (containsNum) {
            metCount++;
        }

        boolean containsUpp = meetsContainingUppercaseCriteria(s);
        if (containsUpp) {
            metCount++;
        }

        if (metCount == 1) {
            return PasswordStrength.WEAK;
        }
        if (metCount == 2) {
            return PasswordStrength.NORMAL;
        }


        return PasswordStrength.STRONG;
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }
}
