<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="UTF-8" import="java.sql.*" errorPage="" %>
<%@page import="java.util.List"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<!DOCTYPE html>
<html>
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
        <div id="wrapper">
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <a class="navbar-brand" href="">Chamados Concluídos por Período</a>
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

                           
                            <li>
                                <a href="#"> Usuário<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="/ProjetoPFC_5/admin/cadastro_usuario.jsp"> Cadastro de Usuário</a>
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

                            <% 
                            if (usuario.getTipo().toString() == "SUPERVISOR") {%>
                                   <li>
                                <a href="#">Relatórios<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="/ProjetoPFC_5/ConcluidoTecnico?acao=relat_concluido_tecnico"> Chamados Concluídos por Técnico</a>
                                    </li>
                                    <li>
                                        <a href="/ProjetoPFC_5/admin/relat_concluido_periodo.jsp"> Chamados Concluídos por Período</a>
                                    </li>
                                    
                                </ul>
                            </li>
                            <%} %>



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

                            <h1 class="page-header">Relatório de Chamados Concluídos por Período</h1>
                            <%}%>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="col-lg-12">
                        <form action="/ProjetoPFC_5/ConcluidoPeriodo" method="POST">
                       
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>Início</label>
                                    <input type="date" class="form-control" name="dtIni">
                                </div>
                            </div>
                        
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>Fim</label>
                                    <input type="date" class="form-control" name="dtFim">
                                </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="form-group">
                            
                            <button type="submit" class="btn btn-default btn-">Gerar</button>
                        </div>
                    </div>
                    </form>
                    
                     <div class="row">
                        <div class="col-lg-12">
                            
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    
                                            <div>
                                                <label>Relatório</label>
                                            </div>
                                              
                                        
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <%
                                            List<Chamado> chamados = (List<Chamado>) request.getAttribute("consulta");

                                        %>
                                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                            
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Data Início</th>
                                                    <th>Data Fim</th>
                                                    <th>Descrição</th>                                                    
                                                    <th>Categoria</th> 
                                                    <th>Subcategoria</th> 
                                                    <th>Nome</th>
                                                    
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%if (chamados != null) {
                                                        for (Chamado c : chamados) {
                                                %>                
                                                <tr> 
                                                    <td> <%= c.getId()%></td>                                                    
                                                    <td> <%= c.getData_inicio()%></td>
                                                    <td> <%= c.getData_fim()%></td>
                                                    <td> <%= c.getDescricao()%></td>
                                                    <td> <%= c.getCategoria().getCategoria()%></td>
                                                    <td> <%= c.getSubcategoria().getSubcategoria()%></td>
                                                    <td> <%= c.getUsuario().getNome()%></td>
                                                    
                                                                                                        
                                                    <%
                                                            }
                                                        }
                                                    %>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                    </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.container-fluid -->
                
            </div>
            <!-- /#page-wrapper -->



        </div>
        <!-- /#wrapper -->

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
    </body>
</html>