<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Categoria</title>
    </head>
        <body>
        <h1>Alterar Categoria</h1>
        <form action="/ProjetoPFC_5/AlterarCategoria" method="POST">
            <%
                Categoria categoria = (Categoria) request.getAttribute("categoria");

            %> 

            Categoria: <input type="text" readonly="true" name="txtCategoria" value="<%=categoria.getCategoria()%>"><br/>
            Prioridade: <select name="optPrioridade">
                <%
                for (Prioridade prioridade : Prioridade.values()) {
                    if (prioridade == categoria.getPrioridade()) {
                %> 
                <option selected><%=prioridade%></option> 
                <%
                } else {
                %>
                <option><%=prioridade%></option> 
                <%
                        }

                    }
                %></select><br/>

            <input type="submit" value="Alterar"></br></br>

        </form>
        <a href="/ProjetoPFC_5/ConsultarCategoria?txtCategoria=">Voltar</a>
    </body>

</html>
