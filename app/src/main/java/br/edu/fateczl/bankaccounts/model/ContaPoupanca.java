package br.edu.fateczl.bankaccounts.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class ContaPoupanca extends ContaBancaria {
    private int diaRendimento;

    public ContaPoupanca() {
        super();
    }

    public int getDiaRendimento() {
        return diaRendimento;
    }

    public void setDiaRendimento(int diaRendimento) {
        if (diaRendimento > 28) {
            this.diaRendimento = checkDia(diaRendimento);
        } else if (diaRendimento < 1) {
            this.diaRendimento = 1;
        } else {
            this.diaRendimento = diaRendimento;
        }
    }

    private int checkDia(int dia) {
        int mes = LocalDate.now().getMonthValue();
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            if (dia > 31) {
                return 31;
            }
        } else {
            if (mes == 2) {
                if (LocalDate.now().isLeapYear()) {
                    return 29;
                } else {
                    return 28;
                }
            }
        }
        return 30;
    }

    public void computeNewSaldo(final float TAXA_POUPANCA) {
        float saldo = getSaldo();
        saldo += TAXA_POUPANCA * (1 + diaRendimento / 100f);
        setSaldo(saldo);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return getCliente() + " ;"
                + getNumConta() + " ;"
                + String.format("%.2f",getSaldo()) + " ;"
                + getDiaRendimento()
                + " ; Poupança";
    }
}
