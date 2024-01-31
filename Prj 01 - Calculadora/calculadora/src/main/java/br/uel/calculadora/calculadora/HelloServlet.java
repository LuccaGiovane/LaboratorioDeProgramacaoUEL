package br.uel.calculadora.calculadora;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/Calcular")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Resultado";
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int num1 = Integer.parseInt(request.getParameter("valor1"));
        int num2 = Integer.parseInt(request.getParameter("valor2"));
        int resultado = 0;

        String simboloOp = "";

        String operacao = request.getParameter("operacao");
        switch (operacao) {
            case "adicao":
                resultado = num1+num2;
                simboloOp = "+";
                break;

            case "subtracao":
                resultado = num1 - num2;
                simboloOp = "-";
                break;

            case "multiplicacao":
                resultado = num1 * num2;
                simboloOp = "x";
                break;

            case "divisao":
                if (num2 != 0) {
                    resultado = num1 / num2;
                    simboloOp = ":";
                } else {
                    System.out.println("Não é possível dividir por zero.");
                }
                break;

            default:
                System.out.println("Escolha inválida.");
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>"+num1 +simboloOp +num2 +"=" +resultado + "</h1>");
        out.println("</body></html>");
    }

}