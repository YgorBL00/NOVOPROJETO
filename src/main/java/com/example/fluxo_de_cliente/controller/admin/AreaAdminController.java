package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AreaAdminController implements Initializable {

    @FXML private Label usuarioLabel;
    @FXML private VBox conteudo;
    @FXML private TilePane cardsContainer;

    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criarCard("Criar Vendedor", "criar-vendedor.fxml");
        criarCard("Lista de Materias", "material-list.fxml");
        criarCard("Outros Recursos", null);

        // Fade In
        conteudo.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(700), conteudo);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuarioLabel.setText("ADMIN: " + usuario.getNome());
    }

    private void criarCard(String titulo, String destinoFxml) {

        VBox card = new VBox();
        card.setPrefSize(260, 140);
        card.setAlignment(Pos.CENTER);
        card.setCursor(Cursor.HAND);

        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 12;
                -fx-border-radius: 12;
                -fx-border-color: #d0d7e2;
                """);

        Label label = new Label(titulo);
        label.setStyle("-fx-font-size: 15; -fx-text-fill: #23336f;");
        card.getChildren().add(label);

        // Hover
        card.setOnMouseEntered(e ->
                card.setStyle("""
                        -fx-background-color: #f5f8ff;
                        -fx-background-radius: 12;
                        -fx-border-radius: 12;
                        -fx-border-color: #245edb;
                        """)
        );

        card.setOnMouseExited(e ->
                card.setStyle("""
                        -fx-background-color: white;
                        -fx-background-radius: 12;
                        -fx-border-radius: 12;
                        -fx-border-color: #d0d7e2;
                        """)
        );

        // Clique
        card.setOnMouseClicked(e -> {

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), conteudo);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev -> {

                if (destinoFxml != null) {
                    Navegador.trocarTela("admin/" + destinoFxml, controller -> {
                        try {
                            controller.getClass()
                                    .getMethod("setUsuario", Usuario.class)
                                    .invoke(controller, usuario);
                        } catch (Exception ignored) {}
                    });
                } else {
                    System.out.println("Outros recursos clicados");
                }
            });

            fadeOut.play();
        });

        cardsContainer.getChildren().add(card);
    }
}
