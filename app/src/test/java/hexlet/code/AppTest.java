package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.services.AppService;
import hexlet.code.services.Configurator;
import hexlet.code.services.DataSourceProvider;
import hexlet.code.services.Router;
import hexlet.code.services.containers.ControllerContainer;
import hexlet.code.services.containers.RepositoryContainer;
import hexlet.code.services.containers.ServiceDummyContainer;
import hexlet.code.util.Environment;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class AppTest {
    private Javalin app;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        var controllerContainer = new ControllerContainer(
                new RepositoryContainer(),
                new ServiceDummyContainer()
        );

        app = new AppService(
                new Router(controllerContainer),
                new Configurator(),
                new Environment(),
                new DataSourceProvider()
        ).getApp();

        UrlCheckRepository.removeAll();
        UrlRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var urlName = "https://example.com/";
            var url = new Url(urlName);
            UrlRepository.insert(url);

            var response = client.get("/urls/" + url.getId());
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains(urlName));
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertEquals(404, response.code());
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://example.com/";
            var requestBody = "url=" + url;
            var response = client.post("/urls", requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains(url));
        });
    }

    @Test
    public void testCreateUrlWithInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var invalidUrl = "invalid-url";
            var requestBody = "url=" + invalidUrl;
            var response = client.post("/urls", requestBody);

            assertEquals(200, response.code());
            assertFalse(response.body().string().contains(invalidUrl));
        });
    }

    @Test
    public void testCreateCheckUrlWithValidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://example.com/";
            var entity = new Url(url);
            UrlRepository.insert(entity);

            var requestBody = "urlId=" + entity.getId();

            var response = client.post(NamedRoutes.urlCheckPath(entity.getId()), requestBody);
            assertEquals(200, response.code());

            var body = response.body().string();

            assertTrue(body.contains("<td>1</td>"));
            assertTrue(body.contains("<td>Test</td>"));
            assertTrue(body.contains("<td>200</td>"));
        });
    }

    @Test
    public void testCreateCheckUrlWithInvalidUrlId() {
        JavalinTest.test(app, (server, client) -> {
            var id = 100L;
            var requestBody = "urlId=" + id;
            var response = client.post(NamedRoutes.urlCheckPath(id), requestBody);

            assertEquals(404, response.code());
        });
    }

    // запустить проверку для сайта, когда на странице добавлено несколько сайтов
    @Test
    public void testCreateCheckUrlWithMultipleUrls() {
        JavalinTest.test(app, (server, client) -> {
            var urls = List.of(new Url("https://example1.com/"), new Url("https://example2.com/"));

            urls.forEach(url -> {
                try {
                    UrlRepository.insert(url);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            var entity = urls.get(0);
            var requestBody = "urlId=" + entity.getId();

            var response1 = client.post(NamedRoutes.urlCheckPath(entity.getId()), requestBody);
            assertEquals(200, response1.code());

            var response2 = client.get(NamedRoutes.urlsPath());
            assertEquals(200, response2.code());
            assertTrue(response2.body().string().contains("<td>200</td>"));
        });
    }
}
