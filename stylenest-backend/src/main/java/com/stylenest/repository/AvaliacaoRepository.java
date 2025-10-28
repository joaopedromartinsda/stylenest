package com.stylenest.repository;

import com.stylenest.model.Avaliacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByProdutoId(Long produtoId);
}
