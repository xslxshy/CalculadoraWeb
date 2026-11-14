package br.edu.calc.plus.CalculatorPlus.service;

import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.domain.dto.JogoListDTO;
import br.edu.calc.plus.domain.dto.RankingDTO;
import br.edu.calc.plus.repo.PartidaRepo;
import br.edu.calc.plus.repo.JogoRepo;
import br.edu.calc.plus.repo.UsuarioRepo;
import br.edu.calc.plus.service.JogoService;
import br.edu.calc.plus.service.PartidaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PartidaServiceTest {
    @Mock
    private PartidaRepo partidaRepo;

    @Mock
    private JogoRepo jogoRepo;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private JogoService jogoService;

    @InjectMocks
    private PartidaService partidaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa se o serviço retorna o valor formatado do prêmio
    @Test
    void testGetPremioUsers() {
        when(partidaRepo.getAllBonus()).thenReturn(150.0);
        String result = partidaService.getPremioUsers();
        assertNotNull(result);
        // Aqui você pode ajustar conforme o mét odo Util.formatarMoeda retorna
    }

    // Testa mét odo que retorna lista de jogos do usuário
    @Test
    void testGetMeusJogos() {
        Partida partida = new Partida();
        partida.setId(1);
        partida.setData(LocalDateTime.now());
        partida.setBonificacao(10.0);
        partida.setTempo(120);
        Jogo jogo = new Jogo();
        jogo.setResultado(5.0);
        jogo.setResposta(5.0);
        jogo.setOperador(br.edu.calc.plus.domain.EOperator.soma);
        jogo.setBonus(2.0);
        partida.setJogoList(java.util.Collections.singletonList(jogo));
        List<Partida> partidaList = java.util.Collections.singletonList(partida);
        when(partidaRepo.findByUsuarioId(1)).thenReturn(partidaList);
        List<JogoListDTO> jogos = partidaService.getMeusJogos(1);
        assertEquals(1, jogos.size());
    }

    // Testa se detecta que o usuário já competiu hoje
    @Test
    void testUserJaCompetiuHoje() {
        when(partidaRepo.getUsuarioCompetil(anyInt(), any(), any())).thenReturn(1L);
        boolean result = partidaService.userJaCompetiuHoje(1);
        assertTrue(result);
    }

    // Testa a inicialização de uma partida
    @Test
    void testIniciarPartida() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        when(usuarioRepo.getById(1)).thenReturn(usuario);
        when(partidaRepo.save(any(Partida.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jogoService.criarJogosAleatorio(10, 1)).thenReturn(Collections.emptyList());
        when(jogoRepo.save(any(Jogo.class))).thenAnswer(inv -> inv.getArgument(0));
        Partida partida = partidaService.iniciarPartida(1);
        assertNotNull(partida);
        assertEquals(usuario, partida.getUsuario());
    }

    // Testa salvar a partida após uma jogada
    @Test
    void testSavePartida() throws Exception {
        Partida partida = new Partida();
        partida.setId(1);
        Jogo jogo = new Jogo();
        jogo.setResultado(10);
        jogo.setResposta(10);
        jogo.setBonus(2);
        partida.setJogoList(Arrays.asList(jogo));
        when(partidaRepo.findByIdAndUsuarioId(1, 1)).thenReturn(partida);
        when(jogoRepo.save(any(Jogo.class))).thenAnswer(inv -> inv.getArgument(0));
        when(partidaRepo.save(any(Partida.class))).thenAnswer(inv -> inv.getArgument(0));
        Partida result = partidaService.savePartida(1, 1, 1, 1, 10.0);
        assertNotNull(result);
    }

    // Testa a finalização de uma partida
    @Test
    void testFinalizaPartida() throws Exception {
        Partida partida = new Partida();
        partida.setId(1);
        partida.setData(LocalDateTime.now().minusMinutes(2));
        Jogo jogo = new Jogo();
        jogo.setResultado(10);
        jogo.setResposta(10);
        partida.setJogoList(Arrays.asList(jogo));
        when(partidaRepo.findByIdAndUsuarioId(1, 1)).thenReturn(partida);
        when(partidaRepo.save(any(Partida.class))).thenAnswer(inv -> inv.getArgument(0));
        Partida result = partidaService.FinalizaPartida(1, 1, LocalDateTime.now().minusMinutes(2));
        assertNotNull(result);
    }

    // Testa recuperação de partida
    @Test
    void testGetPartida() throws Exception {
        Partida partida = new Partida();
        partida.setId(1);
        when(partidaRepo.findByIdAndUsuarioId(1, 1)).thenReturn(partida);
        Partida result = partidaService.getPartida(1, 1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    // Testa o mét odo de ranking
    @Test
    void testGetRanking() {
        RankingDTO dto = new RankingDTO();
        List<RankingDTO> lista = Collections.singletonList(dto);
        when(partidaRepo.getRanking()).thenReturn(lista);
        List<RankingDTO> result = partidaService.getRanking();
        assertEquals(1, result.size());
    }
}

