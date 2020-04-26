<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="controller.*"%>
<%@page import="model.*"%>

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

        <div id="wrapper">
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/ProjetoPFC_5/ConsultarChamado?acao=Consultar">Sistema de chamados</a>
                </div>
				
                <ul class="nav navbar-nav navbar-left navbar-top-links">
                    <li><a href="/ProjetoPFC_5/ConsultarChamado?acao=Consultar"> <i class="fa fa-home fa-fw"></i> Home</a></li>
                </ul>

                 <ul class="nav navbar-right navbar-top-links">
                    <li class="dropdown navbar-inverse">
                        
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><%
                            Usuario usuario = (Usuario)session.getAttribute("usuarioAutenticado");

                            if(usuario !=null){
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
                    </div>
                    <!-- /.sidebar-collapse -->
                </div>
                <!-- /.navbar-static-side -->
            </nav>

            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            
                            <h1 class="page-header">Bem-vindo, <%=usuario.getNome()%>!</h1>
                            <%}%>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    
                                        <form role="form" action="/ProjetoPFC_5/ConsultarChamado" method="GET">
                                            <div>
                                                <label>Esses são os seus chamados</label>
                                            </div>
                                              
                                        </form>
                                    
                                    
                                </div>
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <%
                                            List<Chamado> chamados = (List<Chamado>) request.getAttribute("consulta");

                                        %>
                                        <table id="principal" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                            
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Descrição</th>
                                                    <th>Data Inicio</th>
                                                    <th>Data Fim</th>
                                                    <th>Status</th>
                                                    <th>Nome</th>
                                                    <th>Categoria</th> 
                                                    <th>Subcategoria</th> 
                                                    <th>Tecnico</th>
                                                    <th>Prioridade</th>
                                                    <th>Alterar</th>
                                                    
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%if (chamados != null) {
                                                        for (Chamado c : chamados) {
                                                %>                
                                                <tr>
                                                    <td> <%= c.getId()%></td>
                                                    <td> <%= c.getDescricao()%></td>
                                                    <td> <%= c.getData_inicio()%></td>
                                                    <td> <%= c.getData_fim()%></td>
                                                    <td> <%= c.getStatus()%></td>
                                                    <td> <%= c.getUsuario().getNome()%></td>
                                                    <td> <%= c.getCategoria().getCategoria()%></td>
                                                    <td> <%= c.getSubcategoria().getSubcategoria()%></td>
                                                    <td> <%= c.getTecnico().getNome()%></td>  
                                                    <td> <%= c.getPrioridade()%></td>  
                                                    <td><a href="/ProjetoPFC_5/AlterarPageChamado?acao=<%=c.getId()%>">Alterar</a></td>
                                                    
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
            $(document).ready(function() {
                $('#principal').DataTable({
                        "order": [[ 4, "asc" ]]    
                });
            });
        </script>

    </body>
</html>
