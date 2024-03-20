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
			
			String insert = "INSERT INTO livro (nomeLivro, nomeAutor,anoPublicacao)"
					+ "values"
					+ "(?, ? ,?)";
			PreparedStatement prepearedStatment1 = connection.prepareStatement(insert);
			prepearedStatment1.setString(1, livro.getNomeLivro());
			prepearedStatment1.setString(2, livro.getNomeAutor());
			prepearedStatment1.setDate(3, java.sql.Date.valueOf(livro.getAnoPublicacao()));
			int resultado = prepearedStatment1.executeUpdate();
			if(resultado > 0) {
				return true;
			}else {
				return false;
			}
			
		}catch (Exception e) {
			return false;
		}

	}
	
	public boolean alterar(Livro livro) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			
			String insert = "update livro set nomeLivro=?, nomeAutor=?,anoPublicacao=?"
					+ " where id=?";
			PreparedStatement prepearedStatment1 = connection.prepareStatement(insert);
			prepearedStatment1.setString(1, livro.getNomeLivro());
			prepearedStatment1.setString(2, livro.getNomeAutor());
			prepearedStatment1.setDate(3, java.sql.Date.valueOf(livro.getAnoPublicacao()));
			int resultado = prepearedStatment1.executeUpdate();
			if(resultado > 0) {
				return true;
			}else {
				return false;
			}
			
		}catch (Exception e) {
			return false;
		}

	}
	
	public boolean excluir(Livro livro) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			
			String insert = "delete from livro where id=?";
			PreparedStatement prepearedStatment1 = connection.prepareStatement(insert);
			prepearedStatment1.setInt(1, livro.getId());
			int resultado = prepearedStatment1.executeUpdate();
			if(resultado > 0) {
				return true;
			}else {
				return false;
			}
			
		}catch (Exception e) {
			return false;
		}

	}
	
	public List<Livro> buscarTodos(){
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			
			String consulta = "select * from livro ";
			List<Livro> lista = new ArrayList<Livro>();
			Livro livro;
			PreparedStatement prepearedStatment1 = connection.prepareStatement(consulta);
			ResultSet resultSet = prepearedStatment1.executeQuery();
			while (resultSet.next()) {
				livro = new Livro();
				livro.setId(resultSet.getInt("id"));
				livro.setNomeLivro(resultSet.getString("nomeLivro"));
				livro.setNomeAutor(resultSet.getString("nomeAutor"));
				livro.setAnoPublicacao(resultSet.getString("anoPublicacao"));
				lista.add(livro);
			}
			return lista;
		}catch (Exception e) {
			return null;
		}
	}
	

	public Livro buscar(int id){
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			
			String consulta = "select * from livro where id=? ";
			Livro livro = new Livro();
			PreparedStatement prepearedStatment1 = connection.prepareStatement(consulta);
			prepearedStatment1.setInt(1,id);
			ResultSet resultSet = prepearedStatment1.executeQuery();
			while (resultSet.next()) {
				livro = new Livro();
				livro.setId(resultSet.getInt("id"));
				livro.setNomeLivro(resultSet.getString("nomeLivro"));
				livro.setNomeAutor(resultSet.getString("nomeAutor"));
				livro.setAnoPublicacao(resultSet.getString("anoPublicacao"));
			}
			return livro;
		}catch (Exception e) {
			return null;
		}
	}

}
