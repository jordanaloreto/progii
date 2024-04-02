package com.example.application.views.listagem;

import com.example.application.data.Livro;
import com.example.application.data.SamplePerson;
import com.example.application.services.SamplePersonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.binder.ValidationResult;


import java.util.List;

@Route("grid-buffered-inline-editor")
public class ListagemView extends VerticalLayout {

    public ListagemView() {
        ValidationMessage firstNameValidationMessage = new ValidationMessage();
        ValidationMessage lastNameValidationMessage = new ValidationMessage();
        ValidationMessage emailValidationMessage = new ValidationMessage();

        // tag::snippet[]
        Grid<Livro> grid = new Grid<>(Livro.class, false);
        Editor<Livro> editor = grid.getEditor();

        Grid.Column<Livro> firstNameColumn = grid
                .addColumn(Livro::getNomeLivro).setHeader("First name")
                .setWidth("120px").setFlexGrow(0);
        Grid.Column<Livro> lastNameColumn = grid.addColumn(Livro::getAutor)
                .setHeader("Last name").setWidth("120px").setFlexGrow(0);
        Grid.Column<Livro> emailColumn = grid.addColumn(Livro::getEditora)
                .setHeader("Email");
        Grid.Column<Livro> editColumn = grid.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<Livro> binder = new Binder<>(Livro.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField firstNameField = new TextField();
        firstNameField.setWidthFull();
        binder.forField(firstNameField)
                .asRequired("First name must not be empty")
                .withStatusLabel(firstNameValidationMessage)
                .bind(Livro::getNomeLivro, Livro::setNomeLivro);
        firstNameColumn.setEditorComponent(firstNameField);

        TextField lastNameField = new TextField();
        lastNameField.setWidthFull();
        binder.forField(lastNameField).asRequired("Last name must not be empty")
                .withStatusLabel(lastNameValidationMessage)
                .bind(Livro::getAutor, Livro::setAutor);
        lastNameColumn.setEditorComponent(lastNameField);

        EmailField emailField = new EmailField();
        emailField.setWidthFull();
        binder.forField(emailField).asRequired("Email must not be empty")
                .withValidator(
                        new EmailValidator("Enter a valid email address"))
                .withStatusLabel(emailValidationMessage)
                .bind(Livro::getEditora, Livro::setEditora);
        emailColumn.setEditorComponent(emailField);

        Button saveButton = new Button("Save", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);
        // end::snippet[]

        editor.addCancelListener(e -> {
            firstNameValidationMessage.setText("");
            lastNameValidationMessage.setText("");
            emailValidationMessage.setText("");
        });

        List<Livro> people = DataService.getPeople();
        grid.setItems(people);

        getThemeList().clear();
        getThemeList().add("spacing-s");
        add(grid, firstNameValidationMessage, lastNameValidationMessage,
                emailValidationMessage);
    }

}
