/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator;
import com.example.fluxo_de_cliente.service.FormatoCalculator.ResultadoFormato;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class ResultadoView extends BorderPane {

    private final Stage stage;
    private final Usuario usuario;
    private final double espessuraMm;
    private int indiceAtual = 0;
    private final List<ResultadoFormato> resultados;
    private final boolean possuiPiso;

    private final VBox conteudo = new VBox(15);
    private final Label lblTitulo = new Label();



    public ResultadoView(Stage stage,
                         Usuario usuario,
                         List<ResultadoFormato> resultados,
                         boolean possuiPiso,
                         double espessuraMm) {

        this.resultados = resultados;
        this.possuiPiso = possuiPiso;
        this.espessuraMm = espessuraMm;
        this.stage = stage;
        this.usuario = usuario;


        // =============================
        // ESTILO DO FUNDO
        // =============================
        setPadding(new Insets(25));
        setStyle("""
            -fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #cceeff, #ffffff);
        """);

        // =============================
        // MELHOR MONTAGEM
        // =============================
        ResultadoFormato melhor = resultados.stream()
                .min(Comparator
                        .comparingInt((ResultadoFormato r) -> r.totalPaineis)
                        .thenComparing(r -> -r.aproveitamento))
                .orElse(null);

        if (melhor != null) {
            indiceAtual = resultados.indexOf(melhor);
        }

        // =============================
        // TOPO COM SETAS
        // =============================
        Button btnAnterior = new Button("◀");
        Button btnProximo = new Button("▶");

        btnAnterior.setStyle("""
            -fx-background-color: #2b7cff;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 20;
            -fx-pref-width: 40;
        """);
        btnProximo.setStyle("""
            -fx-background-color: #2b7cff;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 20;
            -fx-pref-width: 40;
        """);

        btnAnterior.setOnAction(e -> {
            indiceAtual = (indiceAtual - 1 + resultados.size()) % resultados.size();
            atualizarTela();
        });

        btnProximo.setOnAction(e -> {
            indiceAtual = (indiceAtual + 1) % resultados.size();
            atualizarTela();
        });

        lblTitulo.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #1a1a1a;
        """);

        HBox topo = new HBox(15, btnAnterior, lblTitulo, btnProximo);
        topo.setAlignment(Pos.CENTER);
        setTop(topo);

        // =============================
        // SCROLL PARA CONTEÚDO
        // =============================
        conteudo.setPadding(new Insets(20));
        ScrollPane scroll = new ScrollPane(conteudo);
        scroll.setFitToWidth(true);
        setCenter(scroll);

        atualizarTela();
    }

    private void atualizarTela() {

        conteudo.getChildren().clear();

        ResultadoFormato r = resultados.get(indiceAtual);

        lblTitulo.setText("Montagem: " + r.nome);

        // =============================
        // PAREDES
        // =============================
        conteudo.getChildren().add(secao(
                "PAREDES",
                r.paineisParede,
                espessuraMm,
                r.alturaParedeReal,
                r.recortesParede
        ));

        // =============================
        // TETO
        // =============================
        conteudo.getChildren().add(secao(
                "TETO",
                r.paineisTeto,
                espessuraMm,
                r.alturaTetoReal,
                r.recortesTeto
        ));

        // =============================
        // PISO
        // =============================
        if (possuiPiso) {
            conteudo.getChildren().add(secao(
                    "PISO",
                    r.paineisPiso,
                    espessuraMm,
                    r.alturaPisoReal,
                    r.recortesPiso
            ));
        }

        // =============================
        // RESUMO FINAL
        // =============================
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

// cálculo total m²
        double areaParede = r.paineisParede * larguraPadrao * r.alturaParedeReal;
        double areaTeto = r.paineisTeto * larguraPadrao * r.alturaTetoReal;
        double areaPiso = possuiPiso
                ? r.paineisPiso * larguraPadrao * r.alturaPisoReal
                : 0;

        double areaTotal = areaParede + areaTeto + areaPiso;

        Label lblTotal = new Label("Total de painéis: " + r.totalPaineis);
        Label lblM2 = new Label(String.format("Total m² de painel: %.2f m²", areaTotal));

        Label lblLista = new Label("PAINÉIS INTEIROS:");
        lblLista.setStyle("-fx-font-weight: bold;");

        VBox listaPaineis = new VBox(5);

        if (r.paineisParede > 0) {
            listaPaineis.getChildren().add(
                    new Label(r.paineisParede + " painéis de "
                            + espessuraMm + "mm ("
                            + String.format("%.2f", larguraPadrao)
                            + " x "
                            + String.format("%.2f", r.alturaParedeReal) + ")")
            );
        }

        if (r.paineisTeto > 0) {
            listaPaineis.getChildren().add(
                    new Label(r.paineisTeto + " painéis de "
                            + espessuraMm + "mm ("
                            + String.format("%.2f", larguraPadrao)
                            + " x "
                            + String.format("%.2f", r.alturaTetoReal) + ")")
            );
        }

        if (possuiPiso && r.paineisPiso > 0) {
            listaPaineis.getChildren().add(
                    new Label(r.paineisPiso + " painéis de "
                            + espessuraMm + "mm ("
                            + String.format("%.2f", larguraPadrao)
                            + " x "
                            + String.format("%.2f", r.alturaPisoReal) + ")")
            );
        }

        resumo.getChildren().addAll(
                lblResumoTitulo,
                lblTotal,
                lblM2,
                lblLista,
                listaPaineis
        );

        conteudo.getChildren().add(resumo);

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

        btnVoltar.setOnAction(e -> {
            stage.setScene(new Scene(
                    new CaixoteView(stage, usuario),
                    950,
                    650
            ));
        });

        botoes.getChildren().addAll(btnVoltar, btnMateriais);

        conteudo.getChildren().add(botoes);
    }


    private VBox secao(String titulo,
                       int quantidadePaineis,
                       double espessuraMm,
                       double alturaPainel,
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

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("""
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #2b7cff;
        """);

        Label lblQtd = new Label(
                quantidadePaineis + " Painéis de "
                        + espessuraMm + "mm ("
                        + String.format("%.2f", larguraPadrao)
                        + " x "
                        + String.format("%.2f", alturaPainel)
                        + ")"
        );
        lblQtd.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox obsBox = new VBox(3);

        if (recortes.isEmpty()) {

            obsBox.getChildren().add(new Label("OBS: Sem necessidade de recorte."));
            obsBox.getChildren().add(new Label("Desperdício: Sem desperdício nesta montagem."));

        } else {

            obsBox.getChildren().add(new Label("OBS: " + recortes.size() + " recorte(s):"));

            for (FormatoCalculator.Recorte r : recortes) {
                obsBox.getChildren().add(
                        new Label(String.format("• %.2f x %.2f m", r.largura, r.altura))
                );
            }
        }

        box.getChildren().addAll(lblTitulo, lblQtd, obsBox);
        return box;
    }
}*/
