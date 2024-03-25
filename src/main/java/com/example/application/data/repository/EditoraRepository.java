package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.application.data.DBConnection;
import com.example.application.data.Editora;

public class EditoraRepository {

    public boolean salvar(Editora editora) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO editora (nomeEditora) VALUES (?)";
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

            String update = "UPDATE editora SET nomeEditora=? WHERE id=?";
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
}
