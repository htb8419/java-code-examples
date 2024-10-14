package org.examples.func;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.BiFunction;

@Component
public class CaptchaVerifier implements BiFunction<String, String, Boolean> {
    private final CaptchaIdGenerator captchaIdGenerator;
    private final int DELAY_WINDOW = 1;

    public CaptchaVerifier(CaptchaIdGenerator captchaIdGenerator) {
        this.captchaIdGenerator = captchaIdGenerator;
    }

    @Override
    public Boolean apply(String captchaId, String responseCode) {
        if (!(StringUtils.hasText(captchaId) && StringUtils.hasText(responseCode))) {
            return false;
        }
        try {
            int responseInt = Integer.parseInt(responseCode);
            for (int i = DELAY_WINDOW; i >= 0; --i) {
                String nCaptchaId = captchaIdGenerator.generate(responseInt, i);
                if (nCaptchaId.equals(captchaId)) {
                    return true;
                }
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
