package com.example.fluxo_de_cliente.service;

import java.util.ArrayList;
import java.util.List;

public class FormatoCalculator {

    public static final double LARGURA_PAINEL = 1.15;

    // =============================
    // TIPOS DE MONTAGEM
    // =============================

    public enum TipoMontagem {
        INTERCALADO,
        POR_FORA,
        POR_DENTRO
    }

    // =============================
    // CLASSE RECORTE
    // =============================

    public static class Recorte {
        public double largura;
        public double altura;

        public Recorte(double largura, double altura) {
            this.largura = largura;
            this.altura = altura;
        }
    }

    // =============================
    // RESULTADO
    // =============================

    public static class ResultadoFormato {

        public String nome;
        public TipoMontagem tipo;

        public int paineisParede;
        public int paineisTeto;
        public int paineisPiso;

        public List<Recorte> recortesParede = new ArrayList<>();
        public List<Recorte> recortesTeto = new ArrayList<>();
        public List<Recorte> recortesPiso = new ArrayList<>();

        public int totalPaineis;

        public double desperdicioM2;
        public double aproveitamento;

        public boolean requerPiso;

        // 🔥 ALTURAS REAIS DOS PAINÉIS
        public double alturaParedeReal;
        public double alturaTetoReal;
        public double alturaPisoReal;

        public ResultadoFormato(String nome, TipoMontagem tipo) {
            this.nome = nome;
            this.tipo = tipo;
        }
    }

    // =============================
    // MÉTODO PRINCIPAL
    // =============================

    public static List<ResultadoFormato> calcularTodos(
            double C_ext,
            double L_ext,
            double A_ext,
            double E,
            boolean temPiso
    ) {

        List<ResultadoFormato> lista = new ArrayList<>();

        lista.add(calcular("Intercalado", TipoMontagem.INTERCALADO,
                C_ext, L_ext, A_ext, E, temPiso));

        lista.add(calcular("Por Fora", TipoMontagem.POR_FORA,
                C_ext, L_ext, A_ext, E, temPiso));

        lista.add(calcular("Por Dentro", TipoMontagem.POR_DENTRO,
                C_ext, L_ext, A_ext, E, temPiso));

        return lista;
    }

    // =============================
    // CÁLCULO INDIVIDUAL
    // =============================

    private static ResultadoFormato calcular(
            String nome,
            TipoMontagem tipo,
            double C_ext,
            double L_ext,
            double A_ext,
            double E,
            boolean temPiso
    ) {

        ResultadoFormato r = new ResultadoFormato(nome, tipo);

        double C = C_ext;
        double L = L_ext;

        switch (tipo) {
            case INTERCALADO:
                C += E;
                L += E;
                break;

            case POR_FORA:
                C += 2 * E;
                break;

            case POR_DENTRO:
                L += 2 * E;
                break;
        }

        // =============================
        // ALTURA REAL PAREDE
        // =============================

        double alturaParede = A_ext - E - (temPiso ? E : 0);
        r.alturaParedeReal = alturaParede;

        // =============================
        // PAREDES
        // =============================

        double[] lados = {C, C, L, L};

        for (double lado : lados) {

            int inteiros = (int) (lado / LARGURA_PAINEL);
            double sobra = lado % LARGURA_PAINEL;

            r.paineisParede += inteiros;

            if (sobra > 0) {

                r.paineisParede++;

                r.recortesParede.add(
                        new Recorte(sobra, alturaParede)
                );

                double larguraDesperdicio = LARGURA_PAINEL - sobra;
                r.desperdicioM2 += larguraDesperdicio * alturaParede;
            }
        }

        // =============================
        // TETO
        // =============================

        r.alturaTetoReal = L;

        int inteirosTeto = (int) (C / LARGURA_PAINEL);
        double sobraTeto = C % LARGURA_PAINEL;

        r.paineisTeto = inteirosTeto;

        if (sobraTeto > 0) {

            r.paineisTeto++;

            r.recortesTeto.add(
                    new Recorte(sobraTeto, L)
            );

            double larguraDesperdicio = LARGURA_PAINEL - sobraTeto;
            r.desperdicioM2 += larguraDesperdicio * L;
        }

        // =============================
        // PISO
        // =============================

        if (temPiso) {

            r.requerPiso = true;
            r.alturaPisoReal = L;

            int inteirosPiso = (int) (C / LARGURA_PAINEL);
            double sobraPiso = C % LARGURA_PAINEL;

            r.paineisPiso = inteirosPiso;

            if (sobraPiso > 0) {

                r.paineisPiso++;

                r.recortesPiso.add(
                        new Recorte(sobraPiso, L)
                );

                double larguraDesperdicio = LARGURA_PAINEL - sobraPiso;
                r.desperdicioM2 += larguraDesperdicio * L;
            }
        }

        // =============================
        // TOTAL
        // =============================

        r.totalPaineis =
                r.paineisParede
                        + r.paineisTeto
                        + r.paineisPiso;

        double areaComprada =
                r.totalPaineis * LARGURA_PAINEL * alturaParede;

        double areaUtil = areaComprada - r.desperdicioM2;

        r.aproveitamento =
                areaComprada == 0
                        ? 0
                        : (areaUtil / areaComprada) * 100;

        return r;
    }
}