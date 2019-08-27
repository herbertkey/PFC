package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBanco;

public class CategoriaDAO {
    
    private static final String CADASTRA_NOVA_CATEGORIA = "INSERT INTO categoria (categoria,prioridade,status) values (?,?,'ATIVO')";
    private static final String CONSULTA_CATEGORIA = "SELECT id, categoria, prioridade FROM categoria WHERE upper(categoria) LIKE ? and status='ATIVO'";
    private static final String ALTERAR_CATEGORIA = "UPDATE categoria SET categoria=?,prioridade=? WHERE upper(categoria)=?";
    private static final String CONSULTA_UMA_CATEGORIA = "SELECT categoria,prioridade FROM categoria Where upper(categoria)=?";
    private static final String EXCLUIR_CATEGORIA = "UPDATE categoria SET status='INATIVO' WHERE upper(categoria)=?";
    private static final String CONSULTA_ID_CATEGORIA_PARA_CADASTRAR_SUBCATEGORIA = "SELECT id FROM categoria Where upper(categoria)=?";
    private static final String CONSULTA_NOME_CATEGORIA_POR_ID = "SELECT categoria FROM categoria Where id=?";

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

    public List<Categoria> consultarCategoria(Categoria categoria) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Categoria> categorias = new ArrayList<Categoria>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_CATEGORIA);
            pstmt.setString(1, "%" + categoria.getCategoria().toUpperCase() + "%");
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                Categoria c = new Categoria();
                c.setId(resultado.getString("id"));
                c.setCategoria(resultado.getString("categoria"));
                c.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));

                categorias.add(c);
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
        return categorias;
    }

    public void alterarCategoria(Categoria categoria) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERAR_CATEGORIA);
            pstmt.setString(1, categoria.getCategoria());
            pstmt.setString(2, categoria.getPrioridade().toString());
            pstmt.setString(3, categoria.getCategoria().toUpperCase());
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

    public Categoria consultaUmaCategoria(Categoria categoria) throws ClassNotFoundException, SQLException {

        Categoria c = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_UMA_CATEGORIA);
            pstmt.setString(1, categoria.getCategoria().toUpperCase());
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            c = new Categoria();
            if (resultado.next()) {
                c.setCategoria(resultado.getString("categoria"));
                c.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
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
        return c;
    }

    public void excluirCategoria(Categoria categoria) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUIR_CATEGORIA);
            pstmt.setString(1, categoria.getCategoria().toUpperCase());
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
    public Categoria consultaIdCategoriaParaCadastrarSubcategoria(Categoria categoria) throws ClassNotFoundException, SQLException {

        Categoria c = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_ID_CATEGORIA_PARA_CADASTRAR_SUBCATEGORIA);
            pstmt.setString(1, categoria.getCategoria().toUpperCase());
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            c = new Categoria();
            if (resultado.next()) {
                c.setId(resultado.getString("id"));
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
        return c;
    }
    public Categoria consultaNomeCategoriaPorID(Categoria categoria) throws ClassNotFoundException, SQLException {

        Categoria c = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_NOME_CATEGORIA_POR_ID);
            pstmt.setInt(1, Integer.parseInt(categoria.getId()));
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            c = new Categoria();
            if (resultado.next()) {
                c.setCategoria(resultado.getString("categoria"));
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
        return c;
    }
    
    
}
