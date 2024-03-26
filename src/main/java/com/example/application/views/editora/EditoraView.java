package com.example.application.views.editora;

import com.example.application.data.Editora;
import com.example.application.data.repository.EditoraRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Editora")
@Route(value = "Editora", layout = MainLayout.class)
@Uses(Icon.class)
public class EditoraView extends Composite<VerticalLayout> {

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
                Notification.show("Editora salvo com sucesso!");
                textField.clear();
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
    }
}
