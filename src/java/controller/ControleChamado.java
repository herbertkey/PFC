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
import javax.servlet.http.HttpSession;
import model.*;

@WebServlet(urlPatterns = {"/AbrirChamado", "/ConsultarChamado", "/AlterarPageChamado", "/AlterarChamado", "/FecharChamado"})
public class ControleChamado extends HttpServlet {

    protected void abrirChamado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Abrir")) {
                Chamado chamado = new Chamado();
                ChamadoDAO chamadoDAO = new ChamadoDAO();
                
                Usuario usuario = new Usuario();
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
                usuario = usuarioDAO.consultaUmUsuario(usuario);
                chamado.setUsuario(usuario);

                chamado.setDescricao(request.getParameter("txtDescricao"));

                Categoria categoria = new Categoria();
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                categoria.setCategoria(request.getParameter("optCategoria"));
                categoria = categoriaDAO.consultaUmaCategoria(categoria);
                chamado.setCategoria(categoria);

                Subcategoria subcategoria = new Subcategoria();
                SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
                subcategoria.setSubcategoria(request.getParameter("optSubcategoria"));
                subcategoria = subcategoriaDAO.consultaUmaSubcategoria(subcategoria);
                chamado.setSubcategoria(subcategoria);

                Usuario tecnico = new Usuario();
                List<Usuario> tecnicos = new ArrayList<Usuario>();
                tecnicos = usuarioDAO.consultarTecnico();       
                tecnico.setId(chamadoDAO.atribuicaoDoChamado(tecnicos));
                chamado.setTecnico(tecnico);          
                                 
                String prioridade = chamadoDAO.calcularPrioridadeDoChamado(chamado);
                if (prioridade.equalsIgnoreCase("BAIXA")) {
                    chamado.setPrioridade(Prioridade.BAIXA);
                } else if (prioridade.equalsIgnoreCase("MEDIA")) {
                    chamado.setPrioridade(Prioridade.MEDIA);
                } else if (prioridade.equalsIgnoreCase("ALTA")) {
                    chamado.setPrioridade(Prioridade.ALTA);
                } else if (prioridade.equalsIgnoreCase("ALTISSIMA")) {
                    chamado.setPrioridade(Prioridade.ALTISSIMA);
                }                         
                
                chamado.setData_inicio(chamadoDAO.getDateTime());

                chamadoDAO.abrirChamado(chamado);
                request.setAttribute("msg", "Chamado aberto com com sucesso");
                request.setAttribute("chamado", chamado);
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
            subcategorias = subcategoriaDAO.consultarSubcategoria(subcategoria, categorias);
            request.setAttribute("consultasubcategoria", subcategorias);

            RequestDispatcher rd = request.getRequestDispatcher("/abertura_chamado.jsp");
            rd.forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void consultarChamado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Consultar")) {
                Chamado chamado = new Chamado();
                //Usuario usuario = new Usuario();
                HttpSession sessaoUsuario = request.getSession();
                
                Usuario usuario = (Usuario)sessaoUsuario.getAttribute("usuarioAutenticado");
                
                //usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
                
                chamado.setStatus(StatusChamado.TODOS);
//                String status = request.getParameter("optStatus");
//                if (status.equalsIgnoreCase("aberto")) {
//                    chamado.setStatus(StatusChamado.ABERTO);
//                } else if (status.equalsIgnoreCase("em_andamento")) {
//                    chamado.setStatus(StatusChamado.EM_ANDAMENTO);
//                } else if (status.equalsIgnoreCase("fechado")) {
//                    chamado.setStatus(StatusChamado.FECHADO);
//                } else if (status.equalsIgnoreCase("todos")) {
//                    chamado.setStatus(StatusChamado.TODOS);
//                }

                List<Chamado> chamados = new ArrayList<Chamado>();
                ChamadoDAO chamadoDAO = new ChamadoDAO();

