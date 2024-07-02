package com.example.demo;

import java.sql.Date;

public class Produto {
    private int id;
    private String nome;
    private String marca;
    private String codBarra;
    private Date validade;
    private String numLote;
    private String descricao;
    private int quantidade;

    // Construtor com ID
    public Produto(int id, String nome, String marca, String codBarra, Date validade, String numLote, String descricao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.codBarra = codBarra;
        this.validade = validade;
        this.numLote = numLote;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    // Construtor sem ID
    public Produto(String nome, String marca, String codBarra, Date validade, String numLote, String descricao, int quantidade) {
        this.nome = nome;
        this.marca = marca;
        this.codBarra = codBarra;
        this.validade = validade;
        this.numLote = numLote;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public Date getValidade() {
        return validade;
    }

    public String getNumLote() {
        return numLote;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
