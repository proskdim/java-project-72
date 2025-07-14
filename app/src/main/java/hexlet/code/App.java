package hexlet.code;

import hexlet.code.services.*;
import hexlet.code.util.Environment;

import java.io.IOException;
import java.sql.SQLException;

public final class App {
    public static void main(String[] args) throws SQLException, IOException {
        var appService = new AppService(
                new Router(new ServiceContainer()),
                new Configurator(),
                new Environment(),
                new DataSourceProvider()
        );

        var app = appService.getApp();
        app.start(appService.getEnvironment().getPort());
    }
}
