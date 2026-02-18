package com.example.fluxo_de_cliente.controller;

import com.example.fluxo_de_cliente.controller.vendedor.AreaVendedorController;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.AuthService;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {

    @FXML private VBox conteudo;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Label mensagemErro;

    private final AuthService authService = new AuthService();
    private final Preferences prefs =
            Preferences.userNodeForPackage(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Recuperar email salvo
        String emailSalvo = prefs.get("email_salvo", "");
        emailField.setText(emailSalvo);

        // Fade in
        conteudo.setOpacity(0);

        FadeTransition fadeIn =
                new FadeTransition(Duration.millis(800), conteudo);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML
    private void fazerLogin() {

        String email = emailField.getText();
        String senha = senhaField.getText();

        Usuario usuario = authService.login(email, senha);

        if (usuario == null) {
            mensagemErro.setText("❌ Email ou senha inválidos");
            return;
        }

        prefs.put("email_salvo", email);

        if (usuario.getCargo().equalsIgnoreCase("ADMIN")) {

            Navegador.trocarTela("area-admin.fxml", controller -> {
                com.example.fluxo_de_cliente.controller.admin.AreaAdminController ctrl = (com.example.fluxo_de_cliente.controller.admin.AreaAdminController) controller;
                ctrl.setUsuario(usuario);
            });

        } else {

            Navegador.trocarTela("area-vendedor.fxml", controller -> {
                AreaVendedorController ctrl = (AreaVendedorController) controller;
                ctrl.setUsuario(usuario);
            });
        }
    }
}
