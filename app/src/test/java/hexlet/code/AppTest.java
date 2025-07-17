package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.services.AppService;
import hexlet.code.services.Configurator;
import hexlet.code.services.DataSourceProvider;
import hexlet.code.services.Router;
import hexlet.code.util.Environment;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AppTest {
    private Javalin app;
    private static MockWebServer mockServer;

    @BeforeAll
    public static void setUpMockServer() throws Exception {
        mockServer = new MockWebServer();

        var body = readFixture("test.html");
        var response = new MockResponse()
                .setBody(body)
                .setResponseCode(200);

        mockServer.enqueue(response);
        mockServer.start();
    }

    @AfterAll
    public static void closeMockServer() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        app = new AppService(
                new Router(),
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
            var response = client.get(NamedRoutes.rootPath());
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var urlName = "https://example.com/";
            var url = new Url(urlName);
            UrlRepository.insert(url);

            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains(urlName));
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath("100500"));
            assertEquals(404, response.code());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://example.com/", "https://example.com:8080/", "https://example.com/path"})
    public void testCreateUrl(String url) {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + url;
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("https://example.com"));
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-url"})
    public void testCreateUrlWithInvalidUrl(String url) {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + url;
            var response = client.post(NamedRoutes.urlsPath(), requestBody);

            assertEquals(200, response.code());
            assertFalse(response.body().string().contains(url));
        });
    }

    @Test
    public void testCheckUrl() throws SQLException {
        var name = mockServer.url("/").toString();
        var url = new Url(name);
        UrlRepository.insert(url);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "id=" + url.getId();
            var response = client.post(NamedRoutes.urlCheckPath(url.getId()), requestBody);
            assertEquals(200, response.code());

            var checks = UrlCheckRepository.findChecksByUrlId(url.getId());

            assertFalse(checks.isEmpty());

            var check = checks.get(0);

            assertEquals(200, check.getStatusCode());
            assertEquals("Example Domain", check.getTitle());
            assertEquals("Header 1", check.getH1());
            assertEquals("test content", check.getDescription());
            assertNotNull(check.getCreatedAt());
        });
    }

    @Test
    public void testCreateCheckUrlWithInvalidUrlId() {
        JavalinTest.test(app, (server, client) -> {
            var id = 100L;
            var requestBody = "id=" + id;
            var response = client.post(NamedRoutes.urlCheckPath(id), requestBody);

            assertEquals(404, response.code());
        });
    }

    private static String readFixture(String fileName) throws Exception {
        Path filePath = getFixturePath(fileName);
        return Files.readString(filePath).trim();
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }
}
