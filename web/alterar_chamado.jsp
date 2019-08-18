<%-- 
    Document   : alterar_chamado
    Created on : 18/08/2019, 19:50:19
    Author     : herbe
--%>
<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Alterar Chamado</h1>
        <form action="/ProjetoPFC_5/AlterarChamado" method="POST">
            <%
                Chamado chamado = (Chamado) request.getAttribute("chamado");

            %> 

            ID: <input type="text" readonly="true" name="txtId" value="<%=chamado.getId()%>"><br/>
            Descrição: <textarea name="txtDescricao" ><%=chamado.getDescricao()%></textarea><br/>
            Data Inicio: <input type="text" readonly="true" name="txtId" value="<%=chamado.getDescricao()%>"><br/>
            Status:<select name="optStatus">
                <%
                    for (StatusChamado status : StatusChamado.values()) {
                        if (status == chamado.getStatus()) {
                %> 
                <option selected><%=status%></option> 
                <%
                } else if (status.equals("TODOS")) {
                } else {
                %>
                <option> < %= status%></option> 
                <%}

                    }
                %></select><br/>
            Nome: <input type="text" readonly="true" name="txtNome" value="<%=chamado.getUsuario().getNome()%>"><br/>
            Categoria: <input type="text" readonly="true" name="txtSubcategoria" value="<%=chamado.getCategoria().getCategoria() %>"><br/>
            Subcategoria <input type="text" readonly="true" name="txtSubcategoria" value="<%=chamado.getSubcategoria().getSubcategoria() %>"><br/>
            Técnico: <input type="text" readonly="true" name="txtTecnico" value="<%=chamado.getTecnico().getNome()%>"><br/>
            Prioridade: <input type="text" readonly="true" name="txtPrioridade" value="<%=chamado.getPrioridade()%>"><br/>

            <input type="submit" value="Alterar"></br></br>

        </form>
        <a href="/ProjetoPFC_5/ConsultarSubcategoria?txtSubcategoria=">Voltar</a>
    </body>
</html>
