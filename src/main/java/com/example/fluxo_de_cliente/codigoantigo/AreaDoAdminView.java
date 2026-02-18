/*package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.view.admin.MaterialList;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AreaDoAdminView extends BorderPane {

    private Usuario usuario;

    public AreaDoAdminView(Stage stage, Usuario usuario) {
        this.usuario = usuario;

        // Fundo geral
        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        // Header
        HBox header = new HBox();
        header.setPrefHeight(70);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 24, 0, 24));
        header.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #d0d7e2;
            -fx-border-width: 0 0 1 0;
        """);

        Label usuarioLabel = new Label("ADMIN: " + usuario.getNome());
        usuarioLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #23336f;");
        header.getChildren().add(usuarioLabel);
        setTop(header);

        // Conteúdo central com cards
        VBox conteudo = new VBox();
        conteudo.setPadding(new Insets(32));
        conteudo.setSpacing(24);
        conteudo.setAlignment(Pos.TOP_CENTER);

        Pane cardCriarVendedor = criarCard("Criar Vendedor", stage, conteudo);
        Pane cardMateriais = criarCard("Lista de Materias", stage, conteudo);
        Pane cardOutros = criarCard("Outros Recursos", stage, conteudo);

        TilePane cardsContainer = new TilePane();
        cardsContainer.setHgap(20);
        cardsContainer.setVgap(20);
        cardsContainer.setPrefColumns(2);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.setPrefTileWidth(260);
        cardsContainer.setPrefTileHeight(140);
        cardsContainer.getChildren().addAll(cardCriarVendedor, cardMateriais, cardOutros);

        conteudo.getChildren().add(cardsContainer);
        setCenter(conteudo);

        // Fade in inicial
        conteudo.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(700), conteudo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private Pane criarCard(String titulo, Stage stage, VBox conteudo) {
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

        // Hover
        card.setOnMouseEntered(e -> card.setStyle("""
            -fx-background-color: #f5f8ff;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-border-color: #245edb;
        """));

        card.setOnMouseExited(e -> card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-border-color: #d0d7e2;
        """));

        // Clique → Fade out + troca de tela
        card.setOnMouseClicked(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), conteudo);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(ev -> {
                switch (titulo) {
                    case "Criar Vendedor" -> stage.setScene(new Scene(new CriarVendedorView(stage, usuario), 1150, 750));
                    case "Lista de Materias" -> stage.setScene(new Scene(new MaterialListView(stage, usuario), 1150, 750));
                    case "Outros Recursos" -> System.out.println("Outros recursos clicados");
                }
            });
            fadeOut.play();
        });

        return card;
    }
}*/