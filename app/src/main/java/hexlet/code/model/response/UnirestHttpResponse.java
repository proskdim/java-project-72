package hexlet.code.model.response;

public final class UnirestHttpResponse implements HttpResponse {
    private final kong.unirest.HttpResponse<String> response;

    public UnirestHttpResponse(kong.unirest.HttpResponse<String> response) {
        this.response = response;
    }

    @Override
    public int getStatus() {
        return response.getStatus();
    }

    @Override
    public String getBody() {
        return response.getBody();
    }
}
