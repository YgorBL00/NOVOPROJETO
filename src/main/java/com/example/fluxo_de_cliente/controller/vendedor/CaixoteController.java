package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CaixoteController {

    @FXML
    private TextField txtComprimento;
    @FXML
    private TextField txtLargura;
    @FXML
    private TextField txtAltura;

    @FXML
    private ComboBox<Integer> cbEspessura;
    @FXML
    private CheckBox chkPiso;
    @FXML
    private ComboBox<String> cbTipoPorta;
    @FXML
    private Spinner<Integer> spQtdPortas;
    @FXML
    private TextField txtTamanhoPorta;
    @FXML
    private Spinner<Integer> spCantoSemAcabamento;
    @FXML
    private TextField txtNomeCliente;

    private static final java.util.Map<Integer, Integer> PIR_ID_POR_ESPESSURA =
            java.util.Map.of(
                    50,  2,
                    70,  3,
                    100, 4,
                    120, 5,
                    150, 6
            );

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
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 0)
        );

        spQtdPortas.setDisable(true);

        cbTipoPorta.valueProperty().addListener((obs, o, n) ->
                spQtdPortas.setDisable(n == null)
        );
    }

    @FXML
    private void avancar() {

        try {
            if (txtNomeCliente.getText().isBlank()
                    || txtComprimento.getText().isEmpty()
                    || txtLargura.getText().isEmpty()
                    || txtAltura.getText().isEmpty()
                    || cbEspessura.getValue() == null) {

                throw new IllegalArgumentException();
            }

            // ✅ DECLARAÇÃO QUE FALTAVA
            String nomeCliente = txtNomeCliente.getText().trim();

            double C = Double.parseDouble(txtComprimento.getText().replace(",", "."));
            double L = Double.parseDouble(txtLargura.getText().replace(",", "."));
            double A = Double.parseDouble(txtAltura.getText().replace(",", "."));

            espessuraMm = cbEspessura.getValue();
            double E = espessuraMm / 1000.0;

            boolean possuiPiso = chkPiso.isSelected();

            var resultados =
                    FormatoCalculator.calcularTodos(C, L, A, E, possuiPiso);

            Navegador.trocarTela("vendedor/resultado.fxml", c -> {
                ResultadoController ctrl = (ResultadoController) c;
                ctrl.carregarDados(
                        usuario,
                        resultados,     // ⚠ ordem corrigida (veja erro 2)
                        nomeCliente,
                        possuiPiso,
                        espessuraMm
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
