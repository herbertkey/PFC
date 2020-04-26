package dao;

//import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Setor;
import model.Tipo;
import model.Usuario;
import sun.misc.BASE64Encoder;
import util.ConectaBanco;

public class UsuarioDAO {

    private static final String AUTENTICA_USUARIO = "SELECT * FROM usuario WHERE numero_registro=? AND senha=? AND status='ATIVO'";
    private static final String CADASTRA_NOVO_USUARIO = "INSERT INTO usuario (numero_registro, email, nome, telefone, senha, tipo, cargo, status, setor) values (?,?,?,?,?,?,?,'ATIVO',?)";
    private static final String CONSULTA_USUARIO = "SELECT numero_registro, email, nome, telefone, senha, tipo, cargo, setor FROM usuario WHERE upper(nome) LIKE ? and status='ATIVO'";
    private static final String ALTERAR_USUARIO = "UPDATE usuario SET email=?,nome=?,telefone=?,tipo=?,cargo=?, setor=? WHERE numero_registro=?";
    private static final String CONSULTA_UM_USUARIO = "SELECT id, numero_registro, email, nome, telefone, tipo, cargo, setor FROM usuario Where numero_registro=?";
    private static final String EXCLUIR_USUARIO = "UPDATE usuario SET status='INATIVO' WHERE numero_registro=?";
    private static final String CONSULTA_TECNICO = "SELECT id, numero_registro, email, nome, telefone, senha, tipo, cargo, setor FROM usuario WHERE tipo='TECNICO' and status='ATIVO'";

    public void cadastraNovoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVO_USUARIO);
            pstmt.setInt(1, usuario.getNumero_registro());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getNome());
            pstmt.setString(4, usuario.getTelefone());
            pstmt.setString(5, criptografa(usuario.getSenha()));
            pstmt.setString(6, usuario.getTipo().toString());
            pstmt.setString(7, usuario.getCargo());
            pstmt.setString(8, usuario.getSetor().toString());
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

    public Usuario autenticaUsuario(Usuario usuario) {
        Usuario usuarioAutenticado = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsUsuario = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(AUTENTICA_USUARIO);
            pstmt.setInt(1, usuario.getNumero_registro());
            pstmt.setString(2, criptografa(usuario.getSenha()));
            rsUsuario = pstmt.executeQuery();
            if (rsUsuario.next()) {
                usuarioAutenticado = new Usuario();
                usuarioAutenticado.setNumero_registro(rsUsuario.getInt("numero_registro"));
                usuarioAutenticado.setNome(rsUsuario.getString("nome"));
                usuarioAutenticado.setSenha(rsUsuario.getString("senha"));
                usuarioAutenticado.setTipo(Tipo.valueOf(rsUsuario.getString("tipo")));
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
        return usuarioAutenticado;
    }

    public List<Usuario> consultarUsuario(Usuario usuario) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_USUARIO);
            pstmt.setString(1, "%" + usuario.getNome().toUpperCase() + "%");
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                Usuario u = new Usuario();
                

                u.setNumero_registro(resultado.getInt("numero_registro"));
                u.setEmail(resultado.getString("email"));
                u.setNome(resultado.getString("nome"));
                u.setTelefone(resultado.getString("telefone"));
                u.setTipo(Tipo.valueOf(resultado.getString("tipo")));
                u.setCargo(resultado.getString("cargo"));
                u.setSetor(Setor.valueOf(resultado.getString("setor")));

                usuarios.add(u);
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
        return usuarios;
    }

    public void alterarUsuario(Usuario usuario) {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERAR_USUARIO);
            pstmt.setString(1, usuario.getEmail());
            pstmt.setString(2, usuario.getNome());
            pstmt.setString(3, usuario.getTelefone());
            pstmt.setString(4, usuario.getTipo().toString());
            pstmt.setString(5, usuario.getCargo());
            pstmt.setString(6, usuario.getSetor().toString());
            pstmt.setInt(7, usuario.getNumero_registro());
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

    public Usuario consultaUmUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {

        Usuario u = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_UM_USUARIO);
            pstmt.setInt(1, usuario.getNumero_registro());
            pstmt.execute();
            ResultSet resultado = pstmt.executeQuery();
            u = new Usuario();
            if (resultado.next()) {
                u.setId(resultado.getInt("id"));
                u.setNumero_registro(resultado.getInt("numero_registro"));
                u.setEmail(resultado.getString("email"));
                u.setNome(resultado.getString("nome"));
                u.setTelefone(resultado.getString("telefone"));
                u.setTipo(Tipo.valueOf(resultado.getString("tipo")));
                u.setCargo(resultado.getString("cargo"));
                u.setSetor(Setor.valueOf(resultado.getString("setor")));
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
        return u;
    }

    public void excluirUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {

            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUIR_USUARIO);
            pstmt.setInt(1, usuario.getNumero_registro());
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
    
    public String criptografa(String senha){ 
        try{ 
            MessageDigest digest = MessageDigest.getInstance("MD5"); 
            digest.update(senha.getBytes()); 
            BASE64Encoder encoder = new BASE64Encoder(); 
            return encoder.encode(digest.digest()); 
        }catch(NoSuchAlgorithmException ns){ 
            ns.printStackTrace(); 
        } 
        return senha; 
    }
    
    public List<Usuario> consultarTecnico() {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_TECNICO);
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                Usuario u = new Usuario();
                
                u.setId(resultado.getInt("id"));
                u.setNumero_registro(resultado.getInt("numero_registro"));
                u.setEmail(resultado.getString("email"));
                u.setNome(resultado.getString("nome"));
                u.setTelefone(resultado.getString("telefone"));
                u.setTipo(Tipo.valueOf(resultado.getString("tipo")));
                u.setCargo(resultado.getString("cargo"));
                u.setSetor(Setor.valueOf(resultado.getString("setor")));

                usuarios.add(u);
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
        return usuarios;
    }


}
