package com.stylenest.dto;

import java.util.List;

public class CarrinhoUpdateDTO {

    private Long usuarioId;
    private List<CarrinhoItemRequestDTO> itens;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<CarrinhoItemRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<CarrinhoItemRequestDTO> itens) {
        this.itens = itens;
    }
}
