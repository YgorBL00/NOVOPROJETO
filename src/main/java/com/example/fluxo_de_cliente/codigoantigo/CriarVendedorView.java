/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.AuthService;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.stage.Stage;

public class CriarVendedorView extends StackPane {

    private AuthService authService = new AuthService();

    public CriarVendedorView(Stage stage, Usuario usuario) {

        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        Label titulo = new Label("Área do Administrador");
        titulo.setStyle("-fx-font-size: 24; -fx-text-fill: #23336f;");

        Label nomeAdmin = new Label("Bem-vindo, " + usuario.getNome());
        nomeAdmin.setStyle("-fx-font-size: 16;");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do vendedor");

        TextField emailField = new TextField();
        emailField.setPromptText("Email do vendedor");

        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");

        Button criarBtn = new Button("Criar Vendedor");
        Label mensagem = new Label();

        criarBtn.setOnAction(e -> {
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

            } catch (Exception ex) {
                mensagem.setText("Erro ao criar vendedor.");
                mensagem.setStyle("-fx-text-fill: red;");
                ex.printStackTrace();
            }
        });

        VBox conteudo = new VBox(12,
                titulo,
                nomeAdmin,
                new Separator(),
                new Label("Criar Novo Vendedor"),
                nomeField,
                emailField,
                senhaField,
                criarBtn,
                mensagem
        );

        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(300);
        getChildren().add(conteudo);

        // Fade in
        conteudo.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(700), conteudo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}*/
