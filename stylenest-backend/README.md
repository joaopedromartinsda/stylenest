# StyleNest Backend (Docker)

## Build da imagem

```bash
docker build -t stylenest-backend .
```

## Execucao local

```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/stylenest \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=senha \
  stylenest-backend
```

Variaveis opcionais:

- `SPRING_JPA_HIBERNATE_DDL_AUTO` (padrao `update`)
- `SPRING_JPA_SHOW_SQL` (padrao `true`)
- `SECURITY_JWT_SECRET`
- `SECURITY_JWT_EXPIRATION`

### Usando variaveis do Neon (PGHOST/PGUSER...)

As variaveis `PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`, `PGSSLMODE` e `PGCHANNELBINDING` tambem sao reconhecidas automaticamente. Exemplo:

```bash
docker run --rm -p 8080:8080 \
  -e PGHOST=ep-morning-sunset-aczotu32-pooler.sa-east-1.aws.neon.tech \
  -e PGDATABASE=neondb \
  -e PGUSER=neondb_owner \
  -e PGPASSWORD=******** \
  -e PGSSLMODE=require \
  -e PGCHANNELBINDING=require \
  stylenest-backend
```

## Deploy no Render

1. Crie um Web Service com este repositorio e escolha **Docker**.
2. No formulario de configuracao informe `Root Directory = stylenest-backend`, `Dockerfile Path = Dockerfile` e `Docker Build Context Directory = .`.
3. Configure as variaveis de ambiente (`SPRING_*` ou `PG*`) conforme o banco utilizado.
4. A porta exposta deve ser `8080`.
5. Caso utilize um Postgres gerenciado (Render, Neon, etc.), aproveite a URL/credenciais fornecidas pelo provedor.
