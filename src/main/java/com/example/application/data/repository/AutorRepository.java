package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.application.data.DBConnection;
import com.example.application.data.Autor;

public class AutorRepository {

    public boolean salvar(Autor autor) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO autor (nomeAutor) VALUES (?)";
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

            String update = "UPDATE autor SET nomeAutor=? WHERE id=?";
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
}
