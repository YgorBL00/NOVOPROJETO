package com.example.fluxo_de_cliente.controller;

import com.example.fluxo_de_cliente.util.Navegador;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PainelInicialController implements Initializable {

    @FXML
    private Label mensagem;

    @FXML
    private Button iniciar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Animação da mensagem
        FadeTransition ftMensagem = new FadeTransition(Duration.seconds(1.5), mensagem);
        ftMensagem.setFromValue(0);
        ftMensagem.setToValue(1);

        mensagem.setTranslateY(30);

        ftMensagem.currentTimeProperty().addListener((obs, o, n) -> {
            if (n != null && ftMensagem.getDuration().greaterThan(Duration.ZERO)) {
                double frac = n.toMillis() / ftMensagem.getDuration().toMillis();
                mensagem.setTranslateY(30 - frac * 30);
            }
        });

        ftMensagem.setOnFinished(ev -> {
            FadeTransition ftBtn = new FadeTransition(Duration.millis(700), iniciar);
            ftBtn.setFromValue(0);
            ftBtn.setToValue(1);
            ftBtn.play();
        });

        ftMensagem.play();
    }

    @FXML
    private void abrirLogin() {
        Navegador.trocarTela("/com/example/fluxo_de_cliente/view/login.fxml");
    }
}
