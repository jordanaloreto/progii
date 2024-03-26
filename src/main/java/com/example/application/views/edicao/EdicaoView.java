package com.example.application.views.edicao;

import com.example.application.data.Edicao;
import com.example.application.data.Livro;
import com.example.application.data.repository.EdicaoRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.notification.Notification;

@PageTitle("Edicao")
@Route(value = "Edicao", layout = MainLayout.class)
@Uses(Icon.class)
public class EdicaoView extends Composite<VerticalLayout> {

    public EdicaoView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField datePicker = new TextField();
        ComboBox comboBox = new ComboBox();
        Button buttonPrimary = new Button();

        buttonPrimary.addClickListener(clickEvent -> {
            String novoConteudo = textField.getValue();
            String anoString = datePicker.getValue();
            Livro livroSelecionado = (Livro) comboBox.getValue();

            if (novoConteudo.isEmpty() || anoString.isEmpty() || livroSelecionado == null) {
                Notification.show("Por favor, preencha todos os campos antes de salvar.");
                return;
            }

            int ano = Integer.parseInt(anoString);

            Edicao edicao = new Edicao();
            edicao.setNovoConteudo(novoConteudo);
            edicao.setAno(ano);
            edicao.setLivro(livroSelecionado);

            EdicaoRepository edicaoRepository = new EdicaoRepository();
            boolean salvou = edicaoRepository.salvar(edicao);

            if (salvou) {
                Notification.show("Edição salva com sucesso!");
                textField.clear();
                datePicker.clear();
                comboBox.clear();
            } else {
                Notification.show("Erro ao salvar a edição. Por favor, tente novamente.");
            }
        });

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Novo Conteúdo");
        textField.setWidth("min-content");
        datePicker.setLabel("Ano");
        datePicker.setWidth("min-content");
        comboBox.setLabel("Livro");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        buttonPrimary.setText("Button");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(datePicker);
        formLayout2Col.add(comboBox);
        getContent().add(buttonPrimary);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }
}
