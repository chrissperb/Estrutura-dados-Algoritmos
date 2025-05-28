/*
 * Sistema de Navegação por Rotas com DFS
 * Implementação de um sistema de navegação que utiliza o algoritmo DFS (Depth-First Search)
 * para explorar e buscar caminhos em um grafo de locais conectados.
 *  
 * Autor: Christian Sperb
 * Data: 28/05/2025
 */



package Atividade03;

import java.util.*;

public class Ex07DFSNavigator {
    private final Map<String, List<String>> grafo;
    private final Scanner scanner;
    private final Random random;
    
    public Ex07DFSNavigator() {
        this.grafo = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }
    
    public static void main(String[] args) {
        Ex07DFSNavigator navegador = new Ex07DFSNavigator();
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
                case 2 -> explorarDFS();
                case 3 -> buscarCaminhosDFS();
                case 4 -> exibirGrafo();
                case 5 -> gerarDadosAleatorios();
                case 6 -> {
                    System.out.println("\n🌟 Obrigado por usar o DFS Navigator! Até logo!");
                    continuar = false;
                }
                default -> System.out.println("\n❌ Opção inválida! Pressione Enter para continuar...");
            }
            
            if (continuar && opcao != 6) {
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
        System.out.println("║          🔍 DFS NAVIGATOR            ║");
        System.out.println("║   Explorador de Rotas em Profundidade║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. ➕ Adicionar Rota                ║");
        System.out.println("║  2. 🚶 Explorar DFS (Ponto Único)    ║");
        System.out.println("║  3. 🗺️  Buscar Caminhos DFS           ║");
        System.out.println("║  4. 📋 Exibir Mapa de Rotas          ║");
        System.out.println("║  5. 🎲 Gerar Dados Aleatórios        ║");
        System.out.println("║  6. 🚪 Sair                          ║");
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
    
    private void explorarDFS() {
        if (grafo.isEmpty()) {
            System.out.println("\n❌ Nenhuma rota cadastrada! Adicione rotas primeiro.");
            return;
        }
        
        System.out.println("\n🚶 EXPLORAÇÃO DFS - PONTO ÚNICO");
        System.out.println("─────────────────────────────────");
        
        System.out.print("📍 Ponto de partida para exploração: ");
        String pontoInicial = scanner.nextLine().trim().toUpperCase();
        
        if (!grafo.containsKey(pontoInicial)) {
            System.out.printf("\n❌ Local '%s' não encontrado no mapa!%n", pontoInicial);
            return;
        }
        
        List<String> ordemVisita = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        
        realizarDFS(pontoInicial, visitados, ordemVisita);
        
        exibirResultadoExploracaoSimples(pontoInicial, ordemVisita);
    }
    
    private void realizarDFS(String verticeAtual, Set<String> visitados, List<String> ordemVisita) {
        visitados.add(verticeAtual);
        ordemVisita.add(verticeAtual);
        
        List<String> vizinhos = grafo.getOrDefault(verticeAtual, new ArrayList<>());
        // Ordenar vizinhos para garantir consistência na exploração
        Collections.sort(vizinhos);
        
        for (String vizinho : vizinhos) {
            if (!visitados.contains(vizinho)) {
                realizarDFS(vizinho, visitados, ordemVisita);
            }
        }
    }
    
    private void exibirResultadoExploracaoSimples(String pontoInicial, List<String> ordemVisita) {
        System.out.println("\n🔍 RESULTADO DA EXPLORAÇÃO DFS");
        System.out.println("─────────────────────────────────");
        System.out.printf("🚀 Ponto inicial: %s%n", pontoInicial);
        System.out.printf("📊 Total de locais visitados: %d%n", ordemVisita.size());
        System.out.printf("🚶 Ordem de visitação: %s%n", String.join(" → ", ordemVisita));
        
        if (ordemVisita.size() < grafo.size()) {
            Set<String> naoVisitados = new HashSet<>(grafo.keySet());
            naoVisitados.removeAll(ordemVisita);
            System.out.printf("⚠️  Locais não alcançados: %s%n", String.join(", ", naoVisitados));
        } else {
            System.out.println("✅ Todos os locais foram explorados!");
        }
    }
    
    private void buscarCaminhosDFS() {
        if (grafo.isEmpty()) {
            System.out.println("\n❌ Nenhuma rota cadastrada! Adicione rotas primeiro.");
            return;
        }
        
        System.out.println("\n🗺️  BUSCAR CAMINHOS COM DFS");
        System.out.println("───────────────────────────────");
        
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
        
        List<List<String>> todosCaminhos = new ArrayList<>();
        List<String> caminhoAtual = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        
        buscarTodosCaminhosDFS(origem, destino, visitados, caminhoAtual, todosCaminhos);
        
        exibirResultadoBuscaCaminhos(origem, destino, todosCaminhos);
    }
    
    private void buscarTodosCaminhosDFS(String origem, String destino, Set<String> visitados, 
                                       List<String> caminhoAtual, List<List<String>> todosCaminhos) {
        visitados.add(origem);
        caminhoAtual.add(origem);
        
        if (origem.equals(destino)) {
            todosCaminhos.add(new ArrayList<>(caminhoAtual));
        } else {
            List<String> vizinhos = grafo.getOrDefault(origem, new ArrayList<>());
            Collections.sort(vizinhos);
            
            for (String vizinho : vizinhos) {
                if (!visitados.contains(vizinho)) {
                    buscarTodosCaminhosDFS(vizinho, destino, visitados, caminhoAtual, todosCaminhos);
                }
            }
        }
        
        // Backtrack
        visitados.remove(origem);
        caminhoAtual.remove(caminhoAtual.size() - 1);
    }
    
    private void exibirResultadoBuscaCaminhos(String origem, String destino, List<List<String>> todosCaminhos) {
        System.out.println("\n🎯 RESULTADO DA BUSCA DFS");
        System.out.println("─────────────────────────────");
        
        if (todosCaminhos.isEmpty()) {
            System.out.printf("❌ Não há caminhos disponíveis entre %s e %s%n", origem, destino);
            return;
        }
        
        System.out.printf("✅ Encontrados %d caminhos possíveis!%n", todosCaminhos.size());
        System.out.println("\n🛣️  TODOS OS CAMINHOS ENCONTRADOS:");
        
        // Ordenar caminhos por comprimento
        todosCaminhos.sort(Comparator.comparing(List::size));
        
        for (int i = 0; i < todosCaminhos.size(); i++) {
            List<String> caminho = todosCaminhos.get(i);
            System.out.printf("\n   %d. %s", i + 1, String.join(" → ", caminho));
            System.out.printf(" (📏 %d paradas)", caminho.size() - 1);
            
            if (i == 0) {
                System.out.print(" ⭐ MAIS CURTO");
            }
        }
        
        // Estatísticas dos caminhos
        int menorCaminho = todosCaminhos.get(0).size() - 1;
        int maiorCaminho = todosCaminhos.get(todosCaminhos.size() - 1).size() - 1;
        
        System.out.println("\n\n📊 ESTATÍSTICAS:");
        System.out.printf("   • Caminho mais curto: %d paradas%n", menorCaminho);
        System.out.printf("   • Caminho mais longo: %d paradas%n", maiorCaminho);
        System.out.printf("   • Total de rotas alternativas: %d%n", todosCaminhos.size());
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
            "PRAIA", "MERCADO", "ESCOLA", "IGREJA", "BANCO",
            "RESTAURANTE", "HOTEL", "FARMACIA", "CORREIOS", "GINASIO"
        };
        
        grafo.clear();
        
        // Garantir conectividade criando uma árvore geradora
        List<String> locaisDisponiveis = new ArrayList<>(Arrays.asList(locais));
        Collections.shuffle(locaisDisponiveis, random);
        
        for (int i = 0; i < locaisDisponiveis.size() - 1; i++) {
            String origem = locaisDisponiveis.get(i);
            String destino = locaisDisponiveis.get(i + 1);
            adicionarConexao(origem, destino);
            adicionarConexao(destino, origem);
        }
        
        // Adicionar conexões extras para criar múltiplos caminhos
        int conexoesExtras = 12 + random.nextInt(18); // 12-29 conexões extras
        
        for (int i = 0; i < conexoesExtras; i++) {
            String origem = locais[random.nextInt(locais.length)];
            String destino = locais[random.nextInt(locais.length)];
            
            if (!origem.equals(destino)) {
                adicionarConexao(origem, destino);
                adicionarConexao(destino, origem);
            }
        }
        
        System.out.println("✅ Dados aleatórios gerados com sucesso!");
        System.out.printf("📊 %d locais conectados com múltiplas rotas%n", locais.length);
        System.out.println("\n💡 Experimente explorar as rotas com DFS!");
        System.out.println("🔍 DFS é ideal para encontrar TODOS os caminhos possíveis!");
    }
}
