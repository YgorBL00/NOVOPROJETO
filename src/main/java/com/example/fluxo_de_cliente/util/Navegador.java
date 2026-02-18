package com.example.fluxo_de_cliente.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class Navegador {

    private static Stage stage;

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    // 🔥 Método simples (sem dados)
    public static void trocarTela(String fxml) {
        trocarTela(fxml, null);
    }

    // 🔥 Método profissional (com dados)
    public static <T> void trocarTela(String fxml, Consumer<T> controllerAction) {
        try {

            FXMLLoader loader = new FXMLLoader(
                    Navegador.class.getResource(
                            "/com/example/fluxo_de_cliente/view/" + fxml)
            );

            Parent root = loader.load();

            if (controllerAction != null) {
                T controller = loader.getController();
                controllerAction.accept(controller);
            }

            stage.setScene(new Scene(root, 1150, 750));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
