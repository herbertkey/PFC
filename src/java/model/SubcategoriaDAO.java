package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBanco;

public class SubcategoriaDAO {
    
    private static final String CADASTRA_NOVA_SUBCATEGORIA = "INSERT INTO subcategoria (subcategoria,prioridade,status,categoria) values (?,?,'ATIVO',(SELECT id FROM categoria Where upper(categoria)=?))";
    private static final String CONSULTA_SUBCATEGORIA = "SELECT s.subcategoria, s.prioridade, c.categoria FROM subcategoria s, categoria c WHERE upper(s.subcategoria) LIKE ? and s.status='ATIVO' and s.categoria=c.id";
    private static final String ALTERAR_SUBCATEGORIA = "UPDATE subcategoria SET subcategoria=?,prioridade=? WHERE upper(subcategoria)=?";
    private static final String CONSULTA_UMA_SUBCATEGORIA = "SELECT s.subcategoria,s.prioridade, c.categoria FROM subcategoria s, categoria c Where upper(subcategoria)=? AND s.categoria=c.id";
    private static final String EXCLUIR_SUBCATEGORIA = "UPDATE subcategoria SET status='INATIVO' WHERE upper(subcategoria)=?";

    public void cadastraNovaSubcategoria(Subcategoria subcategoria) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_SUBCATEGORIA);
            pstmt.setString(1, subcategoria.getSubcategoria());
            pstmt.setString(2, subcategoria.getPrioridade().toString());
            pstmt.setString(3, subcategoria.getCategoria().getCategoria().toUpperCase());
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

    public List<Subcategoria> consultarSubcategoria(Subcategoria subcategoria) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_SUBCATEGORIA);
            pstmt.setString(1, "%" + subcategoria.getSubcategoria().toUpperCase() + "%");
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                Subcategoria s = new Subcategoria();
                s.setSubcategoria(resultado.getString("subcategoria"));
                s.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                Categoria c = new Categoria();
                c.setCategoria("categoria");
                s.setCategoria(c);
                subcategorias.add(s);
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
        return subcategorias;
    }

    public void alterarSubcategoria(Subcategoria subcategoria) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERAR_SUBCATEGORIA);
            pstmt.setString(1, subcategoria.getSubcategoria());
            pstmt.setString(2, subcategoria.getPrioridade().toString());
            pstmt.setString(3, subcategoria.getSubcategoria().toUpperCase());
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

    public Subcategoria consultaUmaSubcategoria(Subcategoria subcategoria) throws ClassNotFoundException, SQLException {

        Subcategoria s = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_UMA_SUBCATEGORIA);
            pstmt.setString(1, subcategoria.getSubcategoria().toUpperCase());
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            s = new Subcategoria();
            if (resultado.next()) {
                s.setSubcategoria(resultado.getString("subcategoria"));
                s.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                Categoria c = new Categoria();
                c.setCategoria("categoria");
                s.setCategoria(c);
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
        return s;
    }

    public void excluirSubcategoria(Subcategoria subcategoria) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUIR_SUBCATEGORIA);
            pstmt.setString(1, subcategoria.getSubcategoria().toUpperCase());
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
