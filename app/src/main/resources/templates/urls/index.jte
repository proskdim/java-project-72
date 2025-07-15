@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
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
                @if(page.getChecks().get(url.getId()) == null)
                    <td></td>
                    <td></td>
                @else
                    <td>${page.formatDate(page.getChecks().get(url.getId()).getCreatedAt())}</td>
                    <td>${page.getChecks().get(url.getId()).getStatusCode()}</td>
                @endif
            </tr>
        @endfor
        </tbody>
    </table>
`
)