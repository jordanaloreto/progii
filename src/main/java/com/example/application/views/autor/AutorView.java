package com.example.application.views.autor;

import java.util.List;

import com.example.application.data.Autor;
import com.example.application.data.Autor;
import com.example.application.data.repository.AutorRepository;
import com.example.application.data.repository.AutorRepository;
import com.example.application.views.MainLayout;
import com.example.application.views.autor.AutorEditorForm;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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

@PageTitle("Autor")
@Route(value = "Autor", layout = MainLayout.class)
@Uses(Icon.class)
public class AutorView extends Composite<VerticalLayout> {
    
    private Grid<Autor> grid;

    public AutorView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        Button buttonPrimary = new Button();
        buttonPrimary.addClickListener(clickEvent -> {
            Autor autor = new Autor();
            autor.setNomeAutor(textField.getValue());

            AutorRepository autorRepository = new AutorRepository();
            boolean salvou = autorRepository.salvar(autor);

            if (salvou) {
                Notification.show("Autor salvo com sucesso!");
                textField.clear();
                refreshGrid();
            } else {
                Notification.show("Erro ao salvar o autor. Por favor, tente novamente.");
            }
        });

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Nome Autor");
        textField.setWidth("min-content");
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(formLayout2Col);
        formLayout2Col.add(textField);
        getContent().add(buttonPrimary);

        // Cria a grid e a adiciona ao layout
        grid = new Grid<>(Autor.class);
        grid.setItems(getAutores()); // Carrega as autors já existentes
        getContent().add(grid);

        grid.addComponentColumn(autor -> {
    Button editButton = new Button("Editar");
    editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    Autor autorSelecionada = autor;
    editButton.addClickListener(event -> {
        // Exibe um diálogo de edição
        Dialog dialog = new Dialog();
        AutorEditorForm autorEditorForm = new AutorEditorForm(autorSelecionada);
        dialog.add(autorEditorForm);

        // Adiciona um botão "Salvar" ao diálogo
        Button saveButton = new Button("Salvar", e -> {
            // Obtém os detalhes editados da autor do formulário
            String novoNomeAutor = autorEditorForm.getNomeAutor();
            autorSelecionada.setNomeAutor(novoNomeAutor);

            // Chama o método alterar do seu repository para salvar as alterações
            AutorRepository autorRepository = new AutorRepository();
            boolean sucesso = autorRepository.alterar(autorSelecionada);

            if (sucesso) {
                dialog.close();
                refreshGrid();
                Notification.show("Autor atualizada com sucesso!");
            } else {
                Notification.show("Erro ao atualizar a autor. Por favor, tente novamente.");
            }
        });
        dialog.add(saveButton);
        dialog.open();
    });
    return editButton;
});


        grid.addComponentColumn(autor -> {
    Button deleteButton = new Button("Excluir");
    deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

    deleteButton.addClickListener(event -> {
        // Obtém a autor selecionada
        Autor autorSelecionada = autor;

        // Abre um diálogo de confirmação
        ConfirmDialog dialog = new ConfirmDialog(
            "Confirmação",
            "Tem certeza de que deseja excluir esta autor?",
            "Sim", // Botão de confirmação
            confirmEvent -> {
                // Chama o método excluir do seu repository para excluir a autor selecionada
                AutorRepository autorRepository = new AutorRepository();
                boolean sucesso = autorRepository.excluir(autorSelecionada);

                if (sucesso) {
                    refreshGrid();
                    Notification.show("Autor excluída com sucesso!", 3000, Notification.Position.MIDDLE);
                } else {
                    Notification.show("Erro ao excluir a autor. Por favor, tente novamente.", 3000, Notification.Position.MIDDLE);
                }
            },
            "Cancelar", // Botão de cancelamento
            cancelEvent -> {
                // O usuário cancelou a exclusão, não faz nada
            }
        );
        dialog.open();
    });

    return deleteButton;
});
    }

    private List<Autor> getAutores() {
        // Obtém a lista de autors existentes do repositório
        AutorRepository autorRepository = new AutorRepository();
        return autorRepository.listarTodos();
    }

    private void refreshGrid() {
        // Atualiza a grid
        grid.setItems(getAutores());
    }
}
