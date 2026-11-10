package br.com.aula.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.aula.dto.AutorSaidaDto;
import br.com.aula.dto.LivroSaidaDto;
import br.com.aula.dto.PedidoEntradaDto;
import br.com.aula.dto.PedidoSaidaDto;
import br.com.aula.exception.LivroNaoEncontradoException;
import br.com.aula.model.LivroModel;
import br.com.aula.model.PedidoModel;
import br.com.aula.repository.LivroRepository;
import br.com.aula.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final LivroRepository livroRepository;

    public PedidoService(PedidoRepository pedidoRepository, LivroRepository livroRepository) {
        this.pedidoRepository = pedidoRepository;
        this.livroRepository = livroRepository;
    }

    public PedidoSaidaDto cadastrar(PedidoEntradaDto dto) {
        LivroModel livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new LivroNaoEncontradoException(dto.livroId()));

        PedidoModel pedido = new PedidoModel();
        pedido.setLivro(livro);
        pedido.setQuantidade(dto.quantidade());

        PedidoModel salvo = pedidoRepository.save(pedido);
        return converterParaDto(salvo);
    }

    public List<PedidoSaidaDto> listarTodos() {
        List<PedidoSaidaDto> resposta = new ArrayList<>();
        List<PedidoModel> itens = pedidoRepository.findAll();
        for (PedidoModel item : itens) {
            resposta.add(converterParaDto(item));
        }
        return resposta;
    }

    private PedidoSaidaDto converterParaDto(PedidoModel item) {
        AutorSaidaDto autorDto = new AutorSaidaDto(
                item.getLivro().getAutor().getId(),
                item.getLivro().getAutor().getNome()
        );
        LivroSaidaDto livroDto = new LivroSaidaDto(
                item.getLivro().getId(),
                item.getLivro().getTitulo(),
                item.getLivro().getAno(),
                autorDto
        );
        return new PedidoSaidaDto(item.getId(), livroDto, item.getQuantidade());
    }
}