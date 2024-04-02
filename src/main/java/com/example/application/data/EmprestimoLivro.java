package com.example.application.data;

public class EmprestimoLivro {
    private Livro livro;
    private Emprestimo emprestimo;
    
    public EmprestimoLivro() {
    }

    public EmprestimoLivro(Livro livro, Emprestimo emprestimo) {
        this.livro = livro;
        this.emprestimo = emprestimo;
    }
    
    public Livro getLivro() {
        return livro;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
    
    }
