package com.example.application.data;

public class Editora {
    private int id;
    private String nomeEditora;
    

    public Editora() {
    }

    public Editora(int id, String nomeEditora){
        super();
        this.id = id;
        this.nomeEditora = nomeEditora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }

    @Override
public String toString() {
    return nomeEditora; // Supondo que 'nomeEditora' seja o nome da editora
}


}
