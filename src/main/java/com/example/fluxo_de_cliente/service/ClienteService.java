package com.example.fluxo_de_cliente.service;

import com.example.fluxo_de_cliente.dao.ClienteDAO;
import com.example.fluxo_de_cliente.model.Cliente;

import java.util.List;

public class ClienteService {
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void criarCliente(String nome, double valor, String emailVendedor) {
        Cliente c = new Cliente();
        c.setNome(nome);
        c.setValor(valor);
        c.setCriadoPorEmail(emailVendedor); // email vai pro banco
        c.setCriadoPor(""); // opcional, só para exibir depois
        clienteDAO.criarCliente(c);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    public void apagarCliente(int id) {
        clienteDAO.apagarCliente(id);
    }
}
