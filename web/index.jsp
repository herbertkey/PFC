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
        <%
            String msg = (String)request.getAttribute("msg");
            if(msg!=null){
        %>
        <font color="red"> <%=msg%></font>        
        <%}%>   
        
<!--        <div class="bg"> -->
  <div class="container" >
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Bem vindo ao sistema de chamados</h3>
                        </div>
                        <div class="panel-body">
                            <form role="form" action="ControleAcesso" method="POST">
                                <fieldset>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Numero do registro" name="txtNumeroDeRegistro" type="text" autofocus>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Sua senha" name="txtSenha" type="password" value="">
                                    </div>
                                    <div class="checkbox">
                                        
                                    </div>
                                    
                                    <input class="btn btn-lg btn-success btn-block" type="submit" value="Entrar" name="acao">
                                    </form>
<!--                                     Change this to a button or input when using this as a form 
                                    <a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>-->
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--</div>-->
       
<!--        <form action="ControleAcesso" method="POST">
            Numero de Registro: <input type="text" name="txtNumeroDeRegistro" pattern="[0-9]+$"><br/>
            Senha: <input type="password" name="txtSenha"><br/>
            <input type="submit" value="Entrar" name="acao">
        </form>-->
        
        
        
        <script src="/ProjetoPFC_5/js/jquery.min.js"></script>
        <script src="/ProjetoPFC_5/js/bootstrap.min.js"></script>
        <script src="/ProjetoPFC_5/js/metisMenu.min.js"></script>
        <script src="/ProjetoPFC_5/js/startmin.js"></script>
    </body>
</html>
