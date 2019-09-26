package model;

public enum Setor {
    REITORIA(1, 4),
    COORDENACAO(2, 2),
    CONTABILIDADE(3,3),
    RECURSOS_HUMANOS(4,2),
    TI(5,3),
    FINANCEIRO(6,3),
    PORTARIA(7,1),
    ADMINISTRACAO(8,1);
    
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
