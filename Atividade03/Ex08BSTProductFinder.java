/*
 * Sistema de Localização de Produtos com Melhor Oferta
 * Este sistema utiliza uma árvore binária de busca (BST) para armazenar produtos
 * e permite ao usuário encontrar a melhor oferta, o produto mais caro, buscar por preço
 * específico, exibir catálogo completo e estatísticas, além de gerar dados aleatórios.
 *  
 * Autor: Christian Sperb
 * Data: 29/05/2025
 */


package Atividade03;

import java.util.*;
import java.util.stream.Collectors;

public class Ex08BSTProductFinder {
    
    private static class Produto {
        String nome;
        double preco;
        String categoria;
        
        public Produto(String nome, double preco, String categoria) {
            this.nome = nome;
            this.preco = preco;
            this.categoria = categoria;
        }
        
        @Override
        public String toString() {
            return String.format("%s (%.2f) - %s", nome, preco, categoria);
        }
    }
    
    private static class NoArvore {
        Produto produto;
        NoArvore esquerda;
        NoArvore direita;
        
        public NoArvore(Produto produto) {
            this.produto = produto;
            this.esquerda = null;
            this.direita = null;
        }
    }
    
    private NoArvore raiz;
    private final Scanner scanner;
    private final Random random;
    
    public Ex08BSTProductFinder() {
        this.raiz = null;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }
    
    public static void main(String[] args) {
        Ex08BSTProductFinder sistema = new Ex08BSTProductFinder();
        sistema.executar();
    }
    
