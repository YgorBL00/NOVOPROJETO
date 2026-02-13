package com.example.fluxo_de_cliente.model;

public class InfoCamara {

    // ===== MONTAGEM =====
    private double comprimento;
    private double largura;
    private double altura;

    private TipoCamara tipoCamara; // CONGELADOS / RESFRIADOS
    private boolean temPiso;

    private TipoPorta tipoPorta; // CORRER / GIRATORIA
    private boolean portaExpositora;
    private int quantidadePortas;

    private String aberturaPorta;

    public enum TipoCamara {
        CONGELADOS, RESFRIADOS
    }

    public enum TipoPorta {
        CORRER, GIRATORIA
    }

    // ===== REFRIGERAÇÃO =====
    private double distanciaMaquinario;

    private boolean precisaCarenagem;
    private boolean precisaSuporte;

    private String tipoProduto;

    private double temperaturaEntradaProduto;
    private TemperaturaExterna temperaturaExterna;
    private double temperaturaInterna;

    private int horasOperacao;

    private TensaoEquipamento tensao;

    public enum TemperaturaExterna {
        T32, T35, T38, T43
    }

    public enum TensaoEquipamento {
        V220_MONO,
        V220_TRIFASICO,
        V380_TRIFASICO
    }

}
