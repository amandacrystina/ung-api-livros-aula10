package br.com.aula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.aula.model.AutorModel;

@Repository
public interface AutorRepository extends JpaRepository<AutorModel, Long> {
}