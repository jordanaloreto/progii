package com.example.application.views.livros;

import com.example.application.data.Autor;
import com.example.application.data.Editora;
import com.example.application.data.Livro;
import com.example.application.data.repository.AutorRepository;
import com.example.application.data.repository.EditoraRepository;
import com.example.application.data.repository.LivroRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.dialog.Dialog;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Livros")
@Route(value = "Livros", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class LivrosView extends Composite<VerticalLayout> {
    
    private Grid<Livro> grid;
    
    public LivrosView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        ComboBox<Editora> comboBox = new ComboBox<>();
        ComboBox<Autor> comboBox2 = new ComboBox<>();
        Button buttonPrimary = new Button();

        buttonPrimary.addClickListener(clickEvent -> {
            String nomeLivro = textField.getValue();
            String anoPublicacao = textField2.getValue();
            Autor autorSelecionado = comboBox2.getValue();
            Editora editoraSelecionada = comboBox.getValue();
        
            if (nomeLivro.isEmpty() || anoPublicacao.isEmpty() || autorSelecionado == null || editoraSelecionada == null) {
                Notification.show("Por favor, preencha todos os campos antes de salvar.");
                return;
            }
                
            Livro livro = new Livro();
            livro.setNomeLivro(nomeLivro);
            livro.setAnoPublicacao(anoPublicacao);
            livro.setAutor(autorSelecionado);
            livro.setEditora(editoraSelecionada);
        
            LivroRepository livroRepository = new LivroRepository();
            boolean salvou = livroRepository.salvar(livro);
        
            if (salvou) {
                Notification.show("Livro salvo com sucesso!");
                textField.clear();
                textField2.clear();
                comboBox.clear();
                comboBox2.clear();
                refreshGrid(); // Atualiza a grid após salvar
            } else {
                Notification.show("Erro ao salvar o livro. Por favor, tente novamente.");
            }
        });
        
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Nome do Livro");
        textField.setWidth("min-content");
        textField2.setLabel("Ano Publicação");
        textField2.setWidth("min-content");
        setComboBoxData(comboBox, comboBox2);
        comboBox.setLabel("Editora");
        comboBox.setWidth("min-content");
        setComboBoxData(comboBox, comboBox2); // Correção aqui
        comboBox2.setLabel("Autor");
        comboBox2.setWidth("min-content");
        buttonPrimary.setText("Salvar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(comboBox);
        formLayout2Col.add(comboBox2);
        getContent().add(buttonPrimary);

        // Cria e configura a grid
       grid = new Grid<>(Livro.class);
    grid.setSizeFull();

    // Adiciona a grid ao layout
    getContent().add(grid);

    getContent().setSizeFull();

    // Atualiza a grid com os dados dos livros ao inicializar a view
    refreshGrid();
    }

    private List<Livro> getLivros() {
        // Obtém os livros do banco de dados
        LivroRepository livroRepository = new LivroRepository();
        return livroRepository.listarTodas();
    }

    private void setComboBoxData(ComboBox<Editora> comboBoxEditora, ComboBox<Autor> comboBoxAutor) {
        // Popula o ComboBox de Editora
        EditoraRepository editoraRepository = new EditoraRepository();
        List<Editora> editoras = editoraRepository.listarTodas();
        comboBoxEditora.setItems(editoras);
        comboBoxEditora.setItemLabelGenerator(Editora::getNomeEditora);

        // Popula o ComboBox de Autor
        AutorRepository autorRepository = new AutorRepository();
        List<Autor> autores = autorRepository.listarTodos();
        comboBoxAutor.setItems(autores);
        comboBoxAutor.setItemLabelGenerator(Autor::getNomeAutor);
    }

    private void refreshGrid() {
        // Atualiza a grid com os dados dos livros
        LivroRepository livroRepository = new LivroRepository();
        List<Livro> livros = livroRepository.listarTodas();
        grid.setItems(livros);
    }
}
