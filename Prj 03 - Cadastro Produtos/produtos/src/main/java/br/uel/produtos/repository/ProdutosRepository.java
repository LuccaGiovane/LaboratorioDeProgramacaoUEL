package br.uel.produtos.repository;

import br.uel.produtos.model.Produto;
import org.springframework.data.repository.CrudRepository;

public interface ProdutosRepository extends CrudRepository<Produto,Integer> {
}
