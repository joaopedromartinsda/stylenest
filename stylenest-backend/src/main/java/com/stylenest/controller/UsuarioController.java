package com.stylenest.controller;
import com.stylenest.service.UsuarioService;
import com.stylenest.dto.LoginDTO;
import com.stylenest.dto.JwtResponse;
import com.stylenest.dto.UsuarioCadastroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody LoginDTO dto) {
        String token = usuarioService.autenticarRetornaJWT(dto.getEmail(), dto.getSenha());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping
public ResponseEntity<Void> cadastrar(@RequestBody UsuarioCadastroDTO dto) {
    usuarioService.cadastrar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
