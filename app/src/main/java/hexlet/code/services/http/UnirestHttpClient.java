package hexlet.code.services.http;

import hexlet.code.model.response.HttpResponse;
import hexlet.code.model.response.UnirestHttpResponse;
import kong.unirest.Unirest;

public final class UnirestHttpClient implements HttpClient {
    @Override
    public HttpResponse get(String url) {
        var response = Unirest.get(url).asString();
        return new UnirestHttpResponse(response);
    }
}