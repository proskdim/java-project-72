@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.page(
page = page,
content = @`
    <h1 class="display-3 mb-0">Анализатор страниц</h1>
    <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>

    <form action="${NamedRoutes.urlsPath()}" method="post">
        <div class="row">
            <div class="col">
                <div class="form-floating">
                    <input id="url-input" autofocus type="text" required aria-label="url" placeholder="Ссылка" name="url" class="form-control w-100">
                    <label for="url-input">Ссылка</label>
                </div>
            </div>
            <div class="col-auto">
                <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-5">Проверить</button>
            </div>
        </div>
    </form>

    <p class ="mt-2 mb-0 text-muted">Пример: https://www.example.com</p>
`
)