@import hexlet.code.dto.urls.UrlPage
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
        <input type="hidden" name="id" value="${page.getUrl().getId()}">
        <button type="submit" class="btn btn-primary">Запустить проверку</button>
    </form>

    <table class="table table-bordered table-hover mt-3">
        <thead>
        <tr>
            <th class="col-1 small">ID</th>
            <th class="col-1 small">Код ответа</th>
            <th class="small">title</th>
            <th class="small">h1</th>
            <th class="small">description</th>
            <th class="col-2 small">Дата проверки</th>
        </tr>
        </thead>
        <tbody>
        @for(var urlCheck : page.getChecks())
            <tr>
                <td>${urlCheck.getId()}</td>
                <td>${urlCheck.getStatusCode()}</td>
                <td>${urlCheck.getTitle()}</td>
                <td>${urlCheck.getH1()}</td>
                <td>${urlCheck.getDescription()}</td>
                <td>${page.formatDate(urlCheck.getCreatedAt())}</td>
            </tr>
        @endfor
        </tbody>
    </table>
`
)