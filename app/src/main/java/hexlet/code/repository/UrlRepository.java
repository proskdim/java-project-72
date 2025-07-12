package hexlet.code.repository;

import hexlet.code.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UrlRepository extends BaseRepository {
    public static final String TABLE_NAME = "urls";
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlRepository.class);

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM %s WHERE id = ?".formatted(TABLE_NAME);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            LOGGER.atDebug().log(statement.toString());
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var entity = new Url(name);

                entity.setId(id);
                entity.setCreatedAt(createdAt);

                return Optional.of(entity);
            }

            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        try (
                var connection = dataSource.getConnection();
                var statement = connection.createStatement()
        ) {
            var sql = "SELECT * FROM %s".formatted(TABLE_NAME);
            statement.executeQuery(sql);
            var resultSet = statement.getResultSet();
            var entities = new ArrayList<Url>();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                var entity = new Url(name);
                entity.setId(id);
                entity.setCreatedAt(createdAt);

                entities.add(entity);
            }

            return entities;
        }
    }

    public static void insert(Url entity) throws SQLException {
        var sql = "INSERT INTO %s (name, created_at) VALUES (?, NOW())".formatted(TABLE_NAME);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getName());
            LOGGER.atDebug().log(statement.toString());
            statement.executeUpdate();

            var createdAt = LocalDateTime.now();
            var generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                var id = generatedKeys.getLong("id");
                entity.setId(id);
                entity.setCreatedAt(createdAt);
            }
        }
    }
}
