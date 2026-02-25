package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private Usuario usuario;
    private int espessuraMm; // ✅ AGORA CORRETO

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    public void initialize() {

        cbEspessura.getItems().addAll(50, 70, 100, 120, 150);
        cbTipoPorta.getItems().addAll("Giratória", "Correr", "Pivotante");

        spQtdPortas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1)
        );

        spCantoSemAcabamento.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1)
        );

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

                throw new IllegalArgumentException();
            }

            double C = Double.parseDouble(txtComprimento.getText().replace(",", "."));
            double L = Double.parseDouble(txtLargura.getText().replace(",", "."));
            double A = Double.parseDouble(txtAltura.getText().replace(",", "."));

            espessuraMm = cbEspessura.getValue();          // ✅ AQUI
            double E = espessuraMm / 1000.0;               // mm → metros

            boolean possuiPiso = chkPiso.isSelected();

            var resultados =
                    FormatoCalculator.calcularTodos(C, L, A, E, possuiPiso);

            Navegador.trocarTela("vendedor/resultado.fxml", c -> {
                ResultadoController ctrl = (ResultadoController) c;
                ctrl.carregarDados(
                        usuario,
                        resultados,
                        possuiPiso,
                        espessuraMm // ✅ AGORA FUNCIONA
                );
            });

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro nos dados");
            alert.setContentText("Verifique os valores informados.");
            alert.showAndWait();
        }
    }
}
