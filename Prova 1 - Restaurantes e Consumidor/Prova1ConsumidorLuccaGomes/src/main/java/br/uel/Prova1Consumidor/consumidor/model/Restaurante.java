package br.uel.Prova1Consumidor.consumidor.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List; // Importe a classe List

@Entity
public class Restaurante implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @OneToMany(mappedBy = "idRestaurante", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemCardapio> itensCardapio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ItemCardapio> getItensCardapio() {
        return itensCardapio;
    }

    public void setItensCardapio(List<ItemCardapio> itensCardapio) {
        this.itensCardapio = itensCardapio;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }
}
