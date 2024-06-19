package br.uel.Prova1Consumidor.consumidor.repository;


import br.uel.Prova1Consumidor.consumidor.model.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
    List<ItemCardapio> findByIdRestaurante(int restauranteId);
}
