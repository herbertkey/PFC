package model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import util.ConectaBanco;

public class ChamadoDAO {

    //private static final String AUTENTICA_USUARIO = "SELECT * FROM usuario WHERE numero_registro=? AND senha=? AND status='ATIVO'";
    private static final String ABRIR_CHAMADO = "INSERT INTO chamado(descricao, data_inicio, status, usuario, categoria, subcategoria, tecnico, prioridade) VALUES (?, ?,'ABERTO', (SELECT id FROM usuario WHERE numero_registro=?), (SELECT id FROM categoria Where upper(categoria)=?), (SELECT id FROM subcategoria Where upper(subcategoria)=?), ?, ?);";
    private static final String CONSULTA_CHAMADO_TECNICO = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade FROM chamado c WHERE (SELECT id FROM usuario WHERE numero_registro=?)=c.tecnico	AND c.status LIKE replace(?,'TODOS','%%')";
    private static final String CONSULTA_CHAMADO_CLIENTE = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade FROM chamado c WHERE (SELECT id FROM usuario WHERE numero_registro=?)=c.usuario	AND c.status LIKE replace(?,'TODOS','%%')";
    private static final String CONSULTA_CHAMADO_SUPERVISOR = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade FROM chamado c WHERE c.status LIKE replace(?,'TODOS','%%')";
    private static final String CONSULTA_UM_CHAMADO = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade FROM chamado c WHERE c.id=?";
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

    public List<Chamado> consultarChamadosCliente(Chamado chamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_CHAMADO_CLIENTE);
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade 
            //FROM chamado c
            //WHERE (SELECT id FROM usuario WHERE numero_registro=?)=c.usuario 
            //AND c.status LIKE replace(?,'TODOS','%%')

            pstmt.setInt(1, chamado.getUsuario().getNumero_registro());
            pstmt.setString(2, "%" + chamado.getStatus().toString() + "%");
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

    public List<Chamado> consultarChamadosTecnico(Chamado chamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_CHAMADO_TECNICO);
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade 
            //FROM chamado c
            //WHERE (SELECT id FROM usuario WHERE numero_registro=?)=c.tecnico 
            //AND c.status LIKE replace(?,'TODOS','%%')

            pstmt.setInt(1, chamado.getUsuario().getNumero_registro());
            pstmt.setString(2, "%" + chamado.getStatus().toString() + "%");
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

    public List<Chamado> consultarChamadosSupervisor(Chamado chamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_CHAMADO_SUPERVISOR);
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade 
            //FROM chamado c
            //WHERE c.status LIKE replace(?,'TODOS','%%')

            pstmt.setString(1, "%" + chamado.getStatus().toString() + "%");
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

    public Chamado consultarUmChamado(Chamado chamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        Chamado c = null;
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_UM_CHAMADO);
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, (SELECT nome FROM usuario WHERE id=c.usuario), c.categoria, c.subcategoria, (SELECT nome as tecnico FROM usuario WHERE id=c.tecnico), c.prioridade 
            //FROM chamado c
            //WHERE c.id=?

            pstmt.setInt(1, Integer.parseInt(chamado.getId()));
            ResultSet resultado = pstmt.executeQuery();

                c = new Chamado();
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
