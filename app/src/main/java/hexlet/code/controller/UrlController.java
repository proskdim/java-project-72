package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import lombok.AllArgsConstructor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Comparator;

import static io.javalin.rendering.template.TemplateUtil.model;

@AllArgsConstructor
public final class UrlController extends ApplicationController {
    private final UrlRepository urlRepository;
    private final UrlCheckRepository urlCheckRepository;

    public void index(Context ctx) throws SQLException {
        var urls = urlRepository.getEntities().stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(Url::getId).reversed()))
                .toList();

        var ids = urls.stream().map(Url::getId).toList();
        var checks = urlCheckRepository.findLastChecksByIds(ids);

        var page = new UrlsPage(urls, checks);

        page.setFlash(ctx.consumeSessionAttribute("flash"));

        ctx.render("urls/index.jte", model("page", page));
    }

    public void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(0L);

        var url = urlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        var checks = urlCheckRepository.findChecksByUrlId(id)
                .stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(UrlCheck::getCreatedAt).reversed()))
                .toList();

        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));

        ctx.render("urls/show.jte", model("page", page));
    }

    public void create(Context ctx) throws SQLException {
        var url = ctx.formParamAsClass("url", String.class).get().trim();

        try {
            var uri = new URI(url).toURL().toString();

            if (urlRepository.findByName(uri).isEmpty()) {
                urlRepository.insert(new Url(uri.toString()));
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
