package hexlet.code.controller;

import hexlet.code.services.UrlCheckService;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class UrlCheckController extends ApplicationController {
    private final UrlCheckService checkService;

    public void create(Context ctx) {
        var urlId = ctx.formParamAsClass("urlId", Long.class).get();

        try {
            var result = checkService.check(urlId);
            logger.info("Created check: {} | {}", result.getId(), result.getTitle());
            ctx.sessionAttribute("flash", "Страница успешно проверена");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Ошибка при проверке страницы: " + e.getMessage());
        }

        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
