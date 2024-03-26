package com.example.application.views.autor;

import com.example.application.data.Autor;
import com.example.application.data.repository.AutorRepository;
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

@PageTitle("Autor")
@Route(value = "Autor", layout = MainLayout.class)
@Uses(Icon.class)
public class AutorView extends Composite<VerticalLayout> {

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
    }
}
