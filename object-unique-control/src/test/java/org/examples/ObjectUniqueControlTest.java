package org.examples;

import org.cryptacular.generator.TOTPGenerator;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Objects;

public class ObjectUniqueControlTest {
    private static final int HOUR_SECONDS = 3600;
    private static final int DAY_SECONDS = HOUR_SECONDS * 24;

    @Test
    public void createObjectUniqueIdTest() {
        Car mustang2014 = new Car("mustang", 1969);
        int hashCode = mustang2014.hashCode();
        //"Car853996129"
        String secretKey = Car.class.getSimpleName() + "-" + hashCode;
        TOTPGenerator totpGenerator = new TOTPGenerator();
        totpGenerator.setNumberOfDigits(9);
        totpGenerator.setTimeStep((int) Duration.ofDays(365).toSeconds());
        for (int i = 1; i <= 10; i++) {
            System.out.println(totpGenerator.generate(secretKey.getBytes()));
        }
      /*  Clock clock = new Clock(DAY_SECONDS);
        System.out.println(clock.getCurrentInterval());
        System.out.println(new Totp(secretKey, clock).now());*/

    }
    record Car(String name, int model) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Car car = (Car) o;
            return model == car.model && Objects.equals(name, car.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, model);
        }
    }
}


