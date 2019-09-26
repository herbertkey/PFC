package model;

public enum StatusChamado {

    ABERTO(1),
    EM_ANDAMENTO(2),
    FECHADO(3);
//    TODOS(4);


    private int id;

    private StatusChamado(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    

}
