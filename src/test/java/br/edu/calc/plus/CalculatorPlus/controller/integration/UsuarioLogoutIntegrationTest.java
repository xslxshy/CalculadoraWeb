package br.edu.calc.plus.CalculatorPlus.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UsuarioLogoutIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    //Testa se o logout via endpoint /logout acontece e redireciona para a URL padrão após logout.
    @Test
    void testLogoutUsuarioIntegration() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().is3xxRedirection()) //Espera redirecionamento após logout
                .andExpect(redirectedUrl("/home"));
    }
}
