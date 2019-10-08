package controller;

import dao.CategoriaDAO;
import dao.SubcategoriaDAO;
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

@WebServlet(urlPatterns = {"/ConsultarSubcategoria", "/CadastrarSubcategoria", "/AlterarSubcategoria", "/AlterarPageSubcategoria", "/ExcluirSubcategoria", "/CadastrarPageSubcategoria"})
public class ControleSubcategoria extends HttpServlet {
    
    protected void cadastrarSubcategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Cadastrar")){
            Subcategoria subcategoria = new Subcategoria();
            
            subcategoria.setSubcategoria(request.getParameter("txtSubcategoria"));            
            String prioridade = request.getParameter("optPrioridade");
            if (prioridade.equalsIgnoreCase("baixa")) {
                subcategoria.setPrioridade(Prioridade.BAIXA);
            } else if (prioridade.equalsIgnoreCase("media")) {
                subcategoria.setPrioridade(Prioridade.MEDIA);
            } else if (prioridade.equalsIgnoreCase("alta")) {
                subcategoria.setPrioridade(Prioridade.ALTA);
            }else if (prioridade.equalsIgnoreCase("altissima")) {
                subcategoria.setPrioridade(Prioridade.ALTISSIMA);
            }
            
            Categoria categoria = new Categoria();
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            categoria.setCategoria(request.getParameter("optCategoria"));
            categoria.setId(categoriaDAO.consultaUmaCategoria(categoria).getId());
            
            subcategoria.setCategoria(categoria);

            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
            
            String nome = subcategoriaDAO.consultaUmaSubcategoria(subcategoria).getSubcategoria();   
            
            if (nome != null) {
                request.setAttribute("msg", "<div class=\"alert alert-danger\" role=\"alert\">\n"
                        + "                                    Subcategoria já cadastrada!\n"
                        + "                                </div>");
                //RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_subcategoria.jsp");
                //rd.forward(request, response);
            } else {
                subcategoriaDAO.cadastraNovaSubcategoria(subcategoria);
                request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                        + "                                    Subcategoria cadastrada com sucesso!\n"
                        + "                                </div>");
                request.setAttribute("subcategoria", subcategoria);
                //RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_subcategoria.jsp");
               // rd.forward(request, response);
            }
            }
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            List<Categoria> categorias = new ArrayList<Categoria>();
            Categoria categoria = new Categoria();
            categoria.setCategoria("");
            categorias = categoriaDAO.consultarCategoria(categoria);

            request.setAttribute("consultacategoria", categorias);
            RequestDispatcher rd = request.getRequestDispatcher("/admin/cadastro_subcategoria.jsp");
            rd.forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void consultarSubcategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            List<Categoria> categorias = new ArrayList<Categoria>();
            Categoria categoria = new Categoria();
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            categoria.setCategoria("");
            categorias = categoriaDAO.consultarCategoria(categoria);
            
            Subcategoria subcategoria = new Subcategoria();
            //subcategoria.setSubcategoria(request.getParameter("txtSubcategoria"));
            subcategoria.setSubcategoria("");
            List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();            
            subcategorias = subcategoriaDAO.consultarSubcategoria(subcategoria,categorias);
            
            request.setAttribute("consulta", subcategorias);
            RequestDispatcher rd = request.getRequestDispatcher("/admin/consultar_subcategoria.jsp");
            rd.forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void alterarSubcategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            Subcategoria subcategoria = new Subcategoria();
            String prioridade = request.getParameter("optPrioridade");
            if (prioridade.equalsIgnoreCase("baixa")) {
                subcategoria.setPrioridade(Prioridade.BAIXA);
            } else if (prioridade.equalsIgnoreCase("media")) {
                subcategoria.setPrioridade(Prioridade.MEDIA);
            } else if (prioridade.equalsIgnoreCase("alta")) {
                subcategoria.setPrioridade(Prioridade.ALTA);
            }else if (prioridade.equalsIgnoreCase("altissima")) {
                subcategoria.setPrioridade(Prioridade.ALTISSIMA);
            }
            
            subcategoria.setSubcategoria(request.getParameter("txtSubcategoria"));

            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
            subcategoriaDAO.alterarSubcategoria(subcategoria);
            
            consultarSubcategoria(request, response);
            
            //request.getRequestDispatcher("/admin/consultar_subcategoria.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void alterarPageSubcategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = (String) request.getParameter("acao");
            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
            Subcategoria subcategoria = new Subcategoria();
            Subcategoria subcategorias = new Subcategoria();
            subcategoria.setSubcategoria(acao);
            subcategorias = subcategoriaDAO.consultaUmaSubcategoria(subcategoria);
            
            Categoria categoria = new Categoria();
            Connection conexao = ConectaBanco.getConexao();
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
            categoria.setId(subcategorias.getCategoria().getId());
            categoria.setCategoria(categoriaDAO.consultaNomeCategoriaPorID(categoria).getCategoria());
            
            subcategorias.setCategoria(categoria);    
            
            request.setAttribute("subcategoria", subcategorias);
            request.getRequestDispatcher("/admin/alterar_subcategoria.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void excluirSubcategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = (String) request.getParameter("acao");
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setSubcategoria(acao);
            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
            subcategoriaDAO.excluirSubcategoria(subcategoria);
            
            request.getRequestDispatcher("/ConsultarSubcategoria?txtSubcategoria=").forward(request, response);

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
            if (url.equals(request.getContextPath() + "/ConsultarSubcategoria")) {
                consultarSubcategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/ExcluirSubcategoria")) {
                excluirSubcategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarPageSubcategoria")) {
                alterarPageSubcategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/CadastrarSubcategoria")) {
                cadastrarSubcategoria(request, response);
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
            if (url.equals(request.getContextPath() + "/CadastrarSubcategoria")) {
                cadastrarSubcategoria(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarSubcategoria")) {
                alterarSubcategoria(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
}
