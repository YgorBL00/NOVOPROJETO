package com.example.fluxo_de_cliente;

import com.example.fluxo_de_cliente.view.PainelInicial;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {
        PainelInicial painelInicial = new PainelInicial(stage);

        Scene scene = new Scene(painelInicial, 1150, 750);

        stage.setTitle("Fluxo de Cliente");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
