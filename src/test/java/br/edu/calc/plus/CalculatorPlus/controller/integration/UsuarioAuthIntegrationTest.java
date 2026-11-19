package br.edu.calc.plus.CalculatorPlus.controller.integration;

import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UsuarioAuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        //Limpa o banco de dados antes de cada teste
        usuarioRepo.deleteAll();
    }

    @Test
    void testCadastroELoginUsuarioIntegration() throws Exception {
        //1 - Cadastra o usuário de teste com senha criptografada direto no banco
        String senhaCriptografada = passwordEncoder.encode("123456");
        usuarioRepo.save(new Usuario(
                null,
                "Usuário Teste",
                "caiofernandes",
                "teste@exemplo.com",
                senhaCriptografada,
                "Cidade",
                LocalDate.of(2000, 1, 1)
        ));

        //2 - Faz login simulando o formulário
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "caiofernandes")
                        .param("password", "123456")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().is3xxRedirection()) //sucesso redireciona pra home
                .andExpect(redirectedUrl("/"));
    }
}
