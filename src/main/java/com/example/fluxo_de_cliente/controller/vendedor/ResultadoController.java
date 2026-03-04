package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.FormatoCalculator;
import com.example.fluxo_de_cliente.service.FormatoCalculator.ResultadoFormato;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Comparator;
import java.util.List;

public class ResultadoController {

    @FXML private VBox conteudo;
    @FXML private Label lblTitulo;
    @FXML private Button btnAnterior;
    @FXML private Button btnProximo;

    private Stage stage;
    private Usuario usuario;
    private List<ResultadoFormato> resultados;
    private boolean possuiPiso;
    private double espessuraMm;
    private int indiceAtual = 0;

    public void setDados(Stage stage,
                         Usuario usuario,
                         List<ResultadoFormato> resultados,
                         boolean possuiPiso,
                         double espessuraMm) {

        this.stage = stage;
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

        btnAnterior.setOnAction(e -> {
            indiceAtual = (indiceAtual - 1 + resultados.size()) % resultados.size();
            atualizarTela();
        });

        btnProximo.setOnAction(e -> {
            indiceAtual = (indiceAtual + 1) % resultados.size();
            atualizarTela();
        });
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
        box.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label lbl = new Label(titulo);
        lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label qtd = new Label(quantidade + " painéis de "
                + espessuraMm + "mm");

        box.getChildren().addAll(lbl, qtd);

        return box;
    }

    private void criarResumo(ResultadoFormato r) {

        VBox resumo = new VBox(10);
        resumo.setPadding(new Insets(15));
        resumo.setStyle("-fx-background-color: #f2f8ff; -fx-background-radius: 10;");

        Label lblTotal = new Label("Total de painéis: " + r.totalPaineis);

        resumo.getChildren().add(lblTotal);
        conteudo.getChildren().add(resumo);
    }

    private void criarBotoes(ResultadoFormato r) {

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar");
        Button btnMateriais = new Button("Lista de Materiais");

        btnVoltar.setOnAction(e -> voltar());
        btnMateriais.setOnAction(e -> listaMateriais(r));

        botoes.getChildren().addAll(btnVoltar, btnMateriais);
        conteudo.getChildren().add(botoes);
    }

    private void voltar() {
        // implementar navegação
    }

    private void listaMateriais(ResultadoFormato r) {
        // implementar navegação
    }
}
