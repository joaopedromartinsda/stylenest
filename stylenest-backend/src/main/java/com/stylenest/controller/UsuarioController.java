package com.stylenest.controller;

import com.stylenest.dto.LoginDTO;
import com.stylenest.dto.LoginResponse;
import com.stylenest.dto.UsuarioCadastroDTO;
import com.stylenest.dto.UsuarioResponse;
import com.stylenest.model.Usuario;
import com.stylenest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "http://localhost:3000",
        "https://stylenest.netlify.app",
        "https://stylenest-on.netlify.app"
})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginDTO dto) {
        UsuarioService.AuthResult resultado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
        LoginResponse response = new LoginResponse(resultado.token(), UsuarioResponse.from(resultado.usuario()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.from(usuario));
    }
}
