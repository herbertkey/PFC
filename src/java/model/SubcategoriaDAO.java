package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.ConectaBanco;

public class SubcategoriaDAO {
        private static final String CADASTRA_NOVA_SUBCATEGORIA = "INSERT INTO subcategoria (subcategoria, prioridade) VALUES (?,?)";
    
    public void cadastraNovaSubcategoria(Subcategoria subcategoria) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_SUBCATEGORIA);
            pstmt.setString(1, subcategoria.getSubcategoria());
            pstmt.setString(2, subcategoria.getPrioridade().toString());
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
