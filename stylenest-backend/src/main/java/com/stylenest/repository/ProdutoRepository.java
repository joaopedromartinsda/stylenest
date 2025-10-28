package com.stylenest.repository;

import com.stylenest.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaIgnoreCase(String categoria);
}
