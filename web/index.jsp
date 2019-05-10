<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<%@page import="AutorizacaoDeAcesso.*"%>
<%@page import="util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Autenticação</title>
    </head>
    <body>
        <h1>Autenticação de Usuário!</h1>
        <%
            String msg = (String)request.getAttribute("msg");
            if(msg!=null){
        %>
        <font color="red"> <%=msg%></font>        
        <%}%>
        
        <form action="ControleAcesso" method="POST">
            Login: <input type="text" name="txtLogin"><br/>
            Senha: <input type="password" name="txtSenha"><br/>
            <input type="submit" value="Entrar" name="acao">
        </form>
    </body>
</html>
