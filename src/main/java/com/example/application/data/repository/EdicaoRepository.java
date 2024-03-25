package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.application.data.DBConnection;
import com.example.application.data.Edicao;

public class EdicaoRepository {

    public boolean salvar(Edicao edicao) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO edicao (novoConteudo, ano, livro_id) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, edicao.getNovoConteudo());
            preparedStatement.setInt(2, edicao.getAno());
            preparedStatement.setInt(3, edicao.getLivro().getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Edicao edicao) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String update = "UPDATE edicao SET novoConteudo=?, ano=?, livro_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, edicao.getNovoConteudo());
            preparedStatement.setInt(2, edicao.getAno());
            preparedStatement.setInt(3, edicao.getLivro().getId());
            preparedStatement.setInt(4, edicao.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Edicao edicao) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String delete = "DELETE FROM edicao WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, edicao.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
