package hexlet.code.model.response;

public final class DummyHttpResponse implements HttpResponse {

    @Override
    public int getStatus() {
        return 200;
    }

    @Override
    public String getBody() {
        return "<html><head><title>Test</title></head><body><h1>Test</h1></body></html>";
    }
}
