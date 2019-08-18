<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Subcategoria</title>
    </head>
    <body>
        <h1>Alterar Subcategoria</h1>
        <form action="/ProjetoPFC_5/AlterarSubategoria" method="POST">
            <%
                Subcategoria subcategoria = (Subcategoria) request.getAttribute("subcategoria");

            %> 

            Subcategoria: <input type="text" readonly="true" name="txtSubcategoria" value="<%=subcategoria.getSubcategoria()%>"><br/>
            Prioridade: <select name="optPrioridade">
                <%
                for (Prioridade prioridade : Prioridade.values()) {
                    if (prioridade == subcategoria.getPrioridade()) {
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
                Categoria: <input type="text" readonly="true" name="txtCategoria" value="<%=subcategoria.getCategoria().getCategoria() %>"><br/>

            <input type="submit" value="Alterar"></br></br>

        </form>
        <a href="/ProjetoPFC_5/ConsultarSubcategoria?txtSubcategoria=">Voltar</a>
    </body>
</html>
