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
                    <a class="navbar-brand" href="/ProjetoPFC_5/principal.jsp">Alteração de subcategoria</a>
                </div>
                <ul class="nav navbar-nav navbar-left navbar-top-links">
                    <li><a href="/ProjetoPFC_5/ConsultarChamado?acao=Consultar"> <i class="fa fa-home fa-fw"></i> Home</a></li>
                </ul>
                <ul class="nav navbar-right navbar-top-links">
                    <li class="dropdown navbar-inverse">
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><%
                            
                            Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioAutenticado");
                            if (usuarioSessao != null) {
                            %>
                            <i class="fa fa-user fa-fw"></i> <%=usuarioSessao.getNome()%> <b class="caret"></b>
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
                            <li>
                                <a href="#"> Usuário<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="/ProjetoPFC_5/CadastrarUsuario?acao="> Cadastro de Usuário</a>
                                    </li>
                                    <li>
                                        <a href="/ProjetoPFC_5/ConsultarUsuario?txtNome="> Consultar Usuário</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"> Categoria<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="/ProjetoPFC_5/admin/cadastro_categoria.jsp"> Cadastro de Categoria</a>
                                    </li>
                                    <li>
                                        <a href="/ProjetoPFC_5/ConsultarCategoria?txtCategoria="> Consultar Categoria</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"> Subcategoria<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="/ProjetoPFC_5/CadastrarSubcategoria?acao="> Cadastro de Subcategoria</a>
                                    </li>
                                    <li>
                                        <a href="/ProjetoPFC_5/ConsultarSubcategoria?txtSubcategoria="> Consultar Subcategoria</a>
                                    </li>
                                </ul>
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
                            <h1 class="page-header">Alteração do cadastro de subcategoria</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <form action="/ProjetoPFC_5/AlterarSubcategoria" method="POST">
            									<%
                									Subcategoria subcategoria = (Subcategoria) request.getAttribute("subcategoria");
           										%>
            									<div class="form-group">
                                            		<label>Subcategoria:</label>
                                           			<input class="form-control" type="text" name="txtSubcategoria" value="<%=subcategoria.getSubcategoria()%>" >
                                       			</div>
            									<div class="form-group">
                                            		<label>Prioridade:</label>
                                           			
                                       			
            										<select class="form-control" name="optPrioridade">
            									    <%
            									    for (Prioridade prioridade : Prioridade.values()) {
            									        if (prioridade == subcategoria.getPrioridade()) {
            									    %> 
            									    <option selected><%=prioridade%></option> 
                								<%
                								} else {
                								%>
                								<option><%=prioridade%></option> 
                								<%
                								        }
								
                								    }
               									%></select>
               									</div>
               									<div class="form-group">
                                            		<label>Categoria:</label>
                                           			<input class="form-control" type="text" name="txtCategoria" value="<%=subcategoria.getCategoria().getCategoria() %>" >
                                       			</div>
               									<div class="form-group">
                                            	<button type="submit" value="Alterar" class="btn btn-default">Salvar</button>
                                        		</div>
            									
        									</form>
                                            <div class="form-group">
                                            <button class="btn btn-default" onclick="window.location.href='/ProjetoPFC_5/ConsultarCategoria?txtCategoria='">Voltar</button>
                                            </div>
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


