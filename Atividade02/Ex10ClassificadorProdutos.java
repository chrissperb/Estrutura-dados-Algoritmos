/*
 * Sistema para Classificar Produtos
 * Este programa classifica produtos com base em suas vendas, permitindo ao usuário
 * adicionar, editar e visualizar produtos. O usuário pode escolher entre diferentes
 * algoritmos de ordenação (Quick Sort, Merge Sort e Bubble Sort) para classificar os produtos.
 *  
 * Autor: Christian Sperb
 * Data: 22/05/2025
 */



package Atividade02;

import java.util.*;

public class Ex10ClassificadorProdutos {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Produto> produtos = new ArrayList<>();
    
    static class Produto {
        private String nome;
        private String categoria;
        private int vendas;
        private double preco;
        private double avaliacao;
        private String codigo;
        
        public Produto(String codigo, String nome, String categoria, int vendas, double preco, double avaliacao) {
            this.codigo = codigo;
            this.nome = nome;
            this.categoria = categoria;
            this.vendas = vendas;
            this.preco = preco;
            this.avaliacao = avaliacao;
        }
        
        public String getCodigo() { return codigo; }
        public String getNome() { return nome; }
        public String getCategoria() { return categoria; }
        public int getVendas() { return vendas; }
        public double getPreco() { return preco; }
        public double getAvaliacao() { return avaliacao; }
        
        public void setVendas(int vendas) { this.vendas = vendas; }
        
        @Override
        public String toString() {
            return String.format("%-12s | %-20s | %-12s | %5d vendas | R$ %8.2f | ★%.1f", 
                               codigo, nome, categoria, vendas, preco, avaliacao);
        }
    }
    
