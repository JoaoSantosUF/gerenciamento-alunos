package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceUsuario serviceUsuario;


    @Test
    public void testGetId() {
        Usuario usuario = new Usuario();
        usuario.setUser("Ana");
        usuario.setEmail("ana@example.com");
        usuario.setSenha("12345");
        usuarioRepository.save(usuario);


        Long id = usuario.getId();
        Assert.assertNotNull("O ID não deve ser nulo", id);
        Assert.assertTrue("O ID deve ser maior que zero", id > 0);
    }

    // Teste para o método getUser()
    @Test
    public void testGetUser() {
        Usuario usuario = new Usuario();
        usuario.setUser("João");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("54321");
        usuarioRepository.save(usuario);


        String user = usuario.getUser();
        Assert.assertEquals("O nome do usuário deve ser João", "João", user);
    }

    @Test
    public void salvarUsuarioComEmailExistente() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setUser("Maria");
        usuarioExistente.setEmail("maria@example.com");
        usuarioExistente.setSenha("123456");
        usuarioRepository.save(usuarioExistente);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUser("Maria");
        novoUsuario.setEmail("maria@example.com");
        novoUsuario.setSenha("654321");

        try {
            serviceUsuario.salvarUsuario(novoUsuario);
            Assert.fail("Deveria ter lançado uma EmailExistsException");
        } catch (EmailExistsException e) {
            Assert.assertEquals("Este email já esta cadastrado: maria@example.com", e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void loginUsuarioComSucesso() throws NoSuchAlgorithmException {

        Usuario usuario = new Usuario();
        usuario.setUser("Carlos");
        usuario.setEmail("carlos@example.com");
        usuario.setSenha(Util.md5("123456"));
        usuarioRepository.save(usuario);


        System.out.println("Senha salva no banco: " + usuario.getSenha());


        Usuario usuarioLogado = serviceUsuario.loginUser("carlos@example.com", "123456");

        if (usuarioLogado == null) {
            System.out.println("Login falhou: Não conseguiu encontrar o usuário.");
        }


        Assert.assertNotNull("O usuário logado não pode ser nulo", usuarioLogado);
        Assert.assertEquals("Carlos", usuarioLogado.getUser());
    }
}
