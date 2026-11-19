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

//Aplica o mock customizado de usuário autenticado para todos os métodos
class RankingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartidaService partidaService;

    //Testa view ranking com LISTA NÃO VAZIA
    @WithMockCustomUser(username = "testuser", nome = "Teste")
    @Test
    void testRankingViewComLista() throws Exception {
        br.edu.calc.plus.domain.dto.RankingDTO ranking = new br.edu.calc.plus.domain.dto.RankingDTO();

        java.util.List<br.edu.calc.plus.domain.dto.RankingDTO> lista = java.util.Collections.singletonList(ranking);
        when(partidaService.getRanking()).thenReturn(lista);

        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lista"))
                .andExpect(view().name("ranking"));
    }

    //Testa view ranking com LISTA VAZIA
    @WithMockCustomUser(username = "testuser", nome = "User Teste")
    @Test
    void testRankingViewListaVazia() throws Exception {
        when(partidaService.getRanking()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("lista", java.util.Collections.emptyList()))
                .andExpect(view().name("ranking"));
    }

    //Testa se rota requer autenticação
    @Test
    void testRankingRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/ranking"))
                .andExpect(status().is3xxRedirection());
    }
}
