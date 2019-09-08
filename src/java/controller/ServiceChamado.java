package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Chamado;
import model.Prioridade;
import model.Usuario;
import util.ConectaBanco;

public class ServiceChamado {
    
    private static final String CONSULTA_PRIORIDADE_CHAMADO = "SELECT prioridade FROM chamado c WHERE ?=tecnico AND (status LIKE 'ABERTO' OR status LIKE 'EM_ANDAMENTO')";
    
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

        Connection conexao = null;
        PreparedStatement pstmt = null;
        int idTecnico=0;
        
        try {
            
            double prioridadeMenorChamado = 9999;            
                        
            for(Usuario u: usuarios){
                
            double prioridadeTotal = 0;
            //double cont=0;
                 
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_PRIORIDADE_CHAMADO);
            //SELECT prioridade 
            //FROM chamado c 
            //WHERE ?=tecnico AND (status LIKE 'ABERTO' OR status LIKE 'EM_ANDAMENTO')
            
            pstmt.setInt(1, u.getId());
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                Chamado c = new Chamado();
                c.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                prioridadeTotal = prioridadeTotal + c.getPrioridade().getPrioridade();
                                    
            }
           
            if(prioridadeTotal<prioridadeMenorChamado){
                prioridadeMenorChamado=prioridadeTotal;
                idTecnico = u.getId();
            }            
            
            }            
            
        } catch (SQLException sqlErro) {
            throw new RuntimeException(sqlErro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return idTecnico;
    }
    
    
    
    
}
