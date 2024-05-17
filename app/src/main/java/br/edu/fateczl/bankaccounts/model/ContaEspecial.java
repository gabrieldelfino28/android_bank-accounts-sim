package br.edu.fateczl.bankaccounts.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class ContaEspecial extends ContaBancaria {
    float limite;

    public ContaEspecial() {
        super();
    }

    public float getLimite() {
        return limite;
    }

    public void setLimite(float limite) {
        this.limite = limite;
    }

    @Override
    public void sacar(float valor) throws ArithmeticException {
        float limiteSaque = getSaldo() + limite;
        if (valor > limiteSaque) {
            throw new ArithmeticException("Saque maior que limite disponível!");
        } else {
            setSaldo(getSaldo() - valor);
        }
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return getCliente() + " ;"
                + getNumConta() + " ;"
                + String.format("%.2f",getSaldo()) + " ;"
                + getLimite()
                + " ; Especial";
    }
}
