package gg.jte.generated.ondemand.urls;
import hexlet.code.util.NamedRoutes;
public final class JtebuildGenerated {
	public static final String JTE_NAME = "urls/build.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,4,4,5,5,5,5,5,5,5,5,5,18,18,18,18,18,18,18,18};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <form");
				var __jte_html_attribute_0 = NamedRoutes.urlsPath();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"post\">\n        <div class=\"row\">\n            <div class=\"col\">\n                <div class=\"form-floating\">\n                    <input id=\"url-input\" autofocus type=\"text\" required aria-label=\"url\" placeholder=\"Ссылка\" name=\"url\" class=\"form-control w-100\">\n                    <label for=\"url-input\">Ссылка</label>\n                </div>\n            </div>\n            <div class=\"col-auto\">\n                <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\n            </div>\n        </div>\n    </form>\n");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
