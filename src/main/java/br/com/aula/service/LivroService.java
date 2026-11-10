package br.com.aula.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.aula.dto.AutorSaidaDto;
import br.com.aula.dto.LivroEntradaDto;
import br.com.aula.dto.LivroSaidaDto;
import br.com.aula.exception.AutorNaoEncontradoException;
import br.com.aula.exception.LivroJaCadastradoException;
import br.com.aula.exception.LivroNaoEncontradoException;
import br.com.aula.model.AutorModel;
import br.com.aula.model.LivroModel;
import br.com.aula.repository.AutorRepository;
import br.com.aula.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepositorio;
    private final AutorRepository autorRepositorio;

    public LivroService(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public LivroSaidaDto Cadastrar(LivroEntradaDto obj) {
        // Validação de duplicidade (requisito da Aula 10 / desafio)
        if (livroRepositorio.findByTitulo(obj.titulo()).isPresent()) {
            throw new LivroJaCadastradoException(obj.titulo());
        }

        AutorModel autor = autorRepositorio.findById(obj.autorId())
                .orElseThrow(() -> new AutorNaoEncontradoException(obj.autorId()));

        LivroModel livro = new LivroModel();
        livro.setTitulo(obj.titulo());
        livro.setAno(obj.ano());
        livro.setAutor(autor);

        LivroModel itemSalvo = this.livroRepositorio.save(livro);
        return this.ConverterParaDto(itemSalvo);
    }

    public LivroSaidaDto ConsultarLivro(Long id) {
        LivroModel livro = this.livroRepositorio.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
        return this.ConverterParaDto(livro);
    }

    public List<LivroSaidaDto> ListarLivros() {
        List<LivroSaidaDto> resposta = new ArrayList<>();
        List<LivroModel> itens = this.livroRepositorio.findAll();
        for (LivroModel item : itens) {
            resposta.add(this.ConverterParaDto(item));
        }
        return resposta;
    }

    public List<LivroSaidaDto> listarPorAutor(Long autorId) {
        List<LivroSaidaDto> resposta = new ArrayList<>();
        List<LivroModel> itens = this.livroRepositorio.findByAutorId(autorId);
        for (LivroModel item : itens) {
            resposta.add(this.ConverterParaDto(item));
        }
        return resposta;
    }

    private LivroSaidaDto ConverterParaDto(LivroModel item) {
        AutorSaidaDto autorDto = new AutorSaidaDto(item.getAutor().getId(), item.getAutor().getNome());
        return new LivroSaidaDto(
                item.getId(),
                item.getTitulo(),
                item.getAno(),
                autorDto
        );
    }
}