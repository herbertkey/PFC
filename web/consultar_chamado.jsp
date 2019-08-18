<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Chamado</title>
    </head>
    <body>
        <h1>Consultar Usuarios</h1>
        <%
            //recupera o usuario da sessão
            Usuario usuario = (Usuario)session.getAttribute("usuarioAutenticado");
        %>
        <form action="/ProjetoPFC_5/ConsultarChamado" method="GET">
            Numero de Registro: <input type="text" readonly="true" name="txtNumeroDeRegistro" value="<%=usuario.getNumero_registro()%>"><br/>
            Nome: <input type="text" name="txtNome" readonly="true" value="<%=usuario.getNome()%>"><br/>
            Status:<select name="optStatus"><%
                for (StatusChamado status : StatusChamado.values()) {
                %>
                <option ><%=status%></option> 
                <%
                    }
                %>     
                <input type="submit" value="Consultar" name="acao">
        </form>
        <%
            List<Chamado> chamados = (List<Chamado>) request.getAttribute("consulta");

        %>        
        <table>            
            <thead>
                <tr>
                    <th>Descrição</th>
                    <th>Data Inicio</th>
                    <th>Data Fim</th>
                    <th>Status</th>
                    <th>Nome</th>
                    <th>Categoria</th> 
                    <th>Subcategoria</th> 
                    <th>Tecnico</th>
                    <th>Prioridade</th>
                    <th>Alterar</th>
                    <th>Excluir</th>
                </tr>
            </thead>            
            <tbody>                
                <%if (chamados != null) {
                        for (Chamado c : chamados) {
                %>                
                <tr>
                    <td> <%= c.getDescricao()%></td>
                    <td> <%= c.getData_inicio()%></td>
                    <td> <%= c.getData_fim()%></td>
                    <td> <%= c.getStatus()%></td>
                    <td> <%= c.getUsuario().getNome() %></td>
                    <td> <%= c.getCategoria().getCategoria()%></td>
                    <td> <%= c.getSubcategoria().getSubcategoria() %></td>
                    <td> <%= c.getTecnico().getNome() %></td>  
                    <td> <%= c.getPrioridade() %></td>  
                    <td>Alterar</td>
                    <td>Excluir</td>
                </tr>       
                <%
                        }
                    }
                %>
            </tbody>  
        </table>
        <a href="/ProjetoPFC_5/principal.jsp">Voltar</a>
    </body>
</html>
