package hexlet.code.services.http;

import hexlet.code.model.response.DummyHttpResponse;
import hexlet.code.model.response.HttpResponse;

public final class UnirestDummyHttpClient implements HttpClient {
    @Override
    public HttpResponse get(String url) {
        return new DummyHttpResponse();
    }
}
