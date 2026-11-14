package br.edu.calc.plus.CalculatorPlus.repo;
import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.repo.JogoRepo;
import br.edu.calc.plus.repo.PartidaRepo;
import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JogoRepoTest {

    @Autowired
    private JogoRepo jogoRepo;
    @Autowired
    private PartidaRepo partidaRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;

    // Cria um novo usuário com dados válidos
    private Usuario novoUsuario() {
        return new Usuario(
                null,
                "NomeUsuario",
                "login123",
                "email@teste.com",
                "Senha@123",
                "Cidade",
                LocalDate.of(2000, 1, 1)
        );
    }

    // Cria uma nova partida associada ao usuário
    private Partida novaPartida(Usuario usuario) {
        Partida partida = new Partida(null, LocalDateTime.now(), 10.0, 60);
        partida.setUsuario(usuario);
        return partida;
    }

    // Testa a contagem de erros em jogos (resultado diferente da resposta)
    @Test
    void testGetAllErros() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Partida partida = partidaRepo.save(novaPartida(usuario));
        Jogo jogo = new Jogo(null, 2, 2, EOperator.soma, 4, 99, 0);
        jogo.setResposta(10); // erro
        jogo.setPartida(partida);
        jogoRepo.save(jogo);
        assertEquals(1, jogoRepo.getAllErros());
    }

    // Testa a contagem de acertos em jogos (resultado igual à resposta)
    @Test
    void testGetAllAcertos() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Partida partida = partidaRepo.save(novaPartida(usuario));
        Jogo jogo = new Jogo(null, 2, 2, EOperator.soma, 4, 99, 0);
        jogo.setResposta(4); // acerto
        jogo.setPartida(partida);
        jogoRepo.save(jogo);
        assertEquals(1, jogoRepo.getAllAcertos());
    }

    // Testa o total de acertos registrados em um dia específico
    @Test
    void testGetAcertosDia() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Partida partida = partidaRepo.save(novaPartida(usuario));
        Jogo jogo = new Jogo(null, 7, 3, EOperator.soma, 10, 99, 0);
        jogo.setResposta(10);
        jogo.setPartida(partida);
        jogoRepo.save(jogo);

        LocalDateTime ini = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now().plusDays(1);
        assertEquals(1, jogoRepo.getAcertosDia(ini, fim));
    }

    // Testa o total de erros registrados em um dia específico
    @Test
    void testGetErrosDia() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Partida partida = partidaRepo.save(novaPartida(usuario));
        Jogo jogo = new Jogo(null, 2, 2, EOperator.soma, 4, 99, 0);
        jogo.setResposta(5);
        jogo.setPartida(partida);
        jogoRepo.save(jogo);

        LocalDateTime ini = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now().plusDays(1);
        assertEquals(1, jogoRepo.getErrosDia(ini, fim));
    }

    // Testa a contagem total de acertos de um usuário específico
    @Test
    void testGetAllAcertosUser() {
        Usuario usuario = usuarioRepo.save(novoUsuario());
        Partida partida = partidaRepo.save(novaPartida(usuario));
        Jogo jogo = new Jogo(null, 5, 5, EOperator.soma, 10, 99, 0);
        jogo.setResposta(10);
        jogo.setPartida(partida);
        jogoRepo.save(jogo);

        assertEquals(1, jogoRepo.getAllAcertosUser(usuario.getId()));
    }
}
