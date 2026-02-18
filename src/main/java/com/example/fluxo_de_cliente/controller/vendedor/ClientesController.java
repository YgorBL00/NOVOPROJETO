package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.dao.ClienteDAO;
import com.example.fluxo_de_cliente.model.Cliente;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientesController implements Initializable {

    @FXML private TableView<Cliente> tabela;

    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Cliente, String> colDocumento = new TableColumn<>("Documento");
        colDocumento.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDocumento()));

        TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTelefone()));

        TableColumn<Cliente, String> colMunicipio = new TableColumn<>("Município");
        colMunicipio.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getMunicipio()));

        TableColumn<Cliente, String> colVendedor = new TableColumn<>("Vendedor");
        colVendedor.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCriadoPor()));

        tabela.getColumns().addAll(
                colNome,
                colDocumento,
                colTelefone,
                colMunicipio,
                colVendedor
        );

        tabela.getItems().addAll(
                new ClienteDAO().listarClientes()
        );
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void voltar() {

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), tabela.getScene().getRoot());
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
}
