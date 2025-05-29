/*
 * Sistema de Seleção de Caminho Mais Curto
 * Este sistema permite adicionar arestas a um grafo, encontrar o caminho mais curto entre dois vértices usando o algoritmo de Dijkstra, 
 * exibir a estrutura do grafo, gerar dados aleatórios e limpar o grafo.
 *  
 * Autor: Christian Sperb
 * Data: 29/05/2025
 */

package Atividade03;

import java.util.*;

public class Ex09ShortestPathFinder {
    private static final Scanner scanner = new Scanner(System.in);
    private Map<String, Map<String, Integer>> graph;
    
    public Ex09ShortestPathFinder() {
        this.graph = new HashMap<>();
    }
    
    public static void main(String[] args) {
        Ex09ShortestPathFinder pathFinder = new Ex09ShortestPathFinder();
        pathFinder.run();
    }
    
    private void run() {
        clearScreen();
        System.out.println("=== SISTEMA DE CAMINHO MAIS CURTO ===");
        
        while (true) {
            showMenu();
            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    addEdge();
                    break;
                case 2:
                    findShortestPath();
                    break;
                case 3:
                    displayGraph();
                    break;
                case 4:
                    generateRandomData();
                    break;
                case 5:
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
        System.out.println("1. Adicionar aresta");
        System.out.println("2. Encontrar caminho mais curto");
        System.out.println("3. Exibir grafo");
        System.out.println("4. Gerar dados aleatórios");
        System.out.println("5. Limpar grafo");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    private void addEdge() {
        clearScreen();
        System.out.println("=== ADICIONAR ARESTA ===");
        
        System.out.print("Origem: ");
        String origin = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Destino: ");
        String destination = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Peso (distância): ");
        int weight = getValidIntInput();
        
        if (weight < 0) {
            System.out.println("\nErro: O peso deve ser não-negativo!");
            return;
        }
        
        addEdgeToGraph(origin, destination, weight);
        System.out.println("\nAresta adicionada com sucesso!");
    }
    
    private void addEdgeToGraph(String origin, String destination, int weight) {
        graph.computeIfAbsent(origin, k -> new HashMap<>()).put(destination, weight);
        graph.computeIfAbsent(destination, k -> new HashMap<>()).put(origin, weight);
    }
    
    private void findShortestPath() {
        clearScreen();
        System.out.println("=== ENCONTRAR CAMINHO MAIS CURTO ===");
        
        if (graph.isEmpty()) {
            System.out.println("Grafo vazio! Adicione arestas primeiro.");
            return;
        }
        
        System.out.print("Vértice de origem: ");
        String start = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Vértice de destino: ");
        String end = scanner.nextLine().trim().toUpperCase();
        
        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            System.out.println("\nErro: Um ou ambos os vértices não existem no grafo!");
            return;
        }
        
        PathResult result = dijkstra(start, end);
        displayPathResult(result, start, end);
    }
    
    private PathResult dijkstra(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Set<String> visited = new HashSet<>();
        
        // Inicializar distâncias
        for (String vertex : graph.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.offer(new Node(start, 0));
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            if (visited.contains(current.vertex)) {
                continue;
            }
            
            visited.add(current.vertex);
            
            if (current.vertex.equals(end)) {
                break;
            }
            
            Map<String, Integer> neighbors = graph.get(current.vertex);
            if (neighbors != null) {
                for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                    String neighborVertex = neighbor.getKey();
                    int weight = neighbor.getValue();
                    int newDistance = distances.get(current.vertex) + weight;
                    
                    if (newDistance < distances.get(neighborVertex)) {
                        distances.put(neighborVertex, newDistance);
                        previous.put(neighborVertex, current.vertex);
                        queue.offer(new Node(neighborVertex, newDistance));
                    }
                }
            }
        }
        
        return new PathResult(distances.get(end), previous);
    }
    
    private void displayPathResult(PathResult result, String start, String end) {
        System.out.println("\n=== RESULTADO ===");
        
        if (result.distance == Integer.MAX_VALUE) {
            System.out.println("Não há caminho entre " + start + " e " + end);
            return;
        }
        
        List<String> path = reconstructPath(result.previous, start, end);
        
        System.out.println("Distância mínima: " + result.distance);
        System.out.println("Caminho: " + String.join(" -> ", path));
        System.out.println("Número de vértices no caminho: " + path.size());
    }
    
    private List<String> reconstructPath(Map<String, String> previous, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;
        
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        
        return path;
    }
    
    private void displayGraph() {
        clearScreen();
        System.out.println("=== ESTRUTURA DO GRAFO ===");
        
        if (graph.isEmpty()) {
            System.out.println("Grafo vazio!");
            return;
        }
        
        System.out.println("Vértices: " + graph.keySet().size());
        System.out.println("Arestas:");
        
        Set<String> processedEdges = new HashSet<>();
        
        for (Map.Entry<String, Map<String, Integer>> vertex : graph.entrySet()) {
            String from = vertex.getKey();
            
            for (Map.Entry<String, Integer> edge : vertex.getValue().entrySet()) {
                String to = edge.getKey();
                int weight = edge.getValue();
                
                String edgeKey = createEdgeKey(from, to);
                
                if (!processedEdges.contains(edgeKey)) {
                    System.out.println(from + " <--> " + to + " (peso: " + weight + ")");
                    processedEdges.add(edgeKey);
                }
            }
        }
    }
    
    private String createEdgeKey(String vertex1, String vertex2) {
        return vertex1.compareTo(vertex2) < 0 ? vertex1 + "-" + vertex2 : vertex2 + "-" + vertex1;
    }
    
    private void generateRandomData() {
        clearScreen();
        System.out.println("=== GERAR DADOS ALEATÓRIOS ===");
        
        Random random = new Random();
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int edgeCount = 10 + random.nextInt(11); // 10-20 arestas
        
        clearGraph();
        
        for (int i = 0; i < edgeCount; i++) {
            String origin = vertices[random.nextInt(vertices.length)];
            String destination = vertices[random.nextInt(vertices.length)];
            
            if (!origin.equals(destination)) {
                int weight = 1 + random.nextInt(20); // peso entre 1 e 20
                addEdgeToGraph(origin, destination, weight);
            }
        }
        
        System.out.println("Dados aleatórios gerados com sucesso!");
        System.out.println("Vértices criados: " + graph.keySet());
        System.out.println("Número de arestas: " + countEdges());
    }
    
    private int countEdges() {
        int count = 0;
        for (Map<String, Integer> edges : graph.values()) {
            count += edges.size();
        }
        return count / 2; // Dividir por 2 porque o grafo é não-direcionado
    }
    
    private void clearGraph() {
        graph.clear();
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
    private static class Node {
        String vertex;
        int distance;
        
        Node(String vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
    
    private static class PathResult {
        int distance;
        Map<String, String> previous;
        
        PathResult(int distance, Map<String, String> previous) {
            this.distance = distance;
            this.previous = previous;
        }
    }
}
