package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

@WebServlet(urlPatterns = {"/ConsultarUsuario", "/CadastrarUsuario", "/AlterarUsuario", "/AlterarPageUsuario", "/ExcluirUsuario"})
public class ControleUsuario extends HttpServlet {

    protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            Usuario usuario = new Usuario();
            usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
            usuario.setNome(request.getParameter("txtNome"));
            usuario.setEmail(request.getParameter("txtEmail"));
            usuario.setTelefone(request.getParameter("txtTelefone"));
            usuario.setSenha(request.getParameter("txtSenha"));

            String tipo = request.getParameter("optTipo");
            if (tipo.equalsIgnoreCase("cliente")) {
                usuario.setTipo(Tipo.CLIENTE);
            } else if (tipo.equalsIgnoreCase("tecnico")) {
                usuario.setTipo(Tipo.TECNICO);
            } else if (tipo.equalsIgnoreCase("supervisor")) {
                usuario.setTipo(Tipo.SUPERVISOR);
            }

            usuario.setCargo(request.getParameter("txtCargo"));

            String setor = request.getParameter("optSetor");
            if (setor.equalsIgnoreCase("reitoria")) {
                usuario.setSetor(Setor.REITORIA);
            } else if (setor.equalsIgnoreCase("coordenacao")) {
                usuario.setSetor(Setor.COORDENACAO);
            } else if (setor.equalsIgnoreCase("administracao")) {
                usuario.setSetor(Setor.ADMINISTRACAO);
            } else if (setor.equalsIgnoreCase("portaria")) {
                usuario.setSetor(Setor.PORTARIA);
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (usuarioDAO.consultaUmUsuario(usuario).getNumero_registro() == usuario.getNumero_registro()) {
                request.setAttribute("msg", "<div class=\"alert alert-danger\" role=\"alert\">\n"
                        + "                                    Usuário já cadastrado!\n"
                        + "                                </div>");
                RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_usuario.jsp");
                rd.forward(request, response);
            } else {
                usuarioDAO.cadastraNovoUsuario(usuario);
                request.setAttribute("msg", "<div class=\"alert alert-danger\" role=\"alert\">\n"
                        + "                                    Usuário cadastrado com sucesso!\n"
                        + "                                </div>");
                request.setAttribute("usuario", usuario);
                RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_usuario.jsp");
                rd.forward(request, response);
            }

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void consultarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(request.getParameter("txtNome"));            
            List<Usuario> usuarios = new ArrayList<Usuario>();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarios = usuarioDAO.consultarUsuario(usuario);

            request.setAttribute("consulta", usuarios);
            RequestDispatcher rd = request.getRequestDispatcher("/admin/consultar_usuario.jsp");
            rd.forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void alterarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            Usuario usuario = new Usuario();
            usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
            usuario.setNome(request.getParameter("txtNome"));
            usuario.setEmail(request.getParameter("txtEmail"));
            usuario.setTelefone(request.getParameter("txtTelefone"));
            String tipo = request.getParameter("optTipo");
            if (tipo.equalsIgnoreCase("cliente")) {
                usuario.setTipo(Tipo.CLIENTE);
            } else if (tipo.equalsIgnoreCase("tecnico")) {
                usuario.setTipo(Tipo.TECNICO);
            } else if (tipo.equalsIgnoreCase("supervisor")) {
                usuario.setTipo(Tipo.SUPERVISOR);
            }

            usuario.setCargo(request.getParameter("txtCargo"));
            String setor = request.getParameter("optSetor");
            if (setor.equalsIgnoreCase("reitoria")) {
                usuario.setSetor(Setor.REITORIA);
            } else if (setor.equalsIgnoreCase("coordenacao")) {
                usuario.setSetor(Setor.COORDENACAO);
            } else if (setor.equalsIgnoreCase("administracao")) {
                usuario.setSetor(Setor.ADMINISTRACAO);
            } else if (setor.equalsIgnoreCase("portaria")) {
                usuario.setSetor(Setor.PORTARIA);
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.alterarUsuario(usuario);
            request.getRequestDispatcher("/admin/consultar_usuario.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void alterarPageUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String numero = (String) request.getParameter("acao");
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = new Usuario();
            Usuario usuarios = new Usuario();
            usuario.setNumero_registro(Integer.parseInt(numero));
            usuarios = usuarioDAO.consultaUmUsuario(usuario);

            request.setAttribute("usuario", usuarios);
            request.getRequestDispatcher("/admin/alterar_usuario.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void excluirUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String numero = (String) request.getParameter("acao");
            Usuario usuario = new Usuario();
            usuario.setNumero_registro(Integer.parseInt(numero));
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.excluirUsuario(usuario);
            request.getRequestDispatcher("/admin/consultar_usuario.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        try {
            if (url.equals(request.getContextPath() + "/ConsultarUsuario")) {
                consultarUsuario(request, response);
            } else if (url.equals(request.getContextPath() + "/ExcluirUsuario")) {
                excluirUsuario(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarPageUsuario")) {
                alterarPageUsuario(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        try {
            if (url.equals(request.getContextPath() + "/CadastrarUsuario")) {
                cadastrarUsuario(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarUsuario")) {
                alterarUsuario(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
}
