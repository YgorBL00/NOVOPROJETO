/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.Material;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.MaterialService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaterialListView extends BorderPane {

    private final MaterialService materialService = new MaterialService();
    private final ObservableList<Material> materiais;

    public MaterialListView(Stage stage, Usuario usuario) {

        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        setPadding(new Insets(20));

        Label titulo = new Label("Lista de Materiais");
        titulo.setStyle("-fx-font-size: 24;");
        setTop(titulo);

        TableView<Material> table = new TableView<>();

        TableColumn<Material, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> cell.getValue().idProperty());

        TableColumn<Material, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(cell -> cell.getValue().nomeProperty());

        TableColumn<Material, Number> valorCol = new TableColumn<>("Valor");
        valorCol.setCellValueFactory(cell -> cell.getValue().valorProperty());

        TableColumn<Material, String> unidadeCol = new TableColumn<>("U.M");
        unidadeCol.setCellValueFactory(cell -> cell.getValue().unidadeProperty());

        TableColumn<Material, String> classeCol = new TableColumn<>("Classe");
        classeCol.setCellValueFactory(cell -> cell.getValue().classeProperty());

        table.getColumns().addAll(idCol, nomeCol, valorCol, unidadeCol, classeCol);

        materiais = FXCollections.observableArrayList(materialService.buscarTodos());
        table.setItems(materiais);

        Button criarBtn = new Button("Criar");
        Button editarBtn = new Button("Editar");
        Button excluirBtn = new Button("Excluir");
        Button voltarBtn = new Button("Voltar");

        criarBtn.setOnAction(e -> {
            MaterialFormPopup popup = new MaterialFormPopup(stage, null, material -> {
                materialService.salvar(material);
                atualizarTabela();
            });
            popup.show();
        });

        editarBtn.setOnAction(e -> {
            Material selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                MaterialFormPopup popup = new MaterialFormPopup(stage, selecionado, material -> {
                    materialService.atualizar(material);
                    atualizarTabela();
                });
                popup.show();
            }
        });

        excluirBtn.setOnAction(e -> {
            Material selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                materialService.excluir(selecionado.getId());
                atualizarTabela();
            }
        });

        voltarBtn.setOnAction(e ->{
             stage.setScene(new Scene
                     (new AreaDoAdminView(stage, usuario),
                             1150,
                             750
             ));
        });

        HBox botoes = new HBox(10, criarBtn, editarBtn, excluirBtn, voltarBtn);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, table, botoes);
        setCenter(layout);
    }

    private void atualizarTabela() {
        materiais.setAll(materialService.buscarTodos());
    }

}*/
