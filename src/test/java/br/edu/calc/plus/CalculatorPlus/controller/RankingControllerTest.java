package br.edu.calc.plus.CalculatorPlus.controller;

import br.edu.calc.plus.CalculatorPlus.security.WithMockCustomUser;
import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.service.PartidaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
// Aplica o mock customizado de usuário autenticado para todos os métodos
class RankingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartidaService partidaService;

    // Testa view ranking com lista não-vazia
    @WithMockCustomUser(username = "testuser", nome = "Fulano Teste")
    @Test
    void testRankingViewComLista() throws Exception {
        br.edu.calc.plus.domain.dto.RankingDTO ranking = new br.edu.calc.plus.domain.dto.RankingDTO();
        // Preencha campos do RankingDTO conforme necessário para a view
        java.util.List<br.edu.calc.plus.domain.dto.RankingDTO> lista = java.util.Collections.singletonList(ranking);
        when(partidaService.getRanking()).thenReturn(lista);

        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lista"))
                .andExpect(view().name("ranking"));
    }

    // Testa view ranking com lista vazia
    @WithMockCustomUser(username = "testuser", nome = "Fulano Teste")
    @Test
    void testRankingViewListaVazia() throws Exception {
        when(partidaService.getRanking()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("lista", java.util.Collections.emptyList()))
                .andExpect(view().name("ranking"));
    }

    // Testa se rota requer autenticação: se DEVE proteger, deixe o teste; senão, remova.
    // Se a rota for pública, esse teste pode ser desnecessário!
    @Test
    void testRankingRequiresAuthentication() throws Exception {
        // Para simular acesso SEM estar autenticado, remova @WithMockCustomUser desse teste (caso override global)
        // E aguarde status de redirecionamento
        mockMvc.perform(get("/ranking"))
                .andExpect(status().is3xxRedirection());
    }
}
