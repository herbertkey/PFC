<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Usuario</title>
    </head>
    <body>
        <h1>Consultar Usuarios</h1>
        <form action="/ProjetoPFC_5/ConsultarUsuario" method="GET">
            Nome: <input type="text" name="txtNome"><br/>
            <input type="submit" value="Consultar">
        </form>
        <%
            List<Usuario> usuarios = (List<Usuario>) request.getAttribute("consulta");

        %>        
        <table>            
            <thead>
                <tr>
                    <th>Numero de Registro</th>
                    <th>E-mail</th>
                    <th>Nome</th>
                    <th>Telefone</th>
                    <th>Tipo</th>
                    <th>Cargo</th> 
                    <th>Setor</th> 
                    <th>Alterar</th>
                    <th>Excluir</th>
                </tr>
            </thead>            
            <tbody>                
                <%if (usuarios != null) {
                        for (Usuario u : usuarios) {
                %>                
                <tr>
                    <td> <%= u.getNumero_registro()%></td>
                    <td> <%= u.getEmail()%></td>
                    <td> <%= u.getNome()%></td>
                    <td> <%= u.getTelefone()%></td>
                    <td> <%= u.getTipo()%></td>
                    <td> <%= u.getCargo()%></td>
                    <td> <%= u.getSetor()%></td>            
                    <td><a href="/ProjetoPFC_5/AlterarPageUsuario?acao=<%=u.getNumero_registro()%>">Alterar</a>    </td>
                    <td><a href="/ProjetoPFC_5/ExcluirUsuario?acao=<%=u.getNumero_registro()%>">Excluir</a>    </td>
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
