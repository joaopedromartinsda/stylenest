package com.stylenest.repository;

import com.stylenest.model.CarrinhoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long> {
    List<CarrinhoItem> findByUsuarioId(Long usuarioId);
    void deleteByUsuarioId(Long usuarioId);
}
