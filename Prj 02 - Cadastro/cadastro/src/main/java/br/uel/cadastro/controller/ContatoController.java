package br.uel.cadastro.controller;


import br.uel.cadastro.model.Contato;
import br.uel.cadastro.repository.ContatoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ContatoController {


   @Autowired
   ContatoRepository contatoRepository;


   @GetMapping("/novo-contato")
   public String mostrarFormNovoContato(Contato contato){
       return "novo-contato";
   }


   @GetMapping(value={"/index", "/"})
   public String mostrarListaContatos(Model model) {
       model.addAttribute("contatos", contatoRepository.findAll());
       return "index";
   }


   @PostMapping("/adicionar-contato")
   public String adicionarContato(@Valid Contato contato, BindingResult result) {
       if (result.hasErrors()) {
           return "/novo-contato";
       }


       contatoRepository.save(contato);
       return "redirect:/index";
   }


   @GetMapping("/editar/{id}")
   public String mostrarFormAtualizar(@PathVariable("id") int id, Model model) {
   Contato contato = contatoRepository.findById(id)
           .orElseThrow(() -> new IllegalArgumentException(
                        "O id do contato é inválido:" + id));
   model.addAttribute("contato", contato);
   return "atualizar-contato";
}

    @PostMapping("/atualizar/{id}")
    public String atualizarContato(@PathVariable("id") int id, @Valid Contato contato,
                            BindingResult result, Model model) {
       if (result.hasErrors()) {
           contato.setId(id);
           return "atualizar-contato";
       }


       contatoRepository.save(contato);
       return "redirect:/index";
    }

    @GetMapping("/remover/{id}")
    public String removerContato(@PathVariable("id") int id) {
    Contato contato = contatoRepository.findById(id)
           .orElseThrow(() -> new IllegalArgumentException("O id do " +
                   "contato é inválido:" + id));
   contatoRepository.delete(contato);
   return "redirect:/index";
}

}

