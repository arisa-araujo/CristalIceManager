package model;

public class Cliente extends Pessoa {
    private long id;
    private String nome;
    private String telefone;
    private String endereco;

    public Cliente(long id, String nome, String contato, String endereco) {
        super(id, nome, contato);
        this.endereco = endereco;
    }

    @Override
    public void mostrarInformacoes() {
        System.out.println("Cliente: " + nome + ", Contato: " + contato + ", Endereço: " + endereco);
    }
}