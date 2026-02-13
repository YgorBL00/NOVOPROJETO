package com.example.fluxo_de_cliente.dao;

import com.example.fluxo_de_cliente.database.DatabaseConnection;
import com.example.fluxo_de_cliente.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenhaHash(rs.getString("senha_hash"));
                u.setCargo(rs.getString("cargo"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void criarUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, email, senha_hash, cargo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenhaHash());
            ps.setString(4, u.getCargo());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
