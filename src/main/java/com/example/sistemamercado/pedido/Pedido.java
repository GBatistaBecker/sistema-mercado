package com.example.sistemamercado.pedido;

import com.example.sistemamercado.produto.Produto;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<Produto> listaDeProdutos = new ArrayList<>();
    private String nome;
    private String endereco;
    private String telefone;

    public Pedido() {
    }

    public Pedido(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void adicionarProduto(Produto produto) {
        listaDeProdutos.add(produto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getQuantidadeTotal() {
        return listaDeProdutos.size();
    }

    public List<Produto> getListaDeProdutos() {
        return listaDeProdutos;
    }
}