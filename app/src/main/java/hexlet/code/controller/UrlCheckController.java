package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

@Slf4j
public final class UrlCheckController {
    public static void create(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        try {
            var url = UrlRepository.find(id)
                    .orElseThrow(() -> new Exception("Страница не найдена"));

            var response = Unirest.get(url.getName()).asString();
            var doc = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var title = doc.title();
            var h1Element = doc.selectFirst("h1");
            var h1 = h1Element == null ? "" : h1Element.text();
            var descriptionElement = doc.selectFirst("meta[name=description]");
            var description = descriptionElement == null ? "" : descriptionElement.attr("content");

            var newUrlCheck = new UrlCheck(statusCode, title, h1, description, id);
            UrlCheckRepository.insert(newUrlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flash-type", "danger");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", e.getMessage());
            ctx.sessionAttribute("flash-type", "danger");
        }

        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
