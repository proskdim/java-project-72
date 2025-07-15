package hexlet.code.services.containers;

import hexlet.code.services.UrlCheckService;
import hexlet.code.services.http.HttpClient;
import hexlet.code.services.http.UnirestDummyHttpClient;

public final class ServiceDummyContainer implements ServiceContainerInterface {
    @Override
    public UrlCheckService urlCheckService() {
        return new UrlCheckService(
                repository().urlRepository(),
                repository().urlCheckRepository(),
                unirestHttpClient()
        );
    }

    private RepositoryContainerInterface repository() {
        return new RepositoryContainer();
    }

    private HttpClient unirestHttpClient() {
        return new UnirestDummyHttpClient();
    }
}
