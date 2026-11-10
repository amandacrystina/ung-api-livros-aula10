
package br.com.aula.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.aula.dto.AutorSaidaDto;
import br.com.aula.dto.LivroEntradaDto;
import br.com.aula.dto.LivroSaidaDto;
import br.com.aula.model.AutorModel;
import br.com.aula.model.LivroModel;
import br.com.aula.repository.AutorRepository;
import br.com.aula.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AutorService autorService;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, AutorService autorService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.autorService = autorService;
    }

    public LivroSaidaDto Cadastrar(LivroEntradaDto obj) {
        AutorModel autor = autorRepository.findById(obj.autorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        LivroModel livro = new LivroModel();
        livro.setTitulo(obj.titulo());
        livro.setAno(obj.ano());
        livro.setAutor(autor);

        LivroModel itemSalvo = this.livroRepository.save(livro);
        return this.ConverterParaDto(itemSalvo);
    }

    public LivroSaidaDto ConsultarLivro(Long id) {
        LivroModel livro = this.livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return this.ConverterParaDto(livro);
    }

    public List<LivroSaidaDto> ListarLivros() {
        List<LivroSaidaDto> resposta = new ArrayList<>();
        for (LivroModel item : this.livroRepository.findAll()) {
            resposta.add(this.ConverterParaDto(item));
        }
        return resposta;
    }

    public List<LivroSaidaDto> listarPorAutor(Long autorId) {
        List<LivroSaidaDto> resposta = new ArrayList<>();
        for (LivroModel item : this.livroRepository.findByAutorId(autorId)) {
            resposta.add(this.ConverterParaDto(item));
        }
        return resposta;
    }

    private LivroSaidaDto ConverterParaDto(LivroModel item) {
        AutorSaidaDto autorDto = null;
        if (item.getAutor() != null) {
            autorDto = autorService.converterParaDto(item.getAutor());
        }

        return new LivroSaidaDto(
            item.getId(),
            item.getTitulo(),
            item.getAno(),
            autorDto
        );
    }
}