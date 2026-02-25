package com.example.fluxo_de_cliente.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

    public static <T> void abrir(
            String fxml,
            String titulo,
            Stage owner,
            PopupConfig<T> config
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Popup.class.getResource(
                            "/com/example/fluxo_de_cliente/view/" + fxml
                    )
            );

            Scene scene = new Scene(loader.load());
            Stage popup = new Stage();

            popup.initOwner(owner);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle(titulo);
            popup.setScene(scene);

            T controller = loader.getController();
            config.configurar(controller, popup);

            popup.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface PopupConfig<T> {
        void configurar(T controller, Stage popup);
    }
}
