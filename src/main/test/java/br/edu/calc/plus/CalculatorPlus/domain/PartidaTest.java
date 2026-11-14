package br.edu.calc.plus.CalculatorPlus.domain;

import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {

    // Testa total de acertos em jogos da partida
    @Test
    void testGetAcertos() {
        Partida partida = new Partida();
        Jogo jogo1 = new Jogo(null, 2, 2, null, 4, 4, 0);
        Jogo jogo2 = new Jogo(null, 3, 3, null, 6, 5, 0);
        partida.setJogoList(Arrays.asList(jogo1, jogo2));
        assertEquals(1, partida.getAcertos());
    }

    // Testa total de erros em jogos da partida
    @Test
    void testGetErros() {
        Partida partida = new Partida();
        Jogo jogo1 = new Jogo(null, 10, 5, null, 15, 14, 0);
        Jogo jogo2 = new Jogo(null, 3, 3, null, 6, 6, 0);
        partida.setJogoList(Arrays.asList(jogo1, jogo2));
        assertEquals(1, partida.getErros());
    }

    // Testa mét odo addBonus, incrementando o valor de bonificação
    @Test
    void testAddBonus() {
        Partida partida = new Partida();
        partida.setBonificacao(10.0);
        partida.addBonus(5.0);
        assertEquals(15.0, partida.getBonificacao());
    }

    // Testa mét odo removeBonus, decrementando o valor de bonificação
    @Test
    void testRemoveBonus() {
        Partida partida = new Partida();
        partida.setBonificacao(10.0);
        partida.removeBonus(5.0);
        assertEquals(5.0, partida.getBonificacao());
    }

    // Testa métodos equals e hashCode baseados no id
    @Test
    void testEqualsAndHashCode() {
        Partida p1 = new Partida(1, LocalDateTime.now(), 10.0, 20);
        Partida p2 = new Partida(1, LocalDateTime.now(), 15.0, 25);
        Partida p3 = new Partida(2, LocalDateTime.now(), 10.0, 20);

        assertEquals(p1, p2); // mesmo id, objetos iguais
        assertNotEquals(p1, p3); // id diferente
        assertEquals(p1.hashCode(), p2.hashCode());
    }

}

