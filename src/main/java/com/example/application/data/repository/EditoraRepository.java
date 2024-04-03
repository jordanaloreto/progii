package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.DBConnection;
import com.example.application.data.Editora;

public class EditoraRepository {

    public boolean salvar(Editora editora) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO editora (nome_editora) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, editora.getNomeEditora());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Editora editora) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String update = "UPDATE editora SET nome_editora=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, editora.getNomeEditora());
            preparedStatement.setInt(2, editora.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Editora editora) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String delete = "DELETE FROM editora WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, editora.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Editora> listarTodas() {
    List<Editora> editoras = new ArrayList<>();
    try (Connection connection = DBConnection.getInstance().getConnection()) {
        String query = "SELECT * FROM editora";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id"); // Supondo que o id seja um inteiro
            String nome = resultSet.getString("nome_editora"); // Substitua "nome_editora" pelo nome da coluna na tabela editora
            Editora editora = new Editora(id, nome); // Supondo que você tenha um construtor em Editora que aceite id e nome
            editoras.add(editora);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return editoras;
}

}
