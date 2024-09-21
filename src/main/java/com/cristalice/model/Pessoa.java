package model;

public abstract class Pessoa {
    protected long id;
    protected String nome;
    protected String contato;

    public Pessoa(long id, String nome, String contato) {
        this.id = id;
        this.nome = nome;
        this.contato = contato;
    }

    public abstract void mostrarInformacoes();
}
