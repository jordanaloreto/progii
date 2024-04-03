package com.example.application.views.editora;

import com.example.application.data.Editora;
import com.example.application.data.repository.EditoraRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Editora")
@Route(value = "Editora", layout = MainLayout.class)
@Uses(Icon.class)
public class EditoraView extends Composite<VerticalLayout> {

    private Grid<Editora> grid;

    public EditoraView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        Button buttonPrimary = new Button();
        buttonPrimary.addClickListener(clickEvent -> {
            Editora editora = new Editora();
            editora.setNomeEditora(textField.getValue());

            EditoraRepository editoraRepository = new EditoraRepository();
            boolean salvou = editoraRepository.salvar(editora);

            if (salvou) {
                Notification.show("Editora salva com sucesso!");
                textField.clear();
                refreshGrid(); // Atualiza a grid após salvar
            } else {
                Notification.show("Erro ao salvar o editora. Por favor, tente novamente.");
            }
        });
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Nome Editora");
        textField.setWidth("min-content");
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(formLayout2Col);
        formLayout2Col.add(textField);
        getContent().add(buttonPrimary);

        // Cria a grid e a adiciona ao layout
        grid = new Grid<>(Editora.class);
        grid.setItems(getEditoras()); // Carrega as editoras já existentes
        getContent().add(grid);

        // Adiciona a coluna de botões à grid
grid.addComponentColumn(editora -> {
    Button editButton = new Button("Editar");
    editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    Editora editoraSelecionada = editora;
    editButton.addClickListener(event -> {
        // Exibe um diálogo de edição
        Dialog dialog = new Dialog();
        EditoraEditorForm editoraEditorForm = new EditoraEditorForm(editoraSelecionada);
        dialog.add(editoraEditorForm);

        // Adiciona um botão "Salvar" ao diálogo
        Button saveButton = new Button("Salvar", e -> {
            // Obtém os detalhes editados da editora do formulário
            String novoNomeEditora = editoraEditorForm.getNomeEditora();
            editoraSelecionada.setNomeEditora(novoNomeEditora);

            // Chama o método alterar do seu repository para salvar as alterações
            EditoraRepository editoraRepository = new EditoraRepository();
            boolean sucesso = editoraRepository.alterar(editoraSelecionada);

            if (sucesso) {
                // Se a alteração for bem-sucedida, fecha o diálogo
                dialog.close();
                // Atualiza a grid ou qualquer outra ação necessária após a edição
                refreshGrid();
                Notification.show("Editora atualizada com sucesso!");
            } else {
                // Se houver um erro, exibe uma mensagem de erro ao usuário
                Notification.show("Erro ao atualizar a editora. Por favor, tente novamente.");
            }
        });
        dialog.add(saveButton);
        dialog.open();
    });
    return editButton;
});


        grid.addComponentColumn(editora -> {
            Button deleteButton = new Button("Excluir");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            deleteButton.addClickListener(event -> {
                // Obtém a editora associada à linha selecionada
                Editora editoraSelecionada = editora;
            });
            return deleteButton;
        });
    }

    private List<Editora> getEditoras() {
        // Obtém a lista de editoras existentes do repositório
        EditoraRepository editoraRepository = new EditoraRepository();
        return editoraRepository.listarTodas();
    }

    private void refreshGrid() {
        // Atualiza a grid
        grid.setItems(getEditoras());
    }
}
