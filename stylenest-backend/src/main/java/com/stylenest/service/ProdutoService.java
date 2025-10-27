package com.stylenest.service;

import com.stylenest.dto.ProdutoCreateDTO;
import com.stylenest.dto.ProdutoDTO;
import com.stylenest.model.Produto;
import com.stylenest.repository.ProdutoRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProdutoService {

    private static final Set<String> CATEGORIAS_VALIDAS = Set.of("kids", "mas", "fem");

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ProdutoDTO criarProduto(ProdutoCreateDTO dto) {
        validarRequisitos(dto);

        Produto produto = new Produto();
        produto.setNome(dto.getNome().trim());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setImagemUrl(dto.getImagemUrl());
        produto.setCategoria(normalizarCategoria(dto.getCategoria()));

        Produto salvo = produtoRepository.save(produto);
        return ProdutoDTO.fromEntity(salvo);
    }

    private void validarRequisitos(ProdutoCreateDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome obrigatorio");
        }

        if (dto.getPreco() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preco obrigatorio");
        }

        if (dto.getCategoria() == null || dto.getCategoria().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria obrigatoria");
        }

        String categoriaNormalizada = dto.getCategoria().trim().toLowerCase();
        if (!CATEGORIAS_VALIDAS.contains(categoriaNormalizada)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria invalida. Use kids, mas ou fem.");
        }
    }

    private String normalizarCategoria(String categoria) {
        return categoria.trim().toLowerCase();
    }
}
