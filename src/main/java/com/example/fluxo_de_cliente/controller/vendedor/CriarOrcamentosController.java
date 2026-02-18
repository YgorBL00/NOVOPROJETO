package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.dao.ClienteDAO;
import com.example.fluxo_de_cliente.model.Cliente;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.CnpjService;
import com.example.fluxo_de_cliente.session.UsuarioSessao;
import com.example.fluxo_de_cliente.util.Navegador;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class CriarOrcamentosController implements Initializable {

    @FXML private VBox conteudo;

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtBairro;
    @FXML private TextField txtMunicipio;
    @FXML private TextField txtEstado;
    @FXML private TextField txtCep;
    @FXML private Button btnBuscar;

    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtDocumento.textProperty().addListener((obs, old, value) -> {
            String numeros = value.replaceAll("\\D", "");
            btnBuscar.setDisable(numeros.length() != 14);
        });
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        UsuarioSessao.setUsuario(usuario);
    }

    @FXML
    private void buscarCnpj() {

        String cnpj = txtDocumento.getText().replaceAll("\\D", "");
        btnBuscar.setDisable(true);
        btnBuscar.setText("Buscando...");

        new Thread(() -> {
            try {
                JsonNode json = CnpjService.buscarCnpj(cnpj);

                Platform.runLater(() -> {
                    txtNome.setText(json.get("company").get("name").asText());
                    txtEndereco.setText(
                            json.get("address").get("street").asText() + ", " +
                                    json.get("address").get("number").asText()
                    );
                    txtBairro.setText(json.get("address").get("district").asText());
                    txtMunicipio.setText(json.get("address").get("city").asText());
                    txtEstado.setText(json.get("address").get("state").asText());
                    txtCep.setText(json.get("address").get("zip").asText());

                    if (json.has("phones")) {
                        txtTelefone.setText(
                                json.get("phones").get(0).get("number").asText()
                        );
                    }

                    btnBuscar.setText("Buscar CNPJ");
                    btnBuscar.setDisable(false);
                });

            } catch (Exception ex) {
                Platform.runLater(() -> {
                    btnBuscar.setText("Buscar CNPJ");
                    btnBuscar.setDisable(false);
                    new Alert(Alert.AlertType.ERROR,
                            "Erro ao consultar CNPJ").showAndWait();
                });
            }
        }).start();
    }

    @FXML
    private void salvarCliente() {

        try {
            Cliente cliente = new Cliente();

            cliente.setDocumento(txtDocumento.getText());
            cliente.setNome(txtNome.getText());
            cliente.setTelefone(txtTelefone.getText());
            cliente.setEndereco(txtEndereco.getText());
            cliente.setBairro(txtBairro.getText());
            cliente.setMunicipio(txtMunicipio.getText());
            cliente.setEstado(txtEstado.getText());
            cliente.setCep(txtCep.getText());

            cliente.setCriadoPorEmail(UsuarioSessao.getEmail());

            new ClienteDAO().criarCliente(cliente);

            new Alert(Alert.AlertType.INFORMATION,
                    "Cliente salvo com sucesso!").showAndWait();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao salvar cliente").showAndWait();
        }
    }

    @FXML
    private void voltar() {

        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), conteudo);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(ev ->
                Navegador.trocarTela("vendedor/area-vendedor.fxml", controller -> {
                    try {
                        controller.getClass()
                                .getMethod("setUsuario", Usuario.class)
                                .invoke(controller, usuario);
                    } catch (Exception ignored) {}
                })
        );

        fadeOut.play();
    }

    @FXML
    private void proximo() {

        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), conteudo);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(ev ->
                Navegador.trocarTela("vendedor/info-camara.fxml", controller -> {
                    try {
                        controller.getClass()
                                .getMethod("setUsuario", Usuario.class)
                                .invoke(controller, usuario);
                    } catch (Exception ignored) {}
                })
        );

        fadeOut.play();
    }
}
