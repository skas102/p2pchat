package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static String formatDateTime(LocalDateTime dt) {
        return dt.format(formatter);
    }
}
