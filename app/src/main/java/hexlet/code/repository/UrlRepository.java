package hexlet.code.repository;

import hexlet.code.model.Url;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public final class UrlRepository extends BaseRepository {
    public static final String TABLE_NAME = "urls";

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM %s WHERE id = ?".formatted(TABLE_NAME);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            log.debug(statement.toString());
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

    public static Optional<Url> findByName(String name) throws SQLException {
        if (name == null) {
            return Optional.empty();
        }

        var sql = "SELECT * FROM %s WHERE name = ?".formatted(TABLE_NAME);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, name);
            log.debug(statement.toString());
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                var id = resultSet.getLong("id");
                var urlName = resultSet.getString("name");
                var entity = new Url(urlName);

                entity.setId(id);

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
            log.debug(statement.toString());
            statement.executeUpdate();

            var timeStamp = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
            var generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                var id = generatedKeys.getLong("id");
                entity.setId(id);
                entity.setCreatedAt(timeStamp);
            }
        }
    }

    public static void removeAll() throws SQLException {
        var sql = "DELETE FROM " + TABLE_NAME;

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
        }
    }
}
