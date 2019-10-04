package dao;

import dao.CategoriaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Categoria;
import model.Prioridade;
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
        categoriaDAO = new CategoriaDAO(conexao);
    }
    
    @After
    public void finaliza() throws SQLException{
        try{
        inicializa();
        
        PreparedStatement pstmt = conexao.prepareStatement("delete from categoria; alter sequence categoria_id_seq restart with 1;");
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
    
    @Test
    public void deveCadastrarNovaCategoria() throws SQLException{
        Categoria categoria = new Categoria();
        categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);
        
        categoriaDAO.cadastraNovaCategoria(categoria);        
        categoria.setCategoria("");        
        inicializa();
        
        Categoria categoriaCadastrada = categoriaDAO.consultarCategoria(categoria).get(0);
        
        assertEquals("Maquinas", categoriaCadastrada.getCategoria());
        assertEquals("ALTA", categoriaCadastrada.getPrioridade().toString());
                
    }
    
    @Test
    public void deveConsultarCategoriasCadastradas() throws SQLException{
        
        Categoria categoria = new Categoria();
        
        for (int i =0;i<10;i++ ){
                
        inicializa();
        
        categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);
        categoriaDAO.cadastraNovaCategoria(categoria);  
        
        }
        
        categoria.setCategoria("");        
        inicializa();
        
        assertEquals(10, categoriaDAO.consultarCategoria(categoria).size());
        
    }
    
    @Test
    public void deveAtualizarUmaCategoria() throws SQLException{
        Categoria categoria = new Categoria();
        
        categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);        
        categoriaDAO.cadastraNovaCategoria(categoria);   
                
        categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.BAIXA);
        inicializa();
        categoriaDAO.alterarCategoria(categoria);        
        
        categoria.setCategoria("");        
        inicializa();        
        Categoria categoriaCadastrada = categoriaDAO.consultarCategoria(categoria).get(0);
        
        assertEquals("Maquinas", categoriaCadastrada.getCategoria());
        assertEquals("BAIXA", categoriaCadastrada.getPrioridade().toString());
        
    }
    
    @Test
    public void deveeExcluirUmaCategoria() throws SQLException, ClassNotFoundException{
        Categoria categoria = new Categoria();
        
        categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);        
        categoriaDAO.cadastraNovaCategoria(categoria);   
        
        inicializa();     
        Categoria categoriaCadastrada = categoriaDAO.consultarCategoria(categoria).get(0);
                 
        categoria.setCategoria(categoriaCadastrada.getCategoria());
        
        inicializa();   
        categoriaDAO.excluirCategoria(categoria);
        
        inicializa();   
        assertEquals(0, categoriaDAO.consultarCategoria(categoria).size());
                
    }
    
    @Test
    public void deveConsultaroNomeDeUmaCategoriaPorId() throws SQLException, ClassNotFoundException{
        Categoria categoria = new Categoria();
        
        for (int i =0;i<10;i++ ){                
        inicializa();              
        if(i==2){
            categoria.setCategoria("Equipamento");
        categoria.setPrioridade(Prioridade.ALTA);
        categoriaDAO.cadastraNovaCategoria(categoria); 
        }else{
            categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);
        categoriaDAO.cadastraNovaCategoria(categoria); 
        }        
        }
        
        categoria.setId("3");                
        inicializa();     
        Categoria categoriaConsultada = categoriaDAO.consultaNomeCategoriaPorID(categoria);
         
        assertEquals("Equipamento",categoriaConsultada.getCategoria() );
                
    }
        @Test
    public void deveConsultaroUmaCategoriaPorNome() throws SQLException, ClassNotFoundException{
        Categoria categoria = new Categoria();
        
        for (int i =0;i<10;i++ ){                
        inicializa();              
        if(i==2){
            categoria.setCategoria("Equipamento");
        categoria.setPrioridade(Prioridade.ALTISSIMA);
        categoriaDAO.cadastraNovaCategoria(categoria); 
        }else{
            categoria.setCategoria("Maquinas");
        categoria.setPrioridade(Prioridade.ALTA);
        categoriaDAO.cadastraNovaCategoria(categoria); 
        }        
        }
        
        categoria.setCategoria("Equipamento");                
        inicializa();     
        Categoria categoriaConsultada = categoriaDAO.consultaUmaCategoria(categoria);
         
        assertEquals("Equipamento",categoriaConsultada.getCategoria() );
        assertEquals("3",categoriaConsultada.getId() );
        assertEquals("ALTISSIMA",categoriaConsultada.getPrioridade().toString());
                
    }
    
    
}

