package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class UrlCheckRepository extends BaseRepository {
    private static final String TABLE_NAME = "url_checks";

    public static void insert(UrlCheck entity) throws SQLException {
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
            log.debug(statement.toString());
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

    public static List<UrlCheck> findChecksByUrlId(Long urlId) throws SQLException {
        var sql = """
                SELECT *
                FROM %s
                WHERE url_id = ?
                """.formatted(TABLE_NAME);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, urlId);
            log.debug(statement.toString());
            var resultSet = statement.executeQuery();
            var entities = new ArrayList<UrlCheck>();

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

                entities.add(entity);
            }

            return entities;
        }
    }

    public static Map<Long, UrlCheck> getLastChecks() throws SQLException {
        var sql = "SELECT DISTINCT ON (url_id) * FROM url_checks ORDER BY url_id DESC, id DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new HashMap<Long, UrlCheck>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var urlId = resultSet.getLong("url_id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
                urlCheck.setId(id);
                urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
                result.put(urlId, urlCheck);
            }
            return result;
        }
    }

    public static void removeAll() throws SQLException {
        var sql = "TRUNCATE TABLE " + TABLE_NAME;

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
        }
    }
}
