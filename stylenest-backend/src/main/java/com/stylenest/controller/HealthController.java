package com.stylenest.controller;

import com.stylenest.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health", description = "Verificação de funcionamento da API")
public class HealthController {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public HealthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Retorna o estado da API e do banco de dados")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "stylenest-backend");
        body.put("timestamp", Instant.now().toString());

        try {
            // Verifica conectividade com o banco
            usuarioRepository.count();
            body.put("status", "UP");
            body.put("db", "UP");
            return ResponseEntity.ok(body);
        } catch (Exception ex) {
            body.put("status", "DOWN");
            body.put("db", "DOWN");
            body.put("error", ex.getClass().getSimpleName());
            return ResponseEntity.status(503).body(body);
        }
    }
}

