package service;

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
    
    
    	@Before
	public void inicializa(){
		//criacao de mocks
		chamadoDAOMock = mock(ChamadoDAO.class);
                usuarioMock = mock(Usuario.class);
                usuarioDAOMock = mock(UsuarioDAO.class);  
                
                //listaUsuarioMock = mock(Usuario.class);
                //usuariosMock.add(listaUsuarioMock);
                //listaChamadoMock = mock(Chamado.class);
                //chamadosMock.add(listaChamadoMock);
                
		//definir comportamento             
                
                //consultaPrioridadeChamadoPorTecnico
                //totalChamadosPorTecnico
                //ultimoChamadoAtribuido
                //reatribuirChamado
                
                Usuario usuario = new Usuario(1, 123, "herbert@umc.br", "Herbert", "123", "123", Tipo.SUPERVISOR, "Supervisor", Setor.ADMINISTRACAO);
                Usuario tecnico = new Usuario(1, 111, "tecnico@umc.br", "Tecnico", "111", "111", Tipo.TECNICO, "Tecnico", Setor.TI);
                Categoria categoria = new Categoria("1", "Software", Prioridade.BAIXA);
                Subcategoria subcategoria = new Subcategoria("1", "Netbeans", Prioridade.BAIXA, categoria);
                Chamado chamado = new Chamado("1", "teste", "19/08/2019", "20/09/2019", StatusChamado.ABERTO, usuario, categoria, subcategoria, tecnico, Prioridade.BAIXA);
                List<Chamado> chamados = new ArrayList<Chamado>();
                chamados.add(chamado);
                usuarios.add(tecnico);
		when(chamadoDAOMock.consultarUmChamado(any(Chamado.class))).thenReturn(chamado);
                when(chamadoDAOMock.consultaPrioridadeChamadoPorTecnico(any(Usuario.class))).thenReturn(chamados);
		//when(nfEletronicaMock.enviar(any(NotaFiscal.class))).thenReturn(new Random().nextLong());
				
		
	}
        
        @Test
	public void deveCalcularAPrioridadeDoChamado(){
            
                ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuarioMock, usuarioDAOMock, usuariosMock, chamadosMock);
                 		
                Chamado chamado = chamadoDAOMock.consultarUmChamado(new Chamado());
                              
		assertEquals(Prioridade.BAIXA, serviceChamado.calcularPrioridadeDoChamado(chamado));
				
		//validar comportamento dos mocks
		//verify(chamadoDAOMock).consultarUmChamado(chamado);
				
	}
        
        @Test
	public void devePegarOIdDoTecnicoComOMenorNumeroDePrioridadesDeChamado(){
            
                 ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuarioMock, usuarioDAOMock, usuariosMock, chamadosMock);
                                  
		assertEquals(1, serviceChamado.atribuicaoDoChamado(usuarios));
				
		//validar comportamento dos mocks
		//verify(chamadoDAOMock).consultarUmChamado(chamado);
				
	}
        
        @Test
        public void deveVerificarSeAFilaDoTecnicoEstaVazia(){
            
                 ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuarioMock, usuarioDAOMock, usuariosMock, chamadosMock);
                                  
		assertEquals(false, serviceChamado.verificarFilaVazia(1));
				
		//validar comportamento dos mocks
		//verify(chamadoDAOMock).consultarUmChamado(chamado);
				
	}
        
        
    
}
