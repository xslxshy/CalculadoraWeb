package br.edu.calc.plus.CalculatorPlus.util;

import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidationTest {

    // Simula validação do input que deve ser número decimal com ponto
    public double parseInputResposta(String input) throws NumberFormatException {
        // Substituir vírgula por ponto para aceitar entradas com vírgula
        input = input.replace(',', '.');
        return Double.parseDouble(input);
    }

    // Verifica se o mét0do aceita entrada decimal com vírgula (ex.: "10,5") e converte corretamente para ponto ("10.5").
    @Test
    void testInputComVirgula() {
        String inputComVirgula = "10,5";
        double valor = parseInputResposta(inputComVirgula);
        assertEquals(10.5, valor);
    }

    // Garante que o mét0do processa corretamente números decimais já usando ponto (ex.: "12.75").
    @Test
    void testInputComPonto() {
        String inputComPonto = "12.75";
        double valor = parseInputResposta(inputComPonto);
        assertEquals(12.75, valor);
    }

    // Valida que uma entrada com múltiplas vírgulas (ex.: "12,7,5") gera exceção de formato inválido.
    @Test
    void testInputInvalido() {
        String entradaInvalida = "12,7,5";
        assertThrows(NumberFormatException.class, () -> {
            parseInputResposta(entradaInvalida);
        });
    }

    // Testa se o mét0do de comparação considera corretamente o valor decimal inserido pelo usuário como igual ou diferente do resultado esperado, respeitando casas decimais.
    @Test
    void testComparacaoDecimal() {
        Jogo jogo = new Jogo(null, 14, 36, EOperator.divisao, 0.3889, 0.38, 0);

        jogo.setResposta(0.37);
        assertFalse(jogo.isCorrect());

        jogo.setResposta(0.39);
        assertFalse(jogo.isCorrect());

        jogo.setResposta(0.38);
        assertTrue(jogo.isCorrect());
    }

}
