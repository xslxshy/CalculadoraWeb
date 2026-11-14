package br.edu.calc.plus.CalculatorPlus.repo;

import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepoTest {

    @Autowired
    private UsuarioRepo usuarioRepo;

    // Utilitário para criar um novo usuário
    private Usuario novoUsuario() {
        return new Usuario(
                null,
                "Fulano Teste",
                "fulano123",
                "fulano@email.com",
                "Senha@123",
                "Cidade",
                LocalDate.of(1990, 5, 20)
        );
    }

    // Testa a busca por nome com sucesso
    @Test
    void testFindByNome() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Optional<Usuario> encontrado = usuarioRepo.findByNome("Fulano Teste");
        assertTrue(encontrado.isPresent());
        assertEquals(usuario.getLogin(), encontrado.get().getLogin());
    }

    // Testa a busca por login com sucesso
    @Test
    void testFindByLogin() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Optional<Usuario> encontrado = usuarioRepo.findByLogin("fulano123");
        assertTrue(encontrado.isPresent());
        assertEquals(usuario.getNome(), encontrado.get().getNome());
    }

    // Testa a busca por nome que não existe
    @Test
    void testFindByNomeNotFound() {
        Optional<Usuario> encontrado = usuarioRepo.findByNome("NomeInexistente");
        assertFalse(encontrado.isPresent());
    }

    // Testa a busca por login que não existe
    @Test
    void testFindByLoginNotFound() {
        Optional<Usuario> encontrado = usuarioRepo.findByLogin("loginInexistente");
        assertFalse(encontrado.isPresent());
    }
}