    public void executar() {
        boolean continuar = true;
        
        while (continuar) {
            limparTela();
            exibirMenu();
            
            int opcao = obterOpcaoMenu();
            
            switch (opcao) {
                case 1 -> adicionarProduto();
                case 2 -> encontrarMelhorOferta();
                case 3 -> encontrarPiorOferta();
                case 4 -> buscarProdutoPorPreco();
                case 5 -> exibirCatalogo();
                case 6 -> exibirEstatisticas();
                case 7 -> gerarDadosAleatorios();
                case 8 -> {
                    System.out.println("\n🌟 Obrigado por usar o BST Product Finder! Até logo!");
                    continuar = false;
                }
                default -> System.out.println("\n❌ Opção inválida! Pressione Enter para continuar...");
            }
            
            if (continuar && opcao != 8) {
                System.out.print("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private void limparTela() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // Fallback: imprimir linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private void exibirMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        💰 BST PRODUCT FINDER         ║");
        System.out.println("║     Localizador de Melhores Ofertas  ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. ➕ Adicionar Produto             ║");
        System.out.println("║  2. 🏆 Encontrar Melhor Oferta       ║");
        System.out.println("║  3. 💸 Encontrar Produto Mais Caro   ║");
        System.out.println("║  4. 🔍 Buscar por Preço Específico   ║");
        System.out.println("║  5. 📋 Exibir Catálogo Completo      ║");
        System.out.println("║  6. 📊 Exibir Estatísticas           ║");
        System.out.println("║  7. 🎲 Gerar Dados Aleatórios        ║");
        System.out.println("║  8. 🚪 Sair                          ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("\n🎯 Escolha uma opção: ");
    }
    
    private int obterOpcaoMenu() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine().trim());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void adicionarProduto() {
        System.out.println("\n➕ ADICIONAR NOVO PRODUTO");
        System.out.println("─────────────────────────────");
        
        System.out.print("📦 Nome do produto: ");
        String nome = scanner.nextLine().trim();
        
        if (nome.isEmpty()) {
            System.out.println("\n❌ Erro: Nome do produto não pode estar vazio!");
            return;
        }
        
        System.out.print("💰 Preço (R$): ");
        double preco;
        try {
            preco = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            if (preco <= 0) {
                System.out.println("\n❌ Erro: Preço deve ser maior que zero!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Erro: Preço inválido!");
            return;
        }
        
        System.out.print("🏷️  Categoria: ");
        String categoria = scanner.nextLine().trim();
        
        if (categoria.isEmpty()) {
            categoria = "Geral";
        }
        
        Produto novoProduto = new Produto(nome, preco, categoria);
        
        if (buscarProdutoPorPrecoExato(preco) != null) {
            System.out.printf("\n⚠️  Já existe um produto com preço R$ %.2f%n", preco);
            System.out.print("Deseja substituir? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s") && !resposta.equals("sim")) {
                System.out.println("❌ Produto não adicionado.");
                return;
            }
        }
        
        raiz = inserirProduto(raiz, novoProduto);
        System.out.printf("\n✅ Produto adicionado: %s%n", novoProduto);
    }
    
    private NoArvore inserirProduto(NoArvore no, Produto produto) {
        if (no == null) {
            return new NoArvore(produto);
        }
        
        if (produto.preco < no.produto.preco) {
            no.esquerda = inserirProduto(no.esquerda, produto);
        } else if (produto.preco > no.produto.preco) {
            no.direita = inserirProduto(no.direita, produto);
        } else {
            // Preço igual - substituir produto
            no.produto = produto;
        }
        
        return no;
    }
    
    private void encontrarMelhorOferta() {
        System.out.println("\n🏆 ENCONTRAR MELHOR OFERTA");
        System.out.println("──────────────────────────────");
        
        if (raiz == null) {
            System.out.println("❌ Nenhum produto cadastrado! Adicione produtos primeiro.");
            return;
        }
        
        Produto melhorOferta = encontrarMinimo(raiz);
        
        System.out.println("✅ Melhor oferta encontrada!");
        System.out.println("🎯 PRODUTO MAIS BARATO:");
        System.out.printf("   📦 %s%n", melhorOferta.nome);
        System.out.printf("   💰 R$ %.2f%n", melhorOferta.preco);
        System.out.printf("   🏷️  %s%n", melhorOferta.categoria);
        
        // Mostrar economia comparada com outros produtos
        List<Produto> todosProdutos = new ArrayList<>();
        coletarTodosProdutos(raiz, todosProdutos);
        
        if (todosProdutos.size() > 1) {
            double precoMedio = calcularPrecoMedio(todosProdutos);
            double economia = precoMedio - melhorOferta.preco;
            
            System.out.printf("\n💡 ECONOMIA POTENCIAL:%n");
            System.out.printf("   📊 Preço médio dos produtos: R$ %.2f%n", precoMedio);
            System.out.printf("   💵 Economia: R$ %.2f (%.1f%%)%n", 
                economia, (economia / precoMedio) * 100);
        }
    }
    
    private Produto encontrarMinimo(NoArvore no) {
        if (no == null) {
            return null;
        }
        
        // Em uma BST, o menor valor está sempre à esquerda
        while (no.esquerda != null) {
            no = no.esquerda;
        }
        
        return no.produto;
    }
    
    private void encontrarPiorOferta() {
        System.out.println("\n💸 ENCONTRAR PRODUTO MAIS CARO");
        System.out.println("──────────────────────────────────");
        
        if (raiz == null) {
            System.out.println("❌ Nenhum produto cadastrado! Adicione produtos primeiro.");
            return;
        }
        
        Produto produtoMaisCaro = encontrarMaximo(raiz);
        
        System.out.println("⚠️  Produto mais caro encontrado:");
        System.out.printf("   📦 %s%n", produtoMaisCaro.nome);
        System.out.printf("   💰 R$ %.2f%n", produtoMaisCaro.preco);
        System.out.printf("   🏷️  %s%n", produtoMaisCaro.categoria);
        
        // Comparar com a melhor oferta
        Produto melhorOferta = encontrarMinimo(raiz);
        double diferenca = produtoMaisCaro.preco - melhorOferta.preco;
        
        System.out.printf("\n📊 COMPARAÇÃO:%n");
        System.out.printf("   🏆 Produto mais barato: R$ %.2f%n", melhorOferta.preco);
        System.out.printf("   💸 Diferença de preço: R$ %.2f%n", diferenca);
    }
    
    private Produto encontrarMaximo(NoArvore no) {
        if (no == null) {
            return null;
        }
        
        // Em uma BST, o maior valor está sempre à direita
        while (no.direita != null) {
            no = no.direita;
        }
        
        return no.produto;
    }
    
    private void buscarProdutoPorPreco() {
        System.out.println("\n🔍 BUSCAR POR PREÇO ESPECÍFICO");
        System.out.println("──────────────────────────────────");
        
        if (raiz == null) {
            System.out.println("❌ Nenhum produto cadastrado! Adicione produtos primeiro.");
            return;
        }
        
        System.out.print("💰 Digite o preço desejado (R$): ");
        double precoDesejado;
        
        try {
            precoDesejado = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("❌ Preço inválido!");
            return;
        }
        
        Produto produtoEncontrado = buscarProdutoPorPrecoExato(precoDesejado);
        
        if (produtoEncontrado != null) {
            System.out.println("✅ Produto encontrado:");
            System.out.printf("   📦 %s%n", produtoEncontrado.nome);
            System.out.printf("   💰 R$ %.2f%n", produtoEncontrado.preco);
            System.out.printf("   🏷️  %s%n", produtoEncontrado.categoria);
        } else {
            System.out.printf("❌ Nenhum produto encontrado com preço R$ %.2f%n", precoDesejado);
            
            // Sugerir produtos próximos
            List<Produto> produtosProximos = encontrarProdutosProximos(precoDesejado, 10.0);
            if (!produtosProximos.isEmpty()) {
                System.out.println("\n💡 PRODUTOS COM PREÇOS SIMILARES:");
                produtosProximos.forEach(p -> 
                    System.out.printf("   • %s - R$ %.2f%n", p.nome, p.preco));
            }
        }
    }
    
    private Produto buscarProdutoPorPrecoExato(double preco) {
        return buscarProdutoRecursivo(raiz, preco);
    }
    
    private Produto buscarProdutoRecursivo(NoArvore no, double preco) {
        if (no == null) {
            return null;
        }
        
        if (Math.abs(no.produto.preco - preco) < 0.01) { // Comparação com tolerância
            return no.produto;
        }
        
        if (preco < no.produto.preco) {
            return buscarProdutoRecursivo(no.esquerda, preco);
        } else {
            return buscarProdutoRecursivo(no.direita, preco);
        }
    }
    
    private List<Produto> encontrarProdutosProximos(double precoReferencia, double tolerancia) {
        List<Produto> todosProdutos = new ArrayList<>();
        coletarTodosProdutos(raiz, todosProdutos);
        
        return todosProdutos.stream()
                .filter(p -> Math.abs(p.preco - precoReferencia) <= tolerancia)
                .sorted(Comparator.comparing(p -> Math.abs(p.preco - precoReferencia)))
                .limit(5)
                .toList();
    }
    
    private void exibirCatalogo() {
        System.out.println("\n📋 CATÁLOGO COMPLETO DE PRODUTOS");
        System.out.println("─────────────────────────────────────");
        
        if (raiz == null) {
            System.out.println("❌ Nenhum produto cadastrado ainda.");
            return;
        }
        
        List<Produto> produtosOrdenados = new ArrayList<>();
        coletarTodosProdutos(raiz, produtosOrdenados);
        
        System.out.printf("📊 Total de produtos: %d%n", produtosOrdenados.size());
        System.out.println("\n💰 PRODUTOS ORDENADOS POR PREÇO:");
        
        for (int i = 0; i < produtosOrdenados.size(); i++) {
            Produto p = produtosOrdenados.get(i);
            String icone = i == 0 ? "🏆" : i == produtosOrdenados.size() - 1 ? "💸" : "📦";
            System.out.printf("   %s %s%n", icone, p);
        }
    }
    
    private void coletarTodosProdutos(NoArvore no, List<Produto> produtos) {
        if (no != null) {
            coletarTodosProdutos(no.esquerda, produtos);
            produtos.add(no.produto);
            coletarTodosProdutos(no.direita, produtos);
        }
    }
    
    private void exibirEstatisticas() {
        System.out.println("\n📊 ESTATÍSTICAS DO CATÁLOGO");
        System.out.println("───────────────────────────────");
        
        if (raiz == null) {
            System.out.println("❌ Nenhum produto cadastrado ainda.");
            return;
        }
        
        List<Produto> todosProdutos = new ArrayList<>();
        coletarTodosProdutos(raiz, todosProdutos);
        
        Produto maisBarato = encontrarMinimo(raiz);
        Produto maisCaro = encontrarMaximo(raiz);
        double precoMedio = calcularPrecoMedio(todosProdutos);
        
        // Agrupar por categoria
        Map<String, Long> produtosPorCategoria = todosProdutos.stream()
                .collect(Collectors.groupingBy(p -> p.categoria, Collectors.counting()));
        
        System.out.printf("📈 RESUMO GERAL:%n");
        System.out.printf("   • Total de produtos: %d%n", todosProdutos.size());
        System.out.printf("   • Produto mais barato: %s (R$ %.2f)%n", maisBarato.nome, maisBarato.preco);
        System.out.printf("   • Produto mais caro: %s (R$ %.2f)%n", maisCaro.nome, maisCaro.preco);
        System.out.printf("   • Preço médio: R$ %.2f%n", precoMedio);
        System.out.printf("   • Variação de preços: R$ %.2f%n", maisCaro.preco - maisBarato.preco);
        
        System.out.printf("%n🏷️  PRODUTOS POR CATEGORIA:%n");
        produtosPorCategoria.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("   • %s: %d produtos%n", entry.getKey(), entry.getValue()));
    }
    
    private double calcularPrecoMedio(List<Produto> produtos) {
        return produtos.stream()
                .mapToDouble(p -> p.preco)
                .average()
                .orElse(0.0);
    }
    
    private void gerarDadosAleatorios() {
        System.out.println("\n🎲 GERAR DADOS ALEATÓRIOS");
        System.out.println("─────────────────────────");
        
        String[][] produtosBase = {
            {"Smartphone Samsung", "Eletrônicos"},
            {"Notebook Dell", "Eletrônicos"},
            {"Fone Bluetooth", "Eletrônicos"},
            {"Camiseta Nike", "Roupas"},
            {"Tênis Adidas", "Calçados"},
            {"Livro Java", "Livros"},
            {"Mouse Gamer", "Eletrônicos"},
            {"Cafeteira Elétrica", "Eletrodomésticos"},
            {"Fritadeira Air Fryer", "Eletrodomésticos"},
            {"Chocolate Nestlé", "Alimentos"},
            {"Shampoo L'Oréal", "Beleza"},
            {"Perfume Natura", "Beleza"},
            {"Mochila Jansport", "Acessórios"},
            {"Relógio Casio", "Acessórios"},
            {"Panela Antiaderente", "Casa"},
            {"Jogo de Cama", "Casa"},
            {"Bicicleta Caloi", "Esportes"},
            {"Whey Protein", "Suplementos"},
            {"Guitarra Yamaha", "Instrumentos"},
            {"Câmera Canon", "Eletrônicos"}
        };
        
        raiz = null; // Limpar árvore existente
        
        for (String[] item : produtosBase) {
            String nome = item[0];
            String categoria = item[1];
            
            // Gerar preço baseado na categoria
            double precoBase = switch (categoria) {
                case "Eletrônicos" -> 200 + random.nextDouble() * 2000;
                case "Eletrodomésticos" -> 150 + random.nextDouble() * 800;
                case "Roupas", "Calçados" -> 50 + random.nextDouble() * 300;
                case "Casa" -> 30 + random.nextDouble() * 200;
                case "Beleza" -> 15 + random.nextDouble() * 100;
                case "Livros" -> 20 + random.nextDouble() * 80;
                default -> 10 + random.nextDouble() * 500;
            };
            
            // Arredondar para 2 casas decimais
            double preco = Math.round(precoBase * 100.0) / 100.0;
            
            Produto produto = new Produto(nome, preco, categoria);
            raiz = inserirProduto(raiz, produto);
        }
        
        System.out.println("✅ Dados aleatórios gerados com sucesso!");
        System.out.printf("📊 %d produtos adicionados ao catálogo%n", produtosBase.length);
        System.out.println("\n💡 Experimente encontrar a melhor oferta!");
        System.out.println("🏆 BST garante busca eficiente O(log n)!");
    }
}
