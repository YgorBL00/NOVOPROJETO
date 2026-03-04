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

    @FXML private VBox conteudo;
    @FXML private Label lblTitulo;
    @FXML private Button btnAnterior;
    @FXML private Button btnProximo;
    @FXML private ScrollPane scrollPane;

    private Usuario usuario;
    private List<ResultadoFormato> resultados;
    private boolean possuiPiso;
    private int espessuraMm;
    private int indiceAtual = 0;

    // ✅ ATRIBUTO QUE FALTAVA
    private String nomeCliente;

    /* =========================
       CICLO DE VIDA
       ========================= */

    @FXML
    public void initialize() {

        btnAnterior.setOnAction(e -> navegar(-1));
        btnProximo.setOnAction(e -> navegar(1));

        btnAnterior.setDisable(true);
        btnProximo.setDisable(true);

        javafx.application.Platform.runLater(() -> {
            scrollPane.setStyle("-fx-background-color: transparent;");

            Region viewport = (Region) scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });
    }

    /* =========================
       MÉTODO ÚNICO DE ENTRADA
       ========================= */

    public void carregarDados(
            Usuario usuario,
            List<ResultadoFormato> resultados,
            String nomeCliente,
            boolean possuiPiso,
            int espessuraMm
    ) {
        this.usuario = usuario;
        this.resultados = resultados;
        this.nomeCliente = nomeCliente;
        this.possuiPiso = possuiPiso;
        this.espessuraMm = espessuraMm;

        definirMelhorResultado();
        atualizarEstadoBotoes();
        atualizarTela();
    }

    /* =========================
       NAVEGAÇÃO
       ========================= */

    private void navegar(int delta) {
        indiceAtual = (indiceAtual + delta + resultados.size()) % resultados.size();
        atualizarTela();
    }

    private void atualizarEstadoBotoes() {
        boolean ativo = resultados != null && resultados.size() > 1;
        btnAnterior.setDisable(!ativo);
        btnProximo.setDisable(!ativo);
    }

    /* =========================
       LÓGICA
       ========================= */

    private void definirMelhorResultado() {
        ResultadoFormato melhor = resultados.stream()
                .min(Comparator
                        .comparingInt((ResultadoFormato r) -> r.totalPaineis)
                        .thenComparing(r -> -r.aproveitamento))
                .orElse(null);

        if (melhor != null) {
            indiceAtual = resultados.indexOf(melhor);
        }
    }

    private void atualizarTela() {

        conteudo.getChildren().clear();

        ResultadoFormato r = resultados.get(indiceAtual);

        // ✅ TÍTULO COM CLIENTE
        lblTitulo.setText("Cliente: " + nomeCliente + "  |  Montagem: " + r.nome);

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

        if (possuiPiso && r.requerPiso) {
            conteudo.getChildren().add(secao(
                    "PISO",
                    r.paineisPiso,
                    r.alturaPisoReal,
                    r.recortesPiso
            ));
        }

        criarResumo(r);
        criarBotoes();
    }

    /* =========================
       COMPONENTES
       ========================= */

    private VBox secao(String tipo,
                       int quantidadePaineis,
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

        Label tituloLbl = new Label(tipo);
        tituloLbl.setStyle("""
        -fx-font-size: 15px;
        -fx-font-weight: bold;
        -fx-text-fill: #2b7cff;
        """);

        box.getChildren().addAll(tituloLbl, new Separator());

        double largura = FormatoCalculator.LARGURA_PAINEL;

        int qtdRecortes = (recortes == null) ? 0 : recortes.size();
        int qtdInteiros = quantidadePaineis - qtdRecortes;

        if (qtdInteiros > 0) {
            box.getChildren().add(labelLinha(
                    String.format(
                            "%d PAINÉIS DE PIR %dmm %.2fX%.2fm - %s",
                            qtdInteiros, espessuraMm, largura, alturaPainel, tipo
                    )
            ));
        }

        if (qtdRecortes > 0) {
            for (FormatoCalculator.Recorte r : recortes) {
                box.getChildren().add(labelLinha(
                        String.format(
                                "1 PAINEL DE PIR %dmm %.2fX%.2fm (RECORTE) - %s",
                                espessuraMm, r.largura, r.altura, tipo
                        )
                ));
            }
        }

        return box;
    }

    private Label labelLinha(String texto) {
        Label lbl = new Label(texto);
        lbl.setStyle("""
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-text-fill: #333;
        """);
        return lbl;
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

        double largura = FormatoCalculator.LARGURA_PAINEL;

        double areaParede = r.paineisParede * largura * r.alturaParedeReal;
        double areaTeto   = r.paineisTeto   * largura * r.alturaTetoReal;
        double areaPiso   = possuiPiso ? r.paineisPiso * largura * r.alturaPisoReal : 0;

        resumo.getChildren().addAll(
                new Label("RESUMO FINAL"),
                new Label("Total de painéis: " + r.totalPaineis),
                new Label(String.format("Área total de painel: %.2f m²", areaParede + areaTeto + areaPiso)),
                new Label(String.format("Desperdício: %.2f m²", r.desperdicioM2)),
                new Label(String.format("Aproveitamento: %.1f%%", r.aproveitamento))
        );

        conteudo.getChildren().add(resumo);
    }

    private void criarBotoes() {

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar");
        Button btnMateriais = new Button("Lista de Materiais");
        Button btnPlantaBaixa = new Button("Planta Baixa");

        btnVoltar.setOnAction(e ->
                Navegador.trocarTela("vendedor/caixote.fxml", c ->
                        ((CaixoteController) c).setUsuario(usuario)
                )
        );

        btnMateriais.setOnAction(e ->
                Navegador.trocarTela("vendedor/lista-material-montagem.fxml", c -> {
                    ListaMaterialMontagemController controller = (ListaMaterialMontagemController) c;
                    controller.carregarDados(
                            usuario,
                            resultados.get(indiceAtual),
                            espessuraMm
                    );
                })
        );


        btnPlantaBaixa.setOnAction(e ->
                Navegador.trocarTela("vendedor/planta_baixa.fxml", c -> {
                    PlantaBaixaController controller = (PlantaBaixaController) c;
                    controller.carregarDados(resultados.get(indiceAtual));
                })
        );

        botoes.getChildren().addAll(btnVoltar, btnMateriais, btnPlantaBaixa);
        conteudo.getChildren().add(botoes);
    }
}