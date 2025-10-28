package com.stylenest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "StyleNest API",
                description = "Serviços de autenticação e cadastro de usuários da StyleNest.",
                version = "1.0.0",
                contact = @Contact(name = "Equipe StyleNest", email = "contato@stylenest.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local"),
                @Server(url = "https://stylenest-mi9i.onrender.com", description = "Produção")
        }
)
public class OpenApiConfig {
}
