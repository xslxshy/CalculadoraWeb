package br.edu.calc.plus.CalculatorPlus.domain;

import br.edu.calc.plus.domain.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    //Testa se a senha atende aos requisitos (mínimo 6 caracteres, maiúscula, minúscula, número, caractere especial)
    @Test
    void testSenhaValida() {
        Usuario usuario = new Usuario(null, "Nome", "login123", "email@teste.com","Abc@1234" , "Cidade", LocalDate.of(1995, 5, 5));
        assertTrue(usuario.senhaValida());

        usuario.setSenha("abc123"); //sem caracteres especiais e maiúscula
        assertFalse(usuario.senhaValida());

        usuario.setSenha("Abcde@1"); //válido
        assertTrue(usuario.senhaValida());

        usuario.setSenha("123456"); //só números
        assertFalse(usuario.senhaValida());
    }

    //Testa se o login ta dentro do tamanho e sem espaços
    @Test
    void testLoginValida() {
        Usuario usuario = new Usuario(null, "Nome", "loginUs", "Senha@123", "email@teste.com", "Cidade", LocalDate.of(1995, 5, 5));
        assertTrue(usuario.loginValida());

        usuario.setLogin("ab"); //mt curto
        assertFalse(usuario.loginValida());

        usuario.setLogin("login muito longo aqui"); //com espaços e muito grande
        assertFalse(usuario.loginValida());

        usuario.setLogin("loginValido"); //válido
        assertTrue(usuario.loginValida());

        usuario.setLogin("login com espaco");
        assertFalse(usuario.loginValida());
    }
}

