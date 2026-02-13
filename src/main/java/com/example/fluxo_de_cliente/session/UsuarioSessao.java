package com.example.fluxo_de_cliente.session;

import com.example.fluxo_de_cliente.model.Usuario;

public class UsuarioSessao {

    private static Usuario usuarioLogado;

    public static void setUsuario(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioLogado;
    }

    public static String getEmail() {
        return usuarioLogado != null ? usuarioLogado.getEmail() : null;
    }

    public static String getNome() {
        return usuarioLogado != null ? usuarioLogado.getNome() : null;
    }
}
