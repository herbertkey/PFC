package model;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import util.ConectaBanco;

public class CategoriaDAOTest {
    private CategoriaDAO categoriaDAO;
    private Connection conexao;
    
   
    @Before
    public void inicializa() throws SQLException{
        conexao = ConectaBanco.getConexao();
        conexao.setAutoCommit(false);
        categoriaDAO = new CategoriaDAO(conexao);
    }
    @After
    public void finaliza()throws SQLException{
        conexao.rollback();
    }
    
    @Test
    public void deveCadastrarNovaCategoria(){
        Categoria categoria = new Categoria();
        categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);
        
        categoriaDAO.cadastraNovaCategoria(categoria);
        
        categoria.setCategoria("");
        
        Categoria categoriaCadastrada = categoriaDAO.consultarCategoria(categoria).get(0);
        
        assertEquals("Maquinas", categoriaCadastrada.getCategoria());
        
    }
    
    
    
    
}
