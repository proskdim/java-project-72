package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class BasePage {
    private String flash;
    private String flashType;

    public String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
