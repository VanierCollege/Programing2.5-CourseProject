package ca.shahrestani.ali.edu.vanier.tool;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class Util {
    public static final String TIME_ZONE = "America/Montreal";

    private Util() {}

    /**
     * Get an element from an array, negative indexes are allowed
     *
     * @param arr the array
     * @param idx the index
     * @param def the default value if the index is not valid
     * @return the element at the index or the default
     * @param <E> the type of element in the array
     */
    public static <E> E elementAt(E[] arr, int idx, E def) {
        if (idx < 0) {
            idx = arr.length - -idx;
        }

        try {
            return arr[idx];
        } catch (ArrayIndexOutOfBoundsException aioobE) {
            return def;
        }
    }

    /**
     * Round a given number to a fixed amount of decimal places
     * <p>
     * <a href="https://stackoverflow.com/a/2808648/11242524">credit</a>
     *
     * @param value the input number
     * @param places the number of decimal places to keep
     * @return the rounded number
     */
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    /**
     * Generate a random string of characters
     *
     * @param count the number of characters to include (length)
     * @param lowerCase whether to include lower case letters or not
     * @param upperCase whether to include upper case letters or not
     * @param numerical whether to include numbers or not
     * @return the randomly generated string
     */
    @SuppressWarnings("deprecation")
    public static String generateID(int count, boolean lowerCase, boolean upperCase, boolean numerical) {
        String chars = "";

        if (lowerCase) {
            chars += "abcdefghijkmnopqrstuvwxyz";
        }
        if (upperCase) {
            chars += "ABCDEFGHJKLMNPQRSTUVWXYZ";
        }
        if (numerical) {
            chars += "23456789";
        }

        return RandomStringUtils.random(count, chars);
    }

    /**
     * Pad a string with zeros on the right to ensure a minimum length
     *
     * @param count the total length of the padded string
     * @param toPad the string to pad
     * @return the padded result
     */
    public static String zerosPadRight(int count, String toPad) {
        return toPad + ("0".repeat(count).substring(toPad.length()));
    }

    /**
     * Pad a number with zeros on the right to ensure a minimum length
     *
     * @param count the total length of the padded string
     * @param toPadn the number to pad
     * @return the padded result
     */
    public static String zerosPadRight(int count, int toPadn) {
        String toPad = String.valueOf(toPadn);
        return toPad + ("0".repeat(count).substring(toPad.length()));
    }

    /**
     * Pad a string with zeros on the left to ensure a minimum length
     *
     * @param count the total length of the padded string
     * @param toPad the string to pad
     * @return the padded result
     */
    public static String zerosPadLeft(int count, String toPad) {
        return ("0".repeat(count).substring(0, count - toPad.length())) + toPad;
    }

    /**
     * Pad a number with zeros on the left to ensure a minimum length
     *
     * @param count the total length of the padded string
     * @param toPadn the number to pad
     * @return the padded result
     */
    public static String zerosPadLeft(int count, int toPadn) {
        String toPad = String.valueOf(toPadn);
        return ("0".repeat(count).substring(0, count - toPad.length())) + toPad;
    }

    /**
     * Get the current time
     *
     * @return the current date-time
     */
    public static ZonedDateTime getNow() {
        return ZonedDateTime.now(ZoneId.of(TIME_ZONE));
    }

    /**
     * Get the numerical value of the current day of the month
     *
     * @return the current day number
     */
    public static int getDayOfMonth() {
        return getNow().getDayOfMonth();
    }

    /**
     * Convert a datetime object to a human-readable format
     *
     * @param zdt the time to parse
     * @return a string representation of the date time
     */
    public static String humanTime(ZonedDateTime zdt) {
        if (zdt == null) {
            return "null";
        }

        return DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm a").format(zdt);
    }
}
