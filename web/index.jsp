<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<%@page import="AutorizacaoDeAcesso.*"%>
<%@page import="util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login de Usuário!</h1>
        <%
            String msg = (String)request.getAttribute("msg");
            if(msg!=null){
        %>
        <font color="red"> <%=msg%></font>        
        <%}%>
        
        <form action="ControleAcesso" method="POST">
            Numero de Registro: <input type="text" name="txtNumeroDeRegistro" pattern="[0-9]+$"><br/>
            Senha: <input type="password" name="txtSenha"><br/>
            <input type="submit" value="Entrar" name="acao">
        </form>
    </body>
</html>
