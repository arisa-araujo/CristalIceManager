package model;

public class Pedido {
    private Cliente cliente;
    private Produto produto;
    private int quantidade;

    public Pedido(Cliente cliente, Produto produto, int quantidade) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public void processarPedido() {
        System.out.println("Processando pedido para " + cliente.nome);
        double total = produto.calcularPrecoTotal(quantidade);
        System.out.println("Total: R$ " + total);
    }
}
