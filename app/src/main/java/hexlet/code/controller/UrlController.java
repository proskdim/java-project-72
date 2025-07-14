package hexlet.code.controller;

import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Comparator;

import static io.javalin.rendering.template.TemplateUtil.model;

public final class UrlController {
    private static final Logger logger = LoggerFactory.getLogger(UrlCheckController.class);

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities().stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(Url::getId).reversed()))
                .toList();

        var ids = urls.stream().map(Url::getId).toList();
        var checks = UrlCheckRepository.findLastChecksByIds(ids);

        var page = new UrlsPage(urls, checks);

        page.setFlash(ctx.consumeSessionAttribute("flash"));

        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(0L);

        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        var checks = UrlCheckRepository.findChecksByUrlId(id)
                .stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(UrlCheck::getCreatedAt).reversed()))
                .toList();

        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));

        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var url = ctx.formParamAsClass("url", String.class).get().trim();

        try {
            var uri = new URI(url).toURL().toString();

            if (UrlRepository.findByName(uri).isEmpty()) {
                UrlRepository.insert(new Url(uri.toString()));
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
            }

            ctx.redirect(NamedRoutes.urlsPath());
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }
}
