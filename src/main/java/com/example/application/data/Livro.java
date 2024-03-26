package com.example.application.data;


public class Livro {
    private int id;
    private String nomeLivro;
    private Autor autor;
    private Editora editora;
    private String anoPublicacao;
    
    
    public Livro(int id, String nomeLivro, String anoPublicacao, Autor autor, Editora editora) {
        super();
        this.id = id;
        this.nomeLivro = nomeLivro;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
    }

    public Livro() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(String anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    
    
}