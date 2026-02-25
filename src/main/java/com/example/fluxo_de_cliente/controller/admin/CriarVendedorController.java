package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.AuthService;
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

    /* ===== setter chamado pelo Navegador ===== */

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        nomeAdmin.setText("Bem-vindo, " + usuario.getNome());
    }

    /* ===== lifecycle ===== */

    @FXML
    public void initialize() {

        conteudo.setOpacity(0);

        FadeTransition fadeIn =
                new FadeTransition(Duration.millis(700), conteudo);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    /* ===== ação ===== */

    @FXML
    private void criarVendedor() {

        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = senhaField.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            mensagemErro("Preencha todos os campos.");
            return;
        }

        try {
            authService.criarVendedor(nome, email, senha);

            mensagemSucesso("Vendedor criado com sucesso!");

            nomeField.clear();
            emailField.clear();
            senhaField.clear();

        } catch (Exception e) {
            mensagemErro("Erro ao criar vendedor.");
            e.printStackTrace();
        }
    }

    /* ===== helpers ===== */

    private void mensagemErro(String texto) {
        mensagem.setText(texto);
        mensagem.setStyle("-fx-text-fill: red;");
    }

    private void mensagemSucesso(String texto) {
        mensagem.setText(texto);
        mensagem.setStyle("-fx-text-fill: green;");
    }
}
