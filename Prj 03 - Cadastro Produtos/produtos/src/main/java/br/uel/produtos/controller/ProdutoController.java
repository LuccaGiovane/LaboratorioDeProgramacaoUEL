package br.uel.produtos.controller;

import br.uel.produtos.model.Produto;
import br.uel.produtos.repository.ProdutosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdutoController
{
    @Autowired
    ProdutosRepository repository;

    @GetMapping("/novo-produto")
    public String mostrarFormNovoProduto(Produto produto)
    {
        return "novo-produto";
    }


    @GetMapping(value ={"/index", "/"})
    public String mostrarListaProdutos(Model model)
    {
       model.addAttribute ("produtos", repository.findAll());
       return "index";
   }


    @PostMapping("/adicionar-produto")
    public String adicionarProduto(@Valid Produto produto, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "/novo-produto";
        }


        repository.save(produto);
        return "redirect:/index";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormAtualizar(@PathVariable("id") int id, Model model)
    {

    Produto contato = repository.findById(id)
           .orElseThrow(() -> new IllegalArgumentException(
                        "O id do produto é inválido:" + id));
    model.addAttribute("produto", contato);
    return "atualizar-produto";
    }

    @PostMapping("/atualizar-produto/{id}")
    public String atualizarProduto(@PathVariable("id") int id, @Valid Produto produto,
                            BindingResult result, Model model) {
       if (result.hasErrors()) {
           produto.setId(id);
           return "atualizar-produto";
       }


       repository.save(produto);
       return "redirect:/index";
    }

    @GetMapping("/remover/{id}")
    public String removerProduto(@PathVariable("id") int id) {
    Produto produtoDeletado = repository.findById(id)
           .orElseThrow(() -> new IllegalArgumentException("O id do " +
                   "contato é inválido:" + id));
   repository.delete(produtoDeletado);
   return "redirect:/index";
}
}
