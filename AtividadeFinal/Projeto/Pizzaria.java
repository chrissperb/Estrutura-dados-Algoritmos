package Projeto;

import Projeto.Pizza.TamanhoPizza;

import java.math.BigDecimal;
import java.util.*;

public class Pizzaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();
        Set<Cliente> setClientes = new HashSet<>();

        boolean continuar = true;
        while (continuar) {
            System.out.println();
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Fazer um novo pedido");
            System.out.println("2 - Alterar um pedido");
            System.out.println("3 - Adicionar um cliente");
            System.out.println("4 - Gerar relatório de vendas");
            System.out.println("5 - Gerar lista de clientes");
            System.out.println("9 - Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos);
                    break;
                case 3:
                    Cliente novoCliente = adicionarCliente(scanner);
                    if (novoCliente == null) {
                        System.out.println("Cliente inválido. Não foi adicionado.");
                    } else if (setClientes.contains(novoCliente)) {
                        System.out.println("Cliente já cadastrado! Não é possível duplicar.");
                    } else {
                        listaClientes.add(novoCliente);
                        setClientes.add(novoCliente);
                        System.out.println("Cliente adicionado com sucesso!");
                    }
                    break;
                case 4:
                    gerarRelatorio();
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 9:
                    System.out.println("Obrigado por utilizar nosso sistema!");
                    System.out.println("Até logo!");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    break;
            }
        }

    }

    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        if (listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Por favor, cadastre um cliente antes de fazer um pedido.");
            Cliente novoCliente = adicionarCliente(scanner);
            if (novoCliente == null) {
                System.out.println("Cliente inválido. Pedido não pode ser realizado.");
                return;
            }
            listaClientes.add(novoCliente);
            System.out.println("Cliente cadastrado com sucesso! Agora você pode fazer o pedido.");
        }

        List<Pizza> pizzas = new ArrayList<>();
        System.out.println("FAZER PEDIDO");

        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.println(x + " - " + cliente.getNome());
            x++;
        }
        System.out.print("Opção: ");
        int cliente = scanner.nextInt();
        scanner.nextLine();

        boolean continuar = true;
        while (continuar) {
            x = 1;
            System.out.println("Qual o tamanho da pizza? ");
            System.out.println("Selecione um tamanho: ");
            for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
                System.out.println(x + " - " + tamanhos);
                x++;
            }
            System.out.print("Opção: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.println("Digite a quantidade de sabores: 1 - 4 ");
                System.out.print("Opção: ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            CardapioService cardapio = new CardapioService();
            List<String> saboresList = new ArrayList<>();
            List<String> saboresSelect = new ArrayList<>();

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione um sabor: ");

                x = 1;
                for (String sabor : cardapio.getCardapio().keySet()) {
                    saboresList.add(sabor);
                    System.out.println(x + " - " + sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                saboresSelect.add(saboresList.get(opcao - 1));
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), TamanhoPizza.getByIndex(tamanho - 1));
            pizzas.add(pizza);

            System.out.println("Pizza cadastrada com sucesso!");
            System.out.println();
            System.out.println("Deseja cadastrar mais uma pizza no pedido?");
            System.out.print("1 - Sim, 2 - Não: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao != 1) {
                continuar = false;
            }
        }
        Pedido pedido = new Pedido(listaPedidos.size() + 1, listaClientes.get(cliente - 1), pizzas, somarPizzas(pizzas));
        listaPedidos.add(pedido);
    }

    private static BigDecimal somarPizzas(List<Pizza> pizzas) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (Pizza pizza : pizzas) {
            valorTotal = valorTotal.add(pizza.getPreco());
        }
        return valorTotal;
    }

    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos) {
        if (listaPedidos.isEmpty()) {
            System.out.println("Não há pedidos para alterar.");
            return;
        }

        System.out.println("ALTERAR PEDIDO");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por nome do cliente");
        System.out.print("Opção: ");
        int opcaoBusca = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoSelecionado = null;

        switch (opcaoBusca) {
            case 1:
                System.out.print("Digite o ID do pedido: ");
                int idBusca = scanner.nextInt();
                scanner.nextLine();
                pedidoSelecionado = listaPedidos.stream()
                        .filter(p -> p.getId() == idBusca)
                        .findFirst()
                        .orElse(null);
                break;
            case 2:
                System.out.print("Digite o nome do cliente: ");
                String nomeBusca = scanner.nextLine();
                pedidoSelecionado = listaPedidos.stream()
                        .filter(p -> p.getCliente().getNome().equalsIgnoreCase(nomeBusca))
                        .findFirst()
                        .orElse(null);
                break;
        }

        if (pedidoSelecionado == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }

        boolean editando = true;
        while (editando) {
            System.out.println("\nPedido encontrado: " + pedidoSelecionado);
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Adicionar pizza");
            System.out.println("2 - Remover pizza");
            System.out.println("3 - Alterar sabores de uma pizza");
            System.out.println("4 - Finalizar edição");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarPizzaAoPedido(scanner, pedidoSelecionado);
                    break;
                case 2:
                    removerPizzaDoPedido(scanner, pedidoSelecionado);
                    break;
                case 3:
                    alterarSaboresPizza(scanner, pedidoSelecionado);
                    break;
                case 4:
                    editando = false;
                    break;
            }

            pedidoSelecionado.setValorTotal(somarPizzas(pedidoSelecionado.getPizzas()));
        }
    }

    private static void adicionarPizzaAoPedido(Scanner scanner, Pedido pedido) {
        Cardapio cardapio = new Cardapio();
        CardapioService cardapioService = new CardapioService();
        System.out.println("ADICIONAR NOVA PIZZA");

        System.out.println("Selecione o tamanho:");
        int x = 1;
        for (Pizza.TamanhoPizza tamanho : Pizza.TamanhoPizza.values()) {
            System.out.println(x + " - " + tamanho);
            x++;
        }
        System.out.print("Opção: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        List<String> sabores = new ArrayList<>();
        System.out.print("Quantos sabores (1-4): ");
        int qtdSabores = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < qtdSabores; i++) {
            System.out.println("\nSabores disponíveis:");
            String[] saboresDisponiveis = cardapio.getCardapio().keySet().toArray(String[]::new);
            for (int j = 0; j < saboresDisponiveis.length; j++) {
                System.out.println((j + 1) + " - " + saboresDisponiveis[j]);
            }
            System.out.print("Escolha o sabor " + (i + 1) + ": ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            sabores.add(saboresDisponiveis[escolha - 1]);
        }

        Pizza novaPizza = new Pizza(sabores, cardapioService.getPrecoJusto(sabores),
                Pizza.TamanhoPizza.getByIndex(tamanho - 1));
        pedido.getPizzas().add(novaPizza);
    }

    private static void removerPizzaDoPedido(Scanner scanner, Pedido pedido) {
        if (pedido.getPizzas().isEmpty()) {
            System.out.println("Não há pizzas para remover!");
            return;
        }

        System.out.println("\nPizzas do pedido:");
        for (int i = 0; i < pedido.getPizzas().size(); i++) {
            System.out.println((i + 1) + " - " + pedido.getPizzas().get(i));
        }

        System.out.print("Escolha a pizza para remover: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= pedido.getPizzas().size()) {
            pedido.getPizzas().remove(escolha - 1);
            System.out.println("Pizza removida com sucesso!");
        }
    }

    private static void alterarSaboresPizza(Scanner scanner, Pedido pedido) {
        Cardapio cardapio = new Cardapio();
        CardapioService cardapioService = new CardapioService();

        System.out.println("\nEscolha a pizza para alterar:");
        for (int i = 0; i < pedido.getPizzas().size(); i++) {
            System.out.println((i + 1) + " - " + pedido.getPizzas().get(i));
        }

        System.out.print("Opção: ");
        int escolhaPizza = scanner.nextInt();
        scanner.nextLine();

        if (escolhaPizza < 1 || escolhaPizza > pedido.getPizzas().size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Pizza pizzaParaAlterar = pedido.getPizzas().get(escolhaPizza - 1);
        List<String> novosSabores = new ArrayList<>();

        System.out.print("Quantos sabores (1-4): ");
        int qtdSabores = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < qtdSabores; i++) {
            System.out.println("\nSabores disponíveis:");
            String[] saboresDisponiveis = cardapio.getCardapio().keySet().toArray(new String[0]);
            for (int j = 0; j < saboresDisponiveis.length; j++) {
                System.out.println((j + 1) + " - " + saboresDisponiveis[j]);
            }
            System.out.print("Escolha o sabor " + (i + 1) + ": ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            novosSabores.add(saboresDisponiveis[escolha - 1]);
        }

        pizzaParaAlterar.setSabores(novosSabores);
        pizzaParaAlterar.setPreco(cardapioService.getPrecoJusto(novosSabores));
        System.out.println("Sabores alterados com sucesso!");
    }

    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("ADICIONAR CLIENTE");
        System.out.println();
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        System.out.println();

        if (nome == null || nome.isBlank() || telefone == null || telefone.isBlank()) {
            return null;
        }

        return new Cliente(nome, endereco, telefone, email);
    }

    private static void gerarRelatorio() {
        System.out.println("Gerar relatorio");
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        int x = 1;
        if (listaClientes.isEmpty()) {
            System.out.println("Lista de clientes esta vazia");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println("Cliente " + x);
                System.out.println(cliente.getNome());
                System.out.println(cliente.getEndereco());
                System.out.println(cliente.getTelefone());
                System.out.println(cliente.getEmail());
                System.out.println();
                x++;
            }
        }
    }
}
