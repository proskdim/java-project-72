package hexlet.code.services;

import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlController;
import hexlet.code.dto.BasePage;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;

import static io.javalin.rendering.template.TemplateUtil.model;

public final class Router {
    public void route(Javalin app) {
        app.get(NamedRoutes.rootPath(), ctx -> {
            var page = new BasePage();
            page.setFlash(ctx.consumeSessionAttribute("flash"));

            ctx.render("index.jte", model("page", page));
        });

        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.post(NamedRoutes.urlCheckPath("{id}"), UrlCheckController::create);
    }
}
