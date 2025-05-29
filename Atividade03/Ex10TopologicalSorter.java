/*
 * Sistema de Ordenação Topológica
 * Este sistema permite a criação de um grafo direcionado, onde os usuários podem adicionar arestas representando dependências entre tarefas.
 * O sistema oferece funcionalidades para realizar ordenação topológica, verificar ciclos, exibir o grafo e gerar dados aleatórios.
 *  
 * Autor: Christian Sperb
 * Data: 29/05/2025
 */



package Atividade03;

import java.util.*;

public class Ex10TopologicalSorter {
    private static final Scanner scanner = new Scanner(System.in);
    private Map<String, Set<String>> graph;
    private Map<String, Integer> inDegree;
    
    public Ex10TopologicalSorter() {
        this.graph = new HashMap<>();
        this.inDegree = new HashMap<>();
    }
    
    public static void main(String[] args) {
        Ex10TopologicalSorter sorter = new Ex10TopologicalSorter();
        sorter.run();
    }
    
    private void run() {
        clearScreen();
        System.out.println("=== SISTEMA DE ORDENAÇÃO TOPOLÓGICA ===");
        
        while (true) {
            showMenu();
            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    addEdge();
                    break;
                case 2:
                    performTopologicalSort();
                    break;
                case 3:
                    displayGraph();
                    break;
                case 4:
                    generateRandomData();
                    break;
                case 5:
                    checkForCycles();
                    break;
                case 6:
                    clearGraph();
                    break;
                case 0:
                    System.out.println("\nSaindo do sistema...");
                    return;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
            
            pauseExecution();
        }
    }
    
