package hexlet.code;

import hexlet.code.services.Router;
import hexlet.code.services.Configurator;
import hexlet.code.services.DataSourceProvider;
import hexlet.code.services.AppService;

import hexlet.code.util.Environment;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.SQLException;

public final class App {
    public static void main(String[] args) throws SQLException, IOException {
        var app = getApp();
        app.start(new Environment().getPort());
    }

    public static Javalin getApp() throws SQLException, IOException {
        return new AppService(
                new Router(),
                new Configurator(),
                new Environment(),
                new DataSourceProvider()
        ).getApp();
    }
}
