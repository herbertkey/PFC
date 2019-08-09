<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de nova Subcategoria</title>
    </head>
    <body>  
        <h1>Área de acesso restrito aos administradores</h1>
        <h2>Cadastro de nova Subcategoria!</h2>

        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {
        %>
        <font color="blue"><%=msg%></font>
        <%
            }
        %>
        <form action="/ProjetoPFC_5/CadastrarSubcategoria" method="POST">
            <%List<Categoria> categorias = (List<Categoria>) request.getAttribute("consultacategoria");%>
            Categoria: <select name="optCategoria">
                <%
                    for (Categoria c : categorias) {
                %> 
                <option ><%=c.getCategoria()%></option> 
                <%
                    }
                %></select><br/>     
            Subcategoria: <input type="text" name="txtSubcategoria"><br/>
            Prioridade: <select name="optPrioridade"><%
                for (Prioridade prioridade : Prioridade.values()) {
                %>
                <option ><%=prioridade%></option> 
                <%
                    }
                %></select><br/>     
        <input type="submit" value="Cadastrar" name="acao">
    </form>
    <a href="/ProjetoPFC_5/principal.jsp">Pagina Principal</a>
    </body>
</html>
