package model;

public enum Prioridade {
    BAIXA(1,1),
    MEDIA(2,2),
    ALTA(3,3),
    ALTISSIMA(4,4);
    
    private int id;
    private int prioridade;

    private Prioridade(int id, int prioridade) {
        this.id = id;
        this.prioridade = prioridade;
    }

    public int getId() {
        return id;
    }

    public int getPrioridade() {
        return prioridade;
    }
    
    
}
