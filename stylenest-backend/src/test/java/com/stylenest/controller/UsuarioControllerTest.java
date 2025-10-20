package com.stylenest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.stylenest.dto.UsuarioCadastroDTO;
import com.stylenest.service.UsuarioService;

import org.mockito.Mockito;

import com.stylenest.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(controllers = UsuarioController.class)
@Import(TestSecurityConfig.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testCadastroUsuarioComSucesso() throws Exception {
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Maria", "maria@example.com", "senha123");

        Mockito.doNothing().when(usuarioService).cadastrar(Mockito.any(UsuarioCadastroDTO.class));

        mvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}