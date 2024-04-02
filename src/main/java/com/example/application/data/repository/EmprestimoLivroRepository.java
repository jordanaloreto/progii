package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.application.data.DBConnection;
import com.example.application.data.Emprestimo;
import com.example.application.data.EmprestimoLivro;
import com.example.application.data.Livro;

public class EmprestimoLivroRepository {

    // Método de salvar em EmprestimoLivroRepository
public boolean salvar(EmprestimoLivro emprestimoLivro, int idEmprestimo) {
    try {
        Connection connection = DBConnection.getInstance().getConnection();

        String insert = "INSERT INTO emprestimo_livro (id_livro, id_emprestimo) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, emprestimoLivro.getLivro().getId());
        preparedStatement.setInt(2, idEmprestimo); // Usar o idEmprestimo fornecido
        int resultado = preparedStatement.executeUpdate();
        return resultado > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public boolean desassociarLivroEmprestimo(Livro livro, Emprestimo emprestimo) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String delete = "DELETE FROM emprestimo_livro WHERE livro_id=? AND emprestimo_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, livro.getId());
            preparedStatement.setInt(2, emprestimo.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