    public static void main(String[] args) {
        exibirCabecalho();
        inicializarProdutosPadrao();
        
        while (true) {
            int opcao = exibirMenuPrincipal();
            
            switch (opcao) {
                case 1 -> visualizarProdutos();
                case 2 -> adicionarProduto();
                case 3 -> editarVendasProduto();
                case 4 -> ordenarPorVendas();
                case 5 -> compararAlgoritmos();
                case 6 -> gerarDadosAleatorios();
                case 7 -> {
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("Obrigado por usar o Classificador de Produtos!");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private static void exibirCabecalho() {
        System.out.println("=".repeat(70));
        System.out.println("         CLASSIFICADOR DE PRODUTOS POR POPULARIDADE");
        System.out.println("=".repeat(70));
        System.out.println("Sistema para ordenar produtos por vendas usando diferentes algoritmos.");
        System.out.println("=".repeat(70));
    }
    
    private static int exibirMenuPrincipal() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("MENU PRINCIPAL");
        System.out.println("-".repeat(50));
        System.out.println("1. Visualizar Produtos");
        System.out.println("2. Adicionar Produto");
        System.out.println("3. Editar Vendas de Produto");
        System.out.println("4. Ordenar por Vendas");
        System.out.println("5. Comparar Algoritmos de Ordenação");
        System.out.println("6. Gerar Dados Aleatórios para Teste");
        System.out.println("7. Sair");
        System.out.print("\nEscolha uma opção: ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void inicializarProdutosPadrao() {
        produtos.add(new Produto("PHONE001", "iPhone 15", "Smartphones", 1250, 4999.00, 4.8));
        produtos.add(new Produto("LAPTOP01", "MacBook Air", "Notebooks", 890, 7999.00, 4.7));
        produtos.add(new Produto("WATCH01", "Apple Watch", "Wearables", 2100, 2499.00, 4.5));
        produtos.add(new Produto("TABLET01", "iPad Pro", "Tablets", 675, 6499.00, 4.6));
        produtos.add(new Produto("MOUSE01", "Magic Mouse", "Periféricos", 3200, 449.00, 4.2));
        produtos.add(new Produto("KEYB001", "Magic Keyboard", "Periféricos", 1800, 899.00, 4.4));
        produtos.add(new Produto("AIRPOD1", "AirPods Pro", "Audio", 2800, 1299.00, 4.9));
        produtos.add(new Produto("CABLE01", "Cabo Lightning", "Acessórios", 5400, 149.00, 4.1));
        produtos.add(new Produto("CASE001", "Case iPhone", "Acessórios", 4200, 199.00, 4.3));
        produtos.add(new Produto("CHARGR1", "Carregador USB-C", "Acessórios", 3800, 299.00, 4.0));
    }
    
    private static void visualizarProdutos() {
        System.out.println("\n" + "=".repeat(90));
        System.out.println("LISTA DE PRODUTOS (" + produtos.size() + " produtos)");
        System.out.println("=".repeat(90));
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        
        System.out.printf("%-12s | %-20s | %-12s | %-11s | %-10s | %s%n", 
                         "Código", "Nome", "Categoria", "Vendas", "Preço", "Avaliação");
        System.out.println("-".repeat(90));
        
        produtos.forEach(System.out::println);
    }
    
    private static void adicionarProduto() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ADICIONAR PRODUTO");
        System.out.println("=".repeat(50));
        
        try {
            System.out.print("Código do produto: ");
            String codigo = scanner.nextLine().trim().toUpperCase();
            
            if (produtos.stream().anyMatch(p -> p.getCodigo().equals(codigo))) {
                System.out.println("Erro: Código já existe!");
                return;
            }
            
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();
            
            System.out.print("Número de vendas: ");
            int vendas = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Preço: R$ ");
            double preco = Double.parseDouble(scanner.nextLine().trim());
            
            System.out.print("Avaliação (0.0 - 5.0): ");
            double avaliacao = Double.parseDouble(scanner.nextLine().trim());
            
            produtos.add(new Produto(codigo, nome, categoria, vendas, preco, avaliacao));
            System.out.println("\nProduto adicionado com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valores numéricos inválidos!");
        }
    }
    
    private static void editarVendasProduto() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EDITAR VENDAS DE PRODUTO");
        System.out.println("=".repeat(50));
        
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine().trim().toUpperCase();
        
        Optional<Produto> produtoOpt = produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst();
        
        if (produtoOpt.isEmpty()) {
            System.out.println("Produto não encontrado!");
            return;
        }
        
        Produto produto = produtoOpt.get();
        System.out.println("Produto atual: " + produto);
        
        try {
            System.out.print("Novo número de vendas: ");
            int novasVendas = Integer.parseInt(scanner.nextLine().trim());
            produto.setVendas(novasVendas);
            System.out.println("Vendas atualizadas com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Número inválido!");
        }
    }
    
    private static void ordenarPorVendas() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto para ordenar!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESCOLHA O ALGORITMO DE ORDENAÇÃO");
        System.out.println("=".repeat(60));
        System.out.println("1. Quick Sort (Recomendado)");
        System.out.println("2. Merge Sort");
        System.out.println("3. Bubble Sort");
        System.out.print("\nEscolha o algoritmo: ");
        
        try {
            int algoritmo = Integer.parseInt(scanner.nextLine().trim());
            List<Produto> produtosCopia = new ArrayList<>(produtos);
            
            long inicio = System.nanoTime();
            
            switch (algoritmo) {
                case 1 -> quickSort(produtosCopia, 0, produtosCopia.size() - 1);
                case 2 -> mergeSort(produtosCopia, 0, produtosCopia.size() - 1);
                case 3 -> bubbleSort(produtosCopia);
                default -> {
                    System.out.println("Algoritmo inválido!");
                    return;
                }
            }
            
            long fim = System.nanoTime();
            double tempoMs = (fim - inicio) / 1_000_000.0;
            
            exibirResultadoOrdenacao(produtosCopia, obterNomeAlgoritmo(algoritmo), tempoMs);
            
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
        }
    }
    
    // QUICK SORT - Algoritmo escolhido como principal
    private static void quickSort(List<Produto> lista, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionar(lista, inicio, fim);
            quickSort(lista, inicio, indicePivo - 1);
            quickSort(lista, indicePivo + 1, fim);
        }
    }
    
    private static int particionar(List<Produto> lista, int inicio, int fim) {
        int pivo = lista.get(fim).getVendas();
        int i = inicio - 1;
        
        for (int j = inicio; j < fim; j++) {
            // Ordenação decrescente (maior número de vendas primeiro)
            if (lista.get(j).getVendas() >= pivo) {
                i++;
                trocar(lista, i, j);
            }
        }
        
        trocar(lista, i + 1, fim);
        return i + 1;
    }
    
    // MERGE SORT
    private static void mergeSort(List<Produto> lista, int inicio, int fim) {
        if (inicio < fim) {
            int meio = inicio + (fim - inicio) / 2;
            mergeSort(lista, inicio, meio);
            mergeSort(lista, meio + 1, fim);
            mesclar(lista, inicio, meio, fim);
        }
    }
    
    private static void mesclar(List<Produto> lista, int inicio, int meio, int fim) {
        List<Produto> temp = new ArrayList<>();
        int i = inicio, j = meio + 1;
        
        while (i <= meio && j <= fim) {
            if (lista.get(i).getVendas() >= lista.get(j).getVendas()) {
                temp.add(lista.get(i++));
            } else {
                temp.add(lista.get(j++));
            }
        }
        
        while (i <= meio) temp.add(lista.get(i++));
        while (j <= fim) temp.add(lista.get(j++));
        
        for (int k = 0; k < temp.size(); k++) {
            lista.set(inicio + k, temp.get(k));
        }
    }
    
