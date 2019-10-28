package service;

import dao.UsuarioDAO;
import dao.ChamadoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;
import util.ConectaBanco;

public class ServiceChamado {
    
    private ChamadoDAO chamadoDAO;
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private List<Usuario> usuarios;
    private List<Chamado> chamados;

    public ServiceChamado(ChamadoDAO chamadoDAO, Usuario usuario, UsuarioDAO usuarioDAO, List<Usuario> usuarios, List<Chamado> chamados) {
        this.chamadoDAO = chamadoDAO;
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.usuarios = usuarios;
        this.chamados = chamados;
    } 
    

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

    
}
