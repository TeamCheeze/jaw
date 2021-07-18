package io.github.dolphin2410.jaw.util.core;

import io.github.dolphin2410.jaw.util.kotlin.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a version comparing system.
 *
 * 0.1 is smaller than 0.1.1
 *
 * 0.1 is equal to 0.01
 *
 * 0.1-beta is smaller than 0.1-release
 *
 * 0.1-final is smaller than 0.1
 *
 * @author Monun
 */
public class Version {
    static class InvalidVersionException extends RuntimeException {
        private final String invalidVersion;
        private final String reason;
        public InvalidVersionException(String invalidVersion, String reason) {
            super("Invalid version: " + invalidVersion + ": " + reason);
            this.invalidVersion = invalidVersion;
            this.reason = reason;
        }
        public String getInvalidVersion() {
            return invalidVersion;
        }
        public String getReason() {
            return reason;
        }
    }
    enum MavenVersionIdentifier {
        NONE(10),
        RELEASE(9),
        GA(8),
        FINAL(7),
        SNAPSHOT(6),
        RC(5),
        ZETA(4),
        BETA(3),
        ALPHA(2),
        DEV(1);
        private final int priority;
        MavenVersionIdentifier(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }
    private static MavenVersionIdentifier getIdentifier(String str) {
        try {
            if (str.equals("0")) {
                return MavenVersionIdentifier.NONE;
            } else {
                return MavenVersionIdentifier.valueOf(str.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.toString());
        }
    }
    @Nullable
    private static <T> T getOrNull(List<T> list, int index) {
        try {
            return list.get(index);
        } catch (Exception e) {
            return null;
        }
    }
    @NotNull
    private static <T> T last(List<T> list) {
        return list.get(list.size() - 1);
    }
    private final String version;
    private void validateVersion(String version) {
        if (!version.matches("^\\d+(\\.\\d+)*(\\.[a-zA-Z]*)?$")){
            throw new InvalidVersionException(version, "Invalid version string");
        }
    }
    private static int max(int a, int b) {
        return Math.max(a, b);
    }
    private static <T> T ifNull(@Nullable T source, @NotNull T alternative) {
        return source == null ? alternative : source;
    }

    /**
     * Create a version from string
     * @param version The string version
     */
    public Version(String version) {
        this.version = version.replace("-", ".").toLowerCase();
        validateVersion(this.version);
    }

    /**
     * Get's the string version
     * @return The stringified version
     */
    public String getVersion() {
        validateVersion(version);
        return version;
    }
    private boolean isLastIndex(List<?> list, int i) {
        return i == list.size() - 1;
    }

    /**
     * Finds the smaller version
     * @param one What to compare
     * @param other What to compare
     * @return The smaller version.
     */
    @Nullable
    public static Version min(Version one, Version other) {
        Version max = max(one, other);
        if (max == null) {
            return null;
        } else if(max == one) {
            return other;
        } else {
            return one;
        }
    }
    /**
     * Finds the bigger version
     * @param other What to compare.
     * @param one What to compare.
     * @return The bigger version. Null if the same.
     */
    @Nullable
    public static Version max(Version one, Version other) {
        one.validateVersion(other.version);
        other.validateVersion(one.version);
        ArrayList<String> currentVersion = new ArrayList<>(Arrays.asList(one.version.split("\\.")));
        ArrayList<String> otherVersion = new ArrayList<>(Arrays.asList(other.version.split("\\.")));
        for (int i : new IntRange(0, max(currentVersion.size(), otherVersion.size()))) {
            String a = ifNull(getOrNull(currentVersion, i), "0");
            String b = ifNull(getOrNull(otherVersion, i), "0");

            if (i == max(currentVersion.size(), otherVersion.size() - 1) - 1 && (!Numbers.isValidInt(a) || !Numbers.isValidInt(b))) {
                if(getIdentifier(a).priority > getIdentifier(b).priority) {
                    return one;
                } else if (getIdentifier(a).priority < getIdentifier(b).priority) {
                    return other;
                } else {
                    return null;
                }
            } else {
                if (Numbers.parseDouble(a) > Numbers.parseDouble(b)) {
                    return one;
                } else if(Numbers.parseDouble(a) < Numbers.parseDouble(b)) {
                    return other;
                }
            }
        }
        return null;
    }
}
