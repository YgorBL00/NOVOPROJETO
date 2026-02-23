package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Material;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.MaterialService;
import com.example.fluxo_de_cliente.controller.admin.MaterialFormPopupController;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MaterialListController {

    @FXML private TableView<Material> tableMateriais;
    @FXML private TableColumn<Material, Number> colId;
    @FXML private TableColumn<Material, String> colNome;
    @FXML private TableColumn<Material, Number> colValor;
    @FXML private TableColumn<Material, String> colUnidade;
    @FXML private TableColumn<Material, String> colClasse;

    @FXML private Button btnCriar;
    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;
    @FXML private Button btnVoltar;

    private final MaterialService materialService = new MaterialService();
    private ObservableList<Material> materiais;
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        carregarMateriais();
        configurarBotoes();
    }

    private void carregarMateriais() {
        materiais = FXCollections.observableArrayList(materialService.buscarTodos());
        tableMateriais.setItems(materiais);

        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colNome.setCellValueFactory(cell -> cell.getValue().nomeProperty());
        colValor.setCellValueFactory(cell -> cell.getValue().valorProperty());
        colUnidade.setCellValueFactory(cell -> cell.getValue().unidadeProperty());
        colClasse.setCellValueFactory(cell -> cell.getValue().classeProperty());
    }

    private void configurarBotoes() {
        btnCriar.setOnAction(e -> abrirPopup(null));
        btnEditar.setOnAction(e -> {
            Material selecionado = tableMateriais.getSelectionModel().getSelectedItem();
            if (selecionado != null) abrirPopup(selecionado);
        });
        btnExcluir.setOnAction(e -> {
            Material selecionado = tableMateriais.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                materialService.excluir(selecionado.getId());
                materiais.setAll(materialService.buscarTodos());
            }
        });
        btnVoltar.setOnAction(e ->
                Navegador.trocarTela("admin/area-admin.fxml", controller -> {
                    AreaAdminController c = (AreaAdminController) controller;
                    c.setUsuario(usuario);
                })
        );
    }

    private void abrirPopup(Material material) {

        var resultado = MaterialFormPopupController.show(material);

        resultado.ifPresent(m -> {

            if (material == null) {
                materialService.salvar(m);
            } else {
                materialService.atualizar(m);
            }

            materiais.setAll(materialService.buscarTodos());
        });
    }
}
