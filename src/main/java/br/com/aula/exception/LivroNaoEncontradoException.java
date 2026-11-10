package br.com.aula.exception;

public class LivroNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LivroNaoEncontradoException(Long id) {
        super("Livro não encontrado. ID: " + id);
    }
}