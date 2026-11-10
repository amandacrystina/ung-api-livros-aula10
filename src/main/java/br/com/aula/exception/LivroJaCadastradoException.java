package br.com.aula.exception;

public class LivroJaCadastradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LivroJaCadastradoException(String titulo) {
        super("Já existe um livro cadastrado com o título: " + titulo);
    }
}