<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Categoria</title>
    </head>
    <body>  
        <h1>Área de acesso restrito aos administradores</h1>
        <h2>Cadastro de nova categoria!</h2>

        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {
        %>
        <font color="blue"><%=msg%></font>
        <%
            }
        %>
        <form action="/ProjetoPFC_5/CadastrarCategoria" method="POST">
            Categoria: <input type="text" name="txtCategoria"><br/>
            Prioridade: <select name="optPrioridade"><%
                for (Prioridade prioridade : Prioridade.values()) {
                %>
                <option ><%=prioridade%></option> 
                <%
                    }
                %></select><br/>     
        <input type="submit" value="Cadastrar">
    </form>
    <a href="/ProjetoPFC_5/principal.jsp">Pagina Principal</a>
</body>
</html>
