package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Material;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.MaterialService;
import com.example.fluxo_de_cliente.util.Navegador;
import com.example.fluxo_de_cliente.util.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    /* ===== setter ===== */

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        carregarMateriais();
        configurarBotoes();
    }

    /* ===== setup ===== */

    private void carregarMateriais() {

        materiais = FXCollections.observableArrayList(
                materialService.buscarTodos()
        );
        tableMateriais.setItems(materiais);

        colId.setCellValueFactory(c -> c.getValue().idProperty());
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colValor.setCellValueFactory(c -> c.getValue().valorProperty());
        colUnidade.setCellValueFactory(c -> c.getValue().unidadeProperty());
        colClasse.setCellValueFactory(c -> c.getValue().classeProperty());
    }

    private void configurarBotoes() {

        btnCriar.setOnAction(e -> abrirPopup(null));

        btnEditar.setOnAction(e -> {
            Material selecionado =
                    tableMateriais.getSelectionModel().getSelectedItem();
            if (selecionado != null) abrirPopup(selecionado);
        });

        btnExcluir.setOnAction(e -> {
            Material selecionado =
                    tableMateriais.getSelectionModel().getSelectedItem();

            if (selecionado != null) {
                materialService.excluir(selecionado.getId());
                atualizarLista();
            }
        });

        btnVoltar.setOnAction(e ->
                Navegador.trocarTela("area-admin.fxml", controller -> {
                    AreaAdminController ctrl =
                            (AreaAdminController) controller;
                    ctrl.setUsuario(usuario);
                })
        );
    }

    /* ===== popup ===== */

    private void abrirPopup(Material material) {

        Stage owner = (Stage) btnCriar.getScene().getWindow();

        Popup.abrir(
                "material-form-popup.fxml",
                material == null ? "Criar Material" : "Editar Material",
                owner,
                (MaterialFormPopupController ctrl, Stage popup) -> {

                    ctrl.setMaterial(material);

                    ctrl.setOnSave(m -> {
                        if (material == null) {
                            materialService.salvar(m);
                        } else {
                            materialService.atualizar(m);
                        }
                        atualizarLista();
                    });

                    ctrl.setOnClose(popup::close);
                }
        );
    }

    private void atualizarLista() {
        materiais.setAll(materialService.buscarTodos());
    }
}
