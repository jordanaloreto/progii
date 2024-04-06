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

            String update = "UPDATE livro SET nome_livro=?, id_autor=?, id_editora=?, ano_Publicacao=?  WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, livro.getNomeLivro());
            preparedStatement.setString(2, livro.getAutor().getNomeAutor());
            preparedStatement.setString(3, livro.getEditora().getNomeEditora());
            preparedStatement.setString(4, livro.getAnoPublicacao());
            preparedStatement.setInt(5, livro.getId());
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

    public boolean removerReferenciasLivro(Livro livro) throws SQLException {
        Connection connection = null;
        try {
            // Obter a conexão
            connection = DBConnection.getInstance().getConnection();
    
            // Remover referências do livro na tabela de empréstimo
            boolean sucessoRemoverEmprestimos = removerEmprestimosDoLivro(connection, livro);
    
            // Remover referências do livro na tabela de edições
            boolean sucessoRemoverEdicoes = removerEdicoesDoLivro(connection, livro);
    
            // Se ambas as operações forem bem-sucedidas, retornar true
            return sucessoRemoverEmprestimos && sucessoRemoverEdicoes;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public boolean removerEmprestimosDoLivro(Connection connection, Livro livro) throws SQLException {
        String delete = "DELETE FROM emprestimo_livro WHERE id_livro = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, livro.getId());
            int resultado = preparedStatement.executeUpdate();
            return true; // Independente do resultado, consideramos como sucesso
        }
    }
    
    public boolean removerEdicoesDoLivro(Connection connection, Livro livro) throws SQLException {
        // Verifica se o livro possui edições associadas
        if (livroTemEdicoes(connection, livro)) {
            String delete = "DELETE FROM edicao WHERE id_livro = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
                preparedStatement.setInt(1, livro.getId());
                int resultado = preparedStatement.executeUpdate();
                return resultado > 0;
            }
        } else {
            // Se o livro não tiver edições, não é necessário excluir nada
            return true;
        }
    }
    
    public boolean livroTemEdicoes(Connection connection, Livro livro) throws SQLException {
        String query = "SELECT COUNT(*) FROM edicao WHERE id_livro = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, livro.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    
    public List<Livro> listarTodas() {
        List<Livro> livros = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String query = "SELECT l.id AS livro_id, l.nome_livro, l.ano_publicacao, a.id AS autor_id, a.nome_autor, e.id AS editora_id, e.nome_editora "
                         + "FROM livro l "
                         + "JOIN autor a ON l.id_autor = a.id "
                         + "JOIN editora e ON l.id_editora = e.id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("livro_id");
                String nomeLivro = resultSet.getString("nome_livro");
                String anoPublicacao = resultSet.getString("ano_publicacao");
                int autorId = resultSet.getInt("autor_id");
                String nomeAutor = resultSet.getString("nome_autor");
                int editoraId = resultSet.getInt("editora_id");
                String nomeEditora = resultSet.getString("nome_editora");
                
                Autor autor = new Autor(autorId, nomeAutor); // Supondo que Autor tenha um construtor correspondente
                Editora editora = new Editora(editoraId, nomeEditora); // Supondo que Editora tenha um construtor correspondente
                
                Livro livro = new Livro(id, nomeLivro, anoPublicacao, autor, editora);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
    
    
    
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

    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String query = "SELECT id, nome_livro FROM livro";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomeLivro = resultSet.getString("nome_livro");
                Livro livro = new Livro();
                livro.setId(id);
                livro.setNomeLivro(nomeLivro);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
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

