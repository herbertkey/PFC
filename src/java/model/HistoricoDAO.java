package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.ConectaBanco;

public class HistoricoDAO {
    
    private static final String INSERIR_INFORMACOES_ADICIONAIS = "INSERT INTO historico (data,informacoes_adicionais,usuario,chamado) VALUES (?,?,(SELECT id FROM usuario WHERE numero_registro=?),?)";
    private static final String CONSULTA_INFORMCOES_ADICIONAIS = "SELECT h.id, h.data, h.informacoes_adicionais, (SELECT nome FROM usuario WHERE id=h.usuario), h.chamado FROM historico h WHERE h.chamado=?";
        
        public void inserirInformacoes(Historico historico) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            
            //INSERT INTO historico (data,informacoes_adicionais,usuario,chamado) 
            //VALUES (?,?,(SELECT id FROM usuario WHERE numero_registro=?),?)

            pstmt = conexao.prepareStatement(INSERIR_INFORMACOES_ADICIONAIS);
            pstmt.setString(1, historico.getData().toString());
            pstmt.setString(2, historico.getInformacoes_adicionais());
            pstmt.setInt(3, historico.getUsuario().getNumero_registro()); 
            pstmt.setInt(4, Integer.parseInt(historico.getChamado().getId())); 
            pstmt.execute();

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
    }
}
