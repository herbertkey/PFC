<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Categoria</title>
    </head>
    <body>
        <h1>Consultar Categorias</h1>
        <form action="/ProjetoPFC_5/ConsultarCategoria" method="GET">
            Categoria: <input type="text" name="txtCategoria"><br/>
            <input type="submit" value="Consultar">
        </form>
        <%
            List<Categoria> categorias = (List<Categoria>) request.getAttribute("consulta");

        %>        
        <table>            
            <thead>
                <tr>
                    <th>Categoria</th>
                    <th>Prioridade</th>
                </tr>
            </thead>            
            <tbody>                
                <%if (categorias != null) {
                        for (Categoria c : categorias) {
                %>                
                <tr>
                    <td> <%= c.getCategoria()%></td>
                    <td> <%= c.getPrioridade()%></td>          
                    <td><a href="/ProjetoPFC_5/AlterarPageCategoria?acao=<%=c.getCategoria()%>">Alterar</a>    </td>
                    <td><a href="/ProjetoPFC_5/ExcluirCategoria?acao=<%=c.getCategoria()%>">Excluir</a>    </td>
                </tr>       
                <%
                        }
                    }
                %>
            </tbody>  
        </table>
        <a href="/ProjetoPFC_5/admin/area_restrita.jsp">Voltar</a>
    </body>
</html>