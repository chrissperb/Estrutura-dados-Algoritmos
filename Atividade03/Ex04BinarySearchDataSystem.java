/*
 * Pesquisa de dados com busca binária 
 * Este programa implementa um sistema de pesquisa de dados utilizando busca binária.
 * Permite gerar dados aleatórios, adicionar registros manualmente, realizar buscas por ID e descrição,
 * comparar buscas binária e linear, exibir estatísticas de busca e reorganizar dados.
 *  
 * Autor: Christian Sperb
 * Data: 27/05/2025
 */

package Atividade03;

import java.util.*;

public class Ex04BinarySearchDataSystem {
    
    // Classe interna para representar um registro de dados
    private static class DataRecord {
        int id;
        String description;
        double value;
        
        DataRecord(int id, String description, double value) {
            this.id = id;
            this.description = description;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return String.format("ID: %d | %s | Valor: %.2f", id, description, value);
        }
    }
    
    private List<DataRecord> records;
    private List<Integer> sortedIds;
    private Scanner scanner;
    private Random random;
    private int comparisons;
    
    public Ex04BinarySearchDataSystem() {
        this.records = new ArrayList<>();
        this.sortedIds = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.comparisons = 0;
    }
    
    public void run() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    generateRandomData();
                    break;
                case 2:
                    addDataManually();
                    break;
                case 3:
                    performBinarySearch();
                    break;
                case 4:
                    performLinearSearchComparison();
                    break;
                case 5:
                    displayAllRecords();
                    break;
                case 6:
                    searchByDescription();
                    break;
                case 7:
                    displaySearchStatistics();
                    break;
                case 8:
                    sortDataById();
                    break;
                case 9:
                    System.out.println("Encerrando o sistema de pesquisa...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            waitForUser();
        }
    }
    
    private void displayMenu() {
        clearScreen();
        System.out.println("=== SISTEMA DE BUSCA BINÁRIA - PESQUISA DE DADOS ===");
        System.out.println("1. Gerar dados aleatórios");
        System.out.println("2. Adicionar registro manualmente");
        System.out.println("3. Buscar por ID (Busca Binária)");
        System.out.println("4. Comparar Busca Binária vs Linear");
        System.out.println("5. Exibir todos os registros");
        System.out.println("6. Buscar por descrição");
        System.out.println("7. Estatísticas de busca");
        System.out.println("8. Reorganizar dados (ordenar por ID)");
        System.out.println("9. Sair");
        System.out.printf("Total de registros: %d | Ordenado: %s\n", 
                         records.size(), isDataSorted() ? "SIM" : "NÃO");
        System.out.print("Escolha uma opção: ");
    }
    
    private int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private void generateRandomData() {
        clearScreen();
        System.out.println("=== GERANDO DADOS ALEATÓRIOS ===");
        
        String[] descriptions = {
            "Produto Eletrônico", "Serviço Premium", "Equipamento Industrial",
            "Software Licenciado", "Hardware Especializado", "Consultoria Técnica",
            "Treinamento Corporativo", "Suporte Técnico", "Manutenção Preventiva",
            "Análise de Dados", "Desenvolvimento Custom", "Integração de Sistemas",
            "Backup e Segurança", "Cloud Computing", "Internet das Coisas"
        };
        
        System.out.print("Quantos registros deseja gerar? (10-1000): ");
        int numRecords = scanner.nextInt();
        numRecords = Math.max(10, Math.min(1000, numRecords));
        
        records.clear();
        sortedIds.clear();
        
        Set<Integer> usedIds = new HashSet<>();
        
        System.out.println("\nGerando " + numRecords + " registros...");
        
        // Gera registros com IDs únicos
        for (int i = 0; i < numRecords; i++) {
            int id;
            do {
                id = 1000 + random.nextInt(9000); // IDs entre 1000-9999
            } while (usedIds.contains(id));
            
            usedIds.add(id);
            
            String description = descriptions[random.nextInt(descriptions.length)];
            double value = 100.0 + (random.nextDouble() * 9900.0); // Valores entre 100-10000
            
            records.add(new DataRecord(id, description, value));
        }
        
        // Ordena automaticamente para usar busca binária
        sortDataById();
        
        System.out.printf("\n✓ %d registros gerados e ordenados com sucesso!\n", numRecords);
        System.out.println("Os dados estão prontos para busca binária.");
    }
    
    private void addDataManually() {
        clearScreen();
        System.out.println("=== ADICIONAR REGISTRO MANUALMENTE ===");
        
        System.out.print("Digite o ID do registro: ");
        int id = scanner.nextInt();
        
        // Verifica se já existe
        if (sortedIds.contains(id)) {
            System.out.println("Erro: ID já existe no sistema!");
            return;
        }
        
        scanner.nextLine(); // Limpa buffer
        System.out.print("Digite a descrição: ");
        String description = scanner.nextLine();
        
        System.out.print("Digite o valor: ");
        double value = scanner.nextDouble();
        
        records.add(new DataRecord(id, description, value));
        
        System.out.println("\nRegistro adicionado. Reordenando dados...");
        sortDataById();
        
        System.out.println("✓ Registro adicionado e dados reordenados com sucesso!");
    }
    
    private void performBinarySearch() {
        clearScreen();
        System.out.println("=== BUSCA BINÁRIA POR ID ===");
        
        if (records.isEmpty()) {
            System.out.println("Sistema vazio! Adicione registros primeiro.");
            return;
        }
        
        if (!isDataSorted()) {
            System.out.println("⚠️  ATENÇÃO: Dados não estão ordenados!");
            System.out.println("A busca binária requer dados ordenados.");
            System.out.print("Deseja ordenar automaticamente? (s/n): ");
            scanner.nextLine();
            String response = scanner.nextLine();
            
            if (response.toLowerCase().startsWith("s")) {
                sortDataById();
                System.out.println("✓ Dados ordenados com sucesso!");
            } else {
                System.out.println("Busca binária cancelada.");
                return;
            }
        }
        
        System.out.print("Digite o ID para buscar: ");
        int targetId = scanner.nextInt();
        
        System.out.println("\nRealizando busca binária...");
        comparisons = 0;
        long startTime = System.nanoTime();
        
        int position = binarySearch(sortedIds, targetId);
        
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0; // ms
        
        displaySearchResults(position, targetId, executionTime, comparisons, "BINÁRIA");
        
        if (position != -1) {
            System.out.printf("Detalhes: %s\n", records.get(position));
        }
        
        displayBinarySearchAnalysis(position, sortedIds.size(), comparisons);
    }
    
    private int binarySearch(List<Integer> array, int target) {
        int left = 0;
        int right = array.size() - 1;
        
        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            
            if (array.get(mid) == target) {
                return mid;
            }
            
            if (array.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    private void performLinearSearchComparison() {
        clearScreen();
        System.out.println("=== COMPARAÇÃO: BUSCA BINÁRIA vs LINEAR ===");
        
        if (records.isEmpty()) {
            System.out.println("Sistema vazio! Adicione registros primeiro.");
            return;
        }
        
        System.out.print("Digite o ID para buscar: ");
        int targetId = scanner.nextInt();
        
        // Busca Linear
        System.out.println("\n1. Executando Busca Linear...");
        comparisons = 0;
        long startTime = System.nanoTime();
        int linearPosition = linearSearch(sortedIds, targetId);
        long linearTime = System.nanoTime();
        int linearComparisons = comparisons;
        
        // Busca Binária (só se estiver ordenado)
        System.out.println("2. Executando Busca Binária...");
        if (!isDataSorted()) sortDataById();
        
        comparisons = 0;
        long binaryStart = System.nanoTime();
        int binaryPosition = binarySearch(sortedIds, targetId);
        long binaryTime = System.nanoTime();
        int binaryComparisons = comparisons;
        
        // Análise comparativa
        displayComparisonResults(targetId, linearPosition, binaryPosition,
                               (linearTime - startTime) / 1_000_000.0,
                               (binaryTime - binaryStart) / 1_000_000.0,
                               linearComparisons, binaryComparisons);
    }
    
    private int linearSearch(List<Integer> array, int target) {
        for (int i = 0; i < array.size(); i++) {
            comparisons++;
            if (array.get(i) == target) {
                return i;
            }
        }
        return -1;
    }
    
    private void displaySearchResults(int position, int targetId, double time, 
                                    int comps, String searchType) {
        System.out.println("\n" + "=".repeat(40));
        System.out.printf("RESULTADO DA BUSCA %s\n", searchType);
        System.out.println("=".repeat(40));
        System.out.printf("Tempo de execução: %.4f ms\n", time);
        System.out.printf("Comparações realizadas: %d\n", comps);
        
        if (position != -1) {
            System.out.println("✓ REGISTRO ENCONTRADO!");
            System.out.printf("Posição no array: %d\n", position);
        } else {
            System.out.println("✗ Registro não encontrado.");
            System.out.printf("ID %d não está presente no sistema.\n", targetId);
        }
    }
    
    private void displayComparisonResults(int targetId, int linearPos, int binaryPos,
                                        double linearTime, double binaryTime,
                                        int linearComps, int binaryComps) {
        clearScreen();
        System.out.println("=== ANÁLISE COMPARATIVA DE PERFORMANCE ===");
        System.out.printf("ID Buscado: %d | Registros: %d\n\n", targetId, records.size());
        
        System.out.println("┌─────────────────┬─────────────┬─────────────┐");
        System.out.println("│     MÉTODO      │   LINEAR    │   BINÁRIA   │");
        System.out.println("├─────────────────┼─────────────┼─────────────┤");
        System.out.printf("│ Tempo (ms)      │   %8.4f  │   %8.4f  │\n", linearTime, binaryTime);
        System.out.printf("│ Comparações     │   %8d  │   %8d  │\n", linearComps, binaryComps);
        System.out.printf("│ Encontrado      │   %8s  │   %8s  │\n", 
                         linearPos != -1 ? "SIM" : "NÃO", 
                         binaryPos != -1 ? "SIM" : "NÃO");
        System.out.println("└─────────────────┴─────────────┴─────────────┘");
        
        // Análise de eficiência
        if (binaryTime > 0 && linearTime > 0) {
            double speedup = linearTime / binaryTime;
            double compReduction = ((double)(linearComps - binaryComps) / linearComps) * 100;
            
            System.out.println("\n📊 ANÁLISE DE EFICIÊNCIA:");
            System.out.printf("• Busca binária foi %.1fx mais rápida\n", speedup);
            System.out.printf("• Redução de %.1f%% nas comparações\n", compReduction);
            System.out.printf("• Economia de %d comparações\n", linearComps - binaryComps);
        }
    }
    
    private void displayBinarySearchAnalysis(int position, int arraySize, int comparisons) {
        System.out.println("\n=== ANÁLISE DA BUSCA BINÁRIA ===");
        
        int maxComparisons = (int) Math.ceil(Math.log(arraySize) / Math.log(2));
        
        System.out.printf("Complexidade teórica: O(log n) onde n = %d\n", arraySize);
        System.out.printf("Máximo de comparações possíveis: %d\n", maxComparisons);
        System.out.printf("Comparações realizadas: %d\n", comparisons);
        
        double efficiency = ((double) comparisons / maxComparisons) * 100;
        System.out.printf("Eficiência desta busca: %.1f%%\n", efficiency);
        
        if (position != -1) {
            if (comparisons == 1) {
                System.out.println("🎯 Melhor caso: Elemento encontrado no centro!");
            } else if (comparisons <= maxComparisons / 2) {
                System.out.println("😊 Caso favorável: Poucas divisões necessárias");
            } else {
                System.out.println("😐 Caso médio: Várias divisões necessárias");
            }
        }
    }
    
    private void sortDataById() {
        records.sort((a, b) -> Integer.compare(a.id, b.id));
        sortedIds.clear();
        for (DataRecord record : records) {
            sortedIds.add(record.id);
        }
    }
    
    private boolean isDataSorted() {
        if (sortedIds.size() != records.size()) {
            // Reconstrói a lista de IDs se necessário
            sortedIds.clear();
            for (DataRecord record : records) {
                sortedIds.add(record.id);
            }
        }
        
        for (int i = 1; i < sortedIds.size(); i++) {
            if (sortedIds.get(i - 1) > sortedIds.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    private void displayAllRecords() {
        clearScreen();
        System.out.println("=== TODOS OS REGISTROS DO SISTEMA ===");
        
        if (records.isEmpty()) {
            System.out.println("Sistema vazio!");
            return;
        }
        
        System.out.printf("Total: %d registros | Ordenado: %s\n\n", 
                         records.size(), isDataSorted() ? "SIM" : "NÃO");
        
        for (int i = 0; i < records.size(); i++) {
            System.out.printf("[%d] %s\n", i, records.get(i));
        }
    }
    
    private void searchByDescription() {
        clearScreen();
        System.out.println("=== BUSCA POR DESCRIÇÃO ===");
        
        if (records.isEmpty()) {
            System.out.println("Sistema vazio!");
            return;
        }
        
        scanner.nextLine(); // Limpa buffer
        System.out.print("Digite a descrição para buscar: ");
        String targetDescription = scanner.nextLine();
        
        System.out.println("\nResultados encontrados:");
        System.out.println("=======================");
        
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).description.toLowerCase().contains(targetDescription.toLowerCase())) {
                System.out.printf("[%d] %s\n", i, records.get(i));
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Nenhum registro encontrado com essa descrição.");
        }
    }
    
    private void displaySearchStatistics() {
        clearScreen();
        System.out.println("=== ESTATÍSTICAS DE BUSCA ===");
        
        System.out.printf("Total de registros: %d\n", records.size());
        System.out.printf("Dados ordenados: %s\n", isDataSorted() ? "SIM" : "NÃO");
        
        if (!records.isEmpty()) {
            System.out.println("\n📈 COMPLEXIDADES DE BUSCA:");
            System.out.println("┌─────────────────┬─────────────┬─────────────┬─────────────┐");
            System.out.println("│     MÉTODO      │ MELHOR CASO │ CASO MÉDIO  │ PIOR CASO   │");
            System.out.println("├─────────────────┼─────────────┼─────────────┼─────────────┤");
            System.out.println("│ Busca Linear    │     O(1)    │    O(n/2)   │     O(n)    │");
            System.out.println("│ Busca Binária   │     O(1)    │  O(log n)   │  O(log n)   │");
            System.out.println("└─────────────────┴─────────────┴─────────────┴─────────────┘");
            
            int n = records.size();
            int logN = (int) Math.ceil(Math.log(n) / Math.log(2));
            
            System.out.printf("\n🎯 PARA SUA BASE ATUAL (%d registros):\n", n);
            System.out.println("┌─────────────────┬─────────────┬─────────────┬─────────────┐");
            System.out.println("│     MÉTODO      │ MELHOR CASO │ CASO MÉDIO  │ PIOR CASO   │");
            System.out.println("├─────────────────┼─────────────┼─────────────┼─────────────┤");
            System.out.printf("│ Linear          │      1      │     %4d    │    %4d     │\n", n/2, n);
            System.out.printf("│ Binária         │      1      │     %4d    │    %4d     │\n", logN/2, logN);
            System.out.println("└─────────────────┴─────────────┴─────────────┴─────────────┘");
            
            // Estatísticas dos IDs
            if (!sortedIds.isEmpty()) {
                int minId = Collections.min(sortedIds);
                int maxId = Collections.max(sortedIds);
                
                System.out.println("\n📊 ESTATÍSTICAS DOS IDs:");
                System.out.printf("• Menor ID: %d\n", minId);
                System.out.printf("• Maior ID: %d\n", maxId);
                System.out.printf("• Intervalo: %d\n", maxId - minId);
            }
        }
    }
    
    private void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private void waitForUser() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
    
    public static void main(String[] args) {
        Ex04BinarySearchDataSystem system = new Ex04BinarySearchDataSystem();
        system.run();
    }
}
