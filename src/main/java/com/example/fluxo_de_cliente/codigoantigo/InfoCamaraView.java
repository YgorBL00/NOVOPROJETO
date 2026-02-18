/*
package com.example.fluxo_de_cliente.codigoantigo;

import com.example.fluxo_de_cliente.model.InfoCamara;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.session.UsuarioSessao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InfoCamaraView extends StackPane {

    private Stage stage;
    private Usuario usuario;

    public InfoCamaraView(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;

        UsuarioSessao.setUsuario(usuario);

        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        // ======================
        // CAMPOS - MONTAGEM
        // ======================
        TextField txtComprimento = new TextField();
        TextField txtLargura = new TextField();
        TextField txtAltura = new TextField();

        ComboBox<InfoCamara.TipoPorta> cbTipoPorta = new ComboBox<>();
        cbTipoPorta.getItems().addAll(InfoCamara.TipoPorta.values());

        Spinner<Integer> spQtdPortas = new Spinner<>(1, 10, 1);
        spQtdPortas.setDisable(true);

        CheckBox chkPortaExpositora = new CheckBox("Porta expositora");

        Spinner<Integer> spQtdPortaExpositora = new Spinner<>(1, 10, 1);
        spQtdPortaExpositora.setDisable(true);

        // Ativa quantidade de portas
        cbTipoPorta.valueProperty().addListener((obs, o, n) -> {
            spQtdPortas.setDisable(n == null);
        });

        // Ativa quantidade de expositoras
        chkPortaExpositora.selectedProperty().addListener((obs, o, n) -> {
            spQtdPortaExpositora.setDisable(!n);
        });
        ComboBox<InfoCamara.TipoCamara> cbTipoCamara = new ComboBox<>();
        cbTipoCamara.getItems().addAll(InfoCamara.TipoCamara.values());

        CheckBox chkPiso = new CheckBox("Possui piso");

        // ======================
        // CAMPOS - REFRIGERAÇÃO
        // ======================
        TextField txtDistanciaMaquina = new TextField();

        CheckBox chkCarenagem = new CheckBox("Necessita carenagem");
        CheckBox chkSuporte = new CheckBox("Necessita suporte");

        TextField txtTempEntrada = new TextField();

        ComboBox<InfoCamara.TemperaturaExterna> cbTempExterna = new ComboBox<>();
        cbTempExterna.getItems().addAll(InfoCamara.TemperaturaExterna.values());

        TextField txtTempInterna = new TextField();

        Spinner<Integer> spHorasOperacao = new Spinner<>(1, 24, 24);

        ComboBox<InfoCamara.TensaoEquipamento> cbTensao = new ComboBox<>();
        cbTensao.getItems().addAll(InfoCamara.TensaoEquipamento.values());

        ComboBox<String> cbAberturaPorta = new ComboBox<>();
        cbAberturaPorta.getItems().addAll(
                "Esquerda",
                "Direita",
                "Reversível"
        );

        // ======================
        // TÍTULOS
        // ======================
        Label lblTitulo = new Label("Informações da Câmara");
        lblTitulo.setStyle("-fx-font-size: 22; -fx-text-fill: #23336f;");

        Label lblMontagem = new Label("Parte de Montagem");
        lblMontagem.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label lblRefrigeracao = new Label("Parte de Refrigeração");
        lblRefrigeracao.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        ComboBox<String> cbTipoProduto = new ComboBox<>();
        cbTipoProduto.setPrefWidth(250);
        cbTipoProduto.setEditable(true);

        ObservableList<String> produtos = FXCollections.observableArrayList(
                "Carne bovina",
                "Carne suína",
                "Frango inteiro",
                "Peito de frango",
                "Coxa e sobrecoxa",
                "Linguiça",
                "Salsicha",
                "Bacon",
                "Presunto",
                "Mortadela",
                "Peixes frescos",
                "Filé de peixe",
                "Camarão",
                "Polvo",
                "Lula",
                "Carne moída",
                "Hambúrguer congelado",

                "Leite",
                "Queijo muçarela",
                "Queijo prato",
                "Queijo parmesão",
                "Queijo minas",
                "Requeijão",
                "Iogurte",
                "Manteiga",
                "Margarina",
                "Creme de leite",
                "Chantilly",
                "Leite condensado",

                "Alface",
                "Tomate",
                "Cebola",
                "Batata",
                "Cenoura",
                "Repolho",
                "Brócolis",
                "Couve-flor",
                "Pepino",
                "Pimentão",
                "Abobrinha",
                "Maçã",
                "Banana",
                "Uva",
                "Laranja",
                "Limão",
                "Morango",
                "Manga",
                "Mamão",

                "Pão congelado",
                "Massa de pizza",
                "Massa de pastel",
                "Massa folhada",
                "Pão de queijo",
                "Croissant congelado",
                "Lasanha congelada",
                "Nhoque",
                "Ravioli",

                "Sorvetes",
                "Polpas de frutas",
                "Açaí",
                "Batata frita congelada",
                "Nuggets",
                "Empanados",
                "Pratos prontos congelados",
                "Legumes congelados",
                "Frutas congeladas",

                "Molhos prontos",
                "Caldos e sopas",
                "Ovos",
                "Temperos frescos",
                "Ervas frescas",
                "Maionese",
                "Ketchup",
                "Mostarda",

                "Caixas plásticas",
                "Paletes",
                "Embalagens a vácuo",
                "Filme plástico",
                "Etiquetas",
                "Termômetro",
                "Luvas térmicas",
                "Avental térmico",
                "Máscara",
                "Botas térmicas"
        );

        cbTipoProduto.setItems(produtos);

        // ======================
        // GRID - MONTAGEM
        // ======================
        GridPane gridMontagem = new GridPane();
        gridMontagem.setHgap(10);
        gridMontagem.setVgap(10);

        int l = 0;
        gridMontagem.add(new Label("Comprimento (m)"), 0, l);
        gridMontagem.add(txtComprimento, 1, l++);

        gridMontagem.add(new Label("Largura (m)"), 0, l);
        gridMontagem.add(txtLargura, 1, l++);

        gridMontagem.add(new Label("Altura (m)"), 0, l);
        gridMontagem.add(txtAltura, 1, l++);

        gridMontagem.add(new Label("Tipo de Câmara"), 0, l);
        gridMontagem.add(cbTipoCamara, 1, l++);

        gridMontagem.add(chkPiso, 1, l++);

        gridMontagem.add(new Label("Tipo de Porta"), 0, l);
        gridMontagem.add(cbTipoPorta, 1, l++);

        gridMontagem.add(new Label("Qtd. Portas"), 0, l);
        gridMontagem.add(spQtdPortas, 1, l++);

        gridMontagem.add(chkPortaExpositora, 1, l++);

        gridMontagem.add(new Label("Qtd. Portas Expositoras"), 0, l);
        gridMontagem.add(spQtdPortaExpositora, 1, l++);

        gridMontagem.add(new Label("Abertura da Porta"), 0, l);
        gridMontagem.add(cbAberturaPorta, 1, l++);

        // ======================
        // GRID - REFRIGERAÇÃO
        // ======================
        GridPane gridRefrigeracao = new GridPane();
        gridRefrigeracao.setHgap(10);
        gridRefrigeracao.setVgap(10);

        l = 0;
        gridRefrigeracao.add(new Label("Distância do maquinário (m)"), 0, l);
        gridRefrigeracao.add(txtDistanciaMaquina, 1, l++);

        gridRefrigeracao.add(chkCarenagem, 1, l++);
        gridRefrigeracao.add(chkSuporte, 1, l++);

        */
