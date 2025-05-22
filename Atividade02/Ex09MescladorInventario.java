/*
 * Sistema para Mesclar Inventários
 * Este programa mescla dois inventários de produtos, tratando conflitos de forma flexível.
 * O usuário pode escolher como resolver conflitos entre produtos com o mesmo código.
 *  
 * Autor: Christian Sperb
 * Data: 22/05/2025
 */

package Atividade02;

import java.util.*;

public class Ex09MescladorInventario {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, Produto> inventario1 = new HashMap<>();
    private static Map<String, Produto> inventario2 = new HashMap<>();
    
    static class Produto {
        private String nome;
        private int quantidade;
        private double preco;
        private String categoria;
        
        public Produto(String nome, int quantidade, double preco, String categoria) {
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
            this.categoria = categoria;
        }
        
        public String getNome() { return nome; }
        public int getQuantidade() { return quantidade; }
        public double getPreco() { return preco; }
        public String getCategoria() { return categoria; }
        
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
        public void setPreco(double preco) { this.preco = preco; }
        
        @Override
        public String toString() {
            return String.format("%-15s | Qtd: %3d | R$ %8.2f | %s", 
                               nome, quantidade, preco, categoria);
        }
    }
    
    public static void main(String[] args) {
        exibirCabecalho();
        inicializarInventariosPadrao();
        
        while (true) {
            int opcao = exibirMenuPrincipal();
            
            switch (opcao) {
                case 1 -> visualizarInventarios();
                case 2 -> adicionarProdutoInventario();
                case 3 -> mesclarInventarios();
                case 4 -> limparInventarios();
                case 5 -> {
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("Obrigado por usar o Mesclador de Inventários!");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private static void exibirCabecalho() {
        System.out.println("=".repeat(60));
        System.out.println("           MESCLADOR DE INVENTÁRIOS DE PRODUTOS");
        System.out.println("=".repeat(60));
        System.out.println("Sistema para mesclar dois inventários com tratamento de conflitos.");
        System.out.println("=".repeat(60));
    }
    
    private static int exibirMenuPrincipal() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("MENU PRINCIPAL");
        System.out.println("-".repeat(50));
        System.out.println("1. Visualizar Inventários");
        System.out.println("2. Adicionar Produto aos Inventários");
        System.out.println("3. Mesclar Inventários");
        System.out.println("4. Limpar Inventários");
        System.out.println("5. Sair");
        System.out.print("\nEscolha uma opção: ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void inicializarInventariosPadrao() {
        inventario1.put("LAPTOP001", new Produto("Laptop Dell", 5, 2500.00, "Eletrônicos"));
        inventario1.put("MOUSE001", new Produto("Mouse Logitech", 20, 45.00, "Periféricos"));
        inventario1.put("CADEIRA001", new Produto("Cadeira Gamer", 8, 800.00, "Móveis"));
        
        inventario2.put("LAPTOP001", new Produto("Laptop Dell Pro", 3, 2800.00, "Eletrônicos"));
        inventario2.put("TECLADO001", new Produto("Teclado Mecânico", 15, 150.00, "Periféricos"));
        inventario2.put("MONITOR001", new Produto("Monitor 24\"", 10, 450.00, "Eletrônicos"));
    }
    
    private static void visualizarInventarios() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INVENTÁRIOS ATUAIS");
        System.out.println("=".repeat(70));
        
        exibirInventario("INVENTÁRIO 1", inventario1);
        exibirInventario("INVENTÁRIO 2", inventario2);
    }
    
    private static void exibirInventario(String titulo, Map<String, Produto> inventario) {
        System.out.println("\n" + titulo + " (" + inventario.size() + " produtos):");
        System.out.println("-".repeat(70));
        
        if (inventario.isEmpty()) {
            System.out.println("Inventário vazio.");
            return;
        }
        
        System.out.printf("%-10s | %-40s%n", "Código", "Detalhes do Produto");
        System.out.println("-".repeat(70));
        
        inventario.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("%-10s | %s%n", 
                        entry.getKey(), entry.getValue()));
    }
    
    private static void adicionarProdutoInventario() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ADICIONAR PRODUTO");
        System.out.println("=".repeat(50));
        
        System.out.print("Qual inventário? (1 ou 2): ");
        String opcaoInventario = scanner.nextLine().trim();
        
        Map<String, Produto> inventarioEscolhido;
        if ("1".equals(opcaoInventario)) {
            inventarioEscolhido = inventario1;
        } else if ("2".equals(opcaoInventario)) {
            inventarioEscolhido = inventario2;
        } else {
            System.out.println("Opção inválida!");
            return;
        }
        
        try {
            System.out.print("Código do produto: ");
            String codigo = scanner.nextLine().trim().toUpperCase();
            
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Preço: R$ ");
            double preco = Double.parseDouble(scanner.nextLine().trim());
            
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();
            
            Produto produto = new Produto(nome, quantidade, preco, categoria);
            inventarioEscolhido.put(codigo, produto);
            
            System.out.println("\nProduto adicionado com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valores numéricos inválidos!");
        }
    }
    
    private static void mesclarInventarios() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MESCLAGEM DE INVENTÁRIOS");
        System.out.println("=".repeat(60));
        
        int opcaoMesclagem = obterOpcaoMesclagem();
        if (opcaoMesclagem == -1) return;
        
        Map<String, Produto> inventarioMesclado = executarMesclagem(opcaoMesclagem);
        exibirResultadoMesclagem(inventarioMesclado, opcaoMesclagem);
    }
    
