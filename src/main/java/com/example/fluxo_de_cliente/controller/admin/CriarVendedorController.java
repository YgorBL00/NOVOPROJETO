package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.AuthService;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CriarVendedorController {

    @FXML private VBox conteudo;
    @FXML private Label nomeAdmin;
    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Label mensagem;

    private final AuthService authService = new AuthService();
    private Usuario usuario;

    // 🔥 Agora sem Stage
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        nomeAdmin.setText("Bem-vindo, " + usuario.getNome());
    }

    @FXML
    public void initialize() {

        conteudo.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(700), conteudo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML
    private void criarVendedor() {

        try {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String senha = senhaField.getText().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                mensagem.setText("Preencha todos os campos.");
                mensagem.setStyle("-fx-text-fill: red;");
                return;
            }

            authService.criarVendedor(nome, email, senha);

            mensagem.setText("Vendedor criado com sucesso!");
            mensagem.setStyle("-fx-text-fill: green;");

            nomeField.clear();
            emailField.clear();
            senhaField.clear();

        } catch (Exception e) {
            mensagem.setText("Erro ao criar vendedor.");
            mensagem.setStyle("-fx-text-fill: red;");
        }
    }

    // 🔥 Botão voltar usando Navegador
    @FXML
    private void voltar() {

        Navegador.trocarTela("admin/area-admin.fxml", controller -> {
            AreaAdminController c = (AreaAdminController) controller;
            c.setUsuario(usuario);
        });
    }
}