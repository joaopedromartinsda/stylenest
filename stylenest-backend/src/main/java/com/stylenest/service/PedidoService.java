package com.stylenest.service;

import com.stylenest.dto.PedidoCreateDTO;
import com.stylenest.dto.PedidoDTO;
import com.stylenest.model.Pedido;
import com.stylenest.model.Produto;
import com.stylenest.model.Usuario;
import com.stylenest.repository.PedidoRepository;
import com.stylenest.repository.ProdutoRepository;
import com.stylenest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public PedidoDTO criarPedido(PedidoCreateDTO dto) {
        if (dto.getUsuarioId() == null || dto.getProdutoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario e produto sao obrigatorios");
        }
        if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade deve ser maior que zero");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProduto(produto);
        pedido.setQuantidade(dto.getQuantidade());

        Pedido salvo = pedidoRepository.save(pedido);
        return PedidoDTO.fromEntity(salvo);
    }
}