    private void showMenu() {
        clearScreen();
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1. Adicionar aresta direcionada");
        System.out.println("2. Realizar ordenação topológica");
        System.out.println("3. Exibir grafo");
        System.out.println("4. Gerar dados aleatórios");
        System.out.println("5. Verificar ciclos");
        System.out.println("6. Limpar grafo");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    private void addEdge() {
        clearScreen();
        System.out.println("=== ADICIONAR ARESTA DIRECIONADA ===");
        
        System.out.print("Origem (dependência): ");
        String origin = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Destino (dependente): ");
        String destination = scanner.nextLine().trim().toUpperCase();
        
        if (origin.equals(destination)) {
            System.out.println("\nErro: Não é possível criar auto-dependência!");
            return;
        }
        
        addEdgeToGraph(origin, destination);
        System.out.println("\nAresta adicionada: " + origin + " -> " + destination);
        System.out.println("Interpretação: '" + destination + "' depende de '" + origin + "'");
    }
    
    private void addEdgeToGraph(String origin, String destination) {
        // Adiciona vértices ao grafo se não existirem
        graph.computeIfAbsent(origin, k -> new HashSet<>());
        graph.computeIfAbsent(destination, k -> new HashSet<>());
        inDegree.putIfAbsent(origin, 0);
        inDegree.putIfAbsent(destination, 0);
        
        // Adiciona aresta e atualiza grau de entrada
        if (graph.get(origin).add(destination)) {
            inDegree.put(destination, inDegree.get(destination) + 1);
        }
    }
    
    private void performTopologicalSort() {
        clearScreen();
        System.out.println("=== ORDENAÇÃO TOPOLÓGICA ===");
        
        if (graph.isEmpty()) {
            System.out.println("Grafo vazio! Adicione arestas primeiro.");
            return;
        }
        
        System.out.println("Escolha o algoritmo:");
        System.out.println("1. Algoritmo de Kahn (BFS)");
        System.out.println("2. DFS com pilha");
        System.out.print("Opção: ");
        
        int choice = getValidIntInput();
        
        SortResult result;
        switch (choice) {
            case 1:
                result = kahnAlgorithm();
                displaySortResult(result, "Kahn (BFS)");
                break;
            case 2:
                result = dfsTopologicalSort();
                displaySortResult(result, "DFS");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    
    private SortResult kahnAlgorithm() {
        Map<String, Integer> tempInDegree = new HashMap<>(inDegree);
        Queue<String> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();
        
        // Adiciona vértices com grau de entrada 0
        for (Map.Entry<String, Integer> entry : tempInDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);
            
            // Remove arestas do vértice atual
            Set<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    int newInDegree = tempInDegree.get(neighbor) - 1;
                    tempInDegree.put(neighbor, newInDegree);
                    
                    if (newInDegree == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        boolean hasCycle = result.size() != graph.size();
        return new SortResult(result, hasCycle);
    }
    
    private SortResult dfsTopologicalSort() {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();
        Stack<String> stack = new Stack<>();
        boolean hasCycle = false;
        
        for (String vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                if (dfsVisit(vertex, visited, recStack, stack)) {
                    hasCycle = true;
                    break;
                }
            }
        }
        
        List<String> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return new SortResult(result, hasCycle);
    }
    
    private boolean dfsVisit(String vertex, Set<String> visited, Set<String> recStack, Stack<String> stack) {
        visited.add(vertex);
        recStack.add(vertex);
        
        Set<String> neighbors = graph.get(vertex);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (recStack.contains(neighbor)) {
                    return true; // Ciclo detectado
                }
                
                if (!visited.contains(neighbor) && dfsVisit(neighbor, visited, recStack, stack)) {
                    return true;
                }
            }
        }
        
        recStack.remove(vertex);
        stack.push(vertex);
        return false;
    }
    
    private void displaySortResult(SortResult result, String algorithm) {
        System.out.println("\n=== RESULTADO DA ORDENAÇÃO (" + algorithm + ") ===");
        
        if (result.hasCycle) {
            System.out.println("❌ IMPOSSÍVEL REALIZAR ORDENAÇÃO TOPOLÓGICA!");
            System.out.println("Motivo: O grafo contém ciclos.");
            System.out.println("Solução: Remova as dependências circulares.");
            return;
        }
        
        System.out.println("✅ Ordenação topológica realizada com sucesso!");
        System.out.println("\nOrdem de execução/processamento:");
        
        for (int i = 0; i < result.sortedVertices.size(); i++) {
            String vertex = result.sortedVertices.get(i);
            System.out.println((i + 1) + ". " + vertex);
        }
        
        System.out.println("\nInterpretação:");
        System.out.println("- Execute as tarefas na ordem mostrada");
        System.out.println("- Cada tarefa só pode começar após suas dependências");
        System.out.println("- Total de tarefas: " + result.sortedVertices.size());
        
        displayDependencies(result.sortedVertices);
    }
    
    private void displayDependencies(List<String> sortedVertices) {
        System.out.println("\nDependências por tarefa:");
        
        for (String vertex : sortedVertices) {
            List<String> dependencies = getDependencies(vertex);
            
            if (dependencies.isEmpty()) {
                System.out.println("• " + vertex + ": Sem dependências (pode iniciar imediatamente)");
            } else {
                System.out.println("• " + vertex + ": Depende de " + dependencies);
            }
        }
    }
    
    private List<String> getDependencies(String vertex) {
        List<String> dependencies = new ArrayList<>();
        
        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            if (entry.getValue().contains(vertex)) {
                dependencies.add(entry.getKey());
            }
        }
        
        Collections.sort(dependencies);
        return dependencies;
    }
    
