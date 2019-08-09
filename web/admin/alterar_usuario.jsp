<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Usuario</title>
    </head>
        <body>
        <h1>Alterar Usuario</h1>
        <form action="/ProjetoPFC_5/AlterarUsuario" method="POST">
            <%
                Usuario usuario = (Usuario) request.getAttribute("usuario");

            %> 

            Numero de Registro: <input type="text" readonly="true" name="txtNumeroDeRegistro" value="<%=usuario.getNumero_registro()%>"><br/>
            Nome: <input type="text" name="txtNome" value="<%=usuario.getNome()%>"><br/>
            E-mail: <input type="text" name="txtEmail" value="<%=usuario.getEmail()%>"><br/>
            Telefone: <input type="text" name="txtTelefone" value="<%=usuario.getTelefone()%>"><br/>
            <% Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioAutenticado");
                if (usuarioSessao.getTipo().toString() == "TECNICO") {%>
            Tipo de Usuario: <input type="text" value="<%=usuario.getTipo()%>" readonly name="optTipo"> <br/>                   
            <%} else {%>
            Tipo de Usuario: <select name="optTipo">
                <%
                for (Tipo tipo : Tipo.values()) {
                    if (tipo == usuario.getTipo()) {
                %> 
                <option selected><%=tipo%></option> 
                <%
                } else {
                %>
                <option><%=tipo%></option> 
                <%
                        }

                    }
                %></select><br/><%
                    }
                %>
            Cargo: <input type="text" name="txtCargo" value="<%=usuario.getCargo()%>"><br/>
            Setor: <select name="optSetor">
                <%
                    for (Setor setor : Setor.values()) {
                        if (setor == usuario.getSetor()) {
                %> 
                <option selected><%=setor%></option> 
                <%
                } else {
                %>
                <option><%=setor%></option> 
                <%
                        }

                    }
                %>
            </select><br/>  

            <input type="submit" value="Alterar"></br></br>

        </form>
        <a href="/ProjetoPFC_5/ConsultarUsuario?txtNome=">Voltar</a>
    </body>

</html>
