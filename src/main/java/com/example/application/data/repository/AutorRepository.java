package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.DBConnection;
import com.example.application.data.Autor;

public class AutorRepository {

    public boolean salvar(Autor autor) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO autor (nome_autor) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, autor.getNomeAutor());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Autor autor) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String update = "UPDATE autor SET nome_autor=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, autor.getNomeAutor());
            preparedStatement.setInt(2, autor.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Autor autor) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String delete = "DELETE FROM autor WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, autor.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Autor> listarTodos() {
    List<Autor> autores = new ArrayList<>();
    try (Connection connection = DBConnection.getInstance().getConnection()) {
        String query = "SELECT * FROM autor";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id"); // Supondo que o id seja um inteiro
            String nome = resultSet.getString("nome_autor"); // Substitua "nome_autor" pelo nome da coluna na tabela autor
            Autor autor = new Autor(id, nome); // Supondo que você tenha um construtor em Autor que aceite id e nome
            autores.add(autor);
        }
    // Imprimir lista de autores
    System.out.println("Lista de Autores:");
    for (Autor autor : autores) {
        System.out.println(autor);
    }
} catch (SQLException e) {
    e.printStackTrace();
}
return autores;
}

}
