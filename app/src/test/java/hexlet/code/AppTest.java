package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.services.AppService;
import hexlet.code.services.Configurator;
import hexlet.code.services.DataSourceProvider;
import hexlet.code.services.Router;
import hexlet.code.util.Environment;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class AppTest {
    private Javalin app;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        app = new AppService(
                new Router(),
                new Configurator(),
                new Environment(),
                new DataSourceProvider()
        ).getApp();

        UrlRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertEquals(response.code(), 200);
            assertTrue(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertEquals(response.code(), 200);
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var urlName = "https://example.com/";
            var url = new Url(urlName);
            UrlRepository.insert(url);

            var response = client.get("/urls/" + url.getId());
            assertEquals(response.code(), 200);
            assertTrue(response.body().string().contains(urlName));
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertEquals(response.code(), 404);
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://example.com/";
            var requestBody = "url=" + url;
            var response = client.post("/urls", requestBody);
            assertEquals(response.code(), 200);
            assertTrue(response.body().string().contains(url));
        });
    }

    @Test
    public void testCreateUrlWithInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var invalidUrl = "invalid-url";
            var requestBody = "url=" + invalidUrl;
            var response = client.post("/urls", requestBody);

            assertEquals(response.code(), 200);
            assertFalse(response.body().string().contains(invalidUrl));
        });
    }
}
