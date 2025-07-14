package gg.jte.generated.ondemand.urls;
import hexlet.code.util.NamedRoutes;
import hexlet.code.dto.urls.UrlsPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,7,7,19,19,21,21,21,22,22,22,22,22,22,22,22,22,22,22,22,23,23,26,26,27,27,27,28,28,28,29,29,31,31,34,34,34,34,34,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Сайты</h1>\n    <table class=\"table table-bordered table-hover mt-3\">\n        <thead>\n        <tr>\n            <th class=\"col-1 small\">ID</th>\n            <th class=\"small\">Имя</th>\n            <th class=\"col-2 small\">Последняя проверка</th>\n            <th class=\"col-1 small\">Код ответа</th>\n        </tr>\n        </thead>\n        <tbody>\n        ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n            <tr>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\n                <td><a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a></td>\n                ");
					if (page.getChecks().isEmpty()) {
						jteOutput.writeContent("\n                    <td></td>\n                    <td></td>\n                ");
					} else {
						jteOutput.writeContent("\n                    <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(page.formatDate(page.getChecks().get(url.getId()).getCreatedAt()));
						jteOutput.writeContent("</td>\n                    <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(page.getChecks().get(url.getId()).getStatusCode());
						jteOutput.writeContent("</td>\n                ");
					}
					jteOutput.writeContent("\n            </tr>\n        ");
				}
				jteOutput.writeContent("\n        </tbody>\n    </table>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
