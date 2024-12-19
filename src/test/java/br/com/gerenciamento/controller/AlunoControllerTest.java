package br.com.gerenciamento.controller;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;

public class AlunoControllerTest {

    @Mock
    private ServiceAluno serviceAluno;

    @InjectMocks
    private AlunoController alunoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
    }

    @Test
    public void testInsertAlunos() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    public void testInserirAlunoWithErrors() throws Exception {

        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "")
                        .param("email", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        List<Aluno> alunos = Arrays.asList(new Aluno(), new Aluno());
        when(serviceAluno.findAll()).thenReturn(alunos);

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attribute("alunosList", alunos));
    }

    @Test
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João");
        when(serviceAluno.getById(1L)).thenReturn(aluno);

        mockMvc.perform(get("/editar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/editar"))
                .andExpect(model().attribute("aluno", aluno));
    }
}