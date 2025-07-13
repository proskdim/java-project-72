package hexlet.code.services;

import hexlet.code.repository.BaseRepository;
import hexlet.code.util.Environment;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@AllArgsConstructor
@Getter
public final class AppService {
    private final Router router;
    private final Configurator configurator;
    private final Environment environment;
    private final DataSourceProvider dataSourceProvider;

    private final Logger LOGGER = LoggerFactory.getLogger(AppService.class);

    public Javalin getApp() throws SQLException, IOException {
        BaseRepository.dataSource = dataSourceProvider.initialize(environment.getDatabaseUrl());
        LOGGER.info("Database initialized");

        var app = Javalin.create(config -> configurator.configure(config));
        LOGGER.info("App configured");

        router.route(app);
        LOGGER.info("App routed");

        return app;
    }
}
