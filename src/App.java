import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import db.DB;
import db.DbException;
import entities.ContaPoupanca;
import exceptions.ContaException;

/**
 * CRUD bancário com conexão com Banco de Dados!
 */
public final class App {
    private App() {
    }

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<ContaPoupanca> contas = new ArrayList<>();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.gConnection();
            st = conn.createStatement();
            int opcao;

            createTable(st);

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
                        create(sc, contas, st);
                        break;

                    case 2:
                        read(contas, st, rs);
                        break;

                    case 3:
                        update(sc, contas, st, rs);
                        break;

                    case 4:
                        delete(sc, contas, st, rs);
                        break;

                    case 5:
                        depositarValor(sc, contas, st, rs);
                        break;

                    case 6:
                        sacarValor(sc, contas, st, rs);
                        break;

                    case 0:
                        System.out.println("Saindo do Sistema....");
                        break;

                    default:
                        System.out.print("Escolha uma opção válida");
                        break;
                }
            } while (opcao != 0);

        } catch (Exception e) {
            throw new ContaException(e.getMessage());
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
            sc.close();
        }
    }

    private static void sacarValor(Scanner sc, List<ContaPoupanca> contas, Statement st, ResultSet rs)
            throws SQLException {
        read(contas, st, rs);
        System.out.print("Selecione um número da conta para fazer o saque: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o valor para sacar: ");
        Double valor = sc.nextDouble();
        sc.nextLine();
        String query = "";

        boolean find = false;

        for (ContaPoupanca c : contas) {
            if (c.getNumeroConta() == id) {
                find = true;
                query = "UPDATE contas SET saldo = "
                        + c.sacar(valor)
                        + " WHERE numero = "
                        + id;
            }
        }
        if (find == true) {
            st.executeUpdate(query);
            System.out.println("Sacado com sucesso!!!");
        } else {
            System.out.println("Conta não localizada");
        }
        System.out.println("");
    }

    private static void depositarValor(Scanner sc, List<ContaPoupanca> contas, Statement st, ResultSet rs)
            throws SQLException {
        read(contas, st, rs);
        System.out.print("Selecione um número da conta para depositar: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o valor para depositar: ");
        Double valor = sc.nextDouble();
        sc.nextLine();
        String query = "";

        boolean find = false;

        for (ContaPoupanca c : contas) {
            if (c.getNumeroConta() == id) {
                find = true;
                query = "UPDATE contas SET saldo = "
                        + c.depositar(valor)
                        + " WHERE numero = "
                        + id;
            }
        }

        if (find == true) {
            st.executeUpdate(query);
            System.out.println("Depositado com sucesso!!!");
        } else {
            System.out.println("Conta não localizada");
        }
        System.out.println("");
    }

    private static void update(Scanner sc, List<ContaPoupanca> contas, Statement st, ResultSet rs) throws SQLException {
        read(contas, st, rs);
        System.out.print("Selecione um número da conta para atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite a nova taxa de Juros: ");
        Double taxaJuros = sc.nextDouble();
        sc.nextLine();

        String query = "UPDATE contas SET taxa_juros = " + taxaJuros + " WHERE numero =" + id;
        st.executeUpdate(query);

        System.out.println("Atualizado com sucesso!!!");
        System.out.println("");
    }

    private static void delete(Scanner sc, List<ContaPoupanca> contas, Statement st, ResultSet rs) throws SQLException {
        read(contas, st, rs);
        System.out.print("Selecione um número da conta para excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "DELETE FROM contas WHERE numero = " + id;
        st.executeUpdate(query);

        System.out.println("Removido conta com sucesso!!!");
        System.out.println("");
    }

    private static void read(List<ContaPoupanca> contas, Statement st, ResultSet rs) throws SQLException {
        contas.clear();
        System.out.println("");

        rs = st.executeQuery("SELECT * FROM contas");
        while (rs.next()) {
            System.out.println("------------------------------");
            System.out.println("Numero da conta: " + rs.getInt("numero"));
            System.out.println(
                    "Saldo da conta: " +
                            String.format("%.2f", rs.getDouble("saldo")));
            System.out.println(
                    "Taxa de Juros: " +
                            String.format("%.2f", rs.getDouble("taxa_juros")));
            System.out.println("------------------------------");

            ContaPoupanca c = new ContaPoupanca(
                    rs.getInt("numero"),
                    rs.getDouble("saldo"),
                    rs.getDouble("taxa_juros"));

            contas.add(c);

        }

    }

    private static void create(Scanner sc, List<ContaPoupanca> contas, Statement st) throws SQLException {
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

        // PARTE DO DB:
        String query = "INSERT INTO contas (numero, saldo, taxa_juros) VALUES("
                + conta.getNumeroConta()
                + ", " + conta.getSaldo()
                + ", " + conta.getTaxaJuros()
                + ");";
        st.executeUpdate(query);

        System.out.println("------------------ Criado Conta Poupança --------------------");
    }

    private static void createTable(Statement st) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS contas (" +
                "numero INTEGER PRIMARY KEY, saldo DOUBLE NOT NULL, taxa_juros DOUBLE NOT NULL);";
        st.execute(query);
    }
}
