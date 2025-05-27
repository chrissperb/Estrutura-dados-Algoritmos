/*
 * Agenda de Contatos - Sistema de Busca Linear 
 * Este sistema permite gerenciar uma agenda de contatos
 * utilizando busca linear para encontrar contatos por número de telefone.
 *  
 * Autor: Christian Sperb
 * Data: 27/05/2025
 */

package Atividade03;

import java.util.*;

public class Ex03ContactAgendaSearchSystem {
    
    // Classe interna para representar um contato
    private static class Contact {
        int phoneNumber;
        String name;
        String email;
        
        Contact(int phoneNumber, String name, String email) {
            this.phoneNumber = phoneNumber;
            this.name = name;
            this.email = email;
        }
        
        @Override
        public String toString() {
            return String.format("Tel: %d | Nome: %s | Email: %s", phoneNumber, name, email);
        }
    }
    
    private List<Contact> contacts;
    private List<Integer> phoneNumbers;
    private Scanner scanner;
    private Random random;
    
    public Ex03ContactAgendaSearchSystem() {
        this.contacts = new ArrayList<>();
        this.phoneNumbers = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }
    
    public void run() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    generateRandomContacts();
                    break;
                case 2:
                    addContactManually();
                    break;
                case 3:
                    performLinearSearch();
                    break;
                case 4:
                    displayAllContacts();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    removeContact();
                    break;
                case 7:
                    displayStatistics();
                    break;
                case 8:
                    System.out.println("Encerrando o sistema de agenda...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            waitForUser();
        }
    }
    
    private void displayMenu() {
        clearScreen();
        System.out.println("=== SISTEMA DE BUSCA LINEAR - AGENDA DE CONTATOS ===");
        System.out.println("1. Gerar contatos aleatórios");
        System.out.println("2. Adicionar contato manualmente");
        System.out.println("3. Buscar por número de telefone (Busca Linear)");
        System.out.println("4. Exibir todos os contatos");
        System.out.println("5. Buscar por nome");
        System.out.println("6. Remover contato");
        System.out.println("7. Estatísticas da agenda");
        System.out.println("8. Sair");
        System.out.printf("Total de contatos: %d\n", contacts.size());
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
    
    private void generateRandomContacts() {
        clearScreen();
        System.out.println("=== GERANDO CONTATOS ALEATÓRIOS ===");
        
        String[] names = {
            "Ana Silva", "Bruno Santos", "Carlos Oliveira", "Diana Costa", "Eduardo Lima",
            "Fernanda Rocha", "Gabriel Alves", "Helena Martins", "Igor Ferreira", "Julia Pereira",
            "Kleber Souza", "Larissa Ribeiro", "Marcos Carvalho", "Natália Barbosa", "Otávio Gomes"
        };
        
        String[] domains = {"gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "uol.com.br"};
        
        System.out.print("Quantos contatos deseja gerar? (1-50): ");
        int numContacts = scanner.nextInt();
        numContacts = Math.max(1, Math.min(50, numContacts));
        
        contacts.clear();
        phoneNumbers.clear();
        
        Set<Integer> usedNumbers = new HashSet<>();
        
        System.out.println("\nGerando " + numContacts + " contatos...\n");
        
        for (int i = 0; i < numContacts; i++) {
            // Gera número único
            int phoneNumber;
            do {
                phoneNumber = 10000000 + random.nextInt(90000000); // 8 dígitos
            } while (usedNumbers.contains(phoneNumber));
            
            usedNumbers.add(phoneNumber);
            
            String name = names[random.nextInt(names.length)];
            String email = name.toLowerCase().replace(" ", ".") + "@" + 
                          domains[random.nextInt(domains.length)];
            
            Contact contact = new Contact(phoneNumber, name, email);
            contacts.add(contact);
            phoneNumbers.add(phoneNumber);
            
            System.out.printf("Contato %d: %s\n", i + 1, contact);
        }
        
        System.out.println("\nContatos gerados com sucesso!");
    }
    
    private void addContactManually() {
        clearScreen();
        System.out.println("=== ADICIONAR CONTATO MANUALMENTE ===");
        
        System.out.print("Digite o número de telefone (8 dígitos): ");
        int phoneNumber = scanner.nextInt();
        
        // Verifica se já existe
        if (phoneNumbers.contains(phoneNumber)) {
            System.out.println("Erro: Número já existe na agenda!");
            return;
        }
        
        scanner.nextLine(); // Limpa buffer
        System.out.print("Digite o nome: ");
        String name = scanner.nextLine();
        
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();
        
        Contact contact = new Contact(phoneNumber, name, email);
        contacts.add(contact);
        phoneNumbers.add(phoneNumber);
        
        System.out.println("\nContato adicionado com sucesso:");
        System.out.println(contact);
    }
    
    private void performLinearSearch() {
        clearScreen();
        System.out.println("=== BUSCA LINEAR POR NÚMERO DE TELEFONE ===");
        
        if (phoneNumbers.isEmpty()) {
            System.out.println("Agenda vazia! Adicione contatos primeiro.");
            return;
        }
        
        System.out.print("Digite o número de telefone para buscar: ");
        int targetNumber = scanner.nextInt();
        
        System.out.println("\nRealizando busca linear...");
        long startTime = System.nanoTime();
        
        int position = linearSearch(phoneNumbers, targetNumber);
        
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0; // ms
        
        System.out.println("==============================");
        System.out.printf("Tempo de execução: %.3f ms\n", executionTime);
        System.out.printf("Comparações realizadas: %d\n", position == -1 ? phoneNumbers.size() : position + 1);
        System.out.println("==============================");
        
        if (position != -1) {
            System.out.println("✓ CONTATO ENCONTRADO!");
            System.out.printf("Posição no array: %d\n", position);
            System.out.printf("Detalhes: %s\n", contacts.get(position));
        } else {
            System.out.println("✗ Contato não encontrado na agenda.");
            System.out.println("Número " + targetNumber + " não está presente.");
        }
        
        displaySearchAnalysis(position, phoneNumbers.size());
    }
    
    private int linearSearch(List<Integer> array, int target) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == target) {
                return i;
            }
        }
        return -1;
    }
    
    private void displaySearchAnalysis(int position, int arraySize) {
        System.out.println("\n=== ANÁLISE DA BUSCA LINEAR ===");
        
        if (position != -1) {
            double percentageSearched = ((double)(position + 1) / arraySize) * 100;
            System.out.printf("Eficiência: %.1f%% da lista foi percorrida\n", percentageSearched);
            
            if (position == 0) {
                System.out.println("Melhor caso: Elemento encontrado na primeira posição!");
            } else if (position == arraySize - 1) {
                System.out.println("Pior caso: Elemento encontrado na última posição!");
            } else {
                System.out.println("Caso médio: Elemento encontrado no meio da busca.");
            }
        } else {
            System.out.println("Pior caso: Lista inteira foi percorrida sem sucesso.");
            System.out.println("Complexidade: O(n) - onde n = " + arraySize);
        }
    }
    
    private void displayAllContacts() {
        clearScreen();
        System.out.println("=== TODOS OS CONTATOS DA AGENDA ===");
        
        if (contacts.isEmpty()) {
            System.out.println("Agenda vazia!");
            return;
        }
        
        System.out.printf("Total: %d contatos\n\n", contacts.size());
        
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("[%d] %s\n", i, contacts.get(i));
        }
    }
    
    private void searchByName() {
        clearScreen();
        System.out.println("=== BUSCA POR NOME ===");
        
        if (contacts.isEmpty()) {
            System.out.println("Agenda vazia!");
            return;
        }
        
        scanner.nextLine(); // Limpa buffer
        System.out.print("Digite o nome para buscar: ");
        String targetName = scanner.nextLine();
        
        System.out.println("\nResultados encontrados:");
        System.out.println("=======================");
        
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).name.toLowerCase().contains(targetName.toLowerCase())) {
                System.out.printf("[%d] %s\n", i, contacts.get(i));
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Nenhum contato encontrado com esse nome.");
        }
    }
    
    private void removeContact() {
        clearScreen();
        System.out.println("=== REMOVER CONTATO ===");
        
        if (contacts.isEmpty()) {
            System.out.println("Agenda vazia!");
            return;
        }
        
        System.out.print("Digite o número de telefone do contato a remover: ");
        int phoneNumber = scanner.nextInt();
        
        int position = linearSearch(phoneNumbers, phoneNumber);
        
        if (position != -1) {
            Contact removedContact = contacts.get(position);
            contacts.remove(position);
            phoneNumbers.remove(position);
            
            System.out.println("Contato removido com sucesso:");
            System.out.println(removedContact);
        } else {
            System.out.println("Contato não encontrado!");
        }
    }
    
    private void displayStatistics() {
        clearScreen();
        System.out.println("=== ESTATÍSTICAS DA AGENDA ===");
        
        System.out.printf("Total de contatos: %d\n", contacts.size());
        
        if (!contacts.isEmpty()) {
            System.out.println("\nComplexidade da Busca Linear:");
            System.out.println("• Melhor caso: O(1) - elemento na primeira posição");
            System.out.println("• Caso médio: O(n/2) - elemento no meio");
            System.out.println("• Pior caso: O(n) - elemento na última posição ou não existe");
            
            System.out.printf("\nPara sua agenda atual (%d contatos):\n", contacts.size());
            System.out.printf("• Melhor caso: 1 comparação\n");
            System.out.printf("• Caso médio: %d comparações\n", contacts.size() / 2);
            System.out.printf("• Pior caso: %d comparações\n", contacts.size());
            
            // Estatísticas dos números
            int minNumber = Collections.min(phoneNumbers);
            int maxNumber = Collections.max(phoneNumbers);
            
            System.out.println("\nEstatísticas dos números:");
            System.out.printf("• Menor número: %d\n", minNumber);
            System.out.printf("• Maior número: %d\n", maxNumber);
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
        Ex03ContactAgendaSearchSystem system = new Ex03ContactAgendaSearchSystem();
        system.run();
    }
}
