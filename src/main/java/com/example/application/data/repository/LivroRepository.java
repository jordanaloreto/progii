package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.DBConnection;
import com.example.application.data.Livro;

public class LivroRepository {

    public boolean salvar(Livro livro) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String insert = "INSERT INTO livro (nomeLivro, nomeAutor, anoPublicacao) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, livro.getNomeLivro());
            preparedStatement.setString(2, livro.getAutor().getNomeAutor()); // Assuming you have a method getNome() in Autor class
            preparedStatement.setString(3, livro.getAnoPublicacao());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Livro livro) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String update = "UPDATE livro SET nomeLivro=?, nomeAutor=?, anoPublicacao=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, livro.getNomeLivro());
            preparedStatement.setString(2, livro.getAutor().getNomeAutor());
            preparedStatement.setString(3, livro.getAnoPublicacao());
            preparedStatement.setInt(4, livro.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Livro livro) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String delete = "DELETE FROM livro WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, livro.getId());
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Livro> buscarTodos() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM livro";
            List<Livro> lista = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Livro livro = new Livro();
                livro.setId(resultSet.getInt("id"));
                livro.setNomeLivro(resultSet.getString("nomeLivro"));
                livro.setAnoPublicacao(resultSet.getString("anoPublicacao"));
                // You may need to fetch Autor and Editora details separately based on your database structure
                // livro.setAutor(fetchAutorDetails(resultSet.getInt("autor_id")));
                // livro.setEditora(fetchEditoraDetails(resultSet.getInt("editora_id")));
                lista.add(livro);
            }
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Livro buscar(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM livro WHERE id=?";
            Livro livro = null;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                livro = new Livro();
                livro.setId(resultSet.getInt("id"));
                livro.setNomeLivro(resultSet.getString("nomeLivro"));
                livro.setAnoPublicacao(resultSet.getString("anoPublicacao"));
                // You may need to fetch Autor and Editora details separately based on your database structure
                // livro.setAutor(fetchAutorDetails(resultSet.getInt("autor_id")));
                // livro.setEditora(fetchEditoraDetails(resultSet.getInt("editora_id")));
            }
            return livro;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
