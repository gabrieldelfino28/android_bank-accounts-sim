package br.edu.fateczl.bankaccounts.control;

import br.edu.fateczl.bankaccounts.model.ContaBancaria;
import br.edu.fateczl.bankaccounts.model.ContaEspecial;

public class EspecialController implements IFactoryConta{
    public ContaBancaria createConta(String cliente, int numConta, float saldo, String limite) {
        ContaEspecial conta = new ContaEspecial();
        conta.setCliente(cliente);
        conta.setNumConta(numConta);
        conta.setSaldo(saldo);
        conta.setLimite(Float.parseFloat(limite));
        return conta;
    }
}
