package model;

public class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public void atualizarEstoque(int quantidade) {
        this.quantidade += quantidade;
    }

    public double calcularPrecoTotal(int quantidade) {
        return this.preco * quantidade;
    }
}
