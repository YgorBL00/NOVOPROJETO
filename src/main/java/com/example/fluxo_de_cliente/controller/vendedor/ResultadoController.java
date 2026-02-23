package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator;
import com.example.fluxo_de_cliente.service.FormatoCalculator.ResultadoFormato;
import com.example.fluxo_de_cliente.util.Navegador;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Comparator;
import java.util.List;

public class ResultadoController {

    @FXML private BorderPane root;
    @FXML private VBox conteudo;
    @FXML private Label lblTitulo;
    @FXML private Button btnAnterior;
    @FXML private Button btnProximo;

    private Usuario usuario;
    private List<ResultadoFormato> resultados;
    private boolean possuiPiso;
    private double espessuraMm;
    private int indiceAtual = 0;

    public void setDados(Usuario usuario,
                         List<ResultadoFormato> resultados,
                         boolean possuiPiso,
                         double espessuraMm) {

        this.usuario = usuario;
        this.resultados = resultados;
        this.possuiPiso = possuiPiso;
        this.espessuraMm = espessuraMm;

        ResultadoFormato melhor = resultados.stream()
                .min(Comparator
                        .comparingInt((ResultadoFormato r) -> r.totalPaineis)
                        .thenComparing(r -> -r.aproveitamento))
                .orElse(null);

        if (melhor != null) {
            indiceAtual = resultados.indexOf(melhor);
        }

        atualizarTela();
    }

    @FXML
    public void initialize() {

        root.setStyle("""
            -fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #cceeff, #ffffff);
        """);

        btnAnterior.setStyle(botaoSeta());
        btnProximo.setStyle(botaoSeta());

        lblTitulo.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #1a1a1a;
        """);

        btnAnterior.setOnAction(e -> {
            indiceAtual = (indiceAtual - 1 + resultados.size()) % resultados.size();
            atualizarTela();
        });

        btnProximo.setOnAction(e -> {
            indiceAtual = (indiceAtual + 1) % resultados.size();
            atualizarTela();
        });
    }

    private String botaoSeta() {
        return """
            -fx-background-color: #2b7cff;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 20;
            -fx-pref-width: 40;
        """;
    }

    private void atualizarTela() {

        conteudo.getChildren().clear();

        ResultadoFormato r = resultados.get(indiceAtual);
        lblTitulo.setText("Montagem: " + r.nome);

        conteudo.getChildren().add(secao(
                "PAREDES",
                r.paineisParede,
                r.alturaParedeReal,
                r.recortesParede
        ));

        conteudo.getChildren().add(secao(
                "TETO",
                r.paineisTeto,
                r.alturaTetoReal,
                r.recortesTeto
        ));

        if (possuiPiso) {
            conteudo.getChildren().add(secao(
                    "PISO",
                    r.paineisPiso,
                    r.alturaPisoReal,
                    r.recortesPiso
            ));
        }

        criarResumo(r);
        criarBotoes(r);
    }

    private VBox secao(String titulo,
                       int quantidade,
                       double altura,
                       List<FormatoCalculator.Recorte> recortes) {

        VBox box = new VBox(8);
        box.setPadding(new Insets(15));
        box.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 10;
        -fx-border-radius: 10;
        -fx-border-color: #d9d9d9;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);
    """);

        double larguraPadrao = FormatoCalculator.LARGURA_PAINEL;

        Label lbl = new Label(titulo);
        lbl.setStyle("""
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-text-fill: #2b7cff;
    """);

        Label qtd = new Label(
                quantidade + " painéis de "
                        + espessuraMm + "mm ("
                        + String.format("%.2f", larguraPadrao)
                        + " x "
                        + String.format("%.2f", altura) + ")"
        );

        box.getChildren().addAll(lbl, qtd);

        // 🔥 PARTE DO RECORTE
        if (recortes != null && !recortes.isEmpty()) {

            Separator sep = new Separator();

            Label tituloRecorte = new Label("Recortes:");
            tituloRecorte.setStyle("-fx-font-weight: bold; -fx-text-fill: #444;");

            VBox listaRecortes = new VBox(5);

            for (FormatoCalculator.Recorte r : recortes) {

                double area = r.largura * r.altura;

                Label item = new Label(
                        "• "
                                + String.format("%.2f", r.largura)
                                + " x "
                                + String.format("%.2f", r.altura)
                                + "  |  Área: "
                                + String.format("%.2f", area)
                                + " m²"
                );

                listaRecortes.getChildren().add(item);
            }

            box.getChildren().addAll(sep, tituloRecorte, listaRecortes);
        }

        return box;
    }

    private void criarResumo(ResultadoFormato r) {

        VBox resumo = new VBox(10);
        resumo.setPadding(new Insets(15));
        resumo.setStyle("""
            -fx-background-color: #f2f8ff;
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-border-color: #2b7cff;
            -fx-border-width: 1.5;
        """);

        Label lblResumoTitulo = new Label("RESUMO FINAL");
        lblResumoTitulo.setStyle("""
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-text-fill: #2b7cff;
        """);

        double larguraPadrao = FormatoCalculator.LARGURA_PAINEL;

        double areaParede = r.paineisParede * larguraPadrao * r.alturaParedeReal;
        double areaTeto = r.paineisTeto * larguraPadrao * r.alturaTetoReal;
        double areaPiso = possuiPiso
                ? r.paineisPiso * larguraPadrao * r.alturaPisoReal
                : 0;

        double areaTotal = areaParede + areaTeto + areaPiso;

        Label lblTotal = new Label("Total de painéis: " + r.totalPaineis);
        Label lblM2 = new Label(String.format("Total m² de painel: %.2f m²", areaTotal));

        resumo.getChildren().addAll(lblResumoTitulo, lblTotal, lblM2);
        conteudo.getChildren().add(resumo);
    }

    private void criarBotoes(ResultadoFormato r) {

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar");
        Button btnMateriais = new Button("Lista de Materiais");

        btnVoltar.setStyle("""
            -fx-background-color: #6c757d;
            -fx-text-fill: white;
            -fx-font-weight: bold;
        """);

        btnMateriais.setStyle("""
            -fx-background-color: #28a745;
            -fx-text-fill: white;
            -fx-font-weight: bold;
        """);

        btnVoltar.setOnAction(e ->
                Navegador.trocarTela(
                        "/com/example/fluxo_de_cliente/view/vendedor/Caixote.fxml",
                        controller -> {
                            CaixoteController c = (CaixoteController) controller;
                            c.setUsuario(usuario);
                        }
                )
        );

        botoes.getChildren().addAll(btnVoltar, btnMateriais);
        conteudo.getChildren().add(botoes);
    }
}