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
import java.lang.Math;

public class ChamadoDAO {

    private static final String ABRIR_CHAMADO = "INSERT INTO chamado(descricao, data_inicio, data_fim, status, usuario, categoria, subcategoria, tecnico, prioridade) VALUES (?, ?,?,'ABERTO', ?, ?, ?, ?, ?)";
    private static final String CONSULTA_CHAMADO_TECNICO = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, cat.categoria, sc.subcategoria, t.nome as tecnico, c.prioridade FROM chamado c INNER JOIN usuario u ON u.id = c.usuario INNER JOIN categoria cat ON cat.id = c.categoria INNER JOIN subcategoria sc ON sc.id = c.subcategoria INNER JOIN usuario t ON t.id = c.tecnico WHERE ?=c.tecnico AND c.status LIKE replace(?,'TODOS','%%') ORDER BY status";
    private static final String CONSULTA_CHAMADO_CLIENTE = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, cat.categoria, sc.subcategoria, t.nome as tecnico, c.prioridade FROM chamado c INNER JOIN usuario u ON u.id = c.usuario INNER JOIN categoria cat ON cat.id = c.categoria INNER JOIN subcategoria sc ON sc.id = c.subcategoria INNER JOIN usuario t ON t.id = c.tecnico WHERE ?=c.usuario AND c.status LIKE replace(?,'TODOS','%%') ORDER BY status";
    private static final String CONSULTA_CHAMADO_SUPERVISOR = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, cat.categoria, sc.subcategoria, t.nome as tecnico, c.prioridade FROM chamado c INNER JOIN usuario u ON u.id = c.usuario INNER JOIN categoria cat ON cat.id = c.categoria INNER JOIN subcategoria sc ON sc.id = c.subcategoria INNER JOIN usuario t ON t.id = c.tecnico WHERE c.status LIKE replace(?,'TODOS','%%') ORDER BY status";
    private static final String CONSULTA_UM_CHAMADO = "SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, u.numero_registro, cat.categoria, sc.subcategoria, t.nome as tecnico, t.id as idtecnico, c.prioridade FROM chamado c INNER JOIN usuario u on u.id = c.usuario INNER JOIN categoria cat on cat.id = c.categoria INNER JOIN subcategoria sc on sc.id = c.subcategoria INNER JOIN usuario t on t.id = c.tecnico WHERE c.id=?";
    private static final String ALTERAR_CHAMADO = "UPDATE chamado SET descricao=?, status=?, categoria=(SELECT id FROM categoria WHERE categoria=?), subcategoria=(SELECT id FROM subcategoria WHERE subcategoria=?), prioridade=?, data_fim=? WHERE id=?";
    private static final String CONSULTA_PRIORIDADE_CHAMADO = "SELECT prioridade FROM chamado WHERE ?=tecnico AND (status LIKE 'ABERTO' OR status LIKE 'EM_ANDAMENTO')";
    private static final String TOTAL_CHAMADOS_POR_TECNICO = "SELECT count(id) AS id FROM chamado WHERE ?=tecnico AND (status LIKE 'ABERTO')";
    private static final String ULTIMO_CHAMADO_ATRIBUIDO = "SELECT id , data_inicio FROM chamado WHERE ?=tecnico AND (status LIKE 'ABERTO') order by data_inicio asc";
    private static final String REATRIBUIR_CHAMADO = "UPDATE chamado SET tecnico=? WHERE id=?";
    
