<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Calculadora" %></h1>
<br/>
<form action="Calcular" >
    <label for="valor">valor 1:</label>
    <input type="number" id="valor" name="valor1" required>
    <br/>

    <label for="valor">valor 2:</label>
    <input type="number" id="valor2" name="valor2" required>
    <br/>

    <label for="operacao">Operação</label>
    <select id="operacao" name="operacao">
      <option value="adicao">Adição (+)</option>
      <option value="subtracao">Subtração (-)</option>
      <option value="multiplicacao">Multiplicação (x)</option>
      <option value="divisao">Divisão (:)</option>
    </select>
    <br/>

    <button type="submit"Enviar>Calcular</button>
</form>

</body>
</html>