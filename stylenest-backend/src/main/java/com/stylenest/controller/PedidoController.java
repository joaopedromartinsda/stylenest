package com.stylenest.controller;

import com.stylenest.dto.PedidoCreateDTO;
import com.stylenest.dto.PedidoDTO;
import com.stylenest.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(dto));
    }
}
