package br.edu.calc.plus.CalculatorPlus.controller.integration;

import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.config.security.user.UserLogado;
import br.edu.calc.plus.repo.PartidaRepo;
import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CompeticaoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PartidaRepo partidaRepo;

    @BeforeEach
    void setUp() {
        usuarioRepo.deleteAll();
        partidaRepo.deleteAll();
    }

    @Test
    void testIniciarNovaCompeticaoIntegration() throws Exception {
        //Salva o usuário no banco e pega o id que foi gerado
        Usuario usuario = usuarioRepo.save(new Usuario(null, "Jogador Integracao", "intUser03", "integration3@teste.com", "Senha@123", "Cidade", LocalDate.of(1995,1,1)));
        Integer idGerado = usuario.getId();

        //Cria UserLogado com ID correto e insere no SecurityContext
        UserLogado userLogado = new UserLogado(
                idGerado, //sync com banco
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getSenha(),
                Set.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken( userLogado, userLogado.getPassword(), userLogado.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/competicao/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("jogar"));

        //Valida que a partida foi criada vinculada ao ID certo
        var partidas = partidaRepo.findByUsuarioId(idGerado);
        assertFalse(partidas.isEmpty());
    }
}
