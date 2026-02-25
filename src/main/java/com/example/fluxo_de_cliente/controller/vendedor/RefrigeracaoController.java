package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.InfoCamara;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RefrigeracaoController {

    @FXML private TextField txtDistanciaMaquina;
    @FXML private TextField txtTempEntrada;
    @FXML private TextField txtTempInterna;
    @FXML private ComboBox<InfoCamara.TemperaturaExterna> cbTempExterna;
    @FXML private Spinner<Integer> spHorasOperacao;
    @FXML private ComboBox<InfoCamara.TensaoEquipamento> cbTensao;

    private Usuario usuario;

    /* ===== setter ===== */

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /* ===== lifecycle ===== */

    @FXML
    public void initialize() {

        cbTempExterna.getItems().addAll(
                InfoCamara.TemperaturaExterna.values()
        );

        cbTensao.getItems().addAll(
                InfoCamara.TensaoEquipamento.values()
        );

        spHorasOperacao.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 24)
        );
    }

    /* ===== navegação ===== */

    @FXML
    private void voltar() {

        Navegador.trocarTela("vendedor/caixote.fxml", controller -> {
            CaixoteController ctrl = (CaixoteController) controller;
            ctrl.setUsuario(usuario);
        });
    }
}
