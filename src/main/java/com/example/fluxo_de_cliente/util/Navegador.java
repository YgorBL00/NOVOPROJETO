package com.example.fluxo_de_cliente.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.function.Consumer;

public class Navegador {

    private static Scene scene;

    public static void init(Scene mainScene) {
        scene = mainScene;
    }

    public static void trocarTela(String caminho) {
        trocarTela(caminho, null);
    }

    public static <T> void trocarTela(String caminho, Consumer<T> action) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(Navegador.class.getResource(caminho));

            Parent root = loader.load();

            if (action != null) {
                T controller = loader.getController();
                action.accept(controller);
            }

            scene.setRoot(root);

        } catch (IOException e) {
            System.out.println("❌ ERRO AO CARREGAR: " + caminho);
            e.printStackTrace();
        }
    }
}