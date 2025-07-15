package hexlet.code.model.response;

public final class DummyHttpResponse implements HttpResponse {

    @Override
    public int getStatus() {
        return 200;
    }

    // тег <title> на странице.
    // тег <h1> на странице.
    // тег <meta name="description" content="..."> на странице.
    @Override
    public String getBody() {
        return "<html>"
             + "<head>"
             + "<title>Test</title>"
             + "<meta name='description' content='Test description'>"
             + "</head>"
             + "<body>"
             + "<h1>Test</h1>"
             + "</body>"
             + "</html>";
    }
}
