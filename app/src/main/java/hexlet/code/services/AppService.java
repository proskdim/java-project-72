package hexlet.code.services;

import hexlet.code.repository.BaseRepository;
import hexlet.code.util.Environment;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

@AllArgsConstructor
@Getter
@Slf4j
public final class AppService {
    private final Router router;
    private final Configurator configurator;
    private final Environment environment;
    private final DataSourceProvider dataSourceProvider;

    public Javalin getApp() throws SQLException, IOException {
        BaseRepository.dataSource = dataSourceProvider.initialize(environment.getDatabaseUrl());
        log.info("Database initialized");

        var app = Javalin.create(config -> configurator.configure(config));
        log.info("App configured");

        router.route(app);
        log.info("App routed");

        return app;
    }
}
