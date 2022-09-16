package br.com.fiap.model;

public class Conta {

    private static int ultimoId = 0;

    private int id;
    private String nomeConta;
    private double valor;
    private String validade;
    private boolean paga;
    private String categoria;
    
    public Conta(String nomeConta, double valor, String validade, String categoria, boolean paga) {
        this.id = ultimoId++;
        this.nomeConta = nomeConta;
        this.valor = valor;
        this.validade = validade;
        this.categoria = categoria;
        this.paga = paga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeConta() {
        return nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    @Override
    public String toString() {
        var isPago = paga ? "PAGO" : "NÃO PAGO";
        return String.format("[ID: %s] %s - R$%s - Vencimento: %s - %s - %s", id, nomeConta, valor, validade, categoria, isPago);
    }



}
