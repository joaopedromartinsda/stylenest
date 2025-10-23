# StyleNest Backend (Docker)

## Build da imagem

```bash
docker build -t stylenest-backend .
```

## Execução local

```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/stylenest \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=senha \
  stylenest-backend
```

Variáveis opcionais:

- `SPRING_JPA_HIBERNATE_DDL_AUTO` (padrão `update`)
- `SPRING_JPA_SHOW_SQL` (padrão `true`)
- `SECURITY_JWT_SECRET`
- `SECURITY_JWT_EXPIRATION`

## Deploy no Render

1. Crie um Web Service com este repositório e escolha **Docker**.
2. Configure as variáveis de ambiente acima utilizando as credenciais do banco.
3. A porta exposta deve ser `8080`.
4. Caso utilize o PostgreSQL do Render, reutilize a URL fornecida.
