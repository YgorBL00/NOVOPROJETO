package com.example.fluxo_de_cliente.controller.admin;

import com.example.fluxo_de_cliente.model.Material;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.Consumer;

public class MaterialFormPopupController {

    @FXML private TextField nomeField;
    @FXML private TextField valorField;
    @FXML private ComboBox<String> unidadeCombo;
    @FXML private ComboBox<String> classeCombo;
    @FXML private Button btnSalvar;
    @FXML private Label mensagem;

    private Material materialExistente;
    private Consumer<Material> onSave;
    private Runnable onClose;

    /* ===== setters ===== */

    public void setMaterial(Material material) {
        this.materialExistente = material;

        if (material != null) {
            nomeField.setText(material.getNome());
            valorField.setText(String.valueOf(material.getValor()));
            unidadeCombo.setValue(material.getUnidade());
            classeCombo.setValue(material.getClasse());
            btnSalvar.setText("Salvar");
        } else {
            btnSalvar.setText("Criar");
        }
    }

    public void setOnSave(Consumer<Material> onSave) {
        this.onSave = onSave;
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    /* ===== lifecycle ===== */

    @FXML
    public void initialize() {

        unidadeCombo.getItems().addAll("pc", "un", "mt", "m²");
        classeCombo.getItems().addAll("Montagem", "Refrigeração");

        unidadeCombo.setValue("un");
    }

    /* ===== ação ===== */

    @FXML
    private void salvar() {

        try {
            String nome = nomeField.getText().trim();
            double valor = Double.parseDouble(valorField.getText().trim());
            String unidade = unidadeCombo.getValue();
            String classe = classeCombo.getValue();

            if (nome.isEmpty() || unidade == null || classe == null) {
                erro("Preencha todos os campos.");
                return;
            }

            Material material =
                    materialExistente != null ? materialExistente : new Material();

            material.setNome(nome);
            material.setValor(valor);
            material.setUnidade(unidade);
            material.setClasse(classe);

            if (onSave != null) onSave.accept(material);
            if (onClose != null) onClose.run();

        } catch (NumberFormatException e) {
            erro("Valor inválido.");
        }
    }

    private void erro(String msg) {
        mensagem.setText(msg);
        mensagem.setStyle("-fx-text-fill: red;");
    }
}
