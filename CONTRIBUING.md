Atualizações recentes que implementei no projeto:

- Ajustei o modelo `Usuario` no backend para incluir `id` autogerado e corrigir o mapeamento JPA.
- Reescrevi o `UsuarioService` e o `UsuarioController` para retornarem respostas JSON padronizadas com token JWT e dados do usuário.
- Criei os DTOs `LoginResponse` e `UsuarioResponse`, removendo o antigo `JwtResponse`.
- Configurei o CORS para aceitar requisições dos hosts usados no front (`127.0.0.1`, `localhost` porta 5500 e 3000).
- Adicionei `SecurityConfig` do Spring Security liberando as rotas públicas da API.
- Atualizei `script.js` no frontend para consumir a API, tratar erros e salvar token/dados do usuário no `localStorage`.
- Validei a compilação do backend com `mvn -q -DskipTests compile`.
