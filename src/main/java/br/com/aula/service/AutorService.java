package br.com.aula.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.aula.dto.AutorEntradaDto;
import br.com.aula.dto.AutorSaidaDto;
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
        AutorModel salvo = autorRepository.save(autor);
        return converterParaDto(salvo);
    }

    public List<AutorSaidaDto> listarTodos() {
        List<AutorSaidaDto> dtos = new ArrayList<>();
        for (AutorModel autor : autorRepository.findAll()) {
            dtos.add(converterParaDto(autor));
        }
        return dtos;
    }

    public AutorSaidaDto buscarPorId(Long id) {
        AutorModel autor = autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        return converterParaDto(autor);
    }

    public AutorSaidaDto converterParaDto(AutorModel autor) {
        return new AutorSaidaDto(autor.getId(), autor.getNome());
    }
}