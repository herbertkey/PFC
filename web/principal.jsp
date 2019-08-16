<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página Principal</title>
    </head>
    <body>
        <%
            //recupera o usuario da sessão
            Usuario usuario = (Usuario)session.getAttribute("usuarioAutenticado");
            
            if(usuario !=null){
        %> 
        <h1>Bem-vindo, <%=usuario.getNome()%>!</h1>
        <%}%>
        <a href="/ProjetoPFC_5/admin/area_restrita.jsp">Área restrita</a><br/><br/>
        <a href="/ProjetoPFC_5/AbrirChamado?acao=">Abrir Chamado</a><br/><br/>
        <a href="/ProjetoPFC_5/ConsultarChamado?acao=">Consultar Chamado</a><br/><br/>
        <a href="/ProjetoPFC_5/ControleAcesso?acao=Sair">Logout</a><br/>
    </body>
</html>
