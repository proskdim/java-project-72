package hexlet.code.services.containers;

import hexlet.code.controller.Controller;

public interface ControllerContainerInterface {
    Controller homeController();
    Controller urlController();
    Controller urlCheckController();
}
