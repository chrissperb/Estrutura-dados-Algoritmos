/*
 * Sistema de Roteamento de Grafos
 * Este sistema permite adicionar locais, conexões entre eles,
 * exibir o grafo, buscar o menor caminho entre dois locais e gerar dados aleatórios. 
 *  
 * Autor: Christian Sperb
 * Data: 27/05/2025
 */

package Atividade03;

import java.util.*;

public class Ex05SistemaRoteamentoGrafos {
    private Map<String, Map<String, Integer>> grafo;
    private Scanner scanner;
    
    public Ex05SistemaRoteamentoGrafos() {
        this.grafo = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        boolean continuar = true;
        
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    adicionarLocal();
                    break;
                case 2:
                    adicionarConexao();
                    break;
                case 3:
                    exibirGrafo();
                    break;
                case 4:
                    buscarCaminho();
                    break;
                case 5:
                    gerarDadosAleatorios();
                    break;
                case 6:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
                limparTela();
            }
        }
        
        System.out.println("Sistema encerrado!");
    }
    
    private void exibirMenu() {
        System.out.println("=== SISTEMA DE ROTEAMENTO ===");
        System.out.println("1. Adicionar Local");
        System.out.println("2. Adicionar Conexão");
        System.out.println("3. Exibir Grafo");
        System.out.println("4. Buscar Caminho");
        System.out.println("5. Gerar Dados Aleatórios");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpar buffer
            return -1;
        }
    }
    
    private void adicionarLocal() {
        System.out.print("Digite o nome do local: ");
        String local = scanner.nextLine().trim();
        
        if (local.isEmpty()) {
            System.out.println("Nome do local não pode estar vazio!");
            return;
        }
        
        if (!grafo.containsKey(local)) {
            grafo.put(local, new HashMap<>());
            System.out.println("Local '" + local + "' adicionado com sucesso!");
        } else {
            System.out.println("Local '" + local + "' já existe!");
        }
    }
    
    private void adicionarConexao() {
        if (grafo.size() < 2) {
            System.out.println("É necessário ter pelo menos 2 locais para criar uma conexão!");
            return;
        }
        
        System.out.print("Local de origem: ");
        String origem = scanner.nextLine().trim();
        
        System.out.print("Local de destino: ");
        String destino = scanner.nextLine().trim();
        
        if (!grafo.containsKey(origem) || !grafo.containsKey(destino)) {
            System.out.println("Um ou ambos os locais não existem!");
            return;
        }
        
        System.out.print("Distância (km): ");
        try {
            int distancia = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            if (distancia <= 0) {
                System.out.println("Distância deve ser maior que zero!");
                return;
            }
            
            // Adicionar conexão bidirecional
            grafo.get(origem).put(destino, distancia);
            grafo.get(destino).put(origem, distancia);
            
            System.out.println("Conexão adicionada: " + origem + " <-> " + destino + " (" + distancia + "km)");
            
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpar buffer
            System.out.println("Distância deve ser um número válido!");
        }
    }
    
    private void exibirGrafo() {
        if (grafo.isEmpty()) {
            System.out.println("Nenhum local cadastrado!");
            return;
        }
        
        System.out.println("\n=== MAPA DE LOCAIS E CONEXÕES ===");
        for (String local : grafo.keySet()) {
            System.out.println("\n" + local + ":");
            Map<String, Integer> conexoes = grafo.get(local);
            
            if (conexoes.isEmpty()) {
                System.out.println("  Sem conexões");
            } else {
                for (Map.Entry<String, Integer> conexao : conexoes.entrySet()) {
                    System.out.println("  -> " + conexao.getKey() + " (" + conexao.getValue() + "km)");
                }
            }
        }
    }
    
    private void buscarCaminho() {
        if (grafo.size() < 2) {
            System.out.println("É necessário ter pelo menos 2 locais conectados!");
            return;
        }
        
        System.out.print("Local de origem: ");
        String origem = scanner.nextLine().trim();
        
        System.out.print("Local de destino: ");
        String destino = scanner.nextLine().trim();
        
        if (!grafo.containsKey(origem) || !grafo.containsKey(destino)) {
            System.out.println("Um ou ambos os locais não existem!");
            return;
        }
        
        List<String> caminho = encontrarMenorCaminho(origem, destino);
        
        if (caminho.isEmpty()) {
            System.out.println("Não há caminho entre " + origem + " e " + destino);
        } else {
            int distanciaTotal = calcularDistanciaTotal(caminho);
            System.out.println("\nMenor caminho encontrado:");
            System.out.println("Rota: " + String.join(" -> ", caminho));
            System.out.println("Distância total: " + distanciaTotal + "km");
        }
    }
    
    private List<String> encontrarMenorCaminho(String origem, String destino) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparing(distancias::get));
        Set<String> visitados = new HashSet<>();
        
        // Inicializar distâncias
        for (String local : grafo.keySet()) {
            distancias.put(local, Integer.MAX_VALUE);
        }
        distancias.put(origem, 0);
        fila.add(origem);
        
        while (!fila.isEmpty()) {
            String atual = fila.poll();
            
            if (visitados.contains(atual)) continue;
            visitados.add(atual);
            
            if (atual.equals(destino)) break;
            
            for (Map.Entry<String, Integer> vizinho : grafo.get(atual).entrySet()) {
                String proximoLocal = vizinho.getKey();
                int novaDistancia = distancias.get(atual) + vizinho.getValue();
                
                if (novaDistancia < distancias.get(proximoLocal)) {
                    distancias.put(proximoLocal, novaDistancia);
                    anteriores.put(proximoLocal, atual);
                    fila.add(proximoLocal);
                }
            }
        }
        
        return reconstruirCaminho(anteriores, origem, destino);
    }
    
    private List<String> reconstruirCaminho(Map<String, String> anteriores, String origem, String destino) {
        List<String> caminho = new ArrayList<>();
        String atual = destino;
        
        while (atual != null) {
            caminho.add(atual);
            atual = anteriores.get(atual);
        }
        
        if (!caminho.get(caminho.size() - 1).equals(origem)) {
            return new ArrayList<>(); // Sem caminho
        }
        
        Collections.reverse(caminho);
        return caminho;
    }
    
    private int calcularDistanciaTotal(List<String> caminho) {
        int total = 0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            total += grafo.get(caminho.get(i)).get(caminho.get(i + 1));
        }
        return total;
    }
    
    private void gerarDadosAleatorios() {
        System.out.println("Gerando dados aleatórios...");
        
        String[] locais = {
            "Centro", "Shopping", "Aeroporto", "Universidade", "Hospital",
            "Estádio", "Parque", "Praia", "Montanha", "Porto"
        };
        
        Random random = new Random();
        
        // Limpar grafo atual
        grafo.clear();
        
        // Adicionar locais aleatórios
        int numLocais = 5 + random.nextInt(6); // 5-10 locais
        Set<String> locaisEscolhidos = new HashSet<>();
        
        while (locaisEscolhidos.size() < numLocais) {
            locaisEscolhidos.add(locais[random.nextInt(locais.length)]);
        }
        
        for (String local : locaisEscolhidos) {
            grafo.put(local, new HashMap<>());
        }
        
        // Adicionar conexões aleatórias
        List<String> listaLocais = new ArrayList<>(locaisEscolhidos);
        int numConexoes = numLocais + random.nextInt(numLocais);
        
        for (int i = 0; i < numConexoes; i++) {
            String origem = listaLocais.get(random.nextInt(listaLocais.size()));
            String destino = listaLocais.get(random.nextInt(listaLocais.size()));
            
            if (!origem.equals(destino) && !grafo.get(origem).containsKey(destino)) {
                int distancia = 1 + random.nextInt(50); // 1-50 km
                grafo.get(origem).put(destino, distancia);
                grafo.get(destino).put(origem, distancia);
            }
        }
        
        System.out.println("Dados aleatórios gerados com sucesso!");
        System.out.println("Locais: " + numLocais);
        System.out.println("Conexões: " + contarConexoes() / 2); // Dividir por 2 pois são bidirecionais
    }
    
    private int contarConexoes() {
        int total = 0;
        for (Map<String, Integer> conexoes : grafo.values()) {
            total += conexoes.size();
        }
        return total;
    }
    
    private void limparTela() {
        try {
            // Para Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Para Linux/Mac
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback: imprimir linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        Ex05SistemaRoteamentoGrafos sistema = new Ex05SistemaRoteamentoGrafos();
        sistema.executar();
    }
}
