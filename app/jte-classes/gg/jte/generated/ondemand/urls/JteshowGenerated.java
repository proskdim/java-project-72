package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.util.NamedRoutes;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,7,7,8,8,8,13,13,13,17,17,17,21,21,21,27,27,27,27,27,27,27,27,27,28,28,28,28,28,28,28,28,28,44,44,46,46,46,47,47,47,48,48,48,49,49,49,50,50,50,51,51,51,53,53,56,56,56,56,56,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n    <table class=\"table table-bordered table-hover mt-3\">\n        <tbody>\n        <tr>\n            <td>ID</td>\n            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n        </tr>\n        <tr>\n            <td>Имя</td>\n            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n        </tr>\n        <tr>\n            <td>Дата создания</td>\n            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.formatDate(page.getUrl().getCreatedAt()));
				jteOutput.writeContent("</td>\n        </tr>\n        </tbody>\n    </table>\n\n    <h2 class=\"mt-5\">Проверки</h2>\n    <form method=\"post\"");
				var __jte_html_attribute_0 = NamedRoutes.urlCheckPath(page.getUrl().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n        <input type=\"hidden\" name=\"urlId\"");
				var __jte_html_attribute_1 = page.getUrl().getId();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_1);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n        <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\n    </form>\n\n    <table class=\"table table-bordered table-hover mt-3\">\n        <thead>\n        <tr>\n            <th class=\"col-1\">ID</th>\n            <th class=\"col-1\">Код ответа</th>\n            <th>title</th>\n            <th>h1</th>\n            <th>description</th>\n            <th class=\"col-2\">Дата проверки</th>\n        </tr>\n        </thead>\n        <tbody>\n        ");
				for (var urlCheck : page.getChecks()) {
					jteOutput.writeContent("\n            <tr>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getId());
					jteOutput.writeContent("</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getStatusCode());
					jteOutput.writeContent("</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getTitle());
					jteOutput.writeContent("</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getH1());
					jteOutput.writeContent("</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getDescription());
					jteOutput.writeContent("</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(page.formatDate(urlCheck.getCreatedAt()));
					jteOutput.writeContent("</td>\n            </tr>\n        ");
				}
				jteOutput.writeContent("\n        </tbody>\n    </table>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
