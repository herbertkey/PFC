<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="controller.*"%>
<%@page import="AutorizacaoDeAcesso.*"%>
<%@page import="util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="/ProjetoPFC_5/css/bootstrap.min.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/metisMenu.min.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/startmin.css" rel="stylesheet">
        <link href="/ProjetoPFC_5/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <title>Sistema de Chamados</title>
        
    </head>
    <body>
        
        
<!--        <div class="bg"> -->
  <div class="container" >
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Login</h3>
                        </div>
                        <div class="panel-body">
                            <form role="form" action="ControleAcesso" method="POST">
                                <fieldset>
                                    <%
                                        String msg = (String) request.getAttribute("msg");
                                        if (msg != null) {
                                    %>
                                    <font color="red"> <%=msg%></font>        
                                    <%}%>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Numero do registro" name="txtNumeroDeRegistro" type="number" autofocus>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Sua senha" name="txtSenha" type="password" value="">
                                    </div>
                                    <div class="checkbox">
                                        
                                    </div>
                                    
                                    <input class="btn btn-lg btn-primary btn-block" type="submit" value="Entrar" name="acao">
                                    </form>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="/ProjetoPFC_5/js/jquery.min.js"></script>
        <script src="/ProjetoPFC_5/js/bootstrap.min.js"></script>
        <script src="/ProjetoPFC_5/js/metisMenu.min.js"></script>
        <script src="/ProjetoPFC_5/js/startmin.js"></script>
    </body>
</html>
