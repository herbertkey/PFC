package model;

public enum Setor {
    REITORIA(1, 3),
    COORDENCAO(2, 1),
    ADMINISTRACAO(3, 1),
    PORTARIA(4, 2);
    
    private int id;
    private int prioridade;

    private Setor(int id, int prioridade) {
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
