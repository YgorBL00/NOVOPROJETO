package com.example.fluxo_de_cliente.view;

import com.example.fluxo_de_cliente.model.Usuario;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AreaDoAdminView extends StackPane {

    private Usuario usuario;

    public AreaDoAdminView(Stage stage, Usuario usuario) {

        // Fundo fixo
        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        Label titulo = new Label("Área do Administrador");
        titulo.setStyle("-fx-font-size: 24; -fx-text-fill: #23336f;");

        Label nome = new Label("Bem-vindo, " + usuario.getNome());
        nome.setStyle("-fx-font-size: 16;");

        VBox conteudo = new VBox(12, titulo, nome);
        conteudo.setAlignment(Pos.CENTER);

        getChildren().add(conteudo);

        // Fade só no conteúdo
        conteudo.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(700), conteudo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
