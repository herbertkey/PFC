<%@page import="model.*"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Subcategoria</title>
    </head>
    <body>
        <h1>Consultar Subcategorias</h1>
        <form action="/ProjetoPFC_5/ConsultarSubcategoria" method="GET">
            Subategoria: <input type="text" name="txtSubcategoria"><br/>
            <input type="submit" value="Consultar">
        </form>
        <%
            List<Subcategoria> subcategorias = (List<Subcategoria>) request.getAttribute("consulta");

        %>        
        <table>            
            <thead>
                <tr>
                    <th>Subcategoria</th>
                    <th>Prioridade</th>
                    <th>Categoria</th>
                </tr>
            </thead>            
            <tbody>                
                <%if (subcategorias != null) {
                        for (Subcategoria s : subcategorias) {
                %>                
                <tr>
                    <td> <%= s.getSubcategoria()%></td>
                    <td> <%= s.getPrioridade()%></td>    
                    <td> <%= s.getCategoria().getCategoria()%></td> 
                    <td><a href="/ProjetoPFC_5/AlterarPageSubcategoria?acao=<%=s.getSubcategoria()%>">Alterar</a>    </td>
                    <td><a href="/ProjetoPFC_5/ExcluirSubcategoria?acao=<%=s.getSubcategoria()%>">Excluir</a>    </td>
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
