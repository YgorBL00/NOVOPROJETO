/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.InfoCamara;
import com.example.fluxo_de_cliente.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RefrigeracaoView extends StackPane {

    public RefrigeracaoView(Stage stage, Usuario usuario) {

        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        TextField txtDistanciaMaquina = new TextField();
        TextField txtTempEntrada = new TextField();
        TextField txtTempInterna = new TextField();

        ComboBox<InfoCamara.TemperaturaExterna> cbTempExterna = new ComboBox<>();
        cbTempExterna.getItems().addAll(InfoCamara.TemperaturaExterna.values());

        Spinner<Integer> spHorasOperacao = new Spinner<>(1, 24, 24);

        ComboBox<InfoCamara.TensaoEquipamento> cbTensao = new ComboBox<>();
        cbTensao.getItems().addAll(InfoCamara.TensaoEquipamento.values());

        Label titulo = new Label("Parte de Refrigeração");
        titulo.setStyle("-fx-font-size: 22; -fx-text-fill: #23336f;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        int l = 0;
        grid.add(new Label("Distância do maquinário (m)"), 0, l);
        grid.add(txtDistanciaMaquina, 1, l++);

        grid.add(new Label("Temp. entrada (°C)"), 0, l);
        grid.add(txtTempEntrada, 1, l++);

        grid.add(new Label("Temp. externa"), 0, l);
        grid.add(cbTempExterna, 1, l++);

        grid.add(new Label("Temp. interna (°C)"), 0, l);
        grid.add(txtTempInterna, 1, l++);

        grid.add(new Label("Horas de operação"), 0, l);
        grid.add(spHorasOperacao, 1, l++);

        grid.add(new Label("Tensão"), 0, l);
        grid.add(cbTensao, 1, l++);

        Button btnVoltar = new Button("Voltar para Montagem");

        btnVoltar.setOnAction(e -> {
            stage.setScene(
                    new Scene(
                            new CaixoteView(stage, usuario),
                            1150,
                            750
                    )
            );
        });

        VBox layout = new VBox(20, titulo, grid, btnVoltar);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);

        getChildren().add(layout);
    }
}*/
