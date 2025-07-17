package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
public class BasePage {
    private String flash;
    private String flashType;

    public String formatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }

        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(timestamp);
    }
}
