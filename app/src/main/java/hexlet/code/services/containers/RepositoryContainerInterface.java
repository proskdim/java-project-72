package hexlet.code.services.containers;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

public interface RepositoryContainerInterface {
    UrlRepository urlRepository();
    UrlCheckRepository urlCheckRepository();
}
