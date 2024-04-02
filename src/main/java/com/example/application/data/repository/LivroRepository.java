package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.data.Autor;
import com.example.application.data.DBConnection;
import com.example.application.data.Editora;
import com.example.application.data.Livro;

public class LivroRepository {

    public boolean salvar(Livro livro) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
	
			String insert = "INSERT INTO livro (nome_Livro, id_autor, id_editora, ano_Publicacao) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, livro.getNomeLivro());
			preparedStatement.setInt(2, livro.getAutor().getId()); // Assuming you have a method getNome() in Autor class
			preparedStatement.setInt(3, livro.getEditora().getId());
            preparedStatement.setString(4, livro.getAnoPublicacao());
            // Assuming you have a method getId() in Editora class
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

    public List<Livro> listarTodas() {
        List<Livro> livros = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM livro";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomeLivro = resultSet.getString("nome_livro");
                String anoPublicacao = resultSet.getString("ano_publicacao");
                
                // Aqui você precisa obter os dados do autor e da editora, assumindo que existam
                int autorId = resultSet.getInt("id");
                Autor autor = buscarAutorPorId(autorId); // Implemente o método para buscar autor por id
                
                int editoraId = resultSet.getInt("id");
                Editora editora = buscarEditoraPorId(editoraId); // Implemente o método para buscar editora por id
                
                Livro livro = new Livro(id, nomeLivro, anoPublicacao, autor, editora);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
    
    // Implemente esses métodos para buscar autor e editora por id no banco de dados
    private Autor buscarAutorPorId(int id) {
        Autor autor = null;
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String query = "SELECT id, nome FROM autor WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int autorId = resultSet.getInt("id");
                String nomeAutor = resultSet.getString("nome");
                autor = new Autor(autorId, nomeAutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autor;
    }
    
    // Método para buscar editora por ID no banco de dados
    private Editora buscarEditoraPorId(int id) {
        Editora editora = null;
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String query = "SELECT id, nome FROM editora WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int editoraId = resultSet.getInt("id");
                String nomeEditora = resultSet.getString("nome");
                editora = new Editora(editoraId, nomeEditora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editora;
    }



    // public List<Livro> buscarTodos() {
    //     try {
    //         Connection connection = DBConnection.getInstance().getConnection();

    //         String query = "SELECT * FROM livro";
    //         List<Livro> lista = new ArrayList<>();
    //         PreparedStatement preparedStatement = connection.prepareStatement(query);
    //         ResultSet resultSet = preparedStatement.executeQuery();
    //         while (resultSet.next()) {
    //             Livro livro = new Livro();
    //             livro.setId(resultSet.getInt("id"));
    //             livro.setNomeLivro(resultSet.getString("nomeLivro"));
    //             livro.setAnoPublicacao(resultSet.getString("anoPublicacao"));
    //             // You may need to fetch Autor and Editora details separately based on your database structure
    //             // livro.setAutor(fetchAutorDetails(resultSet.getInt("livro_id")));
    //             // livro.setEditora(fetchEditoraDetails(resultSet.getInt("editora_id")));
    //             lista.add(livro);
    //         }
    //         return lista;

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public Livro buscar(int id) {
    //     try {
    //         Connection connection = DBConnection.getInstance().getConnection();

    //         String query = "SELECT * FROM livro WHERE id=?";
    //         Livro livro = null;
    //         PreparedStatement preparedStatement = connection.prepareStatement(query);
    //         preparedStatement.setInt(1, id);
    //         ResultSet resultSet = preparedStatement.executeQuery();
    //         if (resultSet.next()) {
    //             livro = new Livro();
    //             livro.setId(resultSet.getInt("id"));
    //             livro.setNomeLivro(resultSet.getString("nomeLivro"));
    //             livro.setAnoPublicacao(resultSet.getString("anoPublicacao"));
    //             // You may need to fetch Autor and Editora details separately based on your database structure
    //             // livro.setAutor(fetchAutorDetails(resultSet.getInt("autor_id")));
    //             // livro.setEditora(fetchEditoraDetails(resultSet.getInt("editora_id")));
    //         }
    //         return livro;

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
}

