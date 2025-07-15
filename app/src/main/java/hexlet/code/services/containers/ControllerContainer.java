package hexlet.code.services.containers;

import hexlet.code.controller.Controller;
import hexlet.code.controller.HomeController;
import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ControllerContainer implements ControllerContainerInterface {
    private final RepositoryContainerInterface repository;
    private final ServiceContainerInterface service;

    public Controller homeController() {
        return new HomeController();
    }

    public Controller urlCheckController() {
        return new UrlCheckController(
                service.urlCheckService()
        );
    }

    public Controller urlController() {
        return new UrlController(
                repository.urlRepository(),
                repository.urlCheckRepository()
        );
    }
}
