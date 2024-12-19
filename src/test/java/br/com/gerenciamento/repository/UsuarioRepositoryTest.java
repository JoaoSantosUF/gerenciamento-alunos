package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {

        Usuario usuario = new Usuario();
        usuario.setEmail("joao@example.com");
        usuario.setUser("joao");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);


        Usuario usuarioEncontrado = usuarioRepository.findByEmail("joao@example.com");


        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals("joao@example.com", usuarioEncontrado.getEmail());
        Assert.assertEquals("joao", usuarioEncontrado.getUser());
        Assert.assertEquals("senha123", usuarioEncontrado.getSenha());
    }

    @Test
    public void testBuscarLogin() {

        Usuario usuario = new Usuario();
        usuario.setUser("lais");
        usuario.setSenha("senha123");
        usuario.setEmail("lais@example.com");
        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("lais", "senha123");


        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals("lais", usuarioEncontrado.getUser());
        Assert.assertEquals("senha123", usuarioEncontrado.getSenha());
        Assert.assertEquals("lais@example.com", usuarioEncontrado.getEmail());
    }

    @Test
    public void testFindByEmailInexistente() {
        Usuario usuario = usuarioRepository.findByEmail("emailinexistente@example.com");
        Assert.assertNull(usuario);
    }

    @Test
    public void testBuscarLoginComUsuarioInvalido() {
        Usuario usuario = usuarioRepository.buscarLogin("usuarioInvalido", "senhaErrada");
        Assert.assertNull(usuario);
    }
}
