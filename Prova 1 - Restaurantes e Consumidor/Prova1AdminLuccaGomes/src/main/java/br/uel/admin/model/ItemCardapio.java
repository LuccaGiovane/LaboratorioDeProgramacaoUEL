package br.uel.admin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


@Entity
@Table(name = "item_cardapio")
public class ItemCardapio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String nome;

    private String descricao;

    @NotNull


    private double preco;

    // Campo que armazena o ID do restaurante na tabela do banco de dados
    @Column(name = "id_restaurante")
    private int idRestaurante;

    // Relacionamento com a tabela Restaurante
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id", insertable = false, updatable = false)
    private Restaurante restaurante;


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }


}
