package service;

import dao.UsuarioDAO;
import dao.ChamadoDAO;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static javafx.scene.input.KeyCode.T;
import model.*;

public class ServiceChamadoTest {
   
    private ChamadoDAO chamadoDAOMock;
    private Usuario usuarioMock;
    private UsuarioDAO usuarioDAOMock;
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private List<Usuario> usuariosMock;
    private Chamado listaChamadoMock;
    private List<Chamado> chamadosMock;
    private Usuario tecnico;
    private Chamado chamado;
    
    
    	@Before
	public void inicializa() throws ClassNotFoundException, SQLException{
		//criacao de mocks
		chamadoDAOMock = mock(ChamadoDAO.class);
                usuarioMock = mock(Usuario.class);
                usuarioDAOMock = mock(UsuarioDAO.class);  
                //usuariosMock = mock(List.class);
                
                //listaUsuarioMock = mock(Usuario.class);
                //usuariosMock.add(listaUsuarioMock);
                //listaChamadoMock = mock(Chamado.class);
                //chamadosMock.add(listaChamadoMock);
                
		//definir comportamento             
                                
                Usuario usuario = new Usuario(1, 111, "herbert@umc.br", "Herbert", "111", "111", Tipo.SUPERVISOR, "Supervisor", Setor.ADMINISTRACAO);
                tecnico = new Usuario(2, 222, "tecnico@umc.br", "Tecnico", "222", "222", Tipo.TECNICO, "Tecnico", Setor.TI);
                //usuarioMock = tecnico;
                Categoria categoria = new Categoria("1", "Software", Prioridade.BAIXA);
                Subcategoria subcategoria = new Subcategoria("1", "Netbeans", Prioridade.BAIXA, categoria);
                chamado = new Chamado("1", "teste", "19/08/2019", "20/09/2019", StatusChamado.ABERTO, usuario, categoria, subcategoria, tecnico, Prioridade.BAIXA);
                List<Chamado> chamados = new ArrayList<Chamado>();
                chamados.add(chamado);
                usuarios.add(tecnico);
		when(chamadoDAOMock.consultarUmChamado(any(Chamado.class))).thenReturn(chamado);
                when(chamadoDAOMock.consultaPrioridadeChamadoPorTecnico(any(Usuario.class))).thenReturn(chamados);
                when(usuarioDAOMock.consultaUmUsuario(any(Usuario.class))).thenReturn(tecnico);
		when(usuarioDAOMock.consultarTecnico()).thenReturn(usuarios);
                when(chamadoDAOMock.totalChamadosPorTecnico(any(Usuario.class))).thenReturn(2);
                when(chamadoDAOMock.ultimoChamadoAtribuido(2)).thenReturn(1);
                
                
				
		
	}
        
        @Test
	public void deveCalcularAPrioridadeDoChamado(){
            
                ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuarioMock, usuarioDAOMock, usuariosMock, chamadosMock);
                 		
                Chamado chamado = chamadoDAOMock.consultarUmChamado(new Chamado());
                              
		assertEquals(Prioridade.BAIXA, serviceChamado.calcularPrioridadeDoChamado(chamado));
		
				
	}
        
        @Test
	public void devePegarOIdDoTecnicoComOMenorNumeroDePrioridadesDeChamado() throws ClassNotFoundException, SQLException{
            
                 ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuarioMock, usuarioDAOMock, usuariosMock, chamadosMock);
                                  
		assertEquals(2, serviceChamado.atribuicaoDoChamado(usuarios));
		
               // Usuario tecnico = usuarioDAOMock.consultaUmUsuario(new Usuario());
		//validar comportamento dos mocks
		verify(chamadoDAOMock).consultaPrioridadeChamadoPorTecnico(tecnico);
				
	}
        
        @Test
        public void deveVerificarSeAFilaDoTecnicoEstaVazia() throws ClassNotFoundException, SQLException{
            
                 ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, tecnico, usuarioDAOMock, usuariosMock, chamadosMock);
                                  
		assertEquals(false, serviceChamado.verificarFilaVazia(2));
				
		//Usuario tecnico = usuarioDAOMock.consultaUmUsuario(new Usuario());
		//validar comportamento dos mocks
		verify(chamadoDAOMock).consultaPrioridadeChamadoPorTecnico(tecnico);
				
	}
 
        @Test
        public void deveAtribuirUmChamadoParaOTecnico() throws ClassNotFoundException, SQLException{
            
                ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuarioMock, usuarioDAOMock, usuarios, chamadosMock);
                
                serviceChamado.realocacaoDeChamado(chamado);
                verify(chamadoDAOMock).reatribuirChamado(2,1);
                				
	}
        
    
}
