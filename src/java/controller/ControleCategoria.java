package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import util.ConectaBanco;

@WebServlet(urlPatterns = {"/ConsultarCategoria", "/CadastrarCategoria", "/AlterarCategoria", "/AlterarPageCategoria", "/ExcluirCategoria"})
public class ControleCategoria extends HttpServlet {

    protected void cadastrarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            Categoria categoria = new Categoria();
            
            categoria.setCategoria(request.getParameter("txtCategoria"));            
            String prioridade = request.getParameter("optPrioridade");
            if (prioridade.equalsIgnoreCase("baixa")) {
                categoria.setPrioridade(Prioridade.BAIXA);
            } else if (prioridade.equalsIgnoreCase("media")) {
                categoria.setPrioridade(Prioridade.MEDIA);
            } else if (prioridade.equalsIgnoreCase("alta")) {
                categoria.setPrioridade(Prioridade.ALTA);
            }else if (prioridade.equalsIgnoreCase("altissima")) {
                categoria.setPrioridade(Prioridade.ALTISSIMA);
            }

            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            String nome = categoriaDAO.consultaUmaCategoria(categoria).getCategoria();
            if (nome != null) {
                request.setAttribute("msg", "<div class=\"alert alert-danger\" role=\"alert\">\n"
                        + "                                    Categoria já cadastrada!\n"
                        + "                                </div>");
                RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_categoria.jsp");
                rd.forward(request, response);
            } else {
                conexao = ConectaBanco.getConexao();
                categoriaDAO = new CategoriaDAO(conexao);
                categoriaDAO.cadastraNovaCategoria(categoria);
                request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                        + "                                    Categoria cadastrada com sucesso!\n"
                        + "                                </div>");
                request.setAttribute("categoria", categoria);
                RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_categoria.jsp");
                rd.forward(request, response);
            }

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void consultarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Categoria categoria = new Categoria();
            categoria.setCategoria(request.getParameter("txtCategoria"));
            List<Categoria> categorias = new ArrayList<Categoria>();
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);            
            categorias = categoriaDAO.consultarCategoria(categoria);

            request.setAttribute("consulta", categorias);
            RequestDispatcher rd = request.getRequestDispatcher("/admin/consultar_categoria.jsp");
            rd.forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void alterarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            Categoria categoria = new Categoria();
            String prioridade = request.getParameter("optPrioridade");
            if (prioridade.equalsIgnoreCase("baixa")) {
                categoria.setPrioridade(Prioridade.BAIXA);
            } else if (prioridade.equalsIgnoreCase("media")) {
                categoria.setPrioridade(Prioridade.MEDIA);
            } else if (prioridade.equalsIgnoreCase("alta")) {
                categoria.setPrioridade(Prioridade.ALTA);
            }else if (prioridade.equalsIgnoreCase("altissima")) {
                categoria.setPrioridade(Prioridade.ALTISSIMA);
            }

            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            categoriaDAO.alterarCategoria(categoria);
            request.getRequestDispatcher("/admin/consultar_categoria.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void alterarPageCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = (String) request.getParameter("acao");
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            Categoria categoria = new Categoria();
            Categoria categorias = new Categoria();
            categoria.setCategoria(acao);
            categorias = categoriaDAO.consultaUmaCategoria(categoria);

            request.setAttribute("categoria", categorias);
            request.getRequestDispatcher("/admin/alterar_categoria.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void excluirCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = (String) request.getParameter("acao");
            Categoria categoria = new Categoria();
            categoria.setCategoria(acao);
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            categoriaDAO.excluirCategoria(categoria);
            request.getRequestDispatcher("/ConsultarCategoria?txtCategoria=").forward(request, response);

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
            if (url.equals(request.getContextPath() + "/ConsultarCategoria")) {
                consultarCategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/ExcluirCategoria")) {
                excluirCategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarPageCategoria")) {
                alterarPageCategoria(request, response);
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
            if (url.equals(request.getContextPath() + "/CadastrarCategoria")) {
                cadastrarCategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarCategoria")) {
                alterarCategoria(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
}
