package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Chamado;
import model.ChamadoDAO;
import model.Prioridade;
import model.Usuario;
import model.UsuarioDAO;
import util.ConectaBanco;

public class ServiceChamado {

    public Prioridade calcularPrioridadeDoChamado(Chamado chamado) {

        double prioridadeFinal = 0;
        double prioridadeCategoria = 0;
        double prioridadeSubcategoria = 0;
        double prioridadeSetor = 0;

        prioridadeCategoria = chamado.getCategoria().getPrioridade().getPrioridade();
        prioridadeSubcategoria = chamado.getSubcategoria().getPrioridade().getPrioridade();
        prioridadeSetor = chamado.getUsuario().getSetor().getPrioridade();

        prioridadeFinal = Math.round((prioridadeSetor + prioridadeCategoria + prioridadeSubcategoria) / 3);

        if (prioridadeFinal==1) {
            return Prioridade.BAIXA;           
        } else if (prioridadeFinal==2) {
            return Prioridade.MEDIA; 
        } else if (prioridadeFinal==3) {
            return Prioridade.ALTA; 
        } else {
            return Prioridade.ALTISSIMA; 
        }

    }
    
    public int atribuicaoDoChamado(List<Usuario> usuarios) {

        int idTecnico=0;
        
        double prioridadeMenorChamado = 9999;
        for(Usuario u: usuarios){
            
            double prioridadeTotal = 0;
            
            List<Chamado> chamados = new ArrayList<Chamado>();
            ChamadoDAO chamadoDAO = new ChamadoDAO();
            chamados = chamadoDAO.consultaPrioridadeChamadoPorTecnico(u);
            
            for(Chamado ch: chamados) {
                prioridadeTotal = prioridadeTotal + ch.getPrioridade().getPrioridade();
                                    
            }
           
            if(prioridadeTotal<prioridadeMenorChamado){
                prioridadeMenorChamado=prioridadeTotal;
                idTecnico = u.getId();
            }            
            
        }
        
        return idTecnico;
    }
    
    public boolean verificarFilaVazia(int idTecnico) {

        double prioridadeTotal = 0;
            
            List<Chamado> chamados = new ArrayList<Chamado>();
            ChamadoDAO chamadoDAO = new ChamadoDAO();
            Usuario usuario = new Usuario();
            usuario.setId(idTecnico);
            chamados = chamadoDAO.consultaPrioridadeChamadoPorTecnico(usuario);
            
            for(Chamado ch: chamados) {
                prioridadeTotal = prioridadeTotal + ch.getPrioridade().getPrioridade();
                                    
            }
            
            if(prioridadeTotal == 0){
                return true;
            }else{
                return false;
            }      
            
    }
    
    public void realocacaoDeChamado(Chamado chamado) {
 
        double quantidadeChamados = 0;
        double quantidadeMaiorChamados = 0;
        int idTecnico = 0;
        int idChamado = 0;
        
        List<Usuario> usuarios = new ArrayList<Usuario>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarios = usuarioDAO.consultarTecnico();
        ChamadoDAO chamadoDAO = new ChamadoDAO();
        
        for(Usuario u: usuarios){
            
            quantidadeChamados = chamadoDAO.totalChamadosPorTecnico(u);
                       
            if(quantidadeChamados>quantidadeMaiorChamados){
                quantidadeMaiorChamados=quantidadeChamados;
                idTecnico = u.getId();
            }            
            
        }
        if(quantidadeMaiorChamados>1){
            idChamado = chamadoDAO.ultimoChamadoAtribuido(idTecnico);
        chamadoDAO.reatribuirChamado(chamado.getTecnico().getId(), idChamado);
        }
        
        
        
        
    }
    
    
    
    
}
