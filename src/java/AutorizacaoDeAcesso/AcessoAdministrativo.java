package AutorizacaoDeAcesso;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

public class AcessoAdministrativo implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        //recupera a sessão
        HttpSession sessaoUsuario = ((HttpServletRequest)request).getSession();
        Usuario usuario = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        
        if(usuario!=null && (usuario.getTipo().equals(Tipo.SUPERVISOR)||usuario.getTipo().equals(Tipo.TECNICO))){
            chain.doFilter(request, response);
        } else{
            ((HttpServletResponse)response).sendRedirect("/ProjetoPFC_5/acessoNegado.jsp");
        }
        
    }

    @Override
    public void destroy() {
        
    }
    
    
}
