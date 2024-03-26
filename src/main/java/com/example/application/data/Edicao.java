package com.example.application.data;

public class Edicao {
    private int id;
    private String novoConteudo;
    private int ano;
    private Livro livro;
    

    public Edicao() {
    }

    public Edicao(int id, String novoConteudo, int ano, Livro livro) {
        this.id = id;
        this.novoConteudo = novoConteudo;
        this.ano = ano;
        this.livro = livro;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNovoConteudo() {
        return novoConteudo;
    }

    public void setNovoConteudo(String novoConteudo) {
        this.novoConteudo = novoConteudo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    
}
