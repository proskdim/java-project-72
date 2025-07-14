package hexlet.code.services;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;

@AllArgsConstructor
@Setter
@Getter
public final class UrlCheckService {
    private final UrlRepository urlRepository;
    private final UrlCheckRepository checkRepository;

    public UrlCheck check(Long urlId) throws Exception {
        var url = getUrl(urlId);
        var response = Unirest.get(url.getName()).asString();
        var statusCode = response.getStatus();
        var responseBody = Jsoup.parse(response.getBody());
        var result = buildUrlCheck(urlId, statusCode, responseBody);

        save(result);

        return result;
    }

    private Url getUrl(Long id) throws Exception {
        try {
            return urlRepository.find(id)
                    .orElseThrow(() -> new Exception("Страница не найдена"));
        } catch (SQLException e) {
            throw new Exception("Ошибка запроса данных");
        }
    }

    private UrlCheck buildUrlCheck(Long urlId, Integer statusCode, Document doc) {
        var h1Node = doc.selectFirst("h1");
        var descriptionNode = doc.selectFirst("head meta[name='description']");

        return new UrlCheck(
                statusCode,
                doc.title(),
                h1Node == null ? "" : h1Node.text(),
                descriptionNode == null ? "" : descriptionNode.attr("content"),
                urlId
        );
    }

    private void save(UrlCheck urlCheck) throws Exception {
        try {
            checkRepository.insert(urlCheck);
        } catch (SQLException e) {
            throw new Exception("Ошибка при добавлении проверки");
        }
    }
}
