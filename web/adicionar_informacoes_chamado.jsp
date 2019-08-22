<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adicionar Informções ao Chamado</title>
    </head>
    <body>
        <h1>Adicionar informações ao Chamado</h1>
        <form action="/ProjetoPFC_5/AlterarPageChamado" method="POST">
            <%
            //recupera o usuario da sessão
                Usuario usuario = (Usuario)session.getAttribute("usuarioAutenticado");

                Chamado chamado = (Chamado) request.getAttribute("chamado");

            %> 
            Numero de Registro: <input type="text" readonly="true" name="txtNumeroDeRegistro" value="<%=usuario.getNumero_registro()%>"><br/>
            Nome: <input type="text" name="txtNome" readonly="true" value="<%=usuario.getNome()%>"><br/><br/>

            ID: <input type="text" readonly="true" name="txtId" value="<%=chamado.getId()%>"><br/>
            Descrição: <textarea readonly="true" name="txtDescricao" ><%=chamado.getDescricao()%></textarea><br/><br/>
            Adicionar Informação: <textarea name="txtInformacoesAdicionais" ></textarea><br/>
            
            <input type="submit" name="acao" value="Adicionar"></br></br>
        </form>
        <a href="/ProjetoPFC_5/AlterarPageChamado?acao=<%=chamado.getId()%>">Voltar</a>
    </body>
</html>
