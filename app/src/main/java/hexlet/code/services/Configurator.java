package hexlet.code.services;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.App;
import io.javalin.config.JavalinConfig;
import io.javalin.rendering.template.JavalinJte;

public final class Configurator {
    public void configure(JavalinConfig config) {
        config.bundledPlugins.enableDevLogging();
        config.fileRenderer(new JavalinJte(createTemplateEngine()));
    }

    private static TemplateEngine createTemplateEngine() {
        var classLoader = App.class.getClassLoader();
        var codeResolver = new ResourceCodeResolver("templates", classLoader);
        var templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);

        return templateEngine;
    }
}
