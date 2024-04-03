package com.example.application.views.editora;

import com.example.application.data.Editora;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class EditoraEditorForm extends FormLayout {
    private TextField nomeEditoraField;

    public EditoraEditorForm(Editora editora) {
        // Inicializa os componentes do formulário
        nomeEditoraField = new TextField("Nome da Editora");
        // Define o valor do TextField com o nome da editora atual
        nomeEditoraField.setValue(editora.getNomeEditora());
        
        // Adiciona os componentes ao layout do formulário
        add(nomeEditoraField);
    }

    // Método para obter o valor atual do nome da editora do TextField
    public String getNomeEditora() {
        return nomeEditoraField.getValue();
    }
}

