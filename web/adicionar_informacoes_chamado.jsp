<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="UTF-8" import="java.sql.*" errorPage="" %>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>


<!DOCTYPE html>
<html lang="en">
    <head>
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
                <a class="navbar-brand" href="/ProjetoPFC_5/principal.jsp">Informações</a>
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
                        
                        <% Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioAutenticado");
                                if (usuarioSessao.getTipo().toString() == "TECNICO") {%>
                                    <li>
                                        <a href="/ProjetoPFC_5/admin/area_restrita.jsp"> Área restrita</a>
                                    </li>                  



                            <%} else {
                            %>


                            <%
                                if (usuarioSessao.getTipo().toString() == "SUPERVISOR") {%>
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
                                    
					<form action="/ProjetoPFC_5/AlterarPageChamado" method="POST">
					<%
					//recupera o usuario da sessão
					Chamado chamado = (Chamado) request.getAttribute("chamado");
                                        %> 
                                        <div class="form-group">
                                            <label for="disabledSelect">Numero de Registro:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtNumeroDeRegistro" value="<%=usuario.getNumero_registro()%>" >
                                        </div>
                                        <div class="form-group">
                                            <label for="disabledSelect">Nome:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtNome" value="<%=usuario.getNome()%>" >
                                        </div>
                                       

                                        
                                        <div class="form-group">
                                            <label for="disabledSelect">Número do chamado:</label>
                                            <input class="form-control" id="disabledInput" readonly="true" type="text" name="txtId" value="<%=chamado.getId()%>" >
                                        </div>
					<div class="form-group">
                                            <label for="disabledSelect">Descrição do chamado:</label>
                                            <textarea class="form-control" id="disabledInput" readonly="true" name="txtDescricao" ><%=chamado.getDescricao()%></textarea>
                                        </div> 
					<div class="form-group">
                                        <label for="disabledSelect">Novas Informações:</label>
                                            <textarea class="form-control" name="txtInformacoesAdicionais" ></textarea>
                                        </div> 
					<button type="submit" name="acao" value="Adicionar" class="btn btn-default">Adicionar novas informações</button> 
					</form>
        
									
                                    </div>
                                    <div class="col-lg-6">
                                    <div class="form-group">
                                            <label>Informações adicionais:</label>
                                        </div>
                                        
                                        <%
                                            List<Historico> historicos = (List<Historico>) request.getAttribute("historico");

                                            if (historicos != null) {
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
