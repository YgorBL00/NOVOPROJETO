package com.example.fluxo_de_cliente.service;

import com.example.fluxo_de_cliente.dao.UsuarioDAO;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.util.HashUtil;

public class AuthService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String email, String senha) {
        Usuario u = usuarioDAO.buscarPorEmail(email);
        if (u != null && HashUtil.verificarHash(senha, u.getSenhaHash())) {
            return u;
        }
        return null;
    }

    public void criarVendedor(String nome, String email, String senha) {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenhaHash(HashUtil.gerarHash(senha));
        u.setCargo("VENDEDOR");
        usuarioDAO.criarUsuario(u);
    }
    public Usuario loginAutomatico(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }
}
