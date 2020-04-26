package controller;

import dao.CategoriaDAO;
import dao.UsuarioDAO;
import dao.HistoricoDAO;
import dao.SubcategoriaDAO;
import service.ServiceChamado;
import java.io.IOException;
import java.sql.Connection;
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
import javax.servlet.http.HttpSession;
import model.*;
import dao.ChamadoDAO;
import model.Chamado;
import util.ConectaBanco;

@WebServlet(urlPatterns = {"/ConcluidoPeriodo", "/ConcluidoTecnico"})
public class ControleRelatorio extends HttpServlet {

    protected void concluidoPeriodo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {            
                String data_ini = request.getParameter("dtIni");
                String data_fim = request.getParameter("dtFim");                 
            
                ChamadoDAO chamadoDAO = new ChamadoDAO();
                List<Chamado> chamados = new ArrayList<Chamado>();
                chamados = chamadoDAO.relChamadosConcluidos(data_ini, data_fim);
                
                request.setAttribute("consulta", chamados);
                RequestDispatcher rd = request.getRequestDispatcher("/admin/relat_concluido_periodo.jsp");
                rd.forward(request, response);
           
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
    
        protected void concluidoTecnico(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                List<Usuario> tecnicos = usuarioDAO.consultarTecnico();
            
                request.setAttribute("consultatecnico", tecnicos);
                
            if (acao.equals("gerar_relatorio")){
                
                Usuario tecnico = new Usuario();
                tecnico.setNome(request.getParameter("optTecnico"));
                
                Chamado chamado = new Chamado();
                chamado.setTecnico(tecnico);
            
                List<Chamado> chamados = new ArrayList<Chamado>();
                ChamadoDAO chamadoDAO = new ChamadoDAO();
               
                chamados = chamadoDAO.relConclusaoTecnico(tecnico);                
           
                request.setAttribute("consulta", chamados);
                            
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("/admin/relat_concluido_tecnico.jsp");
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
            if (url.equals(request.getContextPath() + "/ConcluidoPeriodo")) {
                concluidoPeriodo(request, response);
            } else if (url.equals(request.getContextPath() + "/ConcluidoTecnico")) {
                concluidoTecnico(request, response);
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
            if (url.equals(request.getContextPath() + "/ConcluidoPeriodo")) {
                concluidoPeriodo(request, response);
            } else if (url.equals(request.getContextPath() + "/ConcluidoTecnico")) {
                concluidoTecnico(request, response);
            } 
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
}
