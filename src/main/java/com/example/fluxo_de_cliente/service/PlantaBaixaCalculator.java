package com.example.fluxo_de_cliente.service;

import com.example.fluxo_de_cliente.model.Painel;
import com.example.fluxo_de_cliente.model.PlantaBaixa;

public class PlantaBaixaCalculator {

    public static PlantaBaixa gerar(FormatoCalculator.ResultadoFormato r) {

        PlantaBaixa planta = new PlantaBaixa();

        planta.larguraTotal = r.paineisParede * FormatoCalculator.LARGURA_PAINEL;
        planta.alturaTotal = r.alturaParedeReal;

        double x = 0;

        for (int i = 0; i < r.paineisParede; i++) {
            Painel p = new Painel();
            p.largura = FormatoCalculator.LARGURA_PAINEL;
            p.altura = r.alturaParedeReal;
            p.x = x;
            p.y = 0;

            planta.paineis.add(p);
            x += p.largura;
        }

        return planta;
    }
}