package com.example.application.views.livros;

import com.example.application.data.Editora;

import java.util.List;

import com.example.application.data.Autor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;

import com.example.application.data.Livro;
import com.example.application.data.repository.AutorRepository;
import com.example.application.data.repository.EditoraRepository;

public class LivroEditorForm extends FormLayout {
    private TextField nomeLivroField;
    private TextField anoPublicacaoField;
    private ComboBox<Editora> editoraComboBox;
    private ComboBox<Autor> autorComboBox;

    public LivroEditorForm(Livro livro) {
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

        populateComboBoxes();


        // Preenche os campos com os detalhes do livro fornecido
        if (livro != null) {
            nomeLivroField.setValue(livro.getNomeLivro());
            anoPublicacaoField.setValue(livro.getAnoPublicacao());
            editoraComboBox.setValue(livro.getEditora());
            autorComboBox.setValue(livro.getAutor());
        }
    }

    // Métodos getters para obter os valores dos campos
    public String getNomeLivro() {
        return nomeLivroField.getValue();
    }

    public String getAnoPublicacao() {
        return anoPublicacaoField.getValue();
    }

    public Editora getEditoraSelecionada() {
        return editoraComboBox.getValue();
    }

    public Autor getAutorSelecionado() {
        return autorComboBox.getValue();
    }

     // Método para definir o nome do livro no campo correspondente
     public void setNomeLivro(String nomeLivro) {
        nomeLivroField.setValue(nomeLivro);
    }

    // Método para definir o ano de publicação no campo correspondente
    public void setAnoPublicacao(String anoPublicacao) {
        anoPublicacaoField.setValue(anoPublicacao);
    }

    // Método para definir a editora selecionada no combobox correspondente
    public void setEditoraSelecionada(Editora editora) {
        editoraComboBox.setValue(editora);
    }

    // Método para definir o autor selecionado no combobox correspondente
    public void setAutorSelecionado(Autor autor) {
        autorComboBox.setValue(autor);
    }


    // Método para popular os ComboBox de autor e editora
    public void populateComboBoxes() {
        // Preenche o ComboBox de Editora
        EditoraRepository editoraRepository = new EditoraRepository();
        List<Editora> editoras = editoraRepository.listarTodas();
        editoraComboBox.setItems(editoras);
        editoraComboBox.setItemLabelGenerator(Editora::getNomeEditora);

        // Preenche o ComboBox de Autor
        AutorRepository autorRepository = new AutorRepository();
        List<Autor> autores = autorRepository.listarTodos();
        autorComboBox.setItems(autores);
        autorComboBox.setItemLabelGenerator(Autor::getNomeAutor);
    }
}

