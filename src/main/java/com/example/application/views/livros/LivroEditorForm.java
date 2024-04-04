package com.example.application.views.livros;

import com.example.application.data.Editora;

import java.util.List;

import com.example.application.data.Autor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;

public class LivroEditorForm extends FormLayout {
    private TextField nomeLivroField;
    private TextField anoPublicacaoField;
    private ComboBox<Editora> editoraComboBox;
    private ComboBox<Autor> autorComboBox;

    public LivroEditorForm() {
        // Inicializa os componentes do formulário
        nomeLivroField = new TextField("Nome do Livro");
        anoPublicacaoField = new TextField("Ano de Publicação");
        editoraComboBox = new ComboBox<>("Editora");
        autorComboBox = new ComboBox<>("Autor");

        // Configurações para largura dos campos
        nomeLivroField.setWidth("100%");
        anoPublicacaoField.setWidth("100%");
        editoraComboBox.setWidth("100%");
        autorComboBox.setWidth("100%");

        // Adiciona os componentes ao layout do formulário
        add(nomeLivroField, anoPublicacaoField, editoraComboBox, autorComboBox);
    }

    // Método para definir as opções do ComboBox de Editora
    public void setEditoras(List<Editora> editoras) {
        editoraComboBox.setItems(editoras);
        editoraComboBox.setItemLabelGenerator(Editora::getNomeEditora);
    }

    // Método para definir as opções do ComboBox de Autor
    public void setAutores(List<Autor> autores) {
        autorComboBox.setItems(autores);
        autorComboBox.setItemLabelGenerator(Autor::getNomeAutor);
    }

    // Método para obter o valor do nome do livro
    public String getNomeLivro() {
        return nomeLivroField.getValue();
    }

    // Método para obter o valor do ano de publicação
    public String getAnoPublicacao() {
        return anoPublicacaoField.getValue();
    }

    // Método para obter a editora selecionada
    public Editora getEditoraSelecionada() {
        return editoraComboBox.getValue();
    }

    // Método para obter o autor selecionado
    public Autor getAutorSelecionado() {
        return autorComboBox.getValue();
    }
}
