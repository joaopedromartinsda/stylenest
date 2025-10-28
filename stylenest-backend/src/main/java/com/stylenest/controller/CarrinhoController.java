package com.stylenest.controller;

import com.stylenest.dto.CarrinhoItemDTO;
import com.stylenest.dto.CarrinhoUpdateDTO;
import com.stylenest.service.CarrinhoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @Autowired
    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<CarrinhoItemDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carrinhoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<List<CarrinhoItemDTO>> salvar(@RequestBody CarrinhoUpdateDTO dto) {
        return ResponseEntity.ok(carrinhoService.salvarCarrinho(dto));
    }
}
