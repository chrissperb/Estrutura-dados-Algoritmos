/*
 * Validator de BST para E-commerce
 * Este programa implementa um validador de árvore binária de busca (BST) para gerenciar categorias de produtos em um e-commerce.
 * Utiliza uma árvore binária de busca para organizar categorias de produtos, permitindo validação da estrutura e exibição hierárquica.
 * Permite gerar uma árvore aleatória ou construir manualmente, validar se é uma BST e exibir a estrutura da árvore.
 *  
 * Autor: Christian Sperb
 * Data: 27/05/2025
 */

package Atividade03;

import java.util.*;

public class Ex02EcommerceBSTValidator {
    
    // Classe interna para representar um nó da árvore de categorias
    private static class CategoryNode {
        int categoryId;
        String categoryName;
        CategoryNode left;
        CategoryNode right;
        
        CategoryNode(int id, String name) {
            this.categoryId = id;
            this.categoryName = name;
            this.left = null;
            this.right = null;
        }
        
        @Override
        public String toString() {
            return String.format("ID: %d - %s", categoryId, categoryName);
        }
    }
    
    private CategoryNode root;
    private Scanner scanner;
    private Random random;
    
    public Ex02EcommerceBSTValidator() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.root = null;
    }
    
    public void run() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    generateRandomTree();
                    break;
                case 2:
                    buildTreeManually();
                    break;
                case 3:
                    validateBST();
                    break;
                case 4:
                    displayTree();
                    break;
                case 5:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            waitForUser();
        }
    }
    
    private void displayMenu() {
        clearScreen();
        System.out.println("=== VALIDADOR DE BST - E-COMMERCE ===");
        System.out.println("1. Gerar árvore aleatória");
        System.out.println("2. Construir árvore manualmente");
        System.out.println("3. Validar se é BST");
        System.out.println("4. Exibir árvore atual");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpa o buffer
            return -1;
        }
    }
    
    private void generateRandomTree() {
        clearScreen();
        System.out.println("=== GERANDO ÁRVORE ALEATÓRIA ===");
        
        String[] categories = {
            "Eletrônicos", "Roupas", "Casa", "Esportes", "Livros",
            "Beleza", "Automóveis", "Jogos", "Música", "Filmes"
        };
        
        root = null;
        int numNodes = 5 + random.nextInt(6); // 5 a 10 nós
        
        System.out.println("Criando árvore com " + numNodes + " categorias...\n");
        
        for (int i = 0; i < numNodes; i++) {
            int id = 10 + random.nextInt(90); // IDs entre 10 e 99
            String name = categories[random.nextInt(categories.length)];
            root = insertNode(root, id, name);
            System.out.println("Inserido: " + id + " - " + name);
        }
        
        System.out.println("\nÁrvore gerada com sucesso!");
    }
    
    private void buildTreeManually() {
        clearScreen();
        System.out.println("=== CONSTRUIR ÁRVORE MANUALMENTE ===");
        root = null;
        
        while (true) {
            System.out.print("Digite o ID da categoria (0 para parar): ");
            int id = scanner.nextInt();
            
            if (id == 0) break;
            
            scanner.nextLine(); // Limpa o buffer
            System.out.print("Digite o nome da categoria: ");
            String name = scanner.nextLine();
            
            root = insertNode(root, id, name);
            System.out.println("Categoria inserida: " + id + " - " + name + "\n");
        }
        
        System.out.println("Árvore construída com sucesso!");
    }
    
    private CategoryNode insertNode(CategoryNode node, int id, String name) {
        if (node == null) {
            return new CategoryNode(id, name);
        }
        
        if (id < node.categoryId) {
            node.left = insertNode(node.left, id, name);
        } else if (id > node.categoryId) {
            node.right = insertNode(node.right, id, name);
        }
        
        return node;
    }
    
    private void validateBST() {
        clearScreen();
        System.out.println("=== VALIDAÇÃO DE BST ===");
        
        if (root == null) {
            System.out.println("Árvore vazia! Crie uma árvore primeiro.");
            return;
        }
        
        boolean isValid = isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        System.out.println("Resultado da validação:");
        System.out.println("========================");
        
        if (isValid) {
            System.out.println("✓ A árvore É uma BST válida!");
            System.out.println("✓ As categorias estão organizadas corretamente.");
            System.out.println("✓ A busca e navegação serão eficientes.");
        } else {
            System.out.println("✗ A árvore NÃO é uma BST válida!");
            System.out.println("✗ As categorias não estão organizadas corretamente.");
            System.out.println("✗ Recomenda-se reorganizar a estrutura.");
        }
        
        // Exibe informações adicionais
        System.out.println("\nInformações da árvore:");
        System.out.println("Altura: " + getHeight(root));
        System.out.println("Número de nós: " + countNodes(root));
    }
    
    private boolean isBST(CategoryNode node, int minVal, int maxVal) {
        if (node == null) {
            return true;
        }
        
        if (node.categoryId <= minVal || node.categoryId >= maxVal) {
            return false;
        }
        
        return isBST(node.left, minVal, node.categoryId) && 
               isBST(node.right, node.categoryId, maxVal);
    }
    
    private void displayTree() {
        clearScreen();
        System.out.println("=== ESTRUTURA DA ÁRVORE ===");
        
        if (root == null) {
            System.out.println("Árvore vazia!");
            return;
        }
        
        System.out.println("Travessia em ordem (In-order):");
        System.out.println("==============================");
        inOrderTraversal(root);
        
        System.out.println("\n\nEstrutura hierárquica:");
        System.out.println("======================");
        printTreeStructure(root, "", true);
    }
    
    private void inOrderTraversal(CategoryNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node);
            inOrderTraversal(node.right);
        }
    }
    
    private void printTreeStructure(CategoryNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node);
            
            List<CategoryNode> children = new ArrayList<>();
            if (node.left != null) children.add(node.left);
            if (node.right != null) children.add(node.right);
            
            for (int i = 0; i < children.size(); i++) {
                String newPrefix = prefix + (isLast ? "    " : "│   ");
                
                if (children.get(i) == node.left) {
                    printTreeStructure(node.left, newPrefix, node.right == null);
                } else {
                    printTreeStructure(node.right, newPrefix, true);
                }
            }
        }
    }
    
    private int getHeight(CategoryNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    private int countNodes(CategoryNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
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
            // Fallback: imprimir linhas vazias
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
        Ex02EcommerceBSTValidator validator = new Ex02EcommerceBSTValidator();
        validator.run();
    }
}