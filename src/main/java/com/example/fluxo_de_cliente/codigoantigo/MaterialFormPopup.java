/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.Material;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class MaterialFormPopup extends Stage {

    public MaterialFormPopup(Stage owner, Material materialExistente, Consumer<Material> onSave) {

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle(materialExistente == null ? "Criar Material" : "Editar Material");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField valorField = new TextField();
        valorField.setPromptText("Valor");

        ComboBox<String> unidadeCombo = new ComboBox<>();
        unidadeCombo.getItems().addAll("pc", "un", "mt", "m²");
        unidadeCombo.setValue("un");

        ComboBox<String> classeBox = new ComboBox<>();
        classeBox.getItems().addAll("Montagem", "Refrigeração");

        Button salvarBtn = new Button(materialExistente == null ? "Criar" : "Salvar");

        // Se estiver editando
        if (materialExistente != null) {
            nomeField.setText(materialExistente.getNome());
            valorField.setText(String.valueOf(materialExistente.getValor()));
            unidadeCombo.setValue(materialExistente.getUnidade());
            classeBox.setValue(materialExistente.getClasse());
        }

        Label mensagem = new Label();

        salvarBtn.setOnAction(e -> {
            try {
                String nome = nomeField.getText().trim();
                double valor = Double.parseDouble(valorField.getText().trim());
                String unidade = unidadeCombo.getValue();
                String classe = classeBox.getValue();

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

        VBox root = new VBox(10, nomeField, valorField, unidadeCombo, classeBox, salvarBtn, mensagem);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        setScene(new Scene(root, 300, 320));
    }
}*/
