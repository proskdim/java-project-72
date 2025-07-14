package hexlet.code.services.http;

import hexlet.code.model.response.HttpResponse;

public interface HttpClient {
    HttpResponse get(String url);
}