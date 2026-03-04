package com.example.fluxo_de_cliente.controller;

import com.example.fluxo_de_cliente.util.Navegador;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;



public class PainelInicialController implements Initializable {

    @FXML
    private Label mensagem;

    @FXML
    private Button iniciar;

    @FXML
    private ImageView logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // 🔹 Estado inicial
        mensagem.setOpacity(0);
        iniciar.setOpacity(0);
        mensagem.setTranslateY(30);

        // 🔹 Fade + subida da mensagem
        FadeTransition ftMensagem =
                new FadeTransition(Duration.seconds(1.5), mensagem);
        ftMensagem.setFromValue(0);
        ftMensagem.setToValue(1);

        ftMensagem.currentTimeProperty().addListener((obs, o, n) -> {
            double frac = n.toMillis() / ftMensagem.getDuration().toMillis();
            mensagem.setTranslateY(30 - frac * 30);
        });

        // 🔹 Após mensagem, mostra o botão
        ftMensagem.setOnFinished(ev -> {
            FadeTransition ftBtn =
                    new FadeTransition(Duration.millis(700), iniciar);
            ftBtn.setFromValue(0);
            ftBtn.setToValue(1);
            ftBtn.play();
        });

        ftMensagem.play();
    }
    @FXML
    private void abrirLogin() {
        Navegador.trocarTela("login.fxml");
    }
}
