package br.uel.admin.repository;

import br.uel.admin.model.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
    List<ItemCardapio> findByIdRestaurante(int restauranteId);

    @Modifying
    @Query("DELETE FROM ItemCardapio i WHERE i.id = :itemId")
    void apagaPorId(int itemId);
}
