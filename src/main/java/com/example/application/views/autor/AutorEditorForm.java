package com.example.application.views.autor;

import com.example.application.data.Autor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AutorEditorForm extends FormLayout {
    private TextField nomeAutorField;

    public AutorEditorForm(Autor autor) {
        // Inicializa os componentes do formulário
        nomeAutorField = new TextField("Nome da Autor");
        // Define o valor do TextField com o nome da editora atual
        nomeAutorField.setValue(autor.getNomeAutor());
        
        // Adiciona os componentes ao layout do formulário
        add(nomeAutorField);
    }

    // Método para obter o valor atual do nome da editora do TextField
    public String getNomeAutor() {
        return nomeAutorField.getValue();
    }

}
