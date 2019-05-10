package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PerfilDeAcesso;
import model.Usuario;
import model.UsuarioDAO;


public class ControleUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Cadastrar")) {
                Usuario usuario = new Usuario();
                usuario.setLogin(request.getParameter("txtLogin"));
                usuario.setSenha(request.getParameter("txtSenha"));
                String perfil = request.getParameter("optPerfil");
                if (perfil.equalsIgnoreCase("administrador")) {
                    usuario.setPerfil(PerfilDeAcesso.ADMINISTRADOR);
                } else {
                    usuario.setPerfil(PerfilDeAcesso.COMUM);
                }
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.cadastraNovoUsuario(usuario);
                request.setAttribute("msg", "cadastrado com sucesso");
                RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_usuario.jsp");
                rd.forward(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
