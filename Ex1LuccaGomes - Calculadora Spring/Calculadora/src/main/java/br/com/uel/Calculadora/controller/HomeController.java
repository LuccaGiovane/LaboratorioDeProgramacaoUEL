package br.com.uel.Calculadora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    //tela inicial da calculadora
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/calcular")
    public String calcular(@RequestParam("operando1") double operando1,
                           @RequestParam("operando2") double operando2,
                           @RequestParam("simboloOperacao") String simboloOperacao,
                           Model model)
    {
        double resultado = 0;
        String nomeOperacao = "";

        try
        {
            switch (simboloOperacao)
            {
                case "soma":
                    resultado = operando1 + operando2;
                    nomeOperacao = "Soma";
                    break;

                case "subtracao":
                    resultado = operando1 - operando2;
                    nomeOperacao = "Subtração";
                    break;

                case "multiplicacao":
                    resultado = operando1 * operando2;
                    nomeOperacao = "Multiplicação";
                    break;

                case "divisao":
                    if (operando2 == 0)
                    {
                        if(operando1 == 0)
                        {
                            throw new ArithmeticException("Divisão do tipo zero por zero não é permitida");
                        }
                        throw new ArithmeticException("Divisão por zero não é permitida");
                    }
                    resultado = operando1 / operando2;
                    nomeOperacao = "Divisão";
                    break;

                default:

                    // Caso inválido
                    throw new IllegalArgumentException("Operação inválida: " + simboloOperacao);
            }

            model.addAttribute("resultado", resultado);
            model.addAttribute("nomeOperacao", nomeOperacao);

        }
        catch (ArithmeticException e)
        {
            // Lidar com a exceção de divisão por zero
            model.addAttribute("error", "Erro: " + e.getMessage());
            return "error"; // Página de erro

        }
        catch (IllegalArgumentException e)
        {
            // Lidar com outras exceções
            model.addAttribute("error", "Erro: " + e.getMessage());
            return "error"; // Página de erro
        }
        return "resultado";
    }
}