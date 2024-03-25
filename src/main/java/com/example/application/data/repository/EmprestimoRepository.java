package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.DBConnection;
import com.example.application.data.Emprestimo;

public class EmprestimoRepository {

    public boolean salvar(Emprestimo emprestimo) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO emprestimo (dataEmprestimo) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setDate(1, emprestimo.getDataEmprestimo());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Emprestimo emprestimo) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String update = "UPDATE emprestimo SET dataEmprestimo=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setDate(1, emprestimo.getDataEmprestimo());
            preparedStatement.setInt(2, emprestimo.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Emprestimo emprestimo) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String delete = "DELETE FROM emprestimo WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, emprestimo.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Emprestimo> buscarTodos() {
        List<Emprestimo> listaEmprestimos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM emprestimo";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                java.sql.Date dataEmprestimo = resultSet.getDate("dataEmprestimo");
                Emprestimo emprestimo = new Emprestimo(id, dataEmprestimo);
                listaEmprestimos.add(emprestimo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEmprestimos;
    }

    public Emprestimo buscarPorId(int id) {
        Emprestimo emprestimo = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM emprestimo WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                java.sql.Date dataEmprestimo = resultSet.getDate("dataEmprestimo");
                emprestimo = new Emprestimo(id, dataEmprestimo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimo;
    }
}
