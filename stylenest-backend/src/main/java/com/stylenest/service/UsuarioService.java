package com.stylenest.service;

import com.stylenest.dto.UsuarioCadastroDTO;
import com.stylenest.model.Usuario;
import com.stylenest.repository.UsuarioRepository;
import com.stylenest.security.JwtProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final JwtProvider jwtProvider;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(JwtProvider jwtProvider, UsuarioRepository usuarioRepository) {
        this.jwtProvider = jwtProvider;
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResult autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao encontrado"));

        if (!BCrypt.checkpw(senha, usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha invalida");
        }

        String token = jwtProvider.generateToken(usuario);
        return new AuthResult(token, usuario);
    }

    public Usuario cadastrar(UsuarioCadastroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ja cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(BCrypt.hashpw(dto.getSenha(), BCrypt.gensalt()));

        return usuarioRepository.save(usuario);
    }

    public record AuthResult(String token, Usuario usuario) {
    }
}