    private void checkForCycles() {
        clearScreen();
        System.out.println("=== VERIFICAÇÃO DE CICLOS ===");
        
        if (graph.isEmpty()) {
            System.out.println("Grafo vazio!");
            return;
        }
        
        CycleDetectionResult result = detectCycles();
        
        if (result.hasCycle) {
            System.out.println("❌ CICLO DETECTADO!");
            System.out.println("O grafo contém dependências circulares.");
            
            if (!result.cycleVertices.isEmpty()) {
                System.out.println("Vértices envolvidos no ciclo: " + result.cycleVertices);
            }
            
            System.out.println("\nProblemas causados por ciclos:");
            System.out.println("• Impossibilita ordenação topológica");
            System.out.println("• Cria dependências infinitas");
            System.out.println("• Impede execução sequencial de tarefas");
        } else {
            System.out.println("✅ NENHUM CICLO DETECTADO!");
            System.out.println("O grafo é um DAG (Directed Acyclic Graph).");
            System.out.println("Ordenação topológica é possível.");
        }
    }
    
    private CycleDetectionResult detectCycles() {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();
        List<String> cycleVertices = new ArrayList<>();
        
        for (String vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                if (hasCycleDFS(vertex, visited, recStack, cycleVertices)) {
                    return new CycleDetectionResult(true, cycleVertices);
                }
            }
        }
        
