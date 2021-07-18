package io.github.dolphin2410.jaw.util.core;

import org.jetbrains.annotations.NotNull;

/**
 * Number utilities. This allows you to easily determine if the given string is a number, and parse the number value from the string.
 *
 * @author dolphin2410
 */
public class Numbers {
    public static Double parseDouble(@NotNull String strNum) {
        try {
            return Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.toString());
        }
    }
    public static int parseInt(@NotNull String strNum) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.toString());
        }
    }
    public static boolean isValidDouble(@NotNull String strNum) {
        try {
            Double.parseDouble(strNum);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidInt(@NotNull String strNum) {
        try {
            Integer.parseInt(strNum);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
