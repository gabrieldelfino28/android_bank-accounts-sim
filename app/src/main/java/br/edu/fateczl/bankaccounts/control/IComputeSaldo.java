package br.edu.fateczl.bankaccounts.control;

import br.edu.fateczl.bankaccounts.model.ContaBancaria;

public interface IComputeSaldo {
    static final float TAXA_POUPANCA = 10.40f; //10,40% ao ano
    ContaBancaria updateSaldo(ContaBancaria conta);
}
