package com.example.fluxo_de_cliente.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class Navegador {

    private static Stage stage;

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    // Simples
    public static void trocarTela(String fxml) {
        trocarTela(fxml, null);
    }

    // Profissional
    public static <T> void trocarTela(String fxml, Consumer<T> controllerAction) {

        if (stage == null) {
            throw new IllegalStateException("Navegador não foi inicializado (init)");
        }

        try {
            String caminho = "/com/example/fluxo_de_cliente/view/" + fxml;
            URL resource = Navegador.class.getResource(caminho);

            if (resource == null) {
                throw new RuntimeException("FXML não encontrado: " + caminho);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            if (controllerAction != null) {
                T controller = loader.getController();
                controllerAction.accept(controller);
            }

            Scene scene = new Scene(root, 1150, 750);

            // 🔥 ESSENCIAL — elimina o fundo branco
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);

            // ❌ NÃO chame stage.show() aqui

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar FXML: " + fxml, e);
        }
    }
}