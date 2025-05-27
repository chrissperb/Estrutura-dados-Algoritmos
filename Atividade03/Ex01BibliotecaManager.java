/*
 * Biblioteca Manager 
 * Este programa implementa um sistema de gerenciamento de biblioteca utilizando uma árvore binária de busca.
 * Permite inserir livros, listar livros em diferentes ordens (pré-ordem, em ordem e pós-ordem) e gerenciar os dados dos livros.
 *  
 * Autor: Christian Sperb
 * Data: 27/05/2025
 */

package Atividade03;

import java.util.Scanner;

public class Ex01BibliotecaManager {
    
    // Classe interna para representar um livro
    private static class Livro {
        private final String titulo;
        private final String autor;
        private final int isbn;
        
        public Livro(String titulo, String autor, int isbn) {
            this.titulo = titulo;
            this.autor = autor;
            this.isbn = isbn;
        }
        
        public int getIsbn() {
            return isbn;
        }
        
        @Override
        public String toString() {
            return String.format("ISBN: %d | Título: %s | Autor: %s", isbn, titulo, autor);
        }
    }
    
    // Classe interna para representar um nó da árvore
    private static class No {
        private final Livro livro;
        private No esquerda;
        private No direita;
        
        public No(Livro livro) {
            this.livro = livro;
            this.esquerda = null;
            this.direita = null;
        }
    }
    
    private No raiz;
    private final Scanner scanner;
    
    public Ex01BibliotecaManager() {
        this.raiz = null;
        this.scanner = new Scanner(System.in);
    }
    
    // Método público para inserir livro
    public void inserirLivro(String titulo, String autor, int isbn) {
        Livro novoLivro = new Livro(titulo, autor, isbn);
        raiz = inserirRecursivo(raiz, novoLivro);
        System.out.println("✓ Livro inserido com sucesso!");
    }
    
    // Método privado recursivo para inserção
    private No inserirRecursivo(No atual, Livro livro) {
        if (atual == null) {
            return new No(livro);
        }
        
        if (livro.getIsbn() < atual.livro.getIsbn()) {
            atual.esquerda = inserirRecursivo(atual.esquerda, livro);
        } else if (livro.getIsbn() > atual.livro.getIsbn()) {
            atual.direita = inserirRecursivo(atual.direita, livro);
        } else {
            System.out.println("⚠ Livro com ISBN " + livro.getIsbn() + " já existe na biblioteca!");
        }
        
        return atual;
    }
    
    // Percurso em pré-ordem (Raiz -> Esquerda -> Direita)
    public void percorrerPreOrdem() {
        System.out.println("\n=== PERCURSO PRÉ-ORDEM ===");
        if (raiz == null) {
            System.out.println("Biblioteca vazia.");
            return;
        }
        preOrdemRecursivo(raiz);
    }
    
    private void preOrdemRecursivo(No no) {
        if (no != null) {
            System.out.println(no.livro);
            preOrdemRecursivo(no.esquerda);
            preOrdemRecursivo(no.direita);
        }
    }
    
    // Percurso em ordem (Esquerda -> Raiz -> Direita)
    public void percorrerEmOrdem() {
        System.out.println("\n=== PERCURSO EM ORDEM (ORDENADO POR ISBN) ===");
        if (raiz == null) {
            System.out.println("Biblioteca vazia.");
            return;
        }
        emOrdemRecursivo(raiz);
    }
    
    private void emOrdemRecursivo(No no) {
        if (no != null) {
            emOrdemRecursivo(no.esquerda);
            System.out.println(no.livro);
            emOrdemRecursivo(no.direita);
        }
    }
    
    // Percurso em pós-ordem (Esquerda -> Direita -> Raiz)
    public void percorrerPosOrdem() {
        System.out.println("\n=== PERCURSO PÓS-ORDEM ===");
        if (raiz == null) {
            System.out.println("Biblioteca vazia.");
            return;
        }
        posOrdemRecursivo(raiz);
    }
    
    private void posOrdemRecursivo(No no) {
        if (no != null) {
            posOrdemRecursivo(no.esquerda);
            posOrdemRecursivo(no.direita);
            System.out.println(no.livro);
        }
    }
    
    // Método para exibir o menu
    private void exibirMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       SISTEMA DE BIBLIOTECA         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Inserir livro                    ║");
        System.out.println("║ 2. Listar livros (Pré-ordem)        ║");
        System.out.println("║ 3. Listar livros (Em ordem)         ║");
        System.out.println("║ 4. Listar livros (Pós-ordem)        ║");
        System.out.println("║ 5. Sair                             ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }
    
    // Método para capturar dados do livro
    private void capturarDadosLivro() {
        try {
            System.out.print("\nTítulo do livro: ");
            String titulo = scanner.nextLine().trim();
            
            if (titulo.isEmpty()) {
                System.out.println("❌ Título não pode estar vazio!");
                return;
            }
            
            System.out.print("Autor do livro: ");
            String autor = scanner.nextLine().trim();
            
            if (autor.isEmpty()) {
                System.out.println("❌ Autor não pode estar vazio!");
                return;
            }
            
            System.out.print("ISBN (número inteiro de 10 dígitos): ");
            int isbn = Integer.parseInt(scanner.nextLine().trim());
            
            if (isbn <= 0) {
                System.out.println("❌ ISBN deve ser um número positivo!");
                return;
            }
            
            inserirLivro(titulo, autor, isbn);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ ISBN deve ser um número válido!");
        }
    }
    
    // Método principal para executar o sistema
    public void executar() {
        System.out.println("Bem-vindo ao Sistema de Gerenciamento de Biblioteca!");
        
        while (true) {
            try {
                exibirMenu();
                int opcao = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcao) {
                    case 1:
                        capturarDadosLivro();
                        break;
                    case 2:
                        percorrerPreOrdem();
                        break;
                    case 3:
                        percorrerEmOrdem();
                        break;
                    case 4:
                        percorrerPosOrdem();
                        break;
                    case 5:
                        System.out.println("\n👋 Obrigado por usar o Sistema de Biblioteca!");
                        return;
                    default:
                        System.out.println("❌ Opção inválida! Tente novamente.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite um número válido!");
            }
        }
    }
    
    // Método main para executar o programa
    public static void main(String[] args) {
        Ex01BibliotecaManager biblioteca = new Ex01BibliotecaManager();
        biblioteca.executar();
    }
}