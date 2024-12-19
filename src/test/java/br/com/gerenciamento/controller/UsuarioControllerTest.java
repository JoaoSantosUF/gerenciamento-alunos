package br.com.gerenciamento.controller;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.ServiceUsuario;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() throws Exception {
        usuario = new Usuario();
        usuario.setUser("joao");
        usuario.setSenha("senha123");
        serviceUsuario.salvarUsuario(usuario);
    }

    @Test
    public void testLoginPage() {
        ModelAndView modelAndView = usuarioController.login();

        assertEquals("login/login", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("usuario"));
    }

    @Test
    public void testIndexPage() {
        ModelAndView modelAndView = usuarioController.index();

        assertEquals("home/index", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("aluno"));
    }

    @Test
    public void testCadastrarPage() {
        ModelAndView modelAndView = usuarioController.cadastrar();

        assertEquals("login/cadastro", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("usuario"));
    }


    @Test
    public void testLoginUsuarioFalha() throws Exception {

        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setUser("naoExiste");
        usuarioLogin.setSenha("senhaInvalida");


        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioLogin, "usuario");


        HttpSession session = null;


        ModelAndView modelAndView = usuarioController.login(usuarioLogin, bindingResult, session);


        assertEquals("login/cadastro", modelAndView.getViewName());
        assertEquals(null, modelAndView.getModel().get("msg"));
    }
}
