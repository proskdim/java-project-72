package hexlet.code.services.containers;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

public final class RepositoryContainer implements RepositoryContainerInterface {
    public UrlRepository urlRepository() {
        return new UrlRepository();
    }

    public UrlCheckRepository urlCheckRepository() {
        return new UrlCheckRepository();
    }
}
