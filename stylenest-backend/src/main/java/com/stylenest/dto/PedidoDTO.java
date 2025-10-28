package com.stylenest.dto;

import com.stylenest.model.Pedido;
import java.time.LocalDateTime;

public class PedidoDTO {

    private Long id;
    private Long usuarioId;
    private Long produtoId;
    private Integer quantidade;
    private LocalDateTime criadoEm;

    public static PedidoDTO fromEntity(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setProdutoId(pedido.getProduto().getId());
        dto.setQuantidade(pedido.getQuantidade());
        dto.setCriadoEm(pedido.getCriadoEm());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
