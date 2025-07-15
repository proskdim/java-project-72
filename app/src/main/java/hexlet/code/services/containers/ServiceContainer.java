package hexlet.code.services.containers;

import hexlet.code.services.UrlCheckService;
import hexlet.code.services.http.HttpClient;
import hexlet.code.services.http.UnirestHttpClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ServiceContainer implements ServiceContainerInterface {
    private final RepositoryContainerInterface repository;

    public UrlCheckService urlCheckService() {
        return new UrlCheckService(
                repository.urlRepository(),
                repository.urlCheckRepository(),
                unirestHttpClient()
        );
    }

    private HttpClient unirestHttpClient() {
        return new UnirestHttpClient();
    }
}
