package com.example.fluxo_de_cliente.controller.vendedor;

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

public class AreaVendedorController implements Initializable {

    @FXML private Label usuarioLabel;
    @FXML private VBox conteudo;
    @FXML private TilePane cardsContainer;

    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criarCard("Criar Orçamentos", "criar-orcamento.fxml");
        criarCard("Clientes", "clientes.fxml");
        criarCard("Calculo de Paineis", "painel-calculo.fxml");
        criarCard("Projeto", "caixote.fxml");

        // Fade In
        conteudo.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(700), conteudo);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuarioLabel.setText("USUÁRIO: " + usuario.getNome());
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

        card.setOnMouseClicked(e -> {

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), conteudo);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev ->
                    Navegador.trocarTela(destinoFxml, controller -> {

                        if (controller instanceof AreaVendedorController) return;

                        try {
                            controller.getClass()
                                    .getMethod("setUsuario", Usuario.class)
                                    .invoke(controller, usuario);
                        } catch (Exception ignored) {}
                    })
            );

            fadeOut.play();
        });

        cardsContainer.getChildren().add(card);
    }
}
