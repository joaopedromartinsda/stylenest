package com.stylenest.service;

import com.stylenest.dto.AvaliacaoCreateDTO;
import com.stylenest.dto.AvaliacaoDTO;
import com.stylenest.model.Avaliacao;
import com.stylenest.model.Produto;
import com.stylenest.model.Usuario;
import com.stylenest.repository.AvaliacaoRepository;
import com.stylenest.repository.ProdutoRepository;
import com.stylenest.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            ProdutoRepository produtoRepository,
                            UsuarioRepository usuarioRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> listar(Long produtoId) {
        List<Avaliacao> avaliacoes = produtoId != null
                ? avaliacaoRepository.findByProdutoId(produtoId)
                : avaliacaoRepository.findAll();

        return avaliacoes.stream()
                .map(AvaliacaoDTO::fromEntity)
                .toList();
    }

    @Transactional
    public AvaliacaoDTO criar(AvaliacaoCreateDTO dto) {
        if (dto.getProdutoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto obrigatorio");
        }
        if (dto.getNota() == null || dto.getNota() < 1 || dto.getNota() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota deve ser entre 1 e 5");
        }

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        Usuario usuario = null;
        if (dto.getUsuarioId() != null) {
            usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setProduto(produto);
        avaliacao.setUsuario(usuario);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        Avaliacao salva = avaliacaoRepository.save(avaliacao);
        return AvaliacaoDTO.fromEntity(salva);
    }
}
