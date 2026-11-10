package br.com.aula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.aula.model.AutorModel;

public interface AutorRepository extends JpaRepository<AutorModel, Long> {
}