        return new CycleDetectionResult(false, new ArrayList<>());
    }
    
    private boolean hasCycleDFS(String vertex, Set<String> visited, Set<String> recStack, List<String> cycleVertices) {
        visited.add(vertex);
        recStack.add(vertex);
        
        Set<String> neighbors = graph.get(vertex);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (recStack.contains(neighbor)) {
                    cycleVertices.add(vertex);
                    cycleVertices.add(neighbor);
                    return true;
                }
                
                if (!visited.contains(neighbor) && hasCycleDFS(neighbor, visited, recStack, cycleVertices)) {
                    cycleVertices.add(vertex);
                    return true;
                }
            }
        }
        
        recStack.remove(vertex);
        return false;
    }
    
    private void displayGraph() {
        clearScreen();
        System.out.println("=== ESTRUTURA DO GRAFO DIRECIONADO ===");
        
        if (graph.isEmpty()) {
            System.out.println("Grafo vazio!");
            return;
        }
        
        System.out.println("Vértices (Tarefas): " + graph.keySet().size());
        System.out.println("Arestas (Dependências):");
        
        int edgeCount = 0;
        for (Map.Entry<String, Set<String>> vertex : graph.entrySet()) {
            String from = vertex.getKey();
            Set<String> destinations = vertex.getValue();
            
            if (destinations.isEmpty()) {
                System.out.println(from + " -> (sem dependentes)");
            } else {
                for (String to : destinations) {
                    System.out.println(from + " -> " + to + " ('" + to + "' depende de '" + from + "')");
                    edgeCount++;
                }
            }
        }
        
        System.out.println("\nEstatísticas:");
        System.out.println("• Total de dependências: " + edgeCount);
        System.out.println("• Graus de entrada:");
        
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            String vertex = entry.getKey();
            int degree = entry.getValue();
            String interpretation = degree == 0 ? " (sem dependências)" : " (" + degree + " dependências)";
            System.out.println("  - " + vertex + ": " + degree + interpretation);
        }
    }
    
    private void generateRandomData() {
        clearScreen();
        System.out.println("=== GERAR DADOS ALEATÓRIOS ===");
        
        System.out.println("Escolha o tipo de dados:");
        System.out.println("1. Projeto de desenvolvimento de software");
        System.out.println("2. Processo de manufatura");
        System.out.println("3. Currículo acadêmico");
        System.out.println("4. Dados aleatórios genéricos");
        System.out.print("Opção: ");
        
        int choice = getValidIntInput();
        
        clearGraph();
        
        switch (choice) {
            case 1:
                generateSoftwareProjectData();
                break;
            case 2:
                generateManufacturingData();
                break;
            case 3:
                generateAcademicData();
                break;
            case 4:
                generateGenericRandomData();
                break;
            default:
                System.out.println("Opção inválida!");
                return;
        }
        
        System.out.println("Dados gerados com sucesso!");
        System.out.println("Vértices criados: " + graph.keySet());
    }
    
    private void generateSoftwareProjectData() {
        // Projeto de desenvolvimento de software
        addEdgeToGraph("REQUISITOS", "DESIGN");
        addEdgeToGraph("DESIGN", "CODIFICACAO");
        addEdgeToGraph("CODIFICACAO", "TESTES_UNIT");
        addEdgeToGraph("TESTES_UNIT", "INTEGRACAO");
        addEdgeToGraph("INTEGRACAO", "TESTES_SISTEM");
        addEdgeToGraph("TESTES_SISTEM", "DEPLOY");
        addEdgeToGraph("DESIGN", "DATABASE");
        addEdgeToGraph("DATABASE", "CODIFICACAO");
        addEdgeToGraph("REQUISITOS", "DOCUMENTATION");
    }
    
    private void generateManufacturingData() {
        // Processo de manufatura
        addEdgeToGraph("MATERIA_PRIMA", "PREPARACAO");
        addEdgeToGraph("PREPARACAO", "MOLDAGEM");
        addEdgeToGraph("MOLDAGEM", "SECAGEM");
        addEdgeToGraph("SECAGEM", "MONTAGEM");
        addEdgeToGraph("MONTAGEM", "TESTE_QUAL");
        addEdgeToGraph("TESTE_QUAL", "EMBALAGEM");
        addEdgeToGraph("PREPARACAO", "FERRAMENTAS");
        addEdgeToGraph("FERRAMENTAS", "MOLDAGEM");
    }
    
    private void generateAcademicData() {
        // Currículo acadêmico
        addEdgeToGraph("MATEMATICA_BASICA", "CALCULO_I");
        addEdgeToGraph("CALCULO_I", "CALCULO_II");
        addEdgeToGraph("CALCULO_II", "EQUACOES_DIF");
        addEdgeToGraph("PROGRAMACAO_I", "PROGRAMACAO_II");
        addEdgeToGraph("PROGRAMACAO_II", "ESTRUTURAS_DADOS");
        addEdgeToGraph("ESTRUTURAS_DADOS", "ALGORITMOS");
        addEdgeToGraph("MATEMATICA_BASICA", "PROGRAMACAO_I");
        addEdgeToGraph("ALGORITMOS", "BANCO_DADOS");
    }
    
    private void generateGenericRandomData() {
        Random random = new Random();
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int vertexCount = 5 + random.nextInt(6); // 5-10 vértices
        
        // Seleciona vértices aleatórios
        List<String> selectedVertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            selectedVertices.add(vertices[i]);
        }
        
        // Cria arestas garantindo que não há ciclos
        for (int i = 0; i < selectedVertices.size() - 1; i++) {
            for (int j = i + 1; j < selectedVertices.size(); j++) {
                if (random.nextBoolean() && random.nextBoolean()) { // 25% de chance
                    addEdgeToGraph(selectedVertices.get(i), selectedVertices.get(j));
                }
            }
        }
    }
    
    private void clearGraph() {
        graph.clear();
        inDegree.clear();
        System.out.println("Grafo limpo com sucesso!");
    }
    
    private int getValidIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido: ");
            }
        }
    }
    
    private void clearScreen() {
        // Limpa a tela usando códigos ANSI
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        // Alternativa para sistemas que não suportam ANSI
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    private void pauseExecution() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
    
    // Classes internas para estrutura de dados
    private static class SortResult {
        List<String> sortedVertices;
        boolean hasCycle;
        
        SortResult(List<String> sortedVertices, boolean hasCycle) {
            this.sortedVertices = sortedVertices;
            this.hasCycle = hasCycle;
        }
    }
    
    private static class CycleDetectionResult {
        boolean hasCycle;
        List<String> cycleVertices;
        
        CycleDetectionResult(boolean hasCycle, List<String> cycleVertices) {
            this.hasCycle = hasCycle;
            this.cycleVertices = cycleVertices;
        }
    }
}
