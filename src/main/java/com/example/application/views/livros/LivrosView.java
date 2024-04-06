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
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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

import java.sql.SQLException;
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
            // grid.addColumn(Livro::getNomeLivro).setHeader("Nome do Livro");
    
            // Define tamanho da grid
            grid.setSizeFull();
    
            // Adiciona a grid ao layout
            getContent().add(grid);
    
            getContent().setSizeFull();
    
            // Atualiza a grid com os dados dos livros ao inicializar a view
            refreshGrid();

            grid.addComponentColumn(livro -> {
                Button editButton = new Button("Editar");
                editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                
                // Obtém o livro selecionado
                Livro livroSelecionado = livro;
                
                editButton.addClickListener(event -> {
                    // Exibe um diálogo de edição
                    Dialog dialog = new Dialog();
                    LivroEditorForm livroEditorForm = new LivroEditorForm(livroSelecionado);
                    
                    // Popula os ComboBox de autor e editora antes de definir seus valores
                    livroEditorForm.populateComboBoxes(); 
                    
                    dialog.add(livroEditorForm);
            
                    // Adiciona um botão "Salvar" ao diálogo
                    Button saveButton = new Button("Salvar", e -> {
                        // Obtém os detalhes editados do livro do formulário
                        String novoNomeLivro = livroEditorForm.getNomeLivro();
                        String novoAnoPublicacao = livroEditorForm.getAnoPublicacao();
                        Editora novaEditora = livroEditorForm.getEditoraSelecionada();
                        Autor novoAutor = livroEditorForm.getAutorSelecionado();
            
                        // Atualiza os detalhes do livro com os valores do formulário de edição
                        livroSelecionado.setNomeLivro(novoNomeLivro);
                        livroSelecionado.setAnoPublicacao(novoAnoPublicacao);
                        livroSelecionado.setEditora(novaEditora);
                        livroSelecionado.setAutor(novoAutor);
            
                        // Chama o método alterar do seu repository para salvar as alterações
                        LivroRepository livroRepository = new LivroRepository();
                        boolean sucesso = livroRepository.alterar(livroSelecionado);
            
                        if (sucesso) {
                            dialog.close();
                            refreshGrid();
                            Notification.show("Livro atualizado com sucesso!");
                        } else {
                            Notification.show("Erro ao atualizar o livro. Por favor, tente novamente.");
                        }
                    });
                    dialog.add(saveButton);
                    dialog.open();
                });
                
                return editButton;
            });
            
            grid.addComponentColumn(livro -> {
                Button deleteButton = new Button("Excluir");
                deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
                
                deleteButton.addClickListener(event -> {
                    // Obtém o livro selecionado
                    Livro livroSelecionado = livro;
                
                    // Abre um diálogo de confirmação
                    ConfirmDialog dialog = new ConfirmDialog(
                        "Confirmação",
                        "Tem certeza de que deseja excluir este livro?",
                        "Sim", // Botão de confirmação
                        confirmEvent -> {
                            try {
                                // Remover todas as referências do livro antes de excluir
                                LivroRepository livroRepository = new LivroRepository();
                                livroRepository.removerReferenciasLivro(livroSelecionado);
                                
                                // Continuar com a exclusão do livro
                                boolean sucessoExcluirLivro = livroRepository.excluir(livroSelecionado);
                                if (sucessoExcluirLivro) {
                                    refreshGrid();
                                    Notification.show("Livro excluído com sucesso!", 3000, Notification.Position.MIDDLE);
                                } else {
                                    Notification.show("Erro ao excluir o livro. Por favor, tente novamente.", 3000, Notification.Position.MIDDLE);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Notification.show("Erro ao remover as referências do livro. Por favor, tente novamente.", 3000, Notification.Position.MIDDLE);
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

    private List<Livro> getLivros() {
        LivroRepository livroRepository = new LivroRepository();
        return livroRepository.listarTodas();
    }

    private void refreshGrid() {
        // Atualiza a grid
        grid.setItems(getLivros());
    }
}
