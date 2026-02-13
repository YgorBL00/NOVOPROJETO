package com.example.fluxo_de_cliente.view;

import com.example.fluxo_de_cliente.dao.ClienteDAO;
import com.example.fluxo_de_cliente.model.Cliente;
import com.example.fluxo_de_cliente.model.Usuario;
import com.example.fluxo_de_cliente.service.CnpjService;
import com.example.fluxo_de_cliente.session.UsuarioSessao;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CriarOrcamentosView extends StackPane {

    private Usuario usuario; // ✅ atributo declarado

    public CriarOrcamentosView(Stage stage, Usuario usuario) {

        this.usuario = usuario; // ✅ usuário salvo

        UsuarioSessao.setUsuario(usuario);

        setStyle("-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, #b3e0ff, white);");

        Label titulo = new Label("Cadastro de Cliente");
        titulo.setStyle("-fx-font-size: 24; -fx-text-fill: #23336f;");

        // ===== CAMPOS =====
        TextField txtDocumento = new TextField();
        txtDocumento.setPromptText("CNPJ ou CPF");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Razão Social / Nome");

        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("Telefone");

        TextField txtEndereco = new TextField();
        txtEndereco.setPromptText("Endereço");

        TextField txtBairro = new TextField();
        txtBairro.setPromptText("Bairro");

        TextField txtMunicipio = new TextField();
        txtMunicipio.setPromptText("Município");

        TextField txtEstado = new TextField();
        txtEstado.setPromptText("UF");

        TextField txtCep = new TextField();
        txtCep.setPromptText("CEP");

        Button btnBuscar = new Button("Buscar CNPJ");
        btnBuscar.setDisable(true);

        Button btnSalvar = new Button("Salvar Cliente");
        Button btnVoltar = new Button("Voltar");
        Button btnProximo = new Button("Próxima Página");


        // ===== CPF / CNPJ =====
        txtDocumento.textProperty().addListener((obs, old, value) -> {
            String numeros = value.replaceAll("\\D", "");
            btnBuscar.setDisable(numeros.length() != 14);
        });

        btnProximo.setOnAction(e -> {

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), this);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev -> {
                stage.setScene(
                        new Scene(
                                new InfoCamaraView(stage, this.usuario),
                                1150,
                                750
                        )
                );
            });

            fadeOut.play();
        });


        // ===== BUSCAR CNPJ =====
        btnBuscar.setOnAction(e -> {

            String cnpj = txtDocumento.getText().replaceAll("\\D", "");
            btnBuscar.setDisable(true);
            btnBuscar.setText("Buscando...");

            new Thread(() -> {
                try {
                    JsonNode json = CnpjService.buscarCnpj(cnpj);

                    Platform.runLater(() -> {
                        txtNome.setText(json.get("company").get("name").asText());
                        txtEndereco.setText(
                                json.get("address").get("street").asText() + ", " +
                                        json.get("address").get("number").asText()
                        );
                        txtBairro.setText(json.get("address").get("district").asText());
                        txtMunicipio.setText(json.get("address").get("city").asText());
                        txtEstado.setText(json.get("address").get("state").asText());
                        txtCep.setText(json.get("address").get("zip").asText());

                        if (json.has("phones")) {
                            txtTelefone.setText(
                                    json.get("phones").get(0).get("number").asText()
                            );
                        }

                        btnBuscar.setText("Buscar CNPJ");
                        btnBuscar.setDisable(false);
                    });

                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        btnBuscar.setText("Buscar CNPJ");
                        btnBuscar.setDisable(false);
                        new Alert(Alert.AlertType.ERROR,
                                "Erro ao consultar CNPJ").showAndWait();
                    });
                }
            }).start();
        });

        // ===== SALVAR CLIENTE =====
        btnSalvar.setOnAction(e -> {

            try {
                Cliente cliente = new Cliente();

                cliente.setDocumento(txtDocumento.getText());
                cliente.setNome(txtNome.getText());
                cliente.setTelefone(txtTelefone.getText());
                cliente.setEndereco(txtEndereco.getText());
                cliente.setBairro(txtBairro.getText());
                cliente.setMunicipio(txtMunicipio.getText());
                cliente.setEstado(txtEstado.getText());
                cliente.setCep(txtCep.getText());

                // ✅ usuário logado (via sessão)
                cliente.setCriadoPorEmail(UsuarioSessao.getEmail());

                new ClienteDAO().criarCliente(cliente);

                new Alert(Alert.AlertType.INFORMATION,
                        "Cliente salvo com sucesso!").showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                        "Erro ao salvar cliente").showAndWait();
            }
        });

        // ===== VOLTAR =====
        btnVoltar.setOnAction(e -> {

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), this);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(ev -> {
                stage.setScene(
                        new Scene(
                                new AreaDoVendedorView(stage, this.usuario),
                                1150,
                                750
                        )
                );
            });

            fadeOut.play();
        });

        // ===== FORM =====
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));

        int l = 0;
        form.add(new Label("CNPJ / CPF"), 0, l);
        form.add(txtDocumento, 1, l);
        form.add(btnBuscar, 2, l++);

        form.add(new Label("Nome"), 0, l);
        form.add(txtNome, 1, l++, 2, 1);

        form.add(new Label("Telefone"), 0, l);
        form.add(txtTelefone, 1, l++, 2, 1);

        form.add(new Label("Endereço"), 0, l);
        form.add(txtEndereco, 1, l++, 2, 1);

        form.add(new Label("Bairro"), 0, l);
        form.add(txtBairro, 1, l++);

        form.add(new Label("Município"), 0, l);
        form.add(txtMunicipio, 1, l++);

        form.add(new Label("UF"), 0, l);
        form.add(txtEstado, 1, l);

        form.add(new Label("CEP"), 2, l);
        form.add(txtCep, 3, l++);

        VBox botoes = new VBox(10, btnSalvar, btnVoltar, btnProximo);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        VBox conteudo = new VBox(15, titulo, form, botoes);
        conteudo.setAlignment(Pos.TOP_CENTER);
        conteudo.setMaxWidth(700);

        getChildren().add(conteudo);
    }
}
