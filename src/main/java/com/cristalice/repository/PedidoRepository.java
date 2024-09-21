package com.cristalice.repository;

import com.cristalice.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.data = ?1")
    List<Pedido> findByData(LocalDate data);

    @Query("SELECT p FROM Pedido p WHERE p.data BETWEEN ?1 AND ?2")
    List<Pedido> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
}
