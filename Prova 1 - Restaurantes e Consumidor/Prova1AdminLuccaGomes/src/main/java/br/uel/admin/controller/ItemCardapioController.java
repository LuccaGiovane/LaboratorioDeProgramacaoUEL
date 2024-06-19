package br.uel.admin.controller;

import br.uel.admin.model.ItemCardapio;
import br.uel.admin.model.Restaurante;
import br.uel.admin.repository.ItemCardapioRepository;
import br.uel.admin.repository.RestaurantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class ItemCardapioController
{

    @Autowired
    ItemCardapioRepository itemCardapioRepository;

    @Autowired
    RestaurantesRepository restauranteRepository;

    @GetMapping("/cardapio/{id}")
    public String mostrarFormCardapio(@PathVariable("id") int id, Model model)
    {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        List<ItemCardapio> itensCardapio = itemCardapioRepository.findByIdRestaurante(id);

        model.addAttribute("restaurante", restaurante);
        model.addAttribute("itensCardapio", itensCardapio);

        return "cardapio";
    }

    @GetMapping("/novo-item-cardapio/{id}")
    public String mostrarFormNovoItemCardapio(@PathVariable("id") int id, Model model)
    {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        model.addAttribute("restaurante", restaurante);
        model.addAttribute("itemCardapio", new ItemCardapio());

        return "novo-item-cardapio";
    }


    @PostMapping("/adicionar-prato")
    public String adicionarPrato(@Valid ItemCardapio itemCardapio, BindingResult result, Model model)
    {

        if (result.hasErrors())
        {
            return "novo-item-cardapio";
        }

        int idRestaurante = itemCardapio.getIdRestaurante();
        System.out.println("trace01, id do restaurante: "+ idRestaurante);

        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        itemCardapio.setIdRestaurante(idRestaurante);

        itemCardapioRepository.save(itemCardapio);


        return "redirect:/cardapio/" + restaurante.getId();
    }


    @GetMapping("/editar-prato/{id}")
    public String mostrarFormEditarPrato(@PathVariable("id") int id, Model model)
    {
        ItemCardapio itemCardapio = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));

        model.addAttribute("itemCardapio", itemCardapio);
        return "editar-item-cardapio";
    }

    @PostMapping("/editar-prato/{id}")
    public String editarPrato(@PathVariable("id") int id,
                              @Valid ItemCardapio itemCardapio,
                              BindingResult result,
                              Model model)
    {
        if (result.hasErrors())
        {
            return "editar-item-cardapio";
        }


        ItemCardapio existingItem = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));

        // atualiza os campos do item do cardapio com os novos valores
        existingItem.setNome(itemCardapio.getNome());
        existingItem.setDescricao(itemCardapio.getDescricao());
        existingItem.setPreco(itemCardapio.getPreco());


        itemCardapioRepository.save(existingItem);


        return "redirect:/cardapio/" + existingItem.getIdRestaurante();
    }


    @GetMapping("/apagar-prato/{id}")
    public String apagarPrato(@PathVariable("id") int id)
    {
        ItemCardapio existingItem = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));

        int idVoltar = existingItem.getIdRestaurante();

        // remove o item do cardápio da lista de itens do restaurante antes de excluir
        Restaurante restaurante = existingItem.getRestaurante();
        if (restaurante != null)
        {
            restaurante.getItensCardapio().remove(existingItem);
        }

        itemCardapioRepository.delete(existingItem);

        return "redirect:/cardapio/" + idVoltar;

    }
}