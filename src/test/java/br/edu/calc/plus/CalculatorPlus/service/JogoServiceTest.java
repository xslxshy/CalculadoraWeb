package br.edu.calc.plus.CalculatorPlus.service;

import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.repo.JogoRepo;
import br.edu.calc.plus.service.JogoService;
import br.edu.calc.plus.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class JogoServiceTest {
    @Mock
    private JogoRepo jogoRepo;

    @InjectMocks
    private JogoService jogoService;

    private static class RandomFixo extends Random {
        @Override public int nextInt(int bound) { return bound == 4 ? 0 : 10; }
        @Override public double nextDouble() { return 0.5; }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jogoService, "rand", new RandomFixo());
    }

    //Testa se o serviço retorna o valor formatado do total de erros
    @Test
    void testGetAllErros() {
        when(jogoRepo.getAllErros()).thenReturn(1234L);
        String result = jogoService.getAllErros();
        assertNotNull(result);
    }

    //Testa se retorna certo a quantidade total de acertos
    @Test
    void testGetAllAcertos() {
        when(jogoRepo.getAllAcertos()).thenReturn(5678L);
        long result = jogoService.getAllAcertos();
        assertEquals(5678L, result);
    }

    //Testa se retorna a lista de acertos dos últimos 10 dias
    @Test
    void testGetAcertosUltimos10Dias() {
        when(jogoRepo.getAcertosDia(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(4L);
        long[] valores = jogoService.getAcertosUltimos10Dias();
        assertEquals(10, valores.length);
        for (long v : valores) assertEquals(4L, v);
    }

    //Testa se retorna a lista de erros dos últimos 10 dias
    @Test
    void testGetErrosUltimos10Dias() {
        when(jogoRepo.getErrosDia(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(2L);
        long[] valores = jogoService.getErrosUltimos10Dias();
        assertEquals(10, valores.length);
        for (long v : valores) assertEquals(2L, v);
    }

    //Testa o cálculo de bônus inicial usando operador e id de usuário
    @Test
    void testBonusInicial() {
        //Garantir que bonusUsuario retorne valor mínimo mockando repo
        when(jogoRepo.getAllAcertosUser(anyInt())).thenReturn(0L);
        BigDecimal bonus = jogoService.bonusInicial(EOperator.soma, 1);
        assertNotNull(bonus);
        assertTrue(bonus.doubleValue() > 0);
    }

    //Testa geração de lista aleatória de jogos
    @Test
    void testCriarJogosAleatorio() {
        when(jogoRepo.getAllAcertosUser(anyInt())).thenReturn(0L);
        List<Jogo> jogos = jogoService.criarJogosAleatorio(3, 42);
        assertEquals(3, jogos.size());
        for (Jogo jogo : jogos) {
            assertNotNull(jogo.getOperador());
            assertTrue(jogo.getBonus() >= 0);
        }
    }
}
