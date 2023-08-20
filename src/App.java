import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.ContaPoupanca;
import exceptions.ContaException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<ContaPoupanca> contas = new ArrayList<>();

        try {
            int opcao;
            do {
                System.out.println("");
                System.out.println("SISTEMA BANCÁRIO");
                System.out.println("1 - Criar conta");
                System.out.println("2 - Listar todas contas");
                System.out.println("3 - Atualizar dados da conta");
                System.out.println("4 - Apagar conta");
                System.out.println("5 - Depositar dinheiro");
                System.out.println("6 - Sacar dinheiro");
                System.out.println("0 - Sair");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        create(sc, contas);
                        break;

                    case 2:
                        read(contas);
                        break;

                    case 3:
                        update(sc, contas);
                        break;

                    case 4:
                        delete(sc, contas);
                        break;

                    case 5:
                        depositarValor(sc, contas);
                        break;

                    case 6:
                        sacarValor(sc, contas);
                        break;

                    default:
                        System.out.print("Escolha uma opção válida");
                        break;
                }
            } while (opcao != 0);

        } catch (Exception e) {
            throw new ContaException(e.getMessage());
        } finally {
            sc.close();
        }
    }

    private static void sacarValor(Scanner sc, List<ContaPoupanca> contas) {
        read(contas);
        System.out.print("Selecione um número da conta para fazer o saque: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o valor para sacar: ");
        Double valor = sc.nextDouble();
        sc.nextLine();
        contas.get(id).sacar(valor);
        System.out.println("Sacado com sucesso!!!");
        System.out.println("");
    }

    private static void depositarValor(Scanner sc, List<ContaPoupanca> contas) {
        read(contas);
        System.out.print("Selecione um número da conta para depositar: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o valor para depositar: ");
        Double valor = sc.nextDouble();
        sc.nextLine();
        contas.get(id).depositar(valor);
        System.out.println("Depositado com sucesso!!!");
        System.out.println("");
    }

    private static void update(Scanner sc, List<ContaPoupanca> contas) {
        read(contas);
        System.out.print("Selecione um número da conta para atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite a nova taxa de Juros: ");
        Double taxaJuros = sc.nextDouble();
        sc.nextLine();
        contas.get(id).setTaxaJuros(taxaJuros);
        System.out.println("Atualizado com sucesso!!!");
        System.out.println("");
    }

    private static void delete(Scanner sc, List<ContaPoupanca> contas) {
        read(contas);
        System.out.print("Selecione um número da conta para excluir: ");
        int id = sc.nextInt();
        sc.nextLine();
        contas.remove(id);
        System.out.println("Removido conta com sucesso!!!");
        System.out.println("");
    }

    private static void read(List<ContaPoupanca> contas) {
        System.out.println("");
        for (ContaPoupanca c : contas) {
            System.out.println("------------------------------");
            System.out.println("ID: " + contas.indexOf(c));
            System.out.println("Numero da conta: " + c.getNumeroConta());
            System.out.println("Saldo da conta: " + c.getSaldo());
            System.out.println("Taxa de Juros: " + c.getTaxaJuros());
            System.out.println("------------------------------");
        }
    }

    private static void create(Scanner sc, List<ContaPoupanca> contas) {
        System.out.println("");
        System.out.print("Digite o número da conta: ");
        Integer numeroConta = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o saldo inicial da conta: ");
        Double saldo = sc.nextDouble();
        sc.nextLine();
        System.out.print("Digite a taxa de juros (0 ~ 1): ");
        Double taxaJuros = sc.nextDouble();
        sc.nextLine();

        ContaPoupanca conta = new ContaPoupanca(numeroConta, saldo, taxaJuros);
        contas.add(conta);

        System.out.println("------------------ Criado Conta Poupança --------------------");
    }
}
