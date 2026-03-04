package com.example.fluxo_de_cliente;

import com.example.fluxo_de_cliente.database.DatabaseConnection;
import com.example.fluxo_de_cliente.service.VersaoChecker;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.application.Application;
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

        // 🔥 ESSENCIAL

        Navegador.init(stage);
        Navegador.trocarTela("painel-inicial.fxml");

        stage.setTitle("Fluxo de Cliente");
        stage.setResizable(false);

        stage.getIcons().add(
                new Image(getClass().getResourceAsStream(
                        "/com/example/fluxo_de_cliente/icons/icone.png"))
        );

        stage.show(); // 👈 show SÓ depois de tudo
    }



    public static void main(String[] args) {
        launch(args);
    }



    // Melhor deixar isso fora do Launcher depois
    public static class AppInfo {
        public static final String VERSAO_ATUAL = "1.2.0";
    }

}
