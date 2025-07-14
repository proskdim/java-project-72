package hexlet.code.model.response;

public interface HttpResponse {
    int getStatus();
    String getBody();
}