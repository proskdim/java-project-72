package hexlet.code.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

public final class DataSourceProvider {
    private final String schemaName = "schema.sql";

    public HikariDataSource initialize(String databaseUrl) throws IOException, SQLException {
        var dataSource = getDataSource(databaseUrl);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.createStatement()
        ) {
            statement.execute(readResourceFile(schemaName));
        }

        return dataSource;
    }

    private static HikariDataSource getDataSource(String jdbcUrl) {
        var hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(jdbcUrl);

        if (jdbcUrl.startsWith("jdbc:h2")) {
            hikariConfig.setDriverClassName("org.h2.Driver");
        } else if (jdbcUrl.startsWith("jdbc:postgresql")) {
            hikariConfig.setDriverClassName("org.postgresql.Driver");
        } else {
            throw new RuntimeException("Unknown database driver");
        }

        return new HikariDataSource(hikariConfig);
    }


    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
