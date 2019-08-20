package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConectaBanco;

public class HistoricoDAO {
    
    private static final String INSERIR_INFORMACOES_ADICIONAIS = "INSERT INTO historico (data,informacoes_adicionais,usuario,chamado) VALUES (?,?,(SELECT id FROM usuario WHERE numero_registro=?),?)";
    private static final String CONSULTA_INFORMACOES_ADICIONAIS = "SELECT h.id, h.data, h.informacoes_adicionais, (SELECT nome FROM usuario WHERE id=h.usuario), h.chamado FROM historico h";
        
        public void inserirInformacoes(Historico historico) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            
            //INSERT INTO historico (data,informacoes_adicionais,usuario,chamado) 
            //VALUES (?,?,(SELECT id FROM usuario WHERE numero_registro=?),?)

            pstmt = conexao.prepareStatement(INSERIR_INFORMACOES_ADICIONAIS);
            pstmt.setString(1, historico.getData().toString());
            pstmt.setString(2, historico.getInformacoes_adicionais());
            pstmt.setInt(3, historico.getUsuario().getNumero_registro()); 
            pstmt.setInt(4, Integer.parseInt(historico.getChamado().getId())); 
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
    
        public List<Chamado> consultarChamadosCliente(Chamado chamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_INFORMACOES_ADICIONAIS);
            //SELECT h.id, h.data, h.informacoes_adicionais, (SELECT nome FROM usuario WHERE id=h.usuario), h.chamado 
            //FROM historico h
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                Chamado c = new Chamado();
                Usuario u = new Usuario();
                Usuario t = new Usuario();
                Categoria cat = new Categoria();
                Subcategoria s = new Subcategoria();
                c.setId(resultado.getString("id"));
                c.setDescricao(resultado.getString("descricao"));
                c.setData_inicio(resultado.getString("data_inicio"));
                c.setData_fim(resultado.getString("data_fim"));
                c.setStatus(StatusChamado.valueOf(resultado.getString("status")));
                u.setNome(resultado.getString("nome"));
                c.setUsuario(u);
                cat.setCategoria(resultado.getString("categoria"));
                c.setCategoria(cat);
                s.setSubcategoria(resultado.getString("subcategoria"));
                c.setSubcategoria(s);
                t.setNome(resultado.getString("tecnico"));
                c.setTecnico(t);
                c.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));

                chamados.add(c);
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
        return chamados;
    }
        
        
        
        
}
