package br.com.gerenciamento.repository;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testFindByStatusAtivo() {
        Aluno alunoAtivo1 = new Aluno();
        alunoAtivo1.setNome("Luiza");
        alunoAtivo1.setStatus(Status.ATIVO);
        alunoAtivo1.setMatricula("12345");
        alunoAtivo1.setCurso(Curso.ENFERMAGEM);
        alunoAtivo1.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoAtivo1);

        Aluno alunoAtivo2 = new Aluno();
        alunoAtivo2.setNome("Mariana");
        alunoAtivo2.setStatus(Status.ATIVO);
        alunoAtivo2.setMatricula("67890");
        alunoAtivo2.setCurso(Curso.BIOMEDICINA);
        alunoAtivo2.setTurno(Turno.NOTURNO);
        alunoRepository.save(alunoAtivo2);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertNotNull(alunosAtivos);
        Assert.assertEquals(2, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.stream().anyMatch(a -> a.getNome().equals("Luiza")));
        Assert.assertTrue(alunosAtivos.stream().anyMatch(a -> a.getNome().equals("Mariana")));
    }

    @Test
    public void testFindByStatusInativo() {
        Aluno alunoInativo1 = new Aluno();
        alunoInativo1.setNome("Amanda");
        alunoInativo1.setStatus(Status.INATIVO);
        alunoInativo1.setMatricula("11223");
        alunoInativo1.setCurso(Curso.DIREITO);
        alunoInativo1.setTurno(Turno.NOTURNO);
        alunoRepository.save(alunoInativo1);

        Aluno alunoInativo2 = new Aluno();
        alunoInativo2.setNome("Carlos");
        alunoInativo2.setStatus(Status.INATIVO);
        alunoInativo2.setMatricula("44556");
        alunoInativo2.setCurso(Curso.ENFERMAGEM);
        alunoInativo2.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoInativo2);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertNotNull(alunosInativos);
        Assert.assertEquals(2, alunosInativos.size());
        Assert.assertTrue(alunosInativos.stream().anyMatch(a -> a.getNome().equals("Amanda")));
        Assert.assertTrue(alunosInativos.stream().anyMatch(a -> a.getNome().equals("Carlos")));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Juliano");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("12345");
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Juvenal");
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("67890");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno2);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("ju");
        Assert.assertNotNull(alunos);
        Assert.assertEquals(2, alunos.size());
        Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Juliano")));
        Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Juvenal")));
    }

    @Test
    public void testFindByNomeContainingIgnoreCaseComNomeInvalido() {

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Juliano");
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("12345");
        aluno1.setCurso(Curso.BIOMEDICINA);
        aluno1.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Mariana");
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("67890");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setTurno(Turno.NOTURNO);
        alunoRepository.save(aluno2);

        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Pedro");

        Assert.assertNotNull(alunos);
        Assert.assertEquals(0, alunos.size());
    }

}