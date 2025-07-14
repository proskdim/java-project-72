package hexlet.code.controller;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.services.UrlCheckService;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UrlCheckController {
    private static final Logger logger = LoggerFactory.getLogger(UrlCheckController.class);

    public static void create(Context ctx) {
        var urlId = ctx.formParamAsClass("urlId", Long.class).get();

        var checkService = new UrlCheckService(
                new UrlRepository(),
                new UrlCheckRepository()
        );

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
