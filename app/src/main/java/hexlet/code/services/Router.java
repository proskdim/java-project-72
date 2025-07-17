package hexlet.code.services;

import hexlet.code.controller.HomeController;
import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Router {
    public void route(Javalin app) {
        // root
        app.get(NamedRoutes.rootPath(), HomeController::index);

        // urls
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlsPath(), UrlController::create);

        // checks
        app.post(NamedRoutes.urlCheckPath("{id}"), UrlCheckController::create);
    }
}
