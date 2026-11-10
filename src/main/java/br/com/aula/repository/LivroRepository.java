package br.com.aula.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.aula.model.LivroModel;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {
    List<LivroModel> findByAutorId(Long autorId);
    Optional<LivroModel> findByTitulo(String titulo);
}