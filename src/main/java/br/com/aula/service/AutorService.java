package br.com.aula.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.aula.dto.AutorEntradaDto;
import br.com.aula.dto.AutorSaidaDto;
import br.com.aula.exception.AutorNaoEncontradoException;
import br.com.aula.model.AutorModel;
import br.com.aula.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorSaidaDto cadastrar(AutorEntradaDto dto) {
        AutorModel autor = new AutorModel();
        autor.setNome(dto.nome());
        AutorModel salvo = this.autorRepository.save(autor);
        return converterParaDto(salvo);
    }

    public List<AutorSaidaDto> listarTodos() {
        List<AutorSaidaDto> resposta = new ArrayList<>();
        List<AutorModel> itens = this.autorRepository.findAll();
        for (AutorModel item : itens) {
            resposta.add(converterParaDto(item));
        }
        return resposta;
    }

    public AutorSaidaDto buscarPorId(Long id) {
        AutorModel autor = this.autorRepository.findById(id)
                .orElseThrow(() -> new AutorNaoEncontradoException(id));
        return converterParaDto(autor);
    }

    private AutorSaidaDto converterParaDto(AutorModel item) {
        return new AutorSaidaDto(item.getId(), item.getNome());
    }
}