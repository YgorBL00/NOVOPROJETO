package com.example.fluxo_de_cliente.dao;

import com.example.fluxo_de_cliente.database.DatabaseConnection;
import com.example.fluxo_de_cliente.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {


    // ===== CRIAR CLIENTE =====
    public void criarCliente(Cliente cliente) {

        String sql = """
            INSERT INTO clientes
            (documento, nome, telefone, endereco, bairro, municipio, estado, cep, criado_por)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getDocumento());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getBairro());
            ps.setString(6, cliente.getMunicipio());
            ps.setString(7, cliente.getEstado());
            ps.setString(8, cliente.getCep());
            ps.setString(9, cliente.getCriadoPorEmail()); // email do usuário logado

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== LISTAR CLIENTES =====
    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = """
            SELECT c.id,
                   c.documento,
                   c.nome,
                   c.telefone,
                   c.municipio,
                   u.nome AS vendedor_nome,
                   c.criado_por
            FROM clientes c
            JOIN usuarios u ON c.criado_por = u.email
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setDocumento(rs.getString("documento"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setMunicipio(rs.getString("municipio"));
                c.setCriadoPor(rs.getString("vendedor_nome"));       // para exibir
                c.setCriadoPorEmail(rs.getString("criado_por"));     // para filtro/regra
                clientes.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientes;
    }

    // ===== APAGAR CLIENTE =====
    public void apagarCliente(int id) {

        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
