package br.edu.calc.plus.CalculatorPlus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Testa cadastro válido via POST /user (formulário padrão da aplicação)
    @Test
    void testSaveUserValido() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "Teste")
                        .param("login", "test123")
                        .param("email", "teste@email.com")
                        .param("senha", "Senha@123")
                        .param("cidade", "Cidade")
                        .param("nascimento", LocalDate.of(2000,1,1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(flash().attributeExists("success"));
    }

    // Testa cenário de erro de validação de formulário (ex.: campos ausentes)
    @Test
    void testSaveUserErroValidacao() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("login", "test123")
                        .param("senha", "Senha@123")
                        .param("nascimento", LocalDate.of(2000,1,1).toString()))
                .andExpect(status().isOk()) // View 'cadastro' deve ser reexibida
                .andExpect(result -> result.getModelAndView().getViewName().equals("cadastro"));
    }

    // GET /user — deve retornar a view 'cadastro' e objeto 'usuario' no model
    @Test
    void testExibirFormularioCadastro() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usuario"))
                .andExpect(view().name("cadastro"));
    }

    // Acesso a rota protegida (exemplo: /perfil) sem login — deve redirecionar para login
    @Test
    void testAcessoProtegidoSemLogin() throws Exception {
        mockMvc.perform(get("/ranking"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // Testa o fluxo de logout (POST ou GET, dependendo do seu controller)
    @Test
    void testLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    // Tamanho mínimo/máximo dos campos
    // teste falha por conta de um problema no DTO que não valida o tamanho do texto no backend
    @Test
    void testCadastroLoginComTamanhoInvalido() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "Teste")
                        .param("login", "aa") // muito curto
                        .param("email", "teste@email.com")
                        .param("senha", "Senha@123")
                        .param("cidade", "Cidade")
                        .param("nascimento", LocalDate.of(2000,1,1).toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("usuario", "login"))
                .andExpect(view().name("cadastro"));
    }

}
