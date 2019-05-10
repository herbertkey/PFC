<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Status</title>
    </head>
    <body>  
        <h1>Área de acesso restrito aos administradores</h1>
        <h2>Cadastro de novo Status de Chamado!</h2>
        <%
            String msg = (String) request.getAttribute("msg");
            if(msg != null){
        %>
        <font color="blue"><%=msg%></font>
        <%}%>        
        <form action="ControleStatus" method="POST">
            Categoria: <input type="text" name="txtStatus"><br/>
        <input type="submit" value="Cadastrar_Status" name="acao">
        </form>
    </body>
</html>
