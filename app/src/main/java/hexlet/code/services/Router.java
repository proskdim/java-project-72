package hexlet.code.services;

import hexlet.code.controller.Controller;
import hexlet.code.services.containers.ControllerContainer;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Router {
    private final ControllerContainer container;

    public void route(Javalin app) {
        // root
        app.get(NamedRoutes.rootPath(), homeController()::index);

        // urls
        app.get(NamedRoutes.urlsPath(), urlController()::index);
        app.get(NamedRoutes.urlPath("{id}"), urlController()::show);
        app.post(NamedRoutes.urlsPath(), urlController()::create);

        // checks
        app.post(NamedRoutes.urlCheckPath("{id}"), checkController()::create);
    }

    private Controller homeController() {
        return container.homeController();
    }

    private Controller urlController() {
        return container.urlController();
    }

    private Controller checkController() {
        return container.urlCheckController();
    }
}
