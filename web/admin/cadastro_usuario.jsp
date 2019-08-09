<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Usuario</title>
    </head>
    <body>  
        <h1>Área de acesso restrito aos administradores</h1>
        <h2>Cadastro de novo usuario!</h2>

        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {
        %>
        <font color="blue"><%=msg%></font>
        <%
            }
        %>
        <form action="/ProjetoPFC_5/CadastrarUsuario" method="POST">
            Numero de Registro: <input type="text" name="txtNumeroDeRegistro" pattern="[0-9]+$"><br/>
            Nome: <input type="text" name="txtNome"><br/>
            E-mail: <input type="text" name="txtEmail"><br/>
            Telefone: <input type="text" name="txtTelefone"><br/>
            Senha: <input type="password" name="txtSenha"><br/>
            <% Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioAutenticado");
                if (usuarioSessao.getTipo().toString() == "TECNICO") {%>
            Tipo de Usuário: <input type="text" value="CLIENTE" readonly name="optTipo"> <br/>                   
            <%} else {
            %>Tipo de Usuário: <select name="optTipo"><%
                for (Tipo tipo : Tipo.values()) {
                %>
                <option ><%=tipo%></option> 
                <%
                    }
                %></select><br/><%
                    }
                %>
            Cargo: <input type="text" name="txtCargo"><br/>
            Setor: <select name="optSetor">
                <%
                    for (Setor setores : Setor.values()) {
                %>
                <option ><%=setores%></option> 
                <%
                    }
                %>
            </select><br/>   
        <input type="submit" value="Cadastrar">
    </form>
    <a href="/ProjetoPFC_5/principal.jsp">Pagina Principal</a>
</body>
</html>
