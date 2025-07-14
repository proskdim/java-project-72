package hexlet.code.services;

import hexlet.code.controller.HomeController;
import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Router {
    private final ServiceContainer container;

    public void route(Javalin app) {
        app.get(NamedRoutes.rootPath(), homeController()::index);

        app.get(NamedRoutes.urlsPath(), urlController()::index);
        app.get(NamedRoutes.urlPath("{id}"), urlController()::show);
        app.post(NamedRoutes.urlsPath(), urlController()::create);

        app.post(NamedRoutes.urlCheckPath("{id}"), checkController()::create);
    }

    private HomeController homeController() {
        return container.homeController();
    }

    private UrlController urlController() {
        return container.urlController();
    }

    private UrlCheckController checkController() {
        return container.urlCheckController();
    }
}
