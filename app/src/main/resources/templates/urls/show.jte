@import hexlet.code.dto.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <h1>Сайт: ${page.getUrl().getName()}</h1>
    <table class="table table-bordered table-hover mt-3">
        <tbody>
        <tr>
            <td>ID</td>
            <td>${page.getUrl().getId()}</td>
        </tr>
        <tr>
            <td>Имя</td>
            <td>${page.getUrl().getName()}</td>
        </tr>
        <tr>
            <td>Дата создания</td>
            <td>${page.formatDate(page.getUrl().getCreatedAt())}</td>
        </tr>
        </tbody>
    </table>

    <h2 class="mt-5">Проверки</h2>
    <form method="post" action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}">
        <input type="hidden" name="urlId" value="${page.getUrl().getId()}">
        <button type="submit" class="btn btn-primary">Запустить проверку</button>
    </form>
`
)