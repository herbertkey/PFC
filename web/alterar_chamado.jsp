<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Chamado</title>
    </head>
    <body>
        <h1>Alterar Chamado</h1>
        <form action="/ProjetoPFC_5/AlterarChamado" method="POST">
            <%
                Chamado chamado = (Chamado) request.getAttribute("chamado");
                String msg = (String) request.getAttribute("msg");
                if (msg != null) {
            %> 
            <font color="blue"><%=msg%></font></br>
            <%
                }
            %>

            ID: <input type="text" readonly="true" name="txtId" value="<%=chamado.getId()%>"><br/>
            Numero de Registro: <input type="text" readonly="true" name="txtNumeroDeRegistro" value="<%=chamado.getUsuario().getNumero_registro()%>"><br/>
            Descrição: <textarea name="txtDescricao" ><%=chamado.getDescricao()%></textarea><br/>
            <%
                List<Historico> historicos = (List<Historico>) request.getAttribute("historico");

                if (historicos != null) {

            %>    
            <table>            
                <thead>
                    <tr>
                        <th >Informações Adicionais</th>
                        <th>Data</th>
                        <th>Nome</th>
                    </tr>
                </thead>  
                <%                    for (Historico h : historicos) {
                %> 
                <tbody>
                    <tr>
                        <td> <%= h.getInformacoes_adicionais()%></td>
                        <td> <%= h.getData()%></td>
                        <td> <%= h.getUsuario().getNome()%></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                }
            %>


            Data Inicio: <input type="text" readonly="true" name="txtId" value="<%=chamado.getData_inicio()%>"><br/>
            Status:<select name="optStatus">
                <%
                    for (StatusChamado status : StatusChamado.values()) {
                        if (status == chamado.getStatus()) {
                %> 
                <option selected><%=status%></option> 
                <%
                } else if (status.toString() != "TODOS") {
                %>
                <option> <%= status%></option>
                <%
                        }
                    }


                %></select><br/>
            Nome: <input type="text" readonly="true" name="txtNome" value="<%=chamado.getUsuario().getNome()%>"><br/>
            <%List<Categoria> categorias = (List<Categoria>) request.getAttribute("consultacategoria");%>
            <%List<Subcategoria> subcategorias = (List<Subcategoria>) request.getAttribute("consultasubcategoria");%>
            Categoria: <select name="optCategoria" id="cat">
                <%
                    for (Categoria c : categorias) {
                        if (c.equals(chamado.getCategoria().getCategoria())) {
                %> 
                <option selected><%=c.getCategoria()%></option> 
                <%
                } else {
                %> 
                <option><%=c.getCategoria()%></option> 
                <%
                        }
                    }
                %></select><br/>

            Subcategoria: <select name="optSubcategoria">
                <%
                    for (Subcategoria sc : subcategorias) {
                        if (sc.equals(chamado.getSubcategoria().getSubcategoria())) {
                %> 
                <option selected><%=sc.getSubcategoria()%></option> 
                <%
                } else {
                %> 
                <option><%=sc.getSubcategoria()%></option> 
                <%
                        }
                    }
                %></select><br/>
            Categoria: <input type="text" readonly="true" name="txtSubcategoria" value="<%=chamado.getCategoria().getCategoria()%>"><br/>
            Subcategoria <input type="text" readonly="true" name="txtSubcategoria" value="<%=chamado.getSubcategoria().getSubcategoria()%>"><br/>
            Técnico: <input type="text" readonly="true" name="txtTecnico" value="<%=chamado.getTecnico().getNome()%>"><br/>
            Prioridade: <input type="text" readonly="true" name="txtPrioridade" value="<%=chamado.getPrioridade()%>"><br/>

            <input type="submit" name="acao" value="Adicionar Informacoes"></br></br>
            <input type="submit" name="acao" value="Alterar"></br></br>

        </form>
        <a href="/ProjetoPFC_5/ConsultarChamado?acao=">Voltar</a>
    </body>
</html>
