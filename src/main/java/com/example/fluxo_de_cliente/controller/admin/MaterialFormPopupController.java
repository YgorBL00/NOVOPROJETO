package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Material;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class MaterialFormPopupController extends Stage {

    public TextField nomeField;
    public TextField valorField;
    public ComboBox<String> unidadeCombo;
    public ComboBox<String> classeCombo;
    public Button btnSalvar;
    public Label mensagem;

    private Material materialExistente;
    private Consumer<Material> onSave;

    public MaterialFormPopupController(Stage owner, Material materialExistente, Consumer<Material> onSave) {
        this.materialExistente = materialExistente;
        this.onSave = onSave;

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle(materialExistente == null ? "Criar Material" : "Editar Material");

        nomeField = new TextField();
        nomeField.setPromptText("Nome");

        valorField = new TextField();
        valorField.setPromptText("Valor");

        unidadeCombo = new ComboBox<>();
        unidadeCombo.getItems().addAll("pc", "un", "mt", "m²");
        unidadeCombo.setValue("un");

        classeCombo = new ComboBox<>();
        classeCombo.getItems().addAll("Montagem", "Refrigeração");

        btnSalvar = new Button(materialExistente == null ? "Criar" : "Salvar");
        mensagem = new Label();

        if (materialExistente != null) {
            nomeField.setText(materialExistente.getNome());
            valorField.setText(String.valueOf(materialExistente.getValor()));
            unidadeCombo.setValue(materialExistente.getUnidade());
            classeCombo.setValue(materialExistente.getClasse());
        }

        btnSalvar.setOnAction(e -> {
            try {
                String nome = nomeField.getText().trim();
                double valor = Double.parseDouble(valorField.getText().trim());
                String unidade = unidadeCombo.getValue();
                String classe = classeCombo.getValue();

                if (nome.isEmpty() || unidade == null || classe == null) {
                    mensagem.setText("Preencha todos os campos.");
                    mensagem.setStyle("-fx-text-fill: red;");
                    return;
                }

                Material material = materialExistente != null ? materialExistente : new Material();
                material.setNome(nome);
                material.setValor(valor);
                material.setUnidade(unidade);
                material.setClasse(classe);

                onSave.accept(material);
                close();

            } catch (NumberFormatException ex) {
                mensagem.setText("Valor inválido.");
                mensagem.setStyle("-fx-text-fill: red;");
            }
        });

        VBox root = new VBox(10, nomeField, valorField, unidadeCombo, classeCombo, btnSalvar, mensagem);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(20));

        setScene(new Scene(root, 300, 320));
    }
}
