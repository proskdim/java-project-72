package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlController;
import hexlet.code.dto.BasePage;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.Env;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;
import static io.javalin.rendering.template.TemplateUtil.model;

public final class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final HikariDataSource DATA_SOURCE = getDataSource();

    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = getApp();

        app.start(Env.getPort());
    }

    private static Javalin getApp() throws SQLException, IOException {
        initSchema(DATA_SOURCE, "schema.sql");
        BaseRepository.dataSource = DATA_SOURCE;

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), ctx -> {
            var page = new BasePage();
            page.setFlash(ctx.consumeSessionAttribute("flash"));

            ctx.render("index.jte", model("page", page));
        });

        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlsPath(), UrlController::create);

        return app;
    }

    private static HikariDataSource getDataSource() {
        var hikariConfig = new HikariConfig();

        var jdbcUrl = Env.getDatabaseUrl();
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

    private static void initSchema(HikariDataSource dataSource, String schemaName) throws IOException, SQLException {
        var sql = readResourceFile(schemaName);
        LOGGER.info(sql);

        try (
                var connection = dataSource.getConnection();
                var statement = connection.createStatement()
        ) {
            statement.execute(sql);
        }
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static TemplateEngine createTemplateEngine() {
        var classLoader = App.class.getClassLoader();
        var codeResolver = new ResourceCodeResolver("templates", classLoader);
        var templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);

        return templateEngine;
    }
}
