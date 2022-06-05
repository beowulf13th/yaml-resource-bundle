package ru.maksimvoloshin.utility;

import java.util.Arrays;
import java.util.Objects;

public final class CustomUtil {
    private CustomUtil() {
    }

    public static boolean ifAnyNull(Object... vars) {
        return Arrays.stream(vars).anyMatch(Objects::isNull);
    }
}
