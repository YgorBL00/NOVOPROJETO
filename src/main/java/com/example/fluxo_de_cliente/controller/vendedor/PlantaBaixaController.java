package com.example.fluxo_de_cliente.controller.vendedor;

import com.example.fluxo_de_cliente.model.Painel;
import com.example.fluxo_de_cliente.model.PlantaBaixa;
import com.example.fluxo_de_cliente.service.PlantaBaixaCalculator;
import com.example.fluxo_de_cliente.service.FormatoCalculator.ResultadoFormato;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlantaBaixaController {

    @FXML
    private Canvas canvas;

    private static final double ESCALA = 100; // 1m = 100px

    public void carregarDados(ResultadoFormato resultado) {
        PlantaBaixa planta = PlantaBaixaCalculator.gerar(resultado);
        desenhar(planta);
    }

    private void desenhar(PlantaBaixa planta) {

        GraphicsContext g = canvas.getGraphicsContext2D();

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g.setStroke(Color.BLACK);
        g.setLineWidth(1.5);
        g.setFill(Color.BLACK);

        for (Painel p : planta.paineis) {
            double x = p.x * ESCALA;
            double y = p.y * ESCALA;
            double w = p.largura * ESCALA;
            double h = p.altura * ESCALA;

            g.strokeRect(x, y, w, h);

            g.fillText(
                    String.format("%.2fx%.2f", p.largura, p.altura),
                    x + 5,
                    y + 15
            );
        }

        // Dimensão total
        g.fillText(
                String.format("Largura total: %.2f m", planta.larguraTotal),
                10,
                20
        );
    }
}