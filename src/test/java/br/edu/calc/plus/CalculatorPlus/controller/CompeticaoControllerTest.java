package br.edu.calc.plus.CalculatorPlus.controller;

import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.dto.JogoListDTO;
import br.edu.calc.plus.CalculatorPlus.security.WithMockCustomUser;
import br.edu.calc.plus.service.JogoService;
import br.edu.calc.plus.service.PartidaService;
import br.edu.calc.plus.util.LogadoUtil;
import org.junit.jupiter.api.Test;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
//Aplica para todos os métodos desse teste
@WithMockCustomUser(username = "testuser", nome = "Teste")
public class CompeticaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartidaService partidaService;

    @MockBean
    private JogoService jogoService;

    @MockBean
    private LogadoUtil logado;

    @Test
    void testMeusJogos() throws Exception {
        when(logado.getIdUserLogado(any())).thenReturn(1);
        List<JogoListDTO> listaMock = Arrays.asList(
                new JogoListDTO(1, LocalDateTime.now(), 100.0, 5, 3, 120L)
        );
        when(partidaService.getMeusJogos(1)).thenReturn(listaMock);
        mockMvc.perform(get("/competicao"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lista"))
                .andExpect(view().name("minhasCompeticoes"));
    }

    @Test
    void testDetalhePartidaSucesso() throws Exception {
        when(logado.getIdUserLogado(any())).thenReturn(1);
        Partida p = new Partida();
        p.setId(1);
        p.setData(LocalDateTime.now());

        //Preenche lista de jogos para funcionar getAcertos(), getErros(), etc.

        List<Jogo> jogosMock = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Jogo j = new Jogo();
            j.setValor1(7);
            j.setValor2(3);
            j.setOperador(br.edu.calc.plus.domain.EOperator.soma);
            j.setResultado(10.0);
            j.setResposta(10.0); //Pra garantir isCorrect() (acertos)
            j.setBonus(2.0);
            jogosMock.add(j);
        }
        p.setJogoList(jogosMock);
        when(partidaService.getPartida(1, 1)).thenReturn(p);

        mockMvc.perform(get("/competicao/1/detalhe"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("partida", p))
                .andExpect(view().name("detalheJogo"));
    }

    @Test
    void testNovoJogoJaCompetiu() throws Exception {
        when(logado.getIdUserLogado(any())).thenReturn(1);
        when(partidaService.userJaCompetiuHoje(1)).thenReturn(true);
        mockMvc.perform(get("/competicao/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "Já Participou da competição hoje!!! Aguarde até amanhã"))
                .andExpect(redirectedUrl("/competicao"));
    }

    @Test
    void testNextJogoRespostaValida() throws Exception {
        when(logado.getIdUserLogado(any())).thenReturn(1);
        Partida p = new Partida();
        p.setId(1);

        //Monta lista de Jogo com campos relevantes para Thymeleaf
        List<Jogo> jogosMock = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Jogo j = new Jogo();
            j.setValor1(7);
            j.setValor2(3);
            j.setOperador(br.edu.calc.plus.domain.EOperator.soma);
            j.setResultado(10.0);
            j.setResposta(10.0);
            j.setBonus(2.0);
            jogosMock.add(j);
        }
        p.setJogoList(jogosMock);

        when(partidaService.savePartida(anyInt(), anyInt(), anyInt(), anyInt(), anyDouble())).thenReturn(p);

        //Pra pergunta < 10
        mockMvc.perform(post("/competicao/next").with(csrf())
                        .param("cpResposta", "5")
                        .param("idp", "1")
                        .param("idjogo", "1")
                        .param("perg", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("idPartida", "jogo", "pergunta"))
                .andExpect(view().name("jogar"));

        //Pra pergunta = 10
        mockMvc.perform(post("/competicao/next").with(csrf())
                        .param("cpResposta", "5")
                        .param("idp", "1")
                        .param("idjogo", "1")
                        .param("perg", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(redirectedUrlPattern("/competicao/*/detalhe"));
    }
}
