package br.com.aula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.aula.model.PedidoModel;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
}