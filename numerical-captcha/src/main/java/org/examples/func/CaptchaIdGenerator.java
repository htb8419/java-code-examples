package org.examples.func;

import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.api.Clock;
import org.jboss.aerogear.security.otp.api.Hash;
import org.jboss.aerogear.security.otp.api.Hmac;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Function;

@Component
public class CaptchaIdGenerator implements Function<Integer, String> {
    final String secretKey;
    final Clock clock;

    public CaptchaIdGenerator(@Value("${captcha.secret-key}") String secretKey,
                              @Value("${captcha.interval:60s}") Duration interval) {
        this.secretKey = secretKey;
        this.clock = new Clock((int) interval.getSeconds());
    }

    @Override
    public String apply(Integer response) throws RuntimeException {
        return generate(response, 0);
    }

    public String generate(Integer response, int delayWindow) throws RuntimeException {
        try {
            byte[] secretBytes = secretKey.concat("-").concat(String.valueOf(response)).getBytes();
            Hmac hmac = new Hmac(Hash.SHA1, secretBytes,
                    clock.getCurrentInterval() - delayWindow);
            return Base32.encode(hmac.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
