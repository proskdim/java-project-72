package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class UrlCheckRepository extends BaseRepository {
    private static final String TABLE_NAME = "url_checks";
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlCheckRepository.class);

    public void insert(UrlCheck entity) throws SQLException {
        var sql = """
            INSERT INTO %s (status_code, title, h1, description, url_id, created_at)
            VALUES (?, ?, ?, ?, ?, NOW())
            """.formatted(TABLE_NAME);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, entity.getStatusCode());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getH1());
            statement.setString(4, entity.getDescription());
            statement.setLong(5, entity.getUrlId());
            LOGGER.atDebug().log(statement.toString());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            var timeStamp = new Timestamp(System.currentTimeMillis());

            if (generatedKeys.next()) {
                var id = generatedKeys.getLong("id");
                entity.setId(id);
                entity.setCreatedAt(timeStamp);
            }
        }
    }

    public static Map<Long, UrlCheck> findLastChecksByIds(List<Long> urlIds) throws SQLException {
        if (urlIds.isEmpty()) {
            return new HashMap<>();
        }

        var sql = lastCheckSql(urlIds.size());

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql);
        ) {
            var index = 1;
            for (var urlId : urlIds) {
                statement.setLong(index, urlId);
                index += 1;
            }

            LOGGER.atDebug().log(statement.toString());
            var resultSet = statement.executeQuery();
            var entities = new HashMap<Long, UrlCheck>();

            while (resultSet.next()) {
                var entity = new UrlCheck(
                        resultSet.getInt("status_code"),
                        resultSet.getString("title"),
                        resultSet.getString("h1"),
                        resultSet.getString("description"),
                        resultSet.getLong("url_id")
                );

                entity.setId(resultSet.getLong("id"));
                entity.setCreatedAt(resultSet.getTimestamp("created_at"));
                entities.put(entity.getId(), entity);
            }

            return entities;
        }
    }

    private static String lastCheckSql(int size) {
        var placeholder = IntStream.range(0, size)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(","));

        return """
            WITH last_checks AS (
                SELECT DISTINCT ON (url_id)
                    url_id,
                    id AS check_id,
                    created_at
                FROM %s
                GROUP by url_id, check_id
                order by url_id, created_at DESC
            )

            SELECT *
            FROM %s
            WHERE id IN (
                SELECT check_id
                FROM last_checks
                WHERE url_id IN (%s)
            )""".formatted(TABLE_NAME, TABLE_NAME, placeholder);
    }
}
