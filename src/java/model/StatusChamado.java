package model;

public enum StatusChamado {

    ABERTO(1),
    EM_ANDAMENTO(2),
    PENDENTE_INTERACAO(3),
    FECHADO(4);


    private int id;

    private StatusChamado(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    

}
