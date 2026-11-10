package br.com.aula.exception;

public class AutorNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AutorNaoEncontradoException(Long id) {
        super("Autor não encontrado. ID: " + id);
    }
}