/*gridRefrigeracao.add(new Label("Tipo do produto"), 0, l);
        gridRefrigeracao.add(
                txtTipoProduto, 1, l++);*//*


        gridRefrigeracao.add(new Label("Temp. entrada do produto (°C)"), 0, l);
        gridRefrigeracao.add(txtTempEntrada, 1, l++);

        gridRefrigeracao.add(new Label("Temp. externa"), 0, l);
        gridRefrigeracao.add(cbTempExterna, 1, l++);

        gridRefrigeracao.add(new Label("Temp. interna (°C)"), 0, l);
        gridRefrigeracao.add(txtTempInterna, 1, l++);

        gridRefrigeracao.add(new Label("Horas de operação"), 0, l);
        gridRefrigeracao.add(spHorasOperacao, 1, l++);

        gridRefrigeracao.add(new Label("Tensão"), 0, l);
        gridRefrigeracao.add(cbTensao, 1, l++);

        gridRefrigeracao.add(new Label("Tipo do Produto"), 0, l);
        gridRefrigeracao.add(cbTipoProduto, 1, l++);

        // ======================
        // BOTÕES
        // ======================

        Button btnAvancar = new Button("Calcular / Avançar");
        Button btnVoltar = new Button("Voltar");

        HBox boxBotoes = new HBox(10, btnAvancar, btnVoltar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);

        // ======================
        // CONTEÚDO FINAL
        // ======================
        // Coluna ESQUERDA - Montagem

        VBox colunaMontagem = new VBox(10, lblMontagem, gridMontagem);
        colunaMontagem.setAlignment(Pos.TOP_LEFT);

        // Coluna DIREITA - Refrigeração
        VBox colunaRefrigeracao = new VBox(10, lblRefrigeracao, gridRefrigeracao);
        colunaRefrigeracao.setAlignment(Pos.TOP_LEFT);

        // Layout lado a lado
        HBox areaCentral = new HBox(40, colunaMontagem, colunaRefrigeracao);
        areaCentral.setAlignment(Pos.TOP_CENTER);

        // Conteúdo final
        VBox conteudo = new VBox(
                20,
                lblTitulo,
                areaCentral,
                boxBotoes
        );

        conteudo.setPadding(new Insets(25));
        conteudo.setMaxWidth(900);

        getChildren().add(conteudo);


    }
}
*/
