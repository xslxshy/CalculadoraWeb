package br.edu.calc.plus.CalculatorPlus.domain;

import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogoTest {

    // Testa se o mét odo estaCerto retorna true quando resposta e resultado coincidem
    @Test
    void testEstaCertoRetornaTrue() {
        Jogo jogo = new Jogo(null, 10, 5, EOperator.soma, 15, 15, 2.0);
        assertTrue(jogo.estaCerto());
    }

    // Testa se o mét odo estaCerto retorna false quando resposta e resultado diferem
    @Test
    void testEstaCertoRetornaFalse() {
        Jogo jogo = new Jogo(null, 10, 5, EOperator.soma, 15, 14, 2.0);
        assertFalse(jogo.estaCerto());
    }

    // Testa se o mét odo isCorrect retorna true quando resultado e resposta coincidem
    @Test
    void testIsCorrectRetornaTrue() {
        Jogo jogo = new Jogo(null, 7, 3, EOperator.subtracao, 4, 4, 1.5);
        assertTrue(jogo.isCorrect());
    }

    // Testa se o mét odo isCorrect retorna false quando resultado e resposta não coincidem
    @Test
    void testIsCorrectRetornaFalse() {
        Jogo jogo = new Jogo(null, 7, 3, EOperator.subtracao, 4, 5, 1.5);
        assertFalse(jogo.isCorrect());
    }
}

