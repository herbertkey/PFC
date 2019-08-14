package model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import util.ConectaBanco;

public class ChamadoDAO {
    
    //private static final String AUTENTICA_USUARIO = "SELECT * FROM usuario WHERE numero_registro=? AND senha=? AND status='ATIVO'";
    private static final String ABRIR_CHAMADO = "INSERT INTO chamado(descricao, data_inicio, status, usuario, categoria, subcategoria, tecnico, prioridade) VALUES (?, ?,'ABERTO', (SELECT id FROM usuario WHERE numero_registro=?), (SELECT id FROM categoria Where upper(categoria)=?), (SELECT id FROM subcategoria Where upper(subcategoria)=?), ?, ?);";
    private static final String CONSULTA_CHAMADO_TECNICO = "SELECT descricao, data_inicio, data_fim, status, usuario, categoria, subcategoria, tecnico, prioridade FROM chamado WHERE tecnico=? AND status LIKE ?";
    private static final String CONSULTA_CHAMADO_CLIENTE = "SELECT descricao, data_inicio, data_fim, status, usuario, categoria, subcategoria, tecnico, prioridade FROM chamado WHERE usuario=? AND status LIKE ?";
    //private static final String ALTERAR_USUARIO = "UPDATE usuario SET email=?,nome=?,telefone=?,tipo=?,cargo=?, setor=? WHERE numero_registro=?";
    //private static final String CONSULTA_UM_USUARIO = "SELECT numero_registro, email, nome, telefone, tipo, cargo, setor FROM usuario Where numero_registro=?";
    //private static final String EXCLUIR_USUARIO = "UPDATE usuario SET status='INATIVO' WHERE numero_registro=?";
    
    public void abrirChamado(Chamado chamado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
           
            pstmt = conexao.prepareStatement(ABRIR_CHAMADO);
            pstmt.setString(1, chamado.getDescricao());
            pstmt.setString(2, chamado.getData_inicio().toString());
            //Status do chamado automaticamente Aberto
            pstmt.setInt(3, chamado.getUsuario().getNumero_registro()); //Precisa ser o Numero de Registro do cliente
            pstmt.setString(4, chamado.getCategoria().getCategoria().toString().toUpperCase()); //Precisa ser o ID da categoria
            pstmt.setString(5, chamado.getSubcategoria().getSubcategoria().toString().toUpperCase());
            pstmt.setInt(6, chamado.getTecnico().getNumero_registro()); //Precisa ser o Numero de Registro do técnico
            pstmt.setString(7, chamado.getPrioridade().toString().toUpperCase()); // Fazer o calculo da prioridade e salvar o nome do ENUM
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

    public String getDateTime() { 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	Date date = new Date(); 
	return dateFormat.format(date); 
    }
    
}
