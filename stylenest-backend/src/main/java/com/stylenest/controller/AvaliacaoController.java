package com.stylenest.controller;

import com.stylenest.dto.AvaliacaoCreateDTO;
import com.stylenest.dto.AvaliacaoDTO;
import com.stylenest.service.AvaliacaoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> listar(@RequestParam(required = false) Long produtoId) {
        return ResponseEntity.ok(avaliacaoService.listar(produtoId));
    }

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> criar(@RequestBody AvaliacaoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoService.criar(dto));
    }
}