    private static int obterOpcaoMesclagem() {
        System.out.println("Escolha a estratégia para conflitos de produtos:");
        System.out.println("1. Somar quantidades (manter menor preço)");
        System.out.println("2. Manter produto do Inventário 1");
        System.out.println("3. Manter produto do Inventário 2");
        System.out.println("4. Escolher manualmente para cada conflito");
        System.out.print("\nOpção: ");
        
        try {
            int opcao = Integer.parseInt(scanner.nextLine().trim());
            return (opcao >= 1 && opcao <= 4) ? opcao : -1;
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
            return -1;
        }
    }
    
    private static Map<String, Produto> executarMesclagem(int estrategia) {
        Map<String, Produto> resultado = new HashMap<>(inventario1);
        List<String> conflitos = new ArrayList<>();
        
        // Identificar conflitos
        for (String codigo : inventario2.keySet()) {
            if (resultado.containsKey(codigo)) {
                conflitos.add(codigo);
            }
        }
        
        // Processar produtos sem conflito
        for (Map.Entry<String, Produto> entry : inventario2.entrySet()) {
            if (!resultado.containsKey(entry.getKey())) {
                resultado.put(entry.getKey(), entry.getValue());
            }
        }
        
        // Tratar conflitos conforme estratégia
        if (!conflitos.isEmpty()) {
            System.out.println("\nConflitos encontrados: " + conflitos.size());
            tratarConflitos(resultado, conflitos, estrategia);
        }
        
        return resultado;
    }
    
    private static void tratarConflitos(Map<String, Produto> resultado, List<String> conflitos, int estrategia) {
        for (String codigo : conflitos) {
            Produto produto1 = inventario1.get(codigo);
            Produto produto2 = inventario2.get(codigo);
            
            switch (estrategia) {
                case 1 -> {
                    int novaQuantidade = produto1.getQuantidade() + produto2.getQuantidade();
                    double menorPreco = Math.min(produto1.getPreco(), produto2.getPreco());
                    Produto produtoMesclado = new Produto(produto1.getNome(), novaQuantidade, menorPreco, produto1.getCategoria());
                    resultado.put(codigo, produtoMesclado);
                }
                case 2 -> resultado.put(codigo, produto1);
                case 3 -> resultado.put(codigo, produto2);
                case 4 -> {
                    Produto produtoEscolhido = resolverConflitoManualmente(codigo, produto1, produto2);
                    resultado.put(codigo, produtoEscolhido);
                }
            }
        }
    }
    
    private static Produto resolverConflitoManualmente(String codigo, Produto produto1, Produto produto2) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("CONFLITO NO PRODUTO: " + codigo);
        System.out.println("-".repeat(50));
        System.out.println("1. Inventário 1: " + produto1);
        System.out.println("2. Inventário 2: " + produto2);
        System.out.print("Escolha qual manter (1 ou 2): ");
        
        String escolha = scanner.nextLine().trim();
        return "2".equals(escolha) ? produto2 : produto1;
    }
    
    private static void exibirResultadoMesclagem(Map<String, Produto> inventarioMesclado, int estrategia) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RESULTADO DA MESCLAGEM");
        System.out.println("=".repeat(70));
        System.out.println("Estratégia utilizada: " + obterNomeEstrategia(estrategia));
        System.out.println("Total de produtos no resultado: " + inventarioMesclado.size());
        
        exibirInventario("INVENTÁRIO MESCLADO", inventarioMesclado);
        
        double valorTotal = inventarioMesclado.values().stream()
                .mapToDouble(p -> p.getQuantidade() * p.getPreco())
                .sum();
        
        System.out.println("\n" + "-".repeat(50));
        System.out.printf("VALOR TOTAL DO INVENTÁRIO: R$ %.2f%n", valorTotal);
    }
    
    private static String obterNomeEstrategia(int estrategia) {
        return switch (estrategia) {
            case 1 -> "Somar quantidades (menor preço)";
            case 2 -> "Priorizar Inventário 1";
            case 3 -> "Priorizar Inventário 2";
            case 4 -> "Resolução manual";
            default -> "Desconhecida";
        };
    }
    
    private static void limparInventarios() {
        System.out.print("\nTem certeza que deseja limpar os inventários? (s/N): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();
        
        if ("s".equals(confirmacao) || "sim".equals(confirmacao)) {
            inventario1.clear();
            inventario2.clear();
            inicializarInventariosPadrao();
            System.out.println("Inventários reinicializados com dados padrão!");
        }
    }
}
