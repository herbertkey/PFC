<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="UTF-8" import="java.sql.*" errorPage="" %>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>
        <script src="/ProjetoPFC_5/js/changeSelect.js" ></script>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Sistema de chamados</title>
        <link href="/ProjetoPFC_5/css/bootstrap.min.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/metisMenu.min.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/dataTables/dataTables.responsive.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/startmin.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    </head>
    <body> 
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand" href="/ProjetoPFC_5/principal.jsp">Alterar chamados</a>
            </div>
            <ul class="nav navbar-nav navbar-left navbar-top-links">
                <li><a href="/ProjetoPFC_5/ConsultarChamado?acao=Consultar"> <i class="fa fa-home fa-fw"></i> Home</a></li>
            </ul>
            <ul class="nav navbar-right navbar-top-links">
                <li class="dropdown navbar-inverse">
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><%
                        Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado");
                        if (usuario != null) {
                        %>
                        <i class="fa fa-user fa-fw"></i> <%=usuario.getNome()%> <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="/ProjetoPFC_5/ControleAcesso?acao=Sair"><i class="fa fa-sign-out fa-fw"></i> Sair</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- /.navbar-top-links -->
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        
                        <% if (usuario.getTipo().toString() == "TECNICO") {%>
                                    <li>
                                        <a href="/ProjetoPFC_5/admin/area_restrita.jsp"> Área restrita</a>
                                    </li>                  



                            <%} else {
                            %>


                            <%
                                if (usuario.getTipo().toString() == "SUPERVISOR") {%>
                                     <li>
                                        <a href = "/ProjetoPFC_5/admin/area_restrita.jsp" > Área restrita</a>
                                     </li > 
                                    
                                    
                                    
                            <%
                                }
                            %>


                                
                            
                            <%
                                }
                            %>
                        
                        <li>
                                <a href="/ProjetoPFC_5/AbrirChamado?acao="> Abrir Chamado</a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
	<div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Alteração do chamado</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->

        <!-- formulario -->
		<div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="row">
                                     <div class="col-lg-6">
                                     <form action="/ProjetoPFC_5/AlterarChamado" method="POST">   
                                        
                                        <%
                                            Chamado chamado = (Chamado) request.getAttribute("chamado");
                                            String msg = (String) request.getAttribute("msg");
                                            if (msg != null) {
                                        %> 
                                        <font><%=msg%></font></br>
                                        <%
                                            }
                                        %>
                                        <div class="form-group">
                                            <label for="disabledSelect">Número do chamado:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtId" value="<%=chamado.getId()%>" required="required">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label for="disabledSelect">Data de abertura do chamado:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" value="<%=chamado.getData_inicio()%>" required="required">
                                        </div>                                    
                                        
                                        <div class="form-group">
                                            <label for="disabledSelect">Descrição do chamado:</label>
                                            <textarea class="form-control" id="disabledInput" readonly="true" name="txtDescricao" required="required"><%=chamado.getDescricao()%></textarea>
                                        </div> 
                                        
                                                            
                                        
                                        <% Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioAutenticado");
                                            if (usuarioSessao.getTipo().toString() == "CLIENTE") {%>
                                         
                                        <div class="form-group">
                                            <label for="disabledSelect">Status:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="optStatus" value="<%=chamado.getStatus().toString()%>" required="required">
                                        </div> 
                                        
                                        <%} 
                                            else {
                                        %>
                                        
                                        <div class="form-group">
                                            <label>Status:</label>
                                            <select class="form-control" name="optStatus" required="required">
                                        
                                            <%
                                                for (StatusChamado status : StatusChamado.values()) {
                                                    
                                                    if(!chamado.getStatus().equals(StatusChamado.ABERTO) && status.equals(StatusChamado.ABERTO)){
                                                        continue;
                                                    }
                                                    
                                                    if (status == chamado.getStatus()) {
                                            %>                                             
                                            <option selected><%=status%></option>                                             
                                            <%
                                            } else {
                                            %>                                            
                                            <option> <%= status%></option>                                            
                                            <%
                                                }
                                                }
                                                
                                            %>   
                                            </select>
                                        </div>
                                            <%}
                                            %>
                                        <div class="form-group">
                                            <label for="disabledSelect">Numero de Registro:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtNumeroDeRegistro" value="<%=chamado.getUsuario().getNumero_registro()%>" required="required">
                                        </div>                    
                                        <div class="form-group">
                                            <label for="disabledSelect">Nome:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtNome" value="<%=chamado.getUsuario().getNome()%>" required="required">
                                        </div>      
                                        
                                        <%List<Categoria> categorias = (List<Categoria>) request.getAttribute("consultacategoria");%>
                                        
                                        <%List<Subcategoria> subcategorias = (List<Subcategoria>) request.getAttribute("consultasubcategoria");%>
                                        
                                        <div class="form-group">
                                            <label>Categoria:</label>
                                            <select class="form-control  select-categoria" name="optCategoria" id="cat" required="required">
                                            <%
                                                for (Categoria c : categorias) {
                                                    if (c.getCategoria().equals(chamado.getCategoria().getCategoria())) {
                                            %> 
                                            <option selected><%=c.getCategoria()%></option> 
                                            <%
                                            } else {
                                            %> 
                                            <option><%=c.getCategoria()%></option> 
                                            <%
                                                    }
                                                }
                                            %></select>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>Subcategoria:</label>
                                            <select class="form-control  select-subcategoria" name="optSubcategoria" required="required">
                                        
                                            <%
                                                for (Subcategoria sc : subcategorias) {
                                                    if (sc.getSubcategoria().equals(chamado.getSubcategoria().getSubcategoria())) {
                                            %> 
                                            <option data-categoria="<%=sc.getCategoria().getCategoria()%>" selected><%=sc.getSubcategoria()%></option> 
                                            <%
                                            } else {
                                            %> 
                                            <option data-categoria="<%=sc.getCategoria().getCategoria()%>"><%=sc.getSubcategoria()%></option> 
                                            <%
                                                    }
                                                }
                                            %></select>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label for="disabledSelect">Técnico:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtTecnico" value="<%=chamado.getTecnico().getNome()%>" required="required">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label for="disabledSelect">Prioridade:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtPrioridade" value="<%=chamado.getPrioridade()%>" required="required">
                                        </div>
                                        
                                        <button type="submit" name="acao" value="Alterar" class="btn btn-default">Alterar chamado</button>
                                    
                                        
                                </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label>Informações adicionais:</label>
                                        </div>
                                        
                                        <%
                                            List<Historico> historicos = (List<Historico>) request.getAttribute("historico");

                                            if (historicos != null) {
                                        %>
                                        <%        
                                            
                                        %>
                                        
                                        
                                        <%                    for (Historico h : historicos) {
                                        %>
                                                                                
                                        <div class="row">
                                            <div class="col-lg-8">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <%= h.getUsuario().getNome()%>
                                                    </div>
                                                    <div class="panel-body">
                                                        <p><%= h.getInformacoes_adicionais()%></p>
                                                    </div>
                                                    <div class="panel-footer">
                                                        <%= h.getData()%>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.col-lg-4 -->
                                        </div>
                                        <!-- /.row -->
                                        
                                        <%
                                            }
                                        %>
                                        <%
                                            } 
                                        %>
                                        <%if ("FECHADO" == chamado.getStatus().toString()){ %>
                                        <button type="submit" disabled="true" name="acao" value="Adicionar Informacoes" class="btn btn-default">Adicionar novas informações</button> 
                                        <%}else{%>
                                        <button type="submit" name="acao" value="Adicionar Informacoes" class="btn btn-default">Adicionar novas informações</button> 
                                        <%}%>   
                                        
                                        </form>
                                        
                                    </div>
                                     <!-- /.col-lg-6 -->
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<!-- jQuery -->
        <script src="/ProjetoPFC_5/js/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/ProjetoPFC_5/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="/ProjetoPFC_5/js/metisMenu.min.js"></script>

        <!-- DataTables JavaScript -->
        <script src="/ProjetoPFC_5/js/dataTables/jquery.dataTables.min.js"></script>
        <script src="/ProjetoPFC_5/js/dataTables/dataTables.bootstrap.min.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="/ProjetoPFC_5/js/startmin.js"></script>

        <!-- Page-Level Demo Scripts - Tables - Use for reference -->
        <script>
            $(document).ready(function () {
                $('#dataTables-example').DataTable({
                    responsive: true
                });
            });
        </script>
        <%}%>
    </body>
</html>
