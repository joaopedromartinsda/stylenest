package com.stylenest.service;

import com.stylenest.dto.CarrinhoItemDTO;
import com.stylenest.dto.CarrinhoItemRequestDTO;
import com.stylenest.dto.CarrinhoUpdateDTO;
import com.stylenest.model.CarrinhoItem;
import com.stylenest.model.Produto;
import com.stylenest.model.Usuario;
import com.stylenest.repository.CarrinhoItemRepository;
import com.stylenest.repository.ProdutoRepository;
import com.stylenest.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarrinhoService {

    private final CarrinhoItemRepository carrinhoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public CarrinhoService(CarrinhoItemRepository carrinhoItemRepository,
                           UsuarioRepository usuarioRepository,
                           ProdutoRepository produtoRepository) {
        this.carrinhoItemRepository = carrinhoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<CarrinhoItemDTO> listarPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return carrinhoItemRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(CarrinhoItemDTO::fromEntity)
                .toList();
    }

    @Transactional
    public List<CarrinhoItemDTO> salvarCarrinho(CarrinhoUpdateDTO dto) {
        if (dto.getUsuarioId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario obrigatorio");
        }
        Usuario usuario = validarUsuario(dto.getUsuarioId());

        carrinhoItemRepository.deleteByUsuarioId(usuario.getId());

        List<CarrinhoItemDTO> resposta = new ArrayList<>();
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            return resposta;
        }

        for (CarrinhoItemRequestDTO itemDTO : dto.getItens()) {
            if (itemDTO.getProdutoId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto obrigatorio no carrinho");
            }
            if (itemDTO.getQuantidade() == null || itemDTO.getQuantidade() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade deve ser maior que zero");
            }

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

            CarrinhoItem item = new CarrinhoItem();
            item.setUsuario(usuario);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());

            CarrinhoItem salvo = carrinhoItemRepository.save(item);
            resposta.add(CarrinhoItemDTO.fromEntity(salvo));
        }

        return resposta;
    }

    private Usuario validarUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }
}
