package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.InfoCamara;
import com.example.fluxo_de_cliente.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class RefrigeracaoController {

    @FXML private TextField txtDistanciaMaquina;
    @FXML private TextField txtTempEntrada;
    @FXML private TextField txtTempInterna;
    @FXML private ComboBox<InfoCamara.TemperaturaExterna> cbTempExterna;
    @FXML private Spinner<Integer> spHorasOperacao;
    @FXML private ComboBox<InfoCamara.TensaoEquipamento> cbTensao;

    private Stage stage;
    private Usuario usuario;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    public void initialize() {

        cbTempExterna.getItems().addAll(InfoCamara.TemperaturaExterna.values());
        cbTensao.getItems().addAll(InfoCamara.TensaoEquipamento.values());

        spHorasOperacao.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 24)
        );
    }

    @FXML
    private void voltar() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/fluxo_de_cliente/view/vendedor/CaixoteView.fxml")
            );

            Parent root = loader.load();

            CaixoteController controller = loader.getController();
            controller.setStage(stage);
            controller.setUsuario(usuario);

            stage.setScene(new Scene(root, 1150, 750));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
