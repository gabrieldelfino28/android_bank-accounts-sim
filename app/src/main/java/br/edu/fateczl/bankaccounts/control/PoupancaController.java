package br.edu.fateczl.bankaccounts.control;

import br.edu.fateczl.bankaccounts.model.ContaBancaria;
import br.edu.fateczl.bankaccounts.model.ContaPoupanca;

public class PoupancaController implements IFactoryConta, IComputeSaldo {
    @Override
    public ContaBancaria createConta(String cliente, int numConta, float saldo, String diaRendimento) {
        ContaPoupanca conta = new ContaPoupanca();
        conta.setCliente(cliente);
        conta.setNumConta(numConta);
        conta.setSaldo(saldo);
        conta.setDiaRendimento(Integer.parseInt(diaRendimento));
        return conta;
    }

    @Override
    public ContaBancaria updateSaldo(ContaBancaria conta) {
        ContaPoupanca poupanca = (ContaPoupanca) conta;
        poupanca.computeNewSaldo(TAXA_POUPANCA);
        return poupanca;
    }
}
