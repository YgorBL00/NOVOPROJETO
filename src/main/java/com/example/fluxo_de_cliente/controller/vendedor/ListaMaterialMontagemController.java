package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator.ResultadoFormato;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ListaMaterialMontagemController {

    @FXML private Button btnVoltar;

    private Usuario usuario;
    private ResultadoFormato resultado;
    private int espessuraMm;

    public void carregarDados(
            Usuario usuario,
            ResultadoFormato resultado,
            int espessuraMm
    ) {
        this.usuario = usuario;
        this.resultado = resultado;
        this.espessuraMm = espessuraMm;
    }

    @FXML
    private void initialize() {

        btnVoltar.setOnAction(e ->
                Navegador.trocarTela("vendedor/resultado.fxml", c -> {
                    ResultadoController controller = (ResultadoController) c;
                    controller.carregarDados(
                            usuario,
                            java.util.List.of(resultado), // volta só com o atual
                            "",                            // nomeCliente opcional
                            false,
                            espessuraMm
                    );
                })
        );
    }
}