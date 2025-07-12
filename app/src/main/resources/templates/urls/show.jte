@import hexlet.code.dto.UrlPage
@param UrlPage page

@template.layout.page(
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
      </tbody>
    </table>
`
)