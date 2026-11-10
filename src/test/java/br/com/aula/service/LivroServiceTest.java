package br.com.aula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.aula.dto.LivroEntradaDto;
import br.com.aula.dto.LivroSaidaDto;
import br.com.aula.exception.LivroJaCadastradoException;
import br.com.aula.exception.LivroNaoEncontradoException;
import br.com.aula.model.AutorModel;
import br.com.aula.model.LivroModel;
import br.com.aula.repository.AutorRepository;
import br.com.aula.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LivroService livroService;


    @Test
    void deveRetornarLivroQuandoEncontrado() {
        AutorModel autor = new AutorModel(1L, "Machado de Assis");
        LivroModel livro = new LivroModel();
        livro.setId(1L);
        livro.setTitulo("Dom Casmurro");
        livro.setAno(1899);
        livro.setAutor(autor);

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        LivroSaidaDto resultado = livroService.ConsultarLivro(1L);

        assertEquals("Dom Casmurro", resultado.titulo());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {
        when(livroRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(
            LivroNaoEncontradoException.class,
            () -> livroService.ConsultarLivro(50L)
        );
    }


    @Test
    void deveCadastrarLivroComSucesso() {
        LivroEntradaDto dto = new LivroEntradaDto("Clean Code", 2008, 1L);
        AutorModel autor = new AutorModel(1L, "Robert C. Martin");

        LivroModel livroSalvo = new LivroModel();
        livroSalvo.setId(10L);
        livroSalvo.setTitulo("Clean Code");
        livroSalvo.setAno(2008);
        livroSalvo.setAutor(autor);

        when(livroRepository.findByTitulo("Clean Code")).thenReturn(Optional.empty());
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any(LivroModel.class))).thenReturn(livroSalvo);

        LivroSaidaDto resultado = livroService.Cadastrar(dto);

        assertNotNull(resultado);
        assertEquals(10L, resultado.id());
        assertEquals("Clean Code", resultado.titulo());
        verify(livroRepository).save(any(LivroModel.class));
    }

    @Test
    void deveLancarExcecaoQuandoCadastrarLivroDuplicado() {
        LivroEntradaDto dto = new LivroEntradaDto("Clean Code", 2008, 1L);
        LivroModel livroExistente = new LivroModel();
        livroExistente.setTitulo("Clean Code");

        when(livroRepository.findByTitulo("Clean Code")).thenReturn(Optional.of(livroExistente));

        assertThrows(
            LivroJaCadastradoException.class,
            () -> livroService.Cadastrar(dto)
        );
    }
}