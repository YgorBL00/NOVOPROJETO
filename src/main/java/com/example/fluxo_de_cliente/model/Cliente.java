package com.example.fluxo_de_cliente.model;

public class Cliente {

    private int id;

    // Dados cadastrais
    private String documento; // CPF ou CNPJ
    private String nome;
    private String telefone;
    private String endereco;
    private String bairro;
    private String municipio;
    private String estado;
    private String cep;

    // Auditoria
    private String criadoPor;       // nome do vendedor (exibição)
    private String criadoPorEmail;  // email do vendedor (salvar no banco)

    // ===== GETTERS & SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public String getCriadoPorEmail() {
        return criadoPorEmail;
    }

    public void setCriadoPorEmail(String criadoPorEmail) {
        this.criadoPorEmail = criadoPorEmail;
    }

    public void setValor(double valor) {

    }
}
