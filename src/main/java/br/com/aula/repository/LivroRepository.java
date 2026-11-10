package br.com.aula.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.aula.model.LivroModel;

public interface LivroRepository extends JpaRepository<LivroModel, Long> {
    List<LivroModel> findByAutorId(Long autorId);
}