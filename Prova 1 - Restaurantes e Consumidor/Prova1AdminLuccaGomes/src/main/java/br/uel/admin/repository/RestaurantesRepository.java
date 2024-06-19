package br.uel.admin.repository;

import br.uel.admin.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantesRepository extends JpaRepository<Restaurante, Integer> {
    // Método para encontrar o ID do restaurante pelo nome
    @Query("SELECT r.id FROM Restaurante r WHERE r.nome = :nome")
    Integer findRestauranteIdByNome(@Param("nome") String nome);

    @Query("SELECT r FROM Restaurante r WHERE r.nome = :nomeRestaurante")
    Restaurante findRestauranteByNome(String nomeRestaurante);
}
