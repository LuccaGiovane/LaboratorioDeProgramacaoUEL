package br.uel.Prova1Consumidor.consumidor.controller;

import br.uel.Prova1Consumidor.consumidor.model.ItemCardapio;
import br.uel.Prova1Consumidor.consumidor.model.Restaurante;
import br.uel.Prova1Consumidor.consumidor.repository.ItemCardapioRepository;
import br.uel.Prova1Consumidor.consumidor.repository.RestaurantesRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class ConsumidorController
{
    @Autowired
    RestaurantesRepository restauranteRepository;

    @Autowired
    ItemCardapioRepository itemCardapioRepository;

    private static final String SESSION_CARRINHO = "sessionCarrinho";

    @GetMapping(value={"/index", "/"})
    public String mostrarListaRestaurantes(Model model)
    {
        model.addAttribute("restaurantes", restauranteRepository.findAll());
        return "index";
    }

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



    @GetMapping("/carrinho")
    public String mostrarCarrinho(Model model, HttpSession session)
    {
        // verifica e atualiza os itens do carrinho antes de exibir a pagina
        verificarEAtualizarItensCarrinho(session);


        // calcula o valor total e a quantidade total de itens no carrinho
        List<ItemCardapio> itensCarrinho = (List<ItemCardapio>) session.getAttribute(SESSION_CARRINHO);
        if (itensCarrinho == null)
        {
            // inicializa a lista se estiver vazia
            itensCarrinho = new ArrayList<>();
        }
        else
        {
            double valorTotalCarrinho = 0;
            int quantidadeTotalItens = 0;

            for (ItemCardapio item : itensCarrinho)
            {
                valorTotalCarrinho += item.getValorTotalCarrinho();
                quantidadeTotalItens += item.getQuantidadeCarrinho();
            }

            // Adiciona os atributos ao modelo
            model.addAttribute("sessionCarrinho", itensCarrinho);
            model.addAttribute("valorTotalCarrinho", valorTotalCarrinho);
            model.addAttribute("quantidadeTotalItens", quantidadeTotalItens);
        }

        return "carrinho";
    }


    @PostMapping("/carrinho/{id}")
    public String adicionarAoCarrinho(@PathVariable int id, @RequestParam("quantidade") int quantidade, HttpServletRequest request)
    {
        ItemCardapio itemCardapio = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do produto é inválido: " + id));

        // obtemm a quantidade total no carrinho
        List<ItemCardapio> carrinho = (List<ItemCardapio>) request.getSession().getAttribute(SESSION_CARRINHO);

        // se o carrinho estiver vazio, inicializa ele
        if (CollectionUtils.isEmpty(carrinho))
        {
            carrinho = new ArrayList<>();
        }

        // verifica se o produto esta no carrinho
        Optional<ItemCardapio> produtoExistente = carrinho.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (produtoExistente.isPresent())
        {
            // se o produto já estiver no carrinho, atualiza a quantidade
            ItemCardapio produtoNoCarrinho = produtoExistente.get();

            produtoNoCarrinho.setQuantidadeCarrinho(produtoNoCarrinho.getQuantidadeCarrinho() + quantidade);
            produtoNoCarrinho.setValorTotalCarrinho(produtoNoCarrinho.getPreco() * produtoNoCarrinho.getQuantidadeCarrinho());
        }
        else
        {
        // Cria uma cópia do produto para adicionar ao carrinho
        ItemCardapio produtoCarrinho = new ItemCardapio();

        //Passa o nome do restaurante associado ao item
        int idRestaurante = itemCardapio.getIdRestaurante();
        Restaurante nomeRestaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        produtoCarrinho.setNomeRestaurante(nomeRestaurante.getNome());

        produtoCarrinho.setId(itemCardapio.getId());
        produtoCarrinho.setNome(itemCardapio.getNome());
        produtoCarrinho.setPreco(itemCardapio.getPreco());
        produtoCarrinho.setQuantidadeCarrinho(quantidade);
        produtoCarrinho.setValorTotalCarrinho(itemCardapio.getPreco() * quantidade);

        // Adiciona o produto ao carrinho
        carrinho.add(produtoCarrinho);
        }

        // Atualiza a sessão com o carrinho atualizado
        request.getSession().setAttribute(SESSION_CARRINHO, carrinho);

        return "redirect:/carrinho";
    }


    @PostMapping("/carrinho/incrementar/{id}")
    public String incrementarQuantidade(@PathVariable int id, HttpSession session)
    {
        List<ItemCardapio> carrinho = (List<ItemCardapio>) session.getAttribute(SESSION_CARRINHO);

        if (carrinho != null)
        {
            for (ItemCardapio item : carrinho)
            {
                if (item.getId() == id)
                {
                    item.setQuantidadeCarrinho(item.getQuantidadeCarrinho() + 1);
                    item.setValorTotalCarrinho(item.getPreco() * item.getQuantidadeCarrinho());
                    break;
                }
            }
            session.setAttribute(SESSION_CARRINHO, carrinho);
        }

        return "redirect:/carrinho";
    }


    @PostMapping("/carrinho/decrementar/{id}")
    public String decrementarQuantidade(@PathVariable int id, HttpSession session)
    {
        List<ItemCardapio> carrinho = (List<ItemCardapio>) session.getAttribute(SESSION_CARRINHO);

        if (carrinho != null)
        {
            for (ItemCardapio item : carrinho)
            {
                if (item.getId() == id)
                {
                    int novaQuantidade = item.getQuantidadeCarrinho() - 1;

                    if (novaQuantidade > 0)
                    {
                        item.setQuantidadeCarrinho(novaQuantidade);
                        item.setValorTotalCarrinho(item.getPreco() * novaQuantidade);
                    }
                    else
                    {
                        carrinho.remove(item);
                    }
                    break;
                }
            }
            session.setAttribute(SESSION_CARRINHO, carrinho);
        }

        return "redirect:/carrinho";
    }


    @GetMapping("/carrinho/remover/{id}")
    public String removerCarrinho(@PathVariable("id") int id, HttpServletRequest request)
    {
        List<ItemCardapio> sessionCarrinho = (List<ItemCardapio>) request.getSession().getAttribute(SESSION_CARRINHO);

        ItemCardapio itemToRemove = sessionCarrinho.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);

        if (itemToRemove != null)
        {
            sessionCarrinho.remove(itemToRemove);
            request.getSession().setAttribute(SESSION_CARRINHO, sessionCarrinho);
        }

        return "redirect:/carrinho";
    }


    /* sempre que de alguma forma o carrinho for aberto vai chamar esse metodo. Ele serve para que quando for atualizado
    * alguma coisa no banco, seja o nome do restaurante, nome do prato, valor, e ate se o restaurante ou o prato forem
    * apagados atualizara o carrinho na session */
    private void verificarEAtualizarItensCarrinho(HttpSession session)
    {
        List<ItemCardapio> itensCarrinho = (List<ItemCardapio>) session.getAttribute(SESSION_CARRINHO);

        //pode ser que o carrinho ainda esteja vazio ou o que tinha nele foi deletado
        if (itensCarrinho != null)
        {
            //se o item no carrinho ainda existe no banco
            Iterator<ItemCardapio> iterator = itensCarrinho.iterator();

            while (iterator.hasNext())
            {
                ItemCardapio item = iterator.next();
                Optional<ItemCardapio> itemAtualizadoOptional = itemCardapioRepository.findById(item.getId());

                if (itemAtualizadoOptional.isPresent())
                {
                    ItemCardapio itemAtualizado = itemAtualizadoOptional.get();

                    //pega o nome do restaurante associado ao prato (item)
                    ItemCardapio itemCardapio = itemCardapioRepository.findById(item.getId())
                            .orElseThrow(() -> new IllegalArgumentException("O id do produto é inválido: " + item.getId()));
                    int idRestaurante = itemCardapio.getIdRestaurante();
                    Restaurante nomeRestaurante = restauranteRepository.findById(idRestaurante)
                            .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

                    //atualiza o nome do restaurante associado ao item
                    item.setNomeRestaurante(nomeRestaurante.getNome());
                    // atualiza os dados do item no carrinho
                    item.setNome(itemAtualizado.getNome());
                    item.setDescricao(itemAtualizado.getDescricao());
                    item.setPreco(itemAtualizado.getPreco());

                    //atualiza o preço pois as vezes a alteraçao muda o valor entao a transient deve ser atualizada tambem
                    item.setValorTotalCarrinho(item.getQuantidadeCarrinho() * item.getPreco());

                }
                else
                {
                    // remove o item do carrinho se não existir mais no banco de dados
                    iterator.remove();
                }
            }
            // atualiza a sessão com os itens do carrinho atualizados
            session.setAttribute(SESSION_CARRINHO, itensCarrinho);
        }
    }
}