package com.stylenest.dto;

import com.stylenest.model.CarrinhoItem;
import java.math.BigDecimal;

public class CarrinhoItemDTO {

    private Long produtoId;
    private String nome;
    private BigDecimal preco;
    private String imagemUrl;
    private Integer quantidade;

    public static CarrinhoItemDTO fromEntity(CarrinhoItem item) {
        CarrinhoItemDTO dto = new CarrinhoItemDTO();
        dto.setProdutoId(item.getProduto().getId());
        dto.setNome(item.getProduto().getNome());
        dto.setPreco(item.getProduto().getPreco());
        dto.setImagemUrl(item.getProduto().getImagemUrl());
        dto.setQuantidade(item.getQuantidade());
        return dto;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