    public void abrirChamado(Chamado chamado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            //INSERT INTO chamado(descricao, data_inicio, data_fim, status, usuario, categoria, subcategoria, tecnico, prioridade) 
            //VALUES (?, ?,?,'ABERTO', ?, ?, ?, ?, ?)

            pstmt = conexao.prepareStatement(ABRIR_CHAMADO);
            pstmt.setString(1, chamado.getDescricao());
            pstmt.setString(2, chamado.getData_inicio().toString());
            //Status do chamado automaticamente Aberto
            pstmt.setString(3, chamado.getData_fim().toString());
            pstmt.setInt(4, chamado.getUsuario().getId());
            pstmt.setInt(5, Integer.parseInt(chamado.getCategoria().getId())); //Precisa ser o ID da categoria
            pstmt.setInt(6, Integer.parseInt(chamado.getSubcategoria().getId())); //Precisa ser o ID da subcategoria
            pstmt.setInt(7, chamado.getTecnico().getId()); //Precisa ser o ID do técnico
            pstmt.setString(8, chamado.getPrioridade().toString().toUpperCase()); // Fazer o calculo da prioridade e salvar o nome do ENUM
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
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, cat.categoria, sc.subcategoria, t.nome as tecnico, c.prioridade 
            //FROM chamado c 
            //INNER JOIN usuario u ON u.id = c.usuario 
            //INNER JOIN categoria cat ON cat.id = c.categoria 
            //INNER JOIN subcategoria sc ON sc.id = c.subcategoria 
            //INNER JOIN usuario t ON t.id = c.tecnico 
            //WHERE ?=c.usuario AND c.status LIKE replace(?,'TODOS','%%')

            pstmt.setInt(1, chamado.getUsuario().getId());
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
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, cat.categoria, sc.subcategoria, t.nome as tecnico, c.prioridade 
            //FROM chamado c 
            //INNER JOIN usuario u ON u.id = c.usuario 
            //INNER JOIN categoria cat ON cat.id = c.categoria 
            //INNER JOIN subcategoria sc ON sc.id = c.subcategoria 
            //INNER JOIN usuario t ON t.id = c.tecnico 
            //WHERE ?=c.tecnico AND c.status LIKE replace(?,'TODOS','%%')

            pstmt.setInt(1, chamado.getUsuario().getId());
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
            //SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome, cat.categoria, sc.subcategoria, t.nome as tecnico, c.prioridade 
            //FROM chamado c 
            //INNER JOIN usuario u ON u.id = c.usuario 
            //INNER JOIN categoria cat ON cat.id = c.categoria 
            //INNER JOIN subcategoria sc ON sc.id = c.subcategoria 
            //INNER JOIN usuario t ON t.id = c.tecnico 
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
            // SELECT c.id, c.descricao, c.data_inicio, c.data_fim, c.status, u.nome,u.numero_registro, cat.categoria, sc.subcategoria, t.nome as tecnico, t.id as idtecnico, c.prioridade 
            // FROM chamado c 
            // INNER JOIN usuario u on u.id = c.usuario
            //INNER JOIN categoria cat on cat.id = c.categoria
            //INNER JOIN subcategoria sc on sc.id = c.subcategoria
            //INNER JOIN usuario t on t.id = c.tecnico
            //WHERE c.id=?

            pstmt.setInt(1, Integer.parseInt(chamado.getId()));
            ResultSet resultado = pstmt.executeQuery();
            if (resultado.next()) {
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
                u.setNumero_registro(Integer.parseInt(resultado.getString("numero_registro")));
                c.setUsuario(u);
                cat.setCategoria(resultado.getString("categoria"));
                c.setCategoria(cat);
                s.setSubcategoria(resultado.getString("subcategoria"));
                c.setSubcategoria(s);
                t.setNome(resultado.getString("tecnico"));;
                t.setId(Integer.parseInt(resultado.getString("idtecnico")));
                c.setTecnico(t);
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

    public void alterarChamado(Chamado chamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERAR_CHAMADO);
            //UPDATE chamado SET descricao=?, status=?, categoria=(SELECT id FROM categoria WHERE categoria=?),
            //subcategoria=(SELECT id FROM subcategoria WHERE subcategoria=?), prioridade=?, data_fim=? 
            //WHERE id=?
            pstmt.setString(1, chamado.getDescricao());
            pstmt.setString(2, chamado.getStatus().toString());
            pstmt.setString(3, chamado.getCategoria().getCategoria());
            pstmt.setString(4, chamado.getSubcategoria().getSubcategoria());
            pstmt.setString(5, chamado.getPrioridade().toString());
            pstmt.setString(6, chamado.getData_fim().toString());
            pstmt.setInt(7, Integer.parseInt(chamado.getId()));
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

    public String calcularPrioridadeDoChamado(Chamado chamado) {

        String prioridade = "0";
        double prioridadeFinal = 0;
        double prioridadeCategoria = 0;
        double prioridadeSubcategoria = 0;
        double prioridadeSetor = 0;

        prioridadeCategoria = chamado.getCategoria().getPrioridade().getPrioridade();
        prioridadeSubcategoria = chamado.getSubcategoria().getPrioridade().getPrioridade();
        prioridadeSetor = chamado.getUsuario().getSetor().getPrioridade();

        prioridadeFinal = Math.round((prioridadeSetor + prioridadeCategoria + prioridadeSubcategoria) / 3);

        if (prioridadeFinal==1) {
            prioridade = "BAIXA";
        } else if (prioridadeFinal==2) {
            prioridade = "MEDIA";
        } else if (prioridadeFinal==3) {
            prioridade = "ALTA";
        } else if (prioridadeFinal==4) {
            prioridade = "ALTISSIMA";
        }

        return prioridade;

    }
    
    public int atribuicaoDoChamado(List<Usuario> usuarios) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        int idTecnico=0;
        
        try {
            
            double prioridadeMenorChamado = 9999;            
                        
            for(Usuario u: usuarios){
                
            double prioridadeTotal = 0;
            //double cont=0;
                 
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_PRIORIDADE_CHAMADO);
            //SELECT prioridade 
            //FROM chamado c 
            //WHERE ?=tecnico AND (status LIKE 'ABERTO' OR status LIKE 'EM_ANDAMENTO')
            
            pstmt.setInt(1, u.getId());
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                Chamado c = new Chamado();
                c.setPrioridade(Prioridade.valueOf(resultado.getString("prioridade")));
                prioridadeTotal = prioridadeTotal + c.getPrioridade().getPrioridade();
                //cont = cont + 1;
                    
            }
            //if(cont!=0){
            //prioridadeTotal = (prioridadeTotal/cont);
            //}
            
            if(prioridadeTotal<prioridadeMenorChamado){
                prioridadeMenorChamado=prioridadeTotal;
                idTecnico = u.getId();
            }            
            
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
        return idTecnico;
    }
    
        public List<Chamado> consultaPrioridadeChamadoPorTecnico(Usuario usuario){

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
               
        try {           
            
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_PRIORIDADE_CHAMADO);
            //SELECT prioridade 
            //FROM chamado c 
            //WHERE ?=tecnico AND (status LIKE 'ABERTO' OR status LIKE 'EM_ANDAMENTO')
            
            pstmt.setInt(1, usuario.getId());
            ResultSet resultado = pstmt.executeQuery();
            
            while (resultado.next()) {
                
                Chamado c = new Chamado();
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
    
    public int totalChamadosPorTecnico(Usuario usuario) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        int totalChamados=0;
        
        try {
            
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(TOTAL_CHAMADOS_POR_TECNICO);
//          SELECT count(id) 
//          FROM chamado c 
//          WHERE ?=tecnico AND (status LIKE 'ABERTO')           
            
            pstmt.setInt(1, usuario.getId());
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                totalChamados = Integer.parseInt(resultado.getString("id"));
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
        return totalChamados;
    }

    public int ultimoChamadoAtribuido(int idTecnico) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        int idChamado=0;
        
        try {
            
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ULTIMO_CHAMADO_ATRIBUIDO);
//            SELECT id , data_inicio 
//            FROM chamado 
//            WHERE ?=tecnico AND (status LIKE 'ABERTO')
//            order by data_inicio asc         
            
            pstmt.setInt(1, idTecnico);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                if(idChamado==0){
                    idChamado = Integer.parseInt(resultado.getString("id"));
                }                
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
        return idChamado;
    }
    
    public void reatribuirChamado(int idTecnico, int idChamado) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(REATRIBUIR_CHAMADO);
            //UPDATE chamado SET tecnico=? WHERE id=?
            
            pstmt.setInt(1, idTecnico);
            pstmt.setInt(2, idChamado);
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
}
