package br.edu.fateczl.bankaccounts.model;

public abstract class ContaBancaria {
    private String cliente;
    private int numConta;
    private float saldo;

    public ContaBancaria() {
        super();
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getNumConta() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void sacar(float valor) throws ArithmeticException {
        if (valor > saldo) {
            throw new ArithmeticException("Saque maior que saldo disponível!");
        }else {
            saldo -= valor;
        }
    }

    public void depositar(float valor) {
        saldo += valor;
    }
}
