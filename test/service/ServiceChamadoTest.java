package service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import model.*;

public class ServiceChamadoTest {
   
    private ChamadoDAO chamadoDAOMock;    
    
    	@Before
	public void inicializa(){
		//criacao de mocks
		chamadoDAOMock = mock(ChamadoDAO.class);

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
		//when(pedidoDAOMock.consultaPorId(any(Pedido.class))).thenReturn(pedido);
		//when(nfEletronicaMock.enviar(any(NotaFiscal.class))).thenReturn(new Random().nextLong());
				
		
	}
        @Test
	public void deveCalcularAPrioridadeDoChamado(){
            
                ServiceChamado serviceChamado = new ServiceChamado(chamadoDAOMock, usuario, usuarioDAO, usuarios, chamados)

		NotaFiscalService notaFiscalService = new NotaFiscalService(pedidoDAOMock, notaFiscalDAOMock, nfEletronicaMock);
		
		NotaFiscal notaFiscal = notaFiscalService.gerar(new Pedido());
		
		assertEquals(Double.valueOf(250.0), notaFiscal.getValorBruto(), 0.001);
		assertEquals(Double.valueOf(235.0),notaFiscal.getValorLiquido(), 0.001);
		
		//validar comportamento dos mocks
		verify(notaFiscalDAOMock).cadastrar(notaFiscal);
		verify(nfEletronicaMock).enviar(notaFiscal);
				
	}
        
        
    
}
