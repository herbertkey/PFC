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


@WebServlet(urlPatterns = {"/AbrirChamado"})
public class ControleChamado extends HttpServlet {

    protected void abrirChamado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Abrir")){
            Chamado chamado = new Chamado();
            
            Usuario usuario = new Usuario();
            
            usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
            chamado.setUsuario(usuario);             
            
            chamado.setDescricao(request.getParameter("txtDescricao"));
            
            Categoria categoria = new Categoria();
            categoria.setCategoria(request.getParameter("optCategoria"));
            chamado.setCategoria(categoria);
            
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setSubcategoria(request.getParameter("optSubcategoria"));
            chamado.setSubcategoria(subcategoria);
            
            Usuario tecnico = new Usuario();
            tecnico.setNumero_registro(1);
            chamado.setTecnico(tecnico);
            
            String prioridade = request.getParameter("optPrioridade");
            if (prioridade.equalsIgnoreCase("baixa")) {
                chamado.setPrioridade(Prioridade.BAIXA);
            } else if (prioridade.equalsIgnoreCase("media")) {
                chamado.setPrioridade(Prioridade.MEDIA);
            } else if (prioridade.equalsIgnoreCase("alta")) {
                chamado.setPrioridade(Prioridade.ALTA);
            }else if (prioridade.equalsIgnoreCase("altissima")) {
                chamado.setPrioridade(Prioridade.ALTISSIMA);
            }
               
            ChamadoDAO chamadoDAO = new ChamadoDAO();
            chamado.setData_inicio(chamadoDAO.getDateTime());
            
            chamadoDAO.abrirChamado(chamado);
                request.setAttribute("msg", "Chamado aberto com com sucesso");
                request.setAttribute("chamado", chamado);
                //RequestDispatcher rd = request.getRequestDispatcher("/abertura_chamado.jsp");
                //rd.forward(request, response);
                
                
            }
                
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categorias = new ArrayList<Categoria>();
            Categoria categoria = new Categoria();
            categoria.setCategoria("");
            categorias = categoriaDAO.consultarCategoria(categoria);
            request.setAttribute("consultacategoria", categorias);
            
            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
            List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setSubcategoria("");
            subcategorias = subcategoriaDAO.consultarSubcategoria(subcategoria);
            request.setAttribute("consultasubcategoria", subcategorias);
            
            RequestDispatcher rd = request.getRequestDispatcher("/abertura_chamado.jsp");
            rd.forward(request, response);
            
            
            
            
            
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
                //consultarUsuario(request, response);
            } else if (url.equals(request.getContextPath() + "/ExcluirUsuario")) {
                //excluirUsuario(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarPageUsuario")) {
                //alterarPageUsuario(request, response);
            } else if (url.equals(request.getContextPath() + "/AbrirChamado")) {
                abrirChamado(request, response);
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
            if (url.equals(request.getContextPath() + "/AbrirChamado")) {
                abrirChamado(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarUsuario")) {
                //alterarUsuario(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
}
