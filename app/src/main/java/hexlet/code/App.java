package hexlet.code;

import io.javalin.Javalin;

public final class App {
    public static void main(String[] args) {
        Javalin app = getApp();
        app.start("0.0.0.0", getPort());
    }

    private static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.result("Hello World");
        });

        return app;
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "8080");
        return Integer.valueOf(port);
    }
}
