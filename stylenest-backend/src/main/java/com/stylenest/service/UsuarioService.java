package com.stylenest.service;

import java.util.Optional;
import com.stylenest.model.Usuario;
import com.stylenest.repository.UsuarioRepository;
import com.stylenest.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import com.stylenest.dto.UsuarioCadastroDTO;

@Service
public class UsuarioService {
@Autowired
private JwtProvider jwtProvider;

public String autenticarRetornaJWT(String email, String senha) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

    if (usuarioOpt.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado");
    }

    Usuario usuario = usuarioOpt.get();

    if (!BCrypt.checkpw(senha, usuario.getSenha())) {
        throw new RuntimeException("Senha inválida");
    }

    return jwtProvider.generateToken(usuario);
}

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastrar(UsuarioCadastroDTO dto) {
        if(usuarioRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("E-mail já cadastrado!");

        String senhaHash = BCrypt.hashpw(dto.getSenha(), BCrypt.gensalt());
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(senhaHash);
        usuarioRepository.save(usuario);

        
    }
}