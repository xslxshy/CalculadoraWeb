package br.edu.calc.plus.CalculatorPlus.service;

import br.edu.calc.plus.domain.dto.UserDTO;
import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.repo.UsuarioRepo;
import br.edu.calc.plus.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioRepo usuarioRepo;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa se o mét odo retorna corretamente a contagem de usuários
    @Test
    void testGetCountUsers() {
        when(usuarioRepo.count()).thenReturn(42L);
        long count = usuarioService.getCountUsers();
        assertEquals(42L, count);
        verify(usuarioRepo).count();
    }

    // Testa o fluxo de salvar um novo usuário a partir do DTO
    @Test
    void testSaveUsuario() {
        UserDTO dto = new UserDTO();
        dto.setLogin("tester");
        dto.setNome("Teste");
        dto.setEmail("teste@email.com");
        dto.setSenha("12345");
        dto.setCidade("Cidade");
        Usuario entityMock = new Usuario();
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(entityMock);
        usuarioService.save(dto);
        verify(usuarioRepo, times(1)).save(any(Usuario.class));
    }
}
