package com.stylenest.config;

import com.stylenest.model.Produto;
import com.stylenest.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDataLoader implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    public ProdutoDataLoader(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {
        if (produtoRepository.count() > 0) {
            return;
        }

        List<Produto> produtos = List.of(
                new Produto(
                        "Camiseta Basica Preta",
                        "Camiseta 100% algodao, modelagem unissex com toque macio.",
                        new BigDecimal("79.90"),
                        "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=600&q=80"),
                new Produto(
                        "Calca Jeans Slim",
                        "Calca jeans azul escuro com elastano e acabamento moderno.",
                        new BigDecimal("189.90"),
                        "https://images.unsplash.com/photo-1489987707025-afc232f7ea0f?auto=format&fit=crop&w=600&q=80"),
                new Produto(
                        "Vestido Floral Midi",
                        "Vestido midi com estampa floral exclusiva e tecido leve.",
                        new BigDecimal("229.90"),
                        "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=600&q=80"),
                new Produto(
                        "Tenis Casual Branco",
                        "Tenis branco em couro sintetico com sola emborrachada.",
                        new BigDecimal("249.90"),
                        "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?auto=format&fit=crop&w=600&q=80")
        );

        produtoRepository.saveAll(produtos);
    }
}
