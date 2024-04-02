package com.example.application.views.emprestimo;

import com.example.application.data.Edicao;
import com.example.application.data.Emprestimo;
import com.example.application.data.EmprestimoLivro;
import com.example.application.data.Livro;
import com.example.application.data.repository.EdicaoRepository;
import com.example.application.data.repository.EmprestimoLivroRepository;
import com.example.application.data.repository.EmprestimoRepository;
import com.example.application.data.repository.LivroRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Emprestimo")
@Route(value = "emprestimo", layout = MainLayout.class)
@Uses(Icon.class)
public class EmprestimoView extends Composite<VerticalLayout> {

    public EmprestimoView() {
        FormLayout formLayout2Col = new FormLayout();
        ComboBox comboBox = new ComboBox();
        DateTimePicker dateTimePicker = new DateTimePicker();
        Button buttonPrimary = new Button();
        buttonPrimary.addClickListener(clickEvent -> {
            // Obter o livro selecionado no ComboBox
            Livro livroSelecionado = (Livro) comboBox.getValue();
        
            // Obter a data de empréstimo selecionada no DateTimePicker
            LocalDateTime dataEmprestimo = dateTimePicker.getValue();
        
            // Verificar se os campos obrigatórios foram preenchidos
            if (livroSelecionado == null || dataEmprestimo == null) {
                Notification.show("Por favor, preencha todos os campos antes de salvar.");
                return;
            }
        
            // Criar um objeto de empréstimo
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setDataEmprestimo(Date.valueOf(dataEmprestimo.toLocalDate()));
        
            // Salvar o empréstimo no banco de dados
            EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
            int idEmprestimo = emprestimoRepository.salvar(emprestimo);
        
            if (idEmprestimo != -1) { // Verificar se o empréstimo foi salvo com sucesso
                // Criar um objeto de empréstimo de livro
                EmprestimoLivro emprestimoLivro = new EmprestimoLivro(livroSelecionado, emprestimo);
        
                // Salvar a associação de empréstimo com livro no banco de dados
                EmprestimoLivroRepository emprestimoLivroRepository = new EmprestimoLivroRepository();
                boolean salvouEmprestimoLivro = emprestimoLivroRepository.salvar(emprestimoLivro, idEmprestimo);
        
                // Exibir notificação sobre o resultado da operação de salvar
                if (salvouEmprestimoLivro) {
                    Notification.show("Empréstimo salvo com sucesso!");
                    // Limpar os campos após salvar
                    comboBox.clear();
                    dateTimePicker.clear();
                } else {
                    Notification.show("Erro ao salvar o empréstimo de livro. Por favor, tente novamente.");
                }
            } else {
                Notification.show("Erro ao salvar o empréstimo. Por favor, tente novamente.");
            }
        });
        
        

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        comboBox.setLabel("Livro");
        comboBox.setWidth("min-content");
        setComboBoxData(comboBox);
        dateTimePicker.setLabel("Data do Empréstimo");
        dateTimePicker.setWidth("min-content");
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(formLayout2Col);
        formLayout2Col.add(comboBox);
        formLayout2Col.add(dateTimePicker);
        getContent().add(buttonPrimary);
    }

    private void setComboBoxData(ComboBox<Livro> comboBoxLivro) {
        // Popula o ComboBox de Editora
        LivroRepository livroRepository = new LivroRepository();
        List<Livro> livros = livroRepository.listarTodas();
        comboBoxLivro.setItems(livros);
        comboBoxLivro.setItemLabelGenerator(Livro::getNomeLivro);
    }
}
