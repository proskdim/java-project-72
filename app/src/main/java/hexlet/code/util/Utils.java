package hexlet.code.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Utils {
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
