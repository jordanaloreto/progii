package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.DBConnection;
import com.example.application.data.Emprestimo;

public class EmprestimoRepository {

    public int salvar(Emprestimo emprestimo) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
    
            String insert = "INSERT INTO emprestimo (data_emprestimo) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, emprestimo.getDataEmprestimo());
            preparedStatement.executeUpdate();
    
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Retorna o ID gerado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se não conseguir recuperar o ID
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



}
