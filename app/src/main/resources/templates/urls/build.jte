@import hexlet.code.util.NamedRoutes

@template.layout.page(
content = @`
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
`
)