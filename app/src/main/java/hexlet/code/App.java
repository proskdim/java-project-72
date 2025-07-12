package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.Env;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Name;
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
            ctx.render("urls/build.jte");
        });

        app.get(NamedRoutes.urlsPath(), ctx -> {
            var page = new UrlsPage(UrlRepository.getEntities());

            page.setFlash(ctx.consumeSessionAttribute("flash"));

            ctx.render("urls/index.jte", model("page", page));
        });

        app.get(NamedRoutes.urlPath("{id}"), ctx -> {
           var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(0L);
           var url = UrlRepository.find(id)
                   .orElseThrow(() -> new NotFoundResponse());

           var page = new UrlPage(url);
           ctx.render("urls/show.jte", model("page", page));
        });

        app.post(NamedRoutes.urlsPath(), ctx -> {
            var url = ctx.formParam("url");

            UrlRepository.insert(new Url(url));

            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        });

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
