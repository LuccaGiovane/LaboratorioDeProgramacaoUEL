package br.uel.cadastro.repository;


import br.uel.cadastro.model.Contato;
import org.springframework.data.repository.CrudRepository;


public interface ContatoRepository extends CrudRepository<Contato,Integer> { }