package hexlet.code.util;

public final class Env {
    public static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

    public static int getPort() {
        var port = System.getenv().getOrDefault("PORT", "8080");
        return Integer.valueOf(port);
    }
}
