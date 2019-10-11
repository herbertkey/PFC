package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.Prioridade;
import model.Subcategoria;
import util.ConectaBanco;

public class SubcategoriaDAO {
    
    private static final String CADASTRA_NOVA_SUBCATEGORIA = "INSERT INTO subcategoria (subcategoria,prioridade,status,categoria) values (?,?,'ATIVO',?)";
    private static final String CONSULTA_SUBCATEGORIA = "SELECT subcategoria, prioridade, categoria FROM subcategoria WHERE upper(subcategoria) LIKE ? and status='ATIVO'";
    private static final String ALTERAR_SUBCATEGORIA = "UPDATE subcategoria SET prioridade=? WHERE upper(subcategoria)=?";
    private static final String CONSULTA_UMA_SUBCATEGORIA = "SELECT id, subcategoria, prioridade, categoria FROM subcategoria Where upper(subcategoria)=? AND status='ATIVO'";
    private static final String EXCLUIR_SUBCATEGORIA = "UPDATE subcategoria SET status='INATIVO' WHERE upper(subcategoria)=?";
    private static final String CONSULTAR_DEPENDENCIA = "SELECT subcategoria, prioridade, categoria FROM subcategoria WHERE categoria = ? and status='ATIVO'";

    public void cadastraNovaSubcategoria(Subcategoria subcategoria) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_SUBCATEGORIA);
            pstmt.setString(1, subcategoria.getSubcategoria());
            pstmt.setString(2, subcategoria.getPrioridade().toString());
            pstmt.setInt(3, Integer.parseInt(subcategoria.getCategoria().getId()));
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

    public List<Subcategoria> consultarSubcategoria(Subcategoria subcategoria, List<Categoria> categorias) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
        try {
            conexao = ConectaBanco.getConexao();
            //SELECT subcategoria, prioridade, categoria FROM subcategoria 
            //WHERE upper(s.subcategoria) LIKE ? and s.status='ATIVO'
            pstmt = conexao.prepareStatement(CONSULTA_SUBCATEGORIA);
            pstmt.setString(1, "%" + subcategoria.getSubcategoria().toUpperCase() + "%");
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                Subcategoria s = new Subcategoria();
                s.setSubcategoria(resultado.getString("subcategoria"));
                s.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                Categoria c = new Categoria();
                c.setId(resultado.getString("categoria"));
                
                for(Categoria cs: categorias){
                    String a = cs.getId();
                    String b = c.getId();
                    if(a.equals(b)){
                        c.setCategoria(cs.getCategoria());
                    }                    
                }               
                
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
            
            pstmt.setString(1, subcategoria.getPrioridade().toString());
            pstmt.setString(2, subcategoria.getSubcategoria().toUpperCase());
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
            //SELECT subcategoria,prioridade,categoria FROM subcategoria Where upper(subcategoria)=? AND status='ATIVO'
            pstmt.setString(1, subcategoria.getSubcategoria().toUpperCase());
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            s = new Subcategoria();
            if (resultado.next()) {
                s.setId(resultado.getString("id"));
                s.setSubcategoria(resultado.getString("subcategoria"));
                s.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                Categoria c = new Categoria();
                c.setId(resultado.getString("categoria"));
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
    
    public boolean consultarDependencia(int categoriaId) throws ClassNotFoundException, SQLException {

        Subcategoria s = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_DEPENDENCIA);
            //SELECT subcategoria, prioridade, categoria FROM subcategoria WHERE categoria = ? and status='ATIVO'
            
            pstmt.setInt(1, categoriaId);
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            List<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
            while (resultado.next()) {
                s = new Subcategoria();
                s.setSubcategoria(resultado.getString("subcategoria"));
                s.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                Categoria c = new Categoria();
                c.setId(resultado.getString("categoria"));
                s.setCategoria(c);
                subcategorias.add(s);
            }
            
            if(subcategorias.size()>0){
                return true;
            }else{
                return false;
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
        
    }

}
