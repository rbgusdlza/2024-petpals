package rbgusdlza.petpals.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RandomAuthCodeGenerator {

    private static final int LOWER_BOUND = 100000;
    private static final int UPPER_BOUND = 1000000;

    public static String getAuthCode() {
        return String.valueOf(getRandomNumbers());
    }

    private static int getRandomNumbers() {
        return new SecureRandom().nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }
}
