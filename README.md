# BLogAnime

## Documentaçaõ de USO

> - *para o uso deve primeiro preencher o programa com dados*
> - *ACESSE --> localhost:8080/h2-console*
> - *jdbc:h2:mem:bloganime* `acesse isso na pagina do H2`


### USUARIOS

*Criar um Usuario*
- `localhost:8080/usuarios`
- Busca por ID `localhost:8080/usuarios/{id} --> troque o {id} por um número`
- Delete por ID `/usuarios/{id}`
```
{
  "nome": "Maria Souza",
  "email": "maria@gmail.com",
  "senha": "12345"
}
```
_________________________________________________________________________

### ANIMES

*Criar um Animes*
- `localhost:8080/animes`
- Busca por ID `localhost:8080/animes/{id} --> troque o {id} por um número`
- Delete por ID `/animes/{id}`
```
{
  "titulo": "Demon Slayer",
  "descricao": "Tanjiro luta contra demônios",
  "autor": "Koyoharu Gotouge",
  "anoLancamento": 2019,
  "genero": "Ação e Fantasia"
}
```
_________________________________________________________________________

### Paginção

- *GET* `http://localhost:8080/animes?page=1&size=3
` `page=1` representa a página atual, altere o
número depois da virgula para alternar entre as
páginas.
- Funciona tanto para `/usuarios` e `/animes`
- Caso aja a necessidade de aumentar as páginas
para o controller e altere o size,
```
@PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable)
```
- Altere o size
_________________________________________________________________________

