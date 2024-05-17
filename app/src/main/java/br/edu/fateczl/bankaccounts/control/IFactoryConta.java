package br.edu.fateczl.bankaccounts.control;

import br.edu.fateczl.bankaccounts.model.ContaBancaria;

public interface IFactoryConta {
    ContaBancaria createConta(String cliente, int numConta, float saldo, String diaRendimento_or_limite);
}
