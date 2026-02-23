package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CaixoteController {

    @FXML private TextField txtComprimento;
    @FXML private TextField txtLargura;
    @FXML private TextField txtAltura;
    @FXML private ComboBox<Integer> cbEspessura;
    @FXML private CheckBox chkPiso;
    @FXML private ComboBox<String> cbTipoPorta;
    @FXML private Spinner<Integer> spQtdPortas;
    @FXML private TextField txtTamanhoPorta;
    @FXML private Spinner<Integer> spCantoSemAcabamento;
    @FXML private Button btnAvancar;

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

        cbEspessura.getItems().addAll(50, 70, 100, 120, 150);
        cbTipoPorta.getItems().addAll("Giratória", "Correr", "Pivotante");

        spQtdPortas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        spCantoSemAcabamento.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));

        spQtdPortas.setDisable(true);

        cbTipoPorta.valueProperty().addListener((obs, o, n) ->
                spQtdPortas.setDisable(n == null)
        );
    }

    @FXML
    private void avancar() {

        try {

            if (txtComprimento.getText().isEmpty()
                    || txtLargura.getText().isEmpty()
                    || txtAltura.getText().isEmpty()
                    || cbEspessura.getValue() == null) {

                throw new Exception("Campos obrigatórios não preenchidos");
            }

            double C = Double.parseDouble(txtComprimento.getText().replace(",", "."));
            double L = Double.parseDouble(txtLargura.getText().replace(",", "."));
            double A = Double.parseDouble(txtAltura.getText().replace(",", "."));
            double E = cbEspessura.getValue() / 1000.0;

            boolean possuiPiso = chkPiso.isSelected();

            var resultados = FormatoCalculator.calcularTodos(C, L, A, E, possuiPiso);

            // 🔥 TROCA PROFISSIONAL DE TELA
            Navegador.trocarTela(
                    "/com/example/fluxo_de_cliente/view/vendedor/Resultado.fxml",
                    controller -> {

                        ResultadoController c = (ResultadoController) controller;

                        c.setDados(
                                usuario,
                                resultados,
                                possuiPiso,
                                cbEspessura.getValue()
                        );
                    }
            );

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro nos dados");
            alert.setContentText("Verifique os valores informados.");
            alert.showAndWait();
        }
    }
}
