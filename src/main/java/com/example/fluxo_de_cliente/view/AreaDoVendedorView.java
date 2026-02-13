package com.example.fluxo_de_cliente.view;

import com.example.fluxo_de_cliente.model.Usuario;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AreaDoVendedorView extends BorderPane {

    private Usuario usuario;

    public AreaDoVendedorView(Stage stage, Usuario usuario) {
        this.usuario = usuario;
        // Fundo geral
        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        /* =========================
           HEADER (topo fixo)
        ========================== */
        HBox header = new HBox();
        header.setPrefHeight(70);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 24, 0, 24));
        header.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #d0d7e2;
            -fx-border-width: 0 0 1 0;
        """);

        Label usuarioLabel = new Label("USUÁRIO: " + usuario.getNome());
        usuarioLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #23336f;");

        header.getChildren().add(usuarioLabel);
        setTop(header);

        /* =========================
           CONTEÚDO (cards)
        ========================== */
        VBox conteudo = new VBox();
        conteudo.setPadding(new Insets(32));
        conteudo.setSpacing(24);
        conteudo.setAlignment(Pos.TOP_CENTER);

        FlowPane cardsContainer = new FlowPane();
        cardsContainer.setHgap(20);
        cardsContainer.setVgap(20);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.setPrefWrapLength(900); // controla quebra (responsivo)

        Pane card1 = criarCardComNavegacao(
                "Criar Orçamentos",
                stage,
                this
        );

        Pane card2 = criarCardClientes("Clientes", stage, this);
        Pane card3 = criarCard("Calculo de Paineis");

        cardsContainer.getChildren().addAll(card1, card2, card3);
        conteudo.getChildren().add(cardsContainer);

        setCenter(conteudo);

        /* =========================
           FADE IN (só no conteúdo)
        ========================== */
        conteudo.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(700), conteudo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    /* =========================
       CARD PADRÃO
    ========================== */
    private Pane criarCard(String titulo) {

        VBox card = new VBox();
        card.setPrefSize(260, 140);
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
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

        // Hover (feedback visual)
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

        // Clique (CTA)
        card.setOnMouseClicked(e ->
                System.out.println("Clicou em: " + titulo)
        );

        return card;
    }
    private Pane criarCardComNavegacao(String titulo, Stage stage, Pane telaAtual) {

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

        // Clique → Fade out + troca de tela
        card.setOnMouseClicked(e -> {

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), telaAtual);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev -> {
                CriarOrcamentosView novaTela = new CriarOrcamentosView(stage, this.usuario);
                Scene scene = new Scene(novaTela, 1150, 750);
                stage.setScene(scene);
            });

            fadeOut.play();
        });

        return card;
    }

    private Pane criarCardClientes(String titulo, Stage stage, Pane telaAtual) {

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

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), telaAtual);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev -> {
                stage.setScene(
                        new Scene(
                                new ClientesView(stage, this.usuario),
                                1150,
                                750
                        )
                );
            });

            fadeOut.play();
        });

        return card;
    }


}
