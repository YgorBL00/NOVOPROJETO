package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Material;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.util.Optional;

public class MaterialFormPopupController {

    public static Optional<Material> show(Material materialExistente) {

        Dialog<Material> dialog = new Dialog<>();
        dialog.setTitle(materialExistente == null ? "Criar Material" : "Editar Material");

        ButtonType salvarButtonType = new ButtonType(
                materialExistente == null ? "Criar" : "Salvar",
                ButtonBar.ButtonData.OK_DONE
        );

        dialog.getDialogPane().getButtonTypes().addAll(salvarButtonType, ButtonType.CANCEL);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField valorField = new TextField();
        valorField.setPromptText("Valor");

        ComboBox<String> unidadeCombo = new ComboBox<>();
        unidadeCombo.getItems().addAll("pc", "un", "mt", "m²");
        unidadeCombo.setValue("un");

        ComboBox<String> classeCombo = new ComboBox<>();
        classeCombo.getItems().addAll("Montagem", "Refrigeração");

        if (materialExistente != null) {
            nomeField.setText(materialExistente.getNome());
            valorField.setText(String.valueOf(materialExistente.getValor()));
            unidadeCombo.setValue(materialExistente.getUnidade());
            classeCombo.setValue(materialExistente.getClasse());
        }

        VBox content = new VBox(10, nomeField, valorField, unidadeCombo, classeCombo);
        content.setPadding(new Insets(15));

        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == salvarButtonType) {
                try {
                    String nome = nomeField.getText().trim();
                    double valor = Double.parseDouble(valorField.getText().trim());
                    String unidade = unidadeCombo.getValue();
                    String classe = classeCombo.getValue();

                    if (nome.isEmpty() || unidade == null || classe == null) {
                        return null;
                    }

                    Material material = materialExistente != null
                            ? materialExistente
                            : new Material();

                    material.setNome(nome);
                    material.setValor(valor);
                    material.setUnidade(unidade);
                    material.setClasse(classe);

                    return material;

                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }
}