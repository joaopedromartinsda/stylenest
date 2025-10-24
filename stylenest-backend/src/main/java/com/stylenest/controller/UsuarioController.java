package com.stylenest.controller;

import com.stylenest.dto.LoginDTO;
import com.stylenest.dto.LoginResponse;
import com.stylenest.dto.UsuarioCadastroDTO;
import com.stylenest.dto.UsuarioResponse;
import com.stylenest.model.Usuario;
import com.stylenest.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        "https://stylenest.netlify.app"
})
@Tag(name = "Usuarios", description = "Operacoes de autenticacao e cadastro de usuarios.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Autentica um usuario",
            description = "Valida credenciais e retorna token JWT com os dados do usuario.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login realizado com sucesso.",
                            content = @Content(schema = @Schema(implementation = LoginResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Credenciais invalidas."),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada invalidos.")
            }
    )
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginDTO dto) {
        UsuarioService.AuthResult resultado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
        LoginResponse response = new LoginResponse(resultado.token(), UsuarioResponse.from(resultado.usuario()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Cadastra novo usuario",
            description = "Persiste um novo usuario com email unico e retorna os dados cadastrados.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario criado com sucesso.",
                            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada invalidos."),
                    @ApiResponse(responseCode = "409", description = "Email ja cadastrado.")
            }
    )
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.from(usuario));
    }
}
