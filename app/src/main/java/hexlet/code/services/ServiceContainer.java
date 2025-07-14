package hexlet.code.services;

import hexlet.code.controller.HomeController;
import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.services.http.UnirestHttpClient;

public final class ServiceContainer {
    public HomeController homeController() {
        return new HomeController();
    }

    public UrlCheckController urlCheckController() {
        return new UrlCheckController(urlCheckService());
    }

    public UrlController urlController() {
        return new UrlController(
                urlRepository(),
                urlCheckRepository()
        );
    }

    public UrlCheckService urlCheckService() {
        return new UrlCheckService(
                urlRepository(),
                urlCheckRepository(),
                unirestHttpClient()
        );
    }

    private UrlRepository urlRepository() {
        return new UrlRepository();
    }

    private UrlCheckRepository urlCheckRepository() {
        return new UrlCheckRepository();
    }

    private UnirestHttpClient unirestHttpClient() {
        return new UnirestHttpClient();
    }
}
