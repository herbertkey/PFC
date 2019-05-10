<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Area Restrita</title>
    </head>
    <body>  
        <h1>Área de acesso restrito aos administradores</h1>
        <h2>Cadastro de novo usuario!</h2>
        
        <%
            String msg = (String) request.getAttribute("msg");
            if(msg != null){
        %>
        <font color="blue"><%=msg%></font>
        <%}%>
        <form action="../ControleUsuario" method="POST">
            Login: <input type="text" name="txtLogin"><br/>
            Senha: <input type="text" name="txtSenha"><br/>
            Perfil: <select name="optPerfil">
                        <option>COMUM</option>
                        <option>ADMINISTRADOR</option>
                    </select><br/>
                    <input type="submit" value="Cadastrar" name="acao">
        </form>
        <a href="../principal.jsp">Pagina Principal</a>
    </body>
</html>
