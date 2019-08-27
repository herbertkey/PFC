package model;

public class Categoria {
    
    private String id;
    private String categoria;
    private Prioridade prioridade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }    
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    
    
    
}
