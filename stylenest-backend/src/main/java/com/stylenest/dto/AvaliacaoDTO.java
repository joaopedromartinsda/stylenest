package com.stylenest.dto;

import com.stylenest.model.Avaliacao;
import java.time.LocalDateTime;

public class AvaliacaoDTO {

    private Long id;
    private Long produtoId;
    private Long usuarioId;
    private Integer nota;
    private String comentario;
    private LocalDateTime criadoEm;

    public AvaliacaoDTO() {
    }

    public static AvaliacaoDTO fromEntity(Avaliacao avaliacao) {
        AvaliacaoDTO dto = new AvaliacaoDTO();
        dto.setId(avaliacao.getId());
        dto.setProdutoId(avaliacao.getProduto().getId());
        dto.setUsuarioId(avaliacao.getUsuario() != null ? avaliacao.getUsuario().getId() : null);
        dto.setNota(avaliacao.getNota());
        dto.setComentario(avaliacao.getComentario());
        dto.setCriadoEm(avaliacao.getCriadoEm());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
