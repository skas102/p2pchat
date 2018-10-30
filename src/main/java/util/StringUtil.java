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

    public static byte[] hexToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
