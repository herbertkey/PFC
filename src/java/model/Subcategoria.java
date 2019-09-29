package model;

public class Subcategoria {
    
    private String id;
    private String subcategoria;
    private Prioridade prioridade;
    private Categoria categoria;

    public Subcategoria() {
    }

    public Subcategoria(String id, String subcategoria, Prioridade prioridade, Categoria categoria) {
        this.id = id;
        this.subcategoria = subcategoria;
        this.prioridade = prioridade;
        this.categoria = categoria;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    
    
}
