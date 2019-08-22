package model;

public class Historico {
    
    private String data;
    private String informacoes_adicionais;
    private Usuario usuario;
    private Chamado chamado;

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }    

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getInformacoes_adicionais() {
        return informacoes_adicionais;
    }

    public void setInformacoes_adicionais(String informacoes_adicionais) {
        this.informacoes_adicionais = informacoes_adicionais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
}
