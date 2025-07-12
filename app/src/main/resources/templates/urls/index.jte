@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlsPage
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <h1>Сайты</h1>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1 small">ID</th>
                <th class="small">Имя</th>
                <th class="col-2 small">Последняя проверка</th>
                <th class="col-1 small">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <td>${url.getId()}</td>
                    <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                    <td></td>
                    <td></td>
                </tr>
            @endfor
            </tbody>
        </table>
`
)