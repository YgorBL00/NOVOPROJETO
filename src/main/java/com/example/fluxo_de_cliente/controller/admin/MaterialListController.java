package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Material;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.MaterialService;
import com.example.fluxo_de_cliente.controller.admin.MaterialFormPopupController;
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
    private Stage stage;

    public void initData(Stage stage, Usuario usuario) {
        this.stage = stage;
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
        btnVoltar.setOnAction(e -> stage.setScene(new Scene(
                new com.example.fluxo_de_cliente.codigoantigo.AreaDoAdminView(stage, usuario),
                1150, 750
        )));
    }

    private void abrirPopup(Material material) {
        MaterialFormPopupController popup = new MaterialFormPopupController(stage, material, m -> {
            if (material == null) {
                materialService.salvar(m);
            } else {
                materialService.atualizar(m);
            }
            materiais.setAll(materialService.buscarTodos());
        });
        popup.show();
    }
}
