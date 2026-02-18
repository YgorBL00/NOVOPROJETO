package com.example.fluxo_de_cliente.service;

import com.example.fluxo_de_cliente.database.DatabaseConnection;
import com.example.fluxo_de_cliente.model.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialService {

    public List<Material> buscarTodos() {

        List<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM materiais ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Material(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getString("unidade"),
                        rs.getString("classe")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void salvar(Material material) {

        String sql = "INSERT INTO materiais (nome, valor, unidade, classe) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, material.getNome());
            stmt.setDouble(2, material.getValor());
            stmt.setString(3, material.getUnidade());
            stmt.setString(4, material.getClasse());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Material material) {

        String sql = "UPDATE materiais SET nome=?, valor=?, unidade=?, classe=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, material.getNome());
            stmt.setDouble(2, material.getValor());
            stmt.setString(3, material.getUnidade());
            stmt.setString(4, material.getClasse());
            stmt.setInt(5, material.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {

        String sql = "DELETE FROM materiais WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}