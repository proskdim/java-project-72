package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public final class Url {
    private Long id;

    @ToString.Include
    private String name;
    private Timestamp createdAt;

    public Url(String urlName) {
        this.name = urlName;
    }
}
