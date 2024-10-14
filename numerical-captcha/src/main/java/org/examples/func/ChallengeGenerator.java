package org.examples.func;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;

@Component
public class ChallengeGenerator implements Supplier<ChallengeGenerator.Challenge> {
    private final CaptchaIdGenerator captchaIdGenerator;

    public ChallengeGenerator(CaptchaIdGenerator captchaIdGenerator) {
        this.captchaIdGenerator = captchaIdGenerator;
    }

    @Override
    public Challenge get() {
        int n1 = randomNumber();
        int n2 = randomNumber();
        String operation = randomMathOperation();
        if (operation.equals("-") && n1 < n2) {
            int t = n1;
            n1 = n2;
            n2 = t;
        }
        String challenge = String.format("%d %s %d ?", n1, operation, n2);
        int response = calcResponse(n1, n2, operation);

        return new Challenge(Captcha.generateImage(challenge),
                captchaIdGenerator.apply(response));
    }

    private int calcResponse(int n1, int n2, String operation) {
        return switch (operation) {
            case "+" -> n1 + n2;
            case "*" -> n1 * n2;
            case "-" -> n1 - n2;
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        };
    }

    private String randomMathOperation() {
        int r = new Random().nextInt(3) + 1;
        return switch (r) {
            case 1 -> "*";
            case 2 -> "-";
            default -> "+";
        };
    }

    private int randomNumber() {
        return Integer.parseInt(RandomStringUtils.randomNumeric(1));
    }

    public record Challenge(byte[] captchaImage, String captchaId) {

    }
}
