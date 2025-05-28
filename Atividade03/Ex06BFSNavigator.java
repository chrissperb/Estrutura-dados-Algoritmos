/*
 * Sistema de Navegação por Rotas com BFS
 * Este programa implementa um sistema de navegação por rotas utilizando o algoritmo de busca em largura (BFS). 
 *  
 * Autor: Christian Sperb
 * Data: 28/05/2025
 */



package Atividade03;

import java.util.*;

public class Ex06BFSNavigator {
    private final Map<String, List<String>> grafo;
    private final Scanner scanner;
    private final Random random;
    
    public Ex06BFSNavigator() {
        this.grafo = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }
    
    public static void main(String[] args) {
        Ex06BFSNavigator navegador = new Ex06BFSNavigator();
        navegador.executar();
    }
    
    public void executar() {
        boolean continuar = true;
        
        while (continuar) {
            limparTela();
            exibirMenu();
            
            int opcao = obterOpcaoMenu();
            
            switch (opcao) {
                case 1 -> adicionarRota();
                case 2 -> buscarCaminho();
                case 3 -> exibirGrafo();
                case 4 -> gerarDadosAleatorios();
                case 5 -> {
                    System.out.println("\n🌟 Obrigado por usar o BFS Navigator! Até logo!");
                    continuar = false;
                }
                default -> System.out.println("\n❌ Opção inválida! Pressione Enter para continuar...");
            }
            
            if (continuar && opcao != 5) {
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
        System.out.println("║          🗺️  BFS NAVIGATOR            ║");
        System.out.println("║     Sistema de Navegação por Rotas   ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. ➕ Adicionar Rota                ║");
        System.out.println("║  2. 🔍 Buscar Caminho Mais Curto     ║");
        System.out.println("║  3. 📋 Exibir Mapa de Rotas          ║");
        System.out.println("║  4. 🎲 Gerar Dados Aleatórios        ║");
        System.out.println("║  5. 🚪 Sair                          ║");
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
    
    private void adicionarRota() {
        System.out.println("\n🛣️  ADICIONAR NOVA ROTA");
        System.out.println("─────────────────────────");
        
        System.out.print("📍 Local de origem: ");
        String origem = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("📍 Local de destino: ");
        String destino = scanner.nextLine().trim().toUpperCase();
        
        if (origem.isEmpty() || destino.isEmpty()) {
            System.out.println("\n❌ Erro: Origem e destino não podem estar vazios!");
            return;
        }
        
        if (origem.equals(destino)) {
            System.out.println("\n❌ Erro: Origem e destino devem ser diferentes!");
            return;
        }
        
        adicionarConexao(origem, destino);
        adicionarConexao(destino, origem); // Grafo não direcionado
        
        System.out.printf("\n✅ Rota adicionada: %s ↔ %s%n", origem, destino);
    }
    
    private void adicionarConexao(String origem, String destino) {
        grafo.computeIfAbsent(origem, k -> new ArrayList<>()).add(destino);
    }
    
    private void buscarCaminho() {
        if (grafo.isEmpty()) {
            System.out.println("\n❌ Nenhuma rota cadastrada! Adicione rotas primeiro.");
            return;
        }
        
        System.out.println("\n🔍 BUSCAR CAMINHO MAIS CURTO");
        System.out.println("────────────────────────────");
        
        System.out.print("📍 Ponto de partida: ");
        String origem = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("📍 Destino desejado: ");
        String destino = scanner.nextLine().trim().toUpperCase();
        
        if (!grafo.containsKey(origem)) {
            System.out.printf("\n❌ Local '%s' não encontrado no mapa!%n", origem);
            return;
        }
        
        if (!grafo.containsKey(destino)) {
            System.out.printf("\n❌ Local '%s' não encontrado no mapa!%n", destino);
            return;
        }
        
        List<String> caminho = encontrarCaminhoMaisCurto(origem, destino);
        exibirResultadoBusca(origem, destino, caminho);
    }
    
    private List<String> encontrarCaminhoMaisCurto(String origem, String destino) {
        if (origem.equals(destino)) {
            return Arrays.asList(origem);
        }
        
        Queue<String> fila = new LinkedList<>();
        Map<String, String> antecessor = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        
        fila.offer(origem);
        visitados.add(origem);
        antecessor.put(origem, null);
        
        while (!fila.isEmpty()) {
            String atual = fila.poll();
            
            if (atual.equals(destino)) {
                return reconstruirCaminho(antecessor, destino);
            }
            
            List<String> vizinhos = grafo.getOrDefault(atual, new ArrayList<>());
            for (String vizinho : vizinhos) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    antecessor.put(vizinho, atual);
                    fila.offer(vizinho);
                }
            }
        }
        
        return new ArrayList<>(); // Caminho não encontrado
    }
    
    private List<String> reconstruirCaminho(Map<String, String> antecessor, String destino) {
        List<String> caminho = new ArrayList<>();
        String atual = destino;
        
        while (atual != null) {
            caminho.add(atual);
            atual = antecessor.get(atual);
        }
        
        Collections.reverse(caminho);
        return caminho;
    }
    
    private void exibirResultadoBusca(String origem, String destino, List<String> caminho) {
        System.out.println("\n🎯 RESULTADO DA BUSCA");
        System.out.println("─────────────────────");
        
        if (caminho.isEmpty()) {
            System.out.printf("❌ Não há caminho disponível entre %s e %s%n", origem, destino);
        } else {
            System.out.printf("✅ Caminho mais curto encontrado!%n");
            System.out.printf("📏 Distância: %d paradas%n", caminho.size() - 1);
            System.out.printf("🛣️  Rota: %s%n", String.join(" → ", caminho));
            
            if (caminho.size() > 2) {
                System.out.print("📍 Paradas intermediárias: ");
                List<String> intermediarias = caminho.subList(1, caminho.size() - 1);
                System.out.println(String.join(", ", intermediarias));
            }
        }
    }
    
    private void exibirGrafo() {
        System.out.println("\n🗺️  MAPA DE ROTAS ATUAL");
        System.out.println("─────────────────────────");
        
        if (grafo.isEmpty()) {
            System.out.println("❌ Nenhuma rota cadastrada ainda.");
            return;
        }
        
        System.out.printf("📊 Total de locais: %d%n", grafo.size());
        System.out.println("\n🔗 Conexões disponíveis:");
        
        grafo.entrySet().stream()
              .sorted(Map.Entry.comparingByKey())
              .forEach(entry -> {
                  String local = entry.getKey();
                  List<String> conexoes = new ArrayList<>(entry.getValue());
                  Collections.sort(conexoes);
                  System.out.printf("   %s → %s%n", local, String.join(", ", conexoes));
              });
    }
    
    private void gerarDadosAleatorios() {
        System.out.println("\n🎲 GERAR DADOS ALEATÓRIOS");
        System.out.println("─────────────────────────");
        
        String[] locais = {
            "CENTRO", "SHOPPING", "HOSPITAL", "UNIVERSIDADE", "AEROPORTO",
            "ESTACAO", "PARQUE", "BIBLIOTECA", "TEATRO", "MUSEU",
            "PRAIA", "MERCADO", "ESCOLA", "IGREJA", "BANCO"
        };
        
        grafo.clear();
        
        // Garantir que todos os locais tenham pelo menos uma conexão
        for (int i = 0; i < locais.length - 1; i++) {
            adicionarConexao(locais[i], locais[i + 1]);
            adicionarConexao(locais[i + 1], locais[i]);
        }
        
        // Adicionar conexões aleatórias extras
        int conexoesExtras = 8 + random.nextInt(12); // 8-19 conexões extras
        
        for (int i = 0; i < conexoesExtras; i++) {
            String origem = locais[random.nextInt(locais.length)];
            String destino = locais[random.nextInt(locais.length)];
            
            if (!origem.equals(destino)) {
                adicionarConexao(origem, destino);
                adicionarConexao(destino, origem);
            }
        }
        
        System.out.println("✅ Dados aleatórios gerados com sucesso!");
        System.out.printf("📊 %d locais conectados com rotas variadas%n", locais.length);
        System.out.println("\n💡 Experimente buscar caminhos entre os locais gerados!");
    }
}
