package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Historico;
import model.Usuario;
import util.ConectaBanco;

public class HistoricoDAO {
    
    private static final String INSERIR_INFORMACOES_ADICIONAIS = "INSERT INTO historico (data,informacoes_adicionais,usuario,chamado) VALUES (?,?,?,?)";
    private static final String CONSULTA_INFORMACOES_ADICIONAIS = "SELECT h.id, h.data, h.informacoes_adicionais, u.nome FROM historico h INNER JOIN usuario u on u.id=h.usuario WHERE h.chamado=? ORDER BY data";
        
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
            pstmt.setInt(3, historico.getUsuario().getId()); 
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
    
        public List<Historico> consultarHistoricoChamado(Historico historico) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Historico> historicos = new ArrayList<Historico>();
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_INFORMACOES_ADICIONAIS);
            //SELECT h.id, h.data, h.informacoes_adicionais, u.nome 
            //FROM historico h 
            //INNER JOIN usuario u on u.id=h.usuario 
            //WHERE h.chamado=? ORDER BY data
            pstmt.setInt(1, Integer.parseInt(historico.getChamado().getId()));
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {    
                Historico h = new Historico();
                
                h.setData(resultado.getString("data"));
                h.setInformacoes_adicionais(resultado.getString("informacoes_adicionais"));
                
                Usuario u = new Usuario();                
                u.setNome(resultado.getString("nome"));
                h.setUsuario(u);

                historicos.add(h);
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
        return historicos;
    }
        
        
        
        
}
