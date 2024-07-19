package rbgusdlza.petpals.global.util;

import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PasswordEncryptor {

    private static final String SALT = "kyuzzang";

    public static String encryptPasswordFrom(String password) {
        return Hashing.sha256()
                .hashString(password + SALT, StandardCharsets.UTF_8)
                .toString();
    }
}
