<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <div id="wrapper">
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/ProjetoPFC_5/principal.jsp">Cadastro de usuários</a>
                </div>

                <ul class="nav navbar-nav navbar-left navbar-top-links">
                    <li><a href="/ProjetoPFC_5/principal.jsp"> <i class="fa fa-home fa-fw"></i> Home</a></li>
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
                                <a href="/ProjetoPFC_5/admin/area_restrita.jsp"> Área restrita</a>
                            </li>
                            <li>
                                <a href="#"> Chamado<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="/ProjetoPFC_5/AbrirChamado?acao="> Abrir Chamado</a>
                                    </li>
                                    <li>
                                        <a href="/ProjetoPFC_5/ConsultarChamado?acao="> Consultar Chamado</a>
                                    </li>
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
                            <h1 class="page-header">Cadastro de usuários</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <form role="form">
											
											
                                                <div class="form-group">
                                                    <label>Número de Registro:</label>
                                                    <input type="text" class="form-control" placeholder="Número de Registro" name="txtNumeroDeRegistro" pattern="[0-9]+$">
                                                </div>
                                                <div class="form-group">
                                                    <label>Text Input</label>
                                                    <input class="form-control">
                                                    <p class="help-block">Example block-level help text here.</p>
                                                </div>
												
												
                                                <div class="form-group">
                                                    <label>Text Input with Placeholder</label>
                                                    <input class="form-control" placeholder="Enter text">
                                                </div>
                                                
                                                <button type="submit" class="btn btn-default">Submit Button</button>
                                                <button type="reset" class="btn btn-default">Reset Button</button>
                                            </form>
                                        </div>
                                        
                                    </div>
                                    <!-- /.row (nested) -->
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    
                    
                    <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">

                                <%
                                    String msg = (String) request.getAttribute("msg");
                                    if (msg != null) {
                                %>
                                <font color="blue"><%=msg%></font>
                                <%
                                    }
                                %>
                                <form role="form" action="/ProjetoPFC_5/CadastrarUsuario" method="POST">
                                    <br/>
                                    
                                    <div class="form-group">
                                        <label>Número de Registro:</label>
                                        <input type="text" class="form-control" placeholder="Número de Registro" name="txtNumeroDeRegistro" pattern="[0-9]+$">
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Text Input</label>
                                        <input class="form-control">
                                        <p class="help-block">Example block-level help text here.</p>
                                    </div>
                                    
                                    Nome: <input type="text" name="txtNome"><br/>
                                    <div class="form-group">
                                        <label>Text Input with Placeholder</label>
                                        <input class="form-control" placeholder="Enter text">
                                        
                                    </div>
                                    E-mail: <input type="text" name="txtEmail"><br/>
                                    <div class="form-group">
                                        <label>Text Input with Placeholder</label>
                                        <input class="form-control" placeholder="Enter text">
                                    </div>
                                    Telefone: <input type="text" name="txtTelefone"><br/>
                                    <div class="form-group">
                                        <label>Text Input with Placeholder</label>
                                        <input class="form-control" placeholder="Enter text">
                                    </div>
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
                                    <input class="btn btn-lg btn-primary " type="submit" value="Cadastrar">
                                </form>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
					</div>
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- /#page-wrapper -->
            <div id="page-wrapper">
                <div class="container-fluid">
                    <div class="row">



                        <!-- /.col-lg-12 -->
                    </div>

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
        <%}%>
    </body>
</html>

