package com.example.fluxo_de_cliente;

import com.example.fluxo_de_cliente.database.DatabaseConnection;
import com.example.fluxo_de_cliente.service.VersaoChecker;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (!VersaoChecker.verificarVersao(conn)) {
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/fluxo_de_cliente/view/painel-inicial.fxml"
                    )
            );

            Parent root = loader.load();

            Scene scene = new Scene(root, 1150, 750);

            Navegador.init(scene); // ✅ agora passa Scene

            stage.setScene(scene);

            stage.setTitle("Fluxo de Cliente");
            stage.setResizable(false);

            stage.getIcons().add(
                    new Image(getClass().getResourceAsStream(
                            "/com/example/fluxo_de_cliente/icons/icone.png"))
            );

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    // Melhor deixar isso fora do Launcher depois
    public static class AppInfo {
        public static final String VERSAO_ATUAL = "1.2.0";
    }
}
