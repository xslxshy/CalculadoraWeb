package br.edu.calc.plus.CalculatorPlus.repo;
import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.domain.dto.RankingDTO;
import br.edu.calc.plus.repo.PartidaRepo;
import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PartidaRepoTest {

    @Autowired
    private PartidaRepo partidaRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    //Usuário novo pra testes
    private Usuario novoUsuario() {
        return new Usuario(null, "Nome", "login123", "email@teste.com", "Senha@123", "Cidade", LocalDate.of(2000, 1, 1));
    }

    //Testa soma de todas as bonificações
    @Test
    void testGetAllBonus() {
        Usuario user = usuarioRepo.save(novoUsuario());
        Partida p1 = new Partida(null, LocalDateTime.now(), 10.0, 30L);
        p1.setUsuario(user);
        Partida p2 = new Partida(null, LocalDateTime.now(), 20.0, 40L);
        p2.setUsuario(user);
        partidaRepo.save(p1);
        partidaRepo.save(p2);
        Double bonus = partidaRepo.getAllBonus();
        assertEquals(30.0, bonus);
    }

    //Testa busca por usuário via ID
    @Test
    void testFindByUsuarioId() {
        Usuario user = usuarioRepo.save(novoUsuario());
        Partida p = new Partida(null, LocalDateTime.now(), 10.0, 30L);
        p.setUsuario(user);
        partidaRepo.save(p);
        List<Partida> partidas = partidaRepo.findByUsuarioId(user.getId());
        assertFalse(partidas.isEmpty());
        assertEquals(user.getId(), partidas.get(0).getUsuario().getId());
    }

    //Testa contagem de competições do usuário entre datas indicadas
    @Test
    void testGetUsuarioCompetil() {
        Usuario user = usuarioRepo.save(novoUsuario());
        LocalDateTime now = LocalDateTime.now();
        Partida p = new Partida(null, now.minusHours(1), 15.0, 20L);
        p.setUsuario(user);
        partidaRepo.save(p);
        long count = partidaRepo.getUsuarioCompetil(user.getId(), now.minusDays(1), now.plusDays(1));
        assertEquals(1L, count);
    }

    //Testa busca de partida por ID e usuário
    @Test
    void testFindByIdAndUsuarioId() {
        Usuario user = usuarioRepo.save(novoUsuario());
        Partida p = new Partida(null, LocalDateTime.now(), 10.0, 30L);
        p.setUsuario(user);
        p = partidaRepo.save(p);
        Partida encontrado = partidaRepo.findByIdAndUsuarioId(p.getId(), user.getId());
        assertNotNull(encontrado);
    }

    //Testa retorno do ranking com soma de bonificações e demais dados
    @Test
    void testGetRanking() {
        Usuario user = usuarioRepo.save(novoUsuario());
        Partida p1 = new Partida(null, LocalDateTime.now(), 10.0, 20L);
        p1.setUsuario(user);
        Partida p2 = new Partida(null, LocalDateTime.now(), 30.0, 40L);
        p2.setUsuario(user);
        partidaRepo.save(p1);
        partidaRepo.save(p2);
        List<RankingDTO> ranking = partidaRepo.getRanking();
        assertFalse(ranking.isEmpty());
        // Usa o getter correto getBonusTotal() do DTO
        assertTrue(ranking.get(0).getBonusTotal() >= 0);
    }
}
