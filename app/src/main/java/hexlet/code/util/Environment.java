package hexlet.code.util;

public final class Environment {
    public String getDatabaseUrl() {
        return fetchEnv("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

    public int getPort() {
        var port = fetchEnv("PORT", "8080");
        return Integer.valueOf(port);
    }

    private String fetchEnv(String key, String defaultValue) {
        if (isTestEnvironment()) {
            return defaultValue;
        } else {
            return System.getenv().getOrDefault(key, defaultValue);
        }
    }

    private boolean isTestEnvironment() {
        return "test".equals(System.getenv("APP_ENV"));
    }
}
