package com.example.fluxo_de_cliente.view;

import com.example.fluxo_de_cliente.dao.UsuarioDAO;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.AuthService;
import com.example.fluxo_de_cliente.session.UsuarioSessao;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.prefs.Preferences;


public class LoginView extends StackPane {

    private final AuthService authService = new AuthService();

    public LoginView(Stage stage) {

        Preferences prefs = Preferences.userNodeForPackage(LoginView.class);

        // Fundo fixo
        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        // Campos
        Label titulo = new Label("LOGIN");
        titulo.setStyle("-fx-font-size: 24; -fx-text-fill: #23336f;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        String emailSalvo = prefs.get("email_salvo", "");
        emailField.setText(emailSalvo);

        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");

        Label mensagemErro = new Label();
        mensagemErro.setStyle("-fx-text-fill: red;");

        Button btnLogin = new Button("Entrar");
        btnLogin.setStyle(
                "-fx-background-color: #245edb;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 24;"
        );
        btnLogin.setPrefWidth(160);

        btnLogin.setDefaultButton(true);

        VBox conteudo = new VBox(12,
                titulo,
                emailField,
                senhaField,
                btnLogin,
                mensagemErro
        );
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setMaxWidth(260);

        getChildren().add(conteudo);

        // Fade só no conteúdo
        conteudo.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), conteudo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Ação de login
        btnLogin.setOnAction(e -> {
            String email = emailField.getText();
            String senha = senhaField.getText();

            Usuario usuario = authService.login(email, senha);

            if (usuario == null) {
                mensagemErro.setText("❌ Email ou senha inválidos");
                return;
            }

            prefs.put("email_salvo", email);

            // Redirecionamento por cargo
            if (usuario.getCargo().equalsIgnoreCase("ADMIN")) {

                AreaDoAdminView adminView = new AreaDoAdminView(stage, usuario);
                Scene sceneAdmin = new Scene(adminView, 1150, 750);
                stage.setScene(sceneAdmin);

            } else {

                AreaDoVendedorView vendedorView = new AreaDoVendedorView(stage, usuario);
                Scene sceneVendedor = new Scene(vendedorView, 1150, 750);
                stage.setScene(sceneVendedor);
            }

            stage.setResizable(false);
        });

    }
}
