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
import util.ConectaBanco;

public class ServiceChamado {

    public String calcularPrioridadeDoChamado(Chamado chamado) {

        String prioridade = "0";
        double prioridadeFinal = 0;
        double prioridadeCategoria = 0;
        double prioridadeSubcategoria = 0;
        double prioridadeSetor = 0;

        prioridadeCategoria = chamado.getCategoria().getPrioridade().getPrioridade();
        prioridadeSubcategoria = chamado.getSubcategoria().getPrioridade().getPrioridade();
        prioridadeSetor = chamado.getUsuario().getSetor().getPrioridade();

        prioridadeFinal = Math.round((prioridadeSetor + prioridadeCategoria + prioridadeSubcategoria) / 3);

        if (prioridadeFinal==1) {
            prioridade = "BAIXA";
        } else if (prioridadeFinal==2) {
            prioridade = "MEDIA";
        } else if (prioridadeFinal==3) {
            prioridade = "ALTA";
        } else if (prioridadeFinal==4) {
            prioridade = "ALTISSIMA";
        }

        return prioridade;

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
    
    
    
    
}
