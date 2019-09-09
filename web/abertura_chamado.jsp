<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Abertura Chamado</title>
    </head>
    <body>
        <h2>Abertura de Chamado!</h2>

        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {
        %>
        <font color="blue"><%=msg%></font>
        <%
            }
        %>
        <form action="/ProjetoPFC_5/AbrirChamado" method="POST">
        <%
            //recupera o usuario da sessão
            Usuario usuario = (Usuario)session.getAttribute("usuarioAutenticado");
        %>
        Numero de Registro: <input type="text" readonly="true" name="txtNumeroDeRegistro" value="<%=usuario.getNumero_registro()%>"><br/>
            Nome: <input type="text" name="txtNome" readonly="true" value="<%=usuario.getNome()%>"><br/>
            Descrição: <textarea name="txtDescricao"></textarea><br/>
            <%List<Categoria> categorias = (List<Categoria>) request.getAttribute("consultacategoria");%>
            <%List<Subcategoria> subcategorias = (List<Subcategoria>) request.getAttribute("consultasubcategoria");%>
            Categoria: <select name="optCategoria" id="cat">
                <%
                    for (Categoria c : categorias) {
                %> 
                <option><%=c.getCategoria()%></option> 
                <%
                    }
                %></select><br/>
                
            Subcategoria: <select name="optSubcategoria">
                <%
                    for (Subcategoria sc : subcategorias) {
                        //if(equals(sc.getCategoria().getCategoria())){
                
                %>
                <option ><%=sc.getSubcategoria()%></option> 
                <%                            
                        }
                    
                %>  
                <input type="submit" value="Abrir" name="acao">
    </form>
    <a href="/ProjetoPFC_5/principal.jsp">Pagina Principal</a>
    </body>
</html>
