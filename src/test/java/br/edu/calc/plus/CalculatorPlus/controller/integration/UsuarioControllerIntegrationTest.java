package br.edu.calc.plus.CalculatorPlus.controller.integration;

import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepo usuarioRepo;

    // Testa cadastro via endpoint, integração controller-service-repo-banco
    @Test
    void testCadastroUsuarioIntegration() throws Exception {
        usuarioRepo.deleteAll(); // Limpa o banco antes de começar
        mockMvc.perform(post("/user")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "Integration Test User")
                        .param("login", "integrationUser")
                        .param("email", "integration@teste.com")
                        .param("senha", "Senha@123")
                        .param("cidade", "Test City")
                        .param("nascimento", LocalDate.of(1990,1,1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        // Verifica persistência no banco
        boolean existe = usuarioRepo.findByLogin("integrationUser").isPresent();
        assertTrue(existe);
    }

}