                UsuarioDAO usuarioDAO = new UsuarioDAO();                
                usuario = usuarioDAO.consultaUmUsuario(usuario);
                chamado.setUsuario(usuario);
                if (usuario.getTipo().equals(Tipo.CLIENTE)) {
                    chamados = chamadoDAO.consultarChamadosCliente(chamado);
                } else if (usuario.getTipo().equals(Tipo.TECNICO)) {
                    chamados = chamadoDAO.consultarChamadosTecnico(chamado);
                } else if (usuario.getTipo().equals(Tipo.SUPERVISOR)) {
                    chamados = chamadoDAO.consultarChamadosSupervisor(chamado);
                }

                request.setAttribute("consulta", chamados);
                RequestDispatcher rd = request.getRequestDispatcher("/principal.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/principal.jsp");
                rd.forward(request, response);
            }

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }

    protected void alterarPageChamado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            String acao = (String) request.getParameter("acao");

            if (acao.equals("Adicionar")) {
                HistoricoDAO historicoDAO = new HistoricoDAO();
                Historico historico = new Historico();

                Usuario usuario = new Usuario();
                usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario = usuarioDAO.consultaUmUsuario(usuario);
                historico.setUsuario(usuario);

                historico.setInformacoes_adicionais(request.getParameter("txtInformacoesAdicionais"));

                ChamadoDAO chamadoDAO = new ChamadoDAO();
                Chamado chamado = new Chamado();
                chamado.setId(request.getParameter("txtId"));

                historico.setChamado(chamado);
                historico.setData(chamadoDAO.getDateTime());
                
                historicoDAO.inserirInformacoes(historico);

            }

            ChamadoDAO chamadoDAO = new ChamadoDAO();
            Chamado chamado = new Chamado();
            Chamado chamados = new Chamado();
            HistoricoDAO historicoDAO = new HistoricoDAO();
            Historico historico = new Historico();
            List<Historico> historicos = new ArrayList<Historico>();
            if (acao.equals("Adicionar")) {
                chamado.setId(request.getParameter("txtId"));
            } else {
                chamado.setId(acao);
            }
            historico.setChamado(chamado);
            historicos = historicoDAO.consultarHistoricoChamado(historico);

            chamados = chamadoDAO.consultarUmChamado(chamado);

            List<Categoria> categorias = new ArrayList<Categoria>();
            Categoria categoria = new Categoria();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            categoria.setCategoria("");
            categorias = categoriaDAO.consultarCategoria(categoria);
            request.setAttribute("consultacategoria", categorias);

            SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
            List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setSubcategoria("");
            subcategorias = subcategoriaDAO.consultarSubcategoria(subcategoria, categorias);
            request.setAttribute("consultasubcategoria", subcategorias);

            request.setAttribute("chamado", chamados);
            request.setAttribute("historico", historicos);
            request.getRequestDispatcher("/alterar_chamado.jsp").forward(request, response);

        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }

    protected void alterarChamado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            if (acao.equals("Adicionar Informacoes")) {
                ChamadoDAO chamadoDAO = new ChamadoDAO();
                Chamado chamado = new Chamado();
                Chamado chamados = new Chamado();
                chamado.setId(request.getParameter("txtId"));
                chamados = chamadoDAO.consultarUmChamado(chamado);
                HistoricoDAO historicoDAO = new HistoricoDAO();
                Historico historico = new Historico();
                List<Historico> historicos = new ArrayList<Historico>();
                historico.setChamado(chamado);
                historicos = historicoDAO.consultarHistoricoChamado(historico);

                request.setAttribute("chamado", chamados);
                request.setAttribute("historico", historicos);
                request.getRequestDispatcher("/adicionar_informacoes_chamado.jsp").forward(request, response);

            } else if (acao.equals("Alterar")) {

                Chamado chamado = new Chamado();
                ChamadoDAO chamadoDAO = new ChamadoDAO();
                
                Usuario usuario = new Usuario();
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuario.setNumero_registro(Integer.parseInt(request.getParameter("txtNumeroDeRegistro")));
                usuario = usuarioDAO.consultaUmUsuario(usuario);
                chamado.setUsuario(usuario);

                chamado.setId(request.getParameter("txtId"));

                chamado.setDescricao(request.getParameter("txtDescricao"));

                Categoria categoria = new Categoria();
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                categoria.setCategoria(request.getParameter("optCategoria"));
                categoria = categoriaDAO.consultaUmaCategoria(categoria);
                chamado.setCategoria(categoria);

                Subcategoria subcategoria = new Subcategoria();
                SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
                subcategoria.setSubcategoria(request.getParameter("optSubcategoria"));
                subcategoria = subcategoriaDAO.consultaUmaSubcategoria(subcategoria);
                chamado.setSubcategoria(subcategoria);

                
                String prioridade = chamadoDAO.calcularPrioridadeDoChamado(chamado);
                if (prioridade.equalsIgnoreCase("BAIXA")) {
                    chamado.setPrioridade(Prioridade.BAIXA);
                } else if (prioridade.equalsIgnoreCase("MEDIA")) {
                    chamado.setPrioridade(Prioridade.MEDIA);
                } else if (prioridade.equalsIgnoreCase("ALTA")) {
                    chamado.setPrioridade(Prioridade.ALTA);
                } else if (prioridade.equalsIgnoreCase("ALTISSIMA")) {
                    chamado.setPrioridade(Prioridade.ALTISSIMA);
                } 

                String status = request.getParameter("optStatus");
                if (status.equalsIgnoreCase("aberto")) {
                    chamado.setStatus(StatusChamado.ABERTO);
                } else if (status.equalsIgnoreCase("em_andamento")) {
                    chamado.setStatus(StatusChamado.EM_ANDAMENTO);
                } else if (status.equalsIgnoreCase("fechado")) {
                    chamado.setStatus(StatusChamado.FECHADO);
                } else if (status.equalsIgnoreCase("todos")) {
                    chamado.setStatus(StatusChamado.TODOS);
                }
         

                chamadoDAO.alterarChamado(chamado);

                Chamado chamados = new Chamado();
                chamados.setId(chamado.getId());
                chamados = chamadoDAO.consultarUmChamado(chamados);
                HistoricoDAO historicoDAO = new HistoricoDAO();
                Historico historico = new Historico();
                List<Historico> historicos = new ArrayList<Historico>();

                historico.setChamado(chamados);
                historicos = historicoDAO.consultarHistoricoChamado(historico);

                request.setAttribute("chamado", chamados);
                request.setAttribute("historico", historicos);
                request.setAttribute("msg", "Chamado alterado com sucesso");

                List<Categoria> categorias = new ArrayList<Categoria>();

                
                categoria.setCategoria("");
                categorias = categoriaDAO.consultarCategoria(categoria);
                request.setAttribute("consultacategoria", categorias);

                
                List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();

                subcategoria.setSubcategoria("");
                subcategorias = subcategoriaDAO.consultarSubcategoria(subcategoria, categorias);
                request.setAttribute("consultasubcategoria", subcategorias);

                RequestDispatcher rd = request.getRequestDispatcher("/alterar_chamado.jsp");
                rd.forward(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }

    }
    protected void fecharChamado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = (String) request.getParameter("acao");
            Chamado chamado = new Chamado();
            chamado.setId(acao);
            ChamadoDAO chamadoDAO = new ChamadoDAO();
            chamado.setData_fim(chamadoDAO.getDateTime());           
            
            chamadoDAO.fecharChamado(chamado);
            request.getRequestDispatcher("/consultar_chamado.jsp").forward(request, response);
            

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
            if (url.equals(request.getContextPath() + "/ConsultarChamado")) {
                consultarChamado(request, response);
            } else if (url.equals(request.getContextPath() + "/FecharChamado")) {
                fecharChamado(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarPageChamado")) {
                alterarPageChamado(request, response);
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
            } else if (url.equals(request.getContextPath() + "/AlterarChamado")) {
                alterarChamado(request, response);
            } else if (url.equals(request.getContextPath() + "/AlterarPageChamado")) {
                alterarPageChamado(request, response);
            }
        } catch (Exception erro) {
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            request.setAttribute("erro", erro);
            rd.forward(request, response);
        }
    }
}
