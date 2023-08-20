package entities;

import exceptions.ContaException;

public class ContaPoupanca extends ContaBancaria {
    private Double taxaJuros;

    public ContaPoupanca() {
        super();
    }

    public ContaPoupanca(Integer numeroConta, Double saldo, Double taxaJuros) {
        super(numeroConta, saldo);
        if (taxaJuros < 0 || taxaJuros > 1) {
            throw new ContaException("Taxa de juros não deve ser menor que 0 nem maior que 1");
        }
        this.taxaJuros = taxaJuros;
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(Double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((taxaJuros == null) ? 0 : taxaJuros.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContaPoupanca other = (ContaPoupanca) obj;
        if (taxaJuros == null) {
            if (other.taxaJuros != null)
                return false;
        } else if (!taxaJuros.equals(other.taxaJuros))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContaPoupanca [taxaJuros=" + taxaJuros + "]";
    }

    
    
    
}