    // BUBBLE SORT
    private static void bubbleSort(List<Produto> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            boolean houveTroca = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j).getVendas() < lista.get(j + 1).getVendas()) {
                    trocar(lista, j, j + 1);
                    houveTroca = true;
                }
            }
            if (!houveTroca) break; // Otimização: se não houve troca, já está ordenado
        }
    }
    
    private static void trocar(List<Produto> lista, int i, int j) {
        Produto temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
    
    private static void exibirResultadoOrdenacao(List<Produto> produtosOrdenados, String algoritmo, double tempo) {
        System.out.println("\n" + "=".repeat(90));
        System.out.println("PRODUTOS ORDENADOS POR VENDAS (DECRESCENTE)");
        System.out.println("=".repeat(90));
        System.out.printf("Algoritmo utilizado: %s | Tempo de execução: %.3f ms%n", algoritmo, tempo);
        System.out.println("-".repeat(90));
        
        System.out.printf("%-4s | %-12s | %-20s | %-12s | %-11s | %-10s | %s%n", 
                         "Pos", "Código", "Nome", "Categoria", "Vendas", "Preço", "Avaliação");
        System.out.println("-".repeat(90));
        
        for (int i = 0; i < produtosOrdenados.size(); i++) {
            System.out.printf("%-4d | %s%n", i + 1, produtosOrdenados.get(i));
        }
        
        if (!produtosOrdenados.isEmpty()) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("🏆 PRODUTO MAIS VENDIDO:");
            System.out.println(produtosOrdenados.get(0));
        }
    }
    
    private static void compararAlgoritmos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto para comparar!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARAÇÃO DE ALGORITMOS DE ORDENAÇÃO");
        System.out.println("=".repeat(60));
        
        System.out.printf("%-15s | %-15s | %-15s%n", "Algoritmo", "Tempo (ms)", "Complexidade");
        System.out.println("-".repeat(50));
        
        // Testar cada algoritmo
        String[] algoritmos = {"Quick Sort", "Merge Sort", "Bubble Sort"};
        double[] tempos = new double[3];
        
        for (int i = 0; i < 3; i++) {
            List<Produto> copia = new ArrayList<>(produtos);
            long inicio = System.nanoTime();
            
            switch (i) {
                case 0 -> quickSort(copia, 0, copia.size() - 1);
                case 1 -> mergeSort(copia, 0, copia.size() - 1);
                case 2 -> bubbleSort(copia);
            }
            
            long fim = System.nanoTime();
            tempos[i] = (fim - inicio) / 1_000_000.0;
        }
        
        System.out.printf("%-15s | %12.3f | O(n log n) médio%n", "Quick Sort", tempos[0]);
        System.out.printf("%-15s | %12.3f | O(n log n) sempre%n", "Merge Sort", tempos[1]);
        System.out.printf("%-15s | %12.3f | O(n²) sempre%n", "Bubble Sort", tempos[2]);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("💡 RECOMENDAÇÃO: Quick Sort é geralmente o mais eficiente");
        System.out.println("   para a maioria dos casos práticos em sistemas de e-commerce.");
    }
    
    private static void gerarDadosAleatorios() {
        System.out.print("\nQuantos produtos aleatórios gerar? ");
        try {
            int quantidade = Integer.parseInt(scanner.nextLine().trim());
            
            if (quantidade <= 0 || quantidade > 10000) {
                System.out.println("Quantidade deve ser entre 1 e 10.000!");
                return;
            }
            
            produtos.clear();
            Random random = new Random();
            String[] nomes = {"Smartphone", "Laptop", "Tablet", "Smartwatch", "Fone", "Mouse", "Teclado", "Monitor"};
            String[] categorias = {"Eletrônicos", "Informática", "Audio", "Acessórios"};
            
            for (int i = 1; i <= quantidade; i++) {
                String codigo = String.format("PROD%04d", i);
                String nome = nomes[random.nextInt(nomes.length)] + " " + i;
                String categoria = categorias[random.nextInt(categorias.length)];
                int vendas = random.nextInt(10000);
                double preco = 50 + random.nextDouble() * 5000;
                double avaliacao = 3.0 + random.nextDouble() * 2.0;
                
                produtos.add(new Produto(codigo, nome, categoria, vendas, preco, avaliacao));
            }
            
            System.out.printf("%d produtos aleatórios gerados com sucesso!%n", quantidade);
            
        } catch (NumberFormatException e) {
            System.out.println("Número inválido!");
        }
    }
    
    private static String obterNomeAlgoritmo(int algoritmo) {
        return switch (algoritmo) {
            case 1 -> "Quick Sort";
            case 2 -> "Merge Sort";
            case 3 -> "Bubble Sort";
            default -> "Desconhecido";
        };
    }
}
