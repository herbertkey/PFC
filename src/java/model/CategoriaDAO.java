package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.ConectaBanco;

public class CategoriaDAO {
    
    private static final String CADASTRA_NOVA_CATEGORIA = "INSERT INTO categoria (categoria, prioridade) VALUES (?,?)";
    
    public void cadastraNovaCategoria(Categoria categoria) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_CATEGORIA);
            pstmt.setString(1, categoria.getCategoria());
            pstmt.setString(2, categoria.getPrioridade().toString());
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
