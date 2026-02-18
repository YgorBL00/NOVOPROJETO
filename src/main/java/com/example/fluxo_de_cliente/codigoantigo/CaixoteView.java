//package com.example.fluxo_de_cliente.codigoantigo;
//
//import com.example.fluxo_de_cliente.model.Usuario;
//import com.example.fluxo_de_cliente.service.FormatoCalculator;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//public class CaixoteView extends StackPane {
//
//    private TextField txtComprimento;
//    private TextField txtLargura;
//    private TextField txtAltura;
//    private ComboBox<Integer> cbEspessura;
//    private CheckBox chkPiso;
//
//    public CaixoteView(Stage stage, Usuario usuario) {
//
//        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");
//
//        // =============================
//        // CAMPOS
//        // =============================
//
//        txtComprimento = new TextField();
//        txtLargura = new TextField();
//        txtAltura = new TextField();
//
//        cbEspessura = new ComboBox<>();
//        cbEspessura.getItems().addAll(50, 70, 100, 120, 150);
//
//        chkPiso = new CheckBox("Possui piso");
//
//        ComboBox<String> cbTipoPorta = new ComboBox<>();
//        cbTipoPorta.getItems().addAll("Giratória", "Correr", "Pivotante");
//
//        Spinner<Integer> spQtdPortas = new Spinner<>(1, 10, 1);
//        spQtdPortas.setDisable(true);
//
//        cbTipoPorta.valueProperty().addListener((obs, o, n) ->
//                spQtdPortas.setDisable(n == null)
//        );
//
//        TextField txtTamanhoPorta = new TextField("0,80 x 1,80");
//        Spinner<Integer> spCantoSemAcabamento = new Spinner<>(1, 4, 1);
//
//        // =============================
//        // LISTENERS AUTOMÁTICOS
//        // =============================
//
//        txtComprimento.textProperty().addListener((obs, o, n) -> recalcular());
//        txtLargura.textProperty().addListener((obs, o, n) -> recalcular());
//        txtAltura.textProperty().addListener((obs, o, n) -> recalcular());
//        cbEspessura.valueProperty().addListener((obs, o, n) -> recalcular());
//
//        // =============================
//        // LAYOUT
//        // =============================
//
//        Label titulo = new Label("Parte de Montagem");
//        titulo.setStyle("-fx-font-size: 22; -fx-text-fill: #23336f;");
//
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//
//        int l = 0;
//
//        grid.add(new Label("Comprimento (m)"), 0, l);
//        grid.add(txtComprimento, 1, l++);
//
//        grid.add(new Label("Largura (m)"), 0, l);
//        grid.add(txtLargura, 1, l++);
//
//        grid.add(new Label("Altura (m)"), 0, l);
//        grid.add(txtAltura, 1, l++);
//
//        grid.add(new Label("Espessura do Painel (mm)"), 0, l);
//        grid.add(cbEspessura, 1, l++);
//
//        grid.add(chkPiso, 1, l++);
//
//        grid.add(new Label("Tipo de Porta"), 0, l);
//        grid.add(cbTipoPorta, 1, l++);
//
//        grid.add(new Label("Qtd. Portas"), 0, l);
//        grid.add(spQtdPortas, 1, l++);
//
//        grid.add(new Label("Tamanho da Porta (L x A)"), 0, l);
//        grid.add(txtTamanhoPorta, 1, l++);
//
//        grid.add(new Label("Canto sem acabamento"), 0, l);
//        grid.add(spCantoSemAcabamento, 1, l++);
//
//        Button btnAvancar = new Button("Ir para Formato");
//
//        // =============================
//        // BOTÃO AVANÇAR
//        // =============================
//
//        btnAvancar.setOnAction(e -> {
//
//            try {
//
//                if (txtComprimento.getText().isEmpty()
//                        || txtLargura.getText().isEmpty()
//                        || txtAltura.getText().isEmpty()
//                        || cbEspessura.getValue() == null) {
//
//                    throw new Exception("Campos obrigatórios não preenchidos");
//                }
//
//                double C = Double.parseDouble(txtComprimento.getText().replace(",", "."));
//                double L = Double.parseDouble(txtLargura.getText().replace(",", "."));
//                double A = Double.parseDouble(txtAltura.getText().replace(",", "."));
//                double E = cbEspessura.getValue() / 1000.0;
//
//                boolean possuiPiso = chkPiso.isSelected();
//
//                var resultados = FormatoCalculator.calcularTodos(C, L, A, E, possuiPiso);
//
//                // Remove formato piso se não marcado
//                if (!possuiPiso) {
//                    resultados.removeIf(r -> r.requerPiso);
//                }
//
//                // Escolhe melhor formato (menos painéis)
//                var melhorFormato = resultados.stream()
//                        .min((r1, r2) ->
//                                Integer.compare(r1.totalPaineis, r2.totalPaineis))
//                        .orElse(null);
//
//                if (melhorFormato == null) {
//                    throw new Exception("Nenhum formato encontrado");
//                }
//
//                stage.setScene(new Scene(
//                        new ResultadoView(
//                                stage,
//                                usuario,
//                                resultados,
//                                possuiPiso,
//                                cbEspessura.getValue() // ← AQUI
//                        ),
//                        1150,
//                        750
//                ));
//
//            } catch (Exception ex) {
//
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText("Erro nos dados");
//                alert.setContentText("Verifique os valores informados.");
//                alert.showAndWait();
//            }
//        });
//
//        VBox layout = new VBox(20, titulo, grid, btnAvancar);
//        layout.setPadding(new Insets(30));
//        layout.setAlignment(Pos.TOP_CENTER);
//
//        getChildren().add(layout);
//    }
//
//    private void recalcular() {
//
//        if (txtComprimento.getText().isEmpty()
//                || txtLargura.getText().isEmpty()
//                || txtAltura.getText().isEmpty()
//                || cbEspessura.getValue() == null) {
//            return;
//        }
//
//        try {
//
//            double C = Double.parseDouble(txtComprimento.getText().replace(",", "."));
//            double L = Double.parseDouble(txtLargura.getText().replace(",", "."));
//            double A = Double.parseDouble(txtAltura.getText().replace(",", "."));
//            double E = cbEspessura.getValue() / 1000.0;
//
//            boolean possuiPiso = chkPiso.isSelected(); // 👈 AQUI
//
//            var resultados = FormatoCalculator.calcularTodos(C, L, A, E, possuiPiso);
//
//            var melhor = resultados.stream()
//                    .min((r1, r2) ->
//                            Integer.compare(r1.totalPaineis, r2.totalPaineis))
//                    .orElse(null);
//
//            if (melhor != null) {
//                System.out.println("Melhor formato: " + melhor.nome);
//            }
//
//        } catch (NumberFormatException ignored) {
//        }
//    }
//}