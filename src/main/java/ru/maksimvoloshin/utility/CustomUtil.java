package ru.maksimvoloshin.utility;

import java.util.Arrays;
import java.util.Objects;

/**
 * Simple utility class for project
 */
public final class CustomUtil {
    private CustomUtil() {
    }

    /**
     * Checks if any arguments is null
     * @param vars argurgemts
     * @return true if anyone is null
     */
    public static boolean ifAnyNull(Object... vars) {
        return Arrays.stream(vars).anyMatch(Objects::isNull);
    }
}
