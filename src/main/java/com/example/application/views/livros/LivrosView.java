package com.example.application.views.livros;

import com.example.application.data.Autor;
import com.example.application.data.Editora;
import com.example.application.data.Livro;
import com.example.application.data.repository.LivroRepository;
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
import com.vaadin.flow.router.RouteAlias;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Livros")
@Route(value = "Livros", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class LivrosView extends Composite<VerticalLayout> {

    public LivrosView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        ComboBox comboBox = new ComboBox();
        ComboBox comboBox2 = new ComboBox();
        Button buttonPrimary = new Button();

        buttonPrimary.addClickListener(clickEvent ->{
    Livro livro = new Livro();
    livro.setNomeLivro(textField.getValue());
    livro.setAnoPublicacao(textField2.getValue());
    
    // Obtenha o autor selecionado do ComboBox
    Autor autor = (Autor) comboBox2.getValue();
    livro.setAutor(autor);
    
    // Obtenha a editora selecionada do ComboBox
    Editora editora = (Editora) comboBox.getValue();
    livro.setEditora(editora);

    LivroRepository livroRepository = new LivroRepository();
    livroRepository.salvar(livro);
    
    textField.clear();  
    textField2.clear();
    comboBox.clear();
    comboBox2.clear();
});

        
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Nome do Livro");
        textField.setWidth("min-content");
        textField2.setLabel("Ano Publicação");
        textField2.setWidth("min-content");
        comboBox.setLabel("Editora");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        comboBox2.setLabel("Autor");
        comboBox2.setWidth("min-content");
        setComboBoxSampleData(comboBox2);
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(comboBox);
        formLayout2Col.add(comboBox2);
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
