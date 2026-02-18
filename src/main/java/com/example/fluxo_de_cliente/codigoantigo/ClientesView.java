/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.dao.ClienteDAO;
import com.example.fluxo_de_cliente.model.Cliente;
import com.example.fluxo_de_cliente.model.Usuario;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ClientesView extends BorderPane {

    private Usuario usuario;

    public ClientesView(Stage stage, Usuario usuario) {

        this.usuario = usuario;

        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        // ===== TOPO =====
        Label titulo = new Label("Clientes Cadastrados");
        titulo.setStyle("-fx-font-size: 22; -fx-text-fill: #23336f;");
        BorderPane.setMargin(titulo, new Insets(20));
        setTop(titulo);

        // ===== TABELA =====
        TableView<Cliente> tabela = new TableView<>();

        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Cliente, String> colDocumento = new TableColumn<>("Documento");
        colDocumento.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDocumento()));

        TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));

        TableColumn<Cliente, String> colMunicipio = new TableColumn<>("Município");
        colMunicipio.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getMunicipio()));

        TableColumn<Cliente, String> colVendedor = new TableColumn<>("Vendedor");
        colVendedor.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getCriadoPor()));

        tabela.getColumns().addAll(
                colNome,
                colDocumento,
                colTelefone,
                colMunicipio,
                colVendedor
        );

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ===== CARREGAR DADOS =====
        tabela.getItems().addAll(
                new ClienteDAO().listarClientes()
        );

        setCenter(tabela);

        // ===== VOLTAR =====
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> {

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), this);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev -> {
                stage.setScene(
                        new Scene(
                                new AreaDoVendedorView(stage, this.usuario),
                                1150,
                                750
                        )
                );
            });

            fadeOut.play();
        });

        VBox rodape = new VBox(btnVoltar);
        rodape.setPadding(new Insets(15));
        rodape.setAlignment(Pos.CENTER_RIGHT);

        setBottom(rodape);
    }
}
*/
