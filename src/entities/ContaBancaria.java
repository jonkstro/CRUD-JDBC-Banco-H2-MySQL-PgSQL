package entities;

import exceptions.ContaException;

public abstract class ContaBancaria {
    private Integer numeroConta;
    private Double saldo;

    public ContaBancaria() {
    }

    public ContaBancaria(Integer numeroConta, Double saldo) {
        this.numeroConta = numeroConta;
        if (saldo < 0) {
            throw new ContaException("Saldo inicial inválido");
        }
        this.saldo = saldo;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    // public void setNumeroConta(Integer numeroConta) {
    //     this.numeroConta = numeroConta;
    // }

    public Double getSaldo() {
        return saldo;
    }

    // public void setSaldo(Double saldo) {
    //     this.saldo = saldo;
    // }

    public Double sacar(Double valor) {
        if (saldo < valor) {
            throw new ContaException("Sem saldo");
        }
        return saldo -= valor;
    }

    public Double depositar(Double valor) {
        if (valor <= 0) {
            throw new ContaException("Insira uma quantidade válida");
        }
        return saldo += valor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroConta == null) ? 0 : numeroConta.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContaBancaria other = (ContaBancaria) obj;
        if (numeroConta == null) {
            if (other.numeroConta != null)
                return false;
        } else if (!numeroConta.equals(other.numeroConta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContaBancaria [numeroConta=" + numeroConta + ", saldo=" + saldo + "]";
    }

}
