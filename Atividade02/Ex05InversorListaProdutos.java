/*
 * Inversor de Lista de Produtos 
 * Este programa permite adicionar produtos a uma lista, inverter a lista usando uma pilha e exibir o resultado.
 *  
 * Autor: Christian Sperb
 * Data: 21/05/2025
 */

package Atividade02;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Ex05InversorListaProdutos {
    
    private ArrayList<Produto> listaProdutos;
    private Scanner scanner;
    
    public Ex05InversorListaProdutos() {
        listaProdutos = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    public void adicionarProduto(String nome, double preco) {
        listaProdutos.add(new Produto(nome, preco));
        System.out.println("Produto adicionado com sucesso!");
    }
    
    public ArrayList<Produto> inverterListaComPilha() {
        Stack<Produto> pilha = new Stack<>();
        
        // Adiciona todos os produtos da lista original à pilha
        for (Produto produto : listaProdutos) {
            pilha.push(produto);
        }
        
        // Cria uma nova lista e adiciona os elementos da pilha (que sairão na ordem inversa)
        ArrayList<Produto> listaInvertida = new ArrayList<>();
        while (!pilha.isEmpty()) {
            listaInvertida.add(pilha.pop());
        }
        
        return listaInvertida;
    }
    
    public void exibirLista(ArrayList<Produto> lista, String titulo) {
        if (lista.isEmpty()) {
            System.out.println("A lista de produtos está vazia.");
            return;
        }
        
        System.out.println("\n==== " + titulo + " ====");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        System.out.println("Total: " + lista.size() + " produto(s)");
        System.out.println();
    }
    
    public void carregarProdutosExemplo() {
        adicionarProduto("Notebook", 3599.99);
        adicionarProduto("Smartphone", 1299.99);
        adicionarProduto("Monitor", 899.50);
        adicionarProduto("Teclado", 159.90);
        adicionarProduto("Mouse", 89.90);
        System.out.println("Produtos de exemplo carregados com sucesso!");
    }
    
    public void limparLista() {
        listaProdutos.clear();
        System.out.println("Lista de produtos limpa com sucesso!");
    }
    
    public void iniciar() {
        int opcao = 0;
        
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 6);
        
        scanner.close();
    }
    
    private void exibirMenu() {
        System.out.println("\n===== INVERSOR DE LISTA DE PRODUTOS =====");
        System.out.println("1. Adicionar produto");
        System.out.println("2. Exibir lista original");
        System.out.println("3. Exibir lista invertida (usando pilha)");
        System.out.println("4. Carregar produtos de exemplo");
        System.out.println("5. Limpar lista de produtos");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarNovoProduto();
                break;
                
            case 2:
                exibirLista(listaProdutos, "Lista Original de Produtos");
                break;
                
            case 3:
                if (listaProdutos.isEmpty()) {
                    System.out.println("A lista de produtos está vazia. Nada para inverter.");
                    break;
                }
                
                ArrayList<Produto> listaInvertida = inverterListaComPilha();
                exibirLista(listaInvertida, "Lista Invertida de Produtos");
                exibirProcessoInversao();
                break;
                
            case 4:
                carregarProdutosExemplo();
                break;
                
            case 5:
                limparLista();
                break;
                
            case 6:
                System.out.println("Encerrando o programa. Até mais!");
                break;
                
            default:
                System.out.println("Opção inválida! Tente novamente.\n");
        }
    }
    
    private void adicionarNovoProduto() {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        
        double preco = 0;
        boolean precoValido = false;
        
        while (!precoValido) {
            System.out.print("Digite o preço do produto: R$ ");
            try {
                preco = Double.parseDouble(scanner.nextLine());
                precoValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido! Digite um valor numérico.");
            }
        }
        
        adicionarProduto(nome, preco);
    }
    
    private void exibirProcessoInversao() {
        if (listaProdutos.isEmpty()) {
            return;
        }
        
        System.out.println("\n===== DEMONSTRAÇÃO DO PROCESSO DE INVERSÃO =====");
        System.out.println("Lista original:");
        for (int i = 0; i < listaProdutos.size(); i++) {
            System.out.println("Posição " + i + ": " + listaProdutos.get(i).getNome());
        }
        
        System.out.println("\nProcesso de empilhamento (inserção na pilha):");
        for (int i = 0; i < listaProdutos.size(); i++) {
            System.out.println("Push: " + listaProdutos.get(i).getNome() + " → topo da pilha");
        }
        
        System.out.println("\nEstado final da pilha (topo → base):");
        for (int i = listaProdutos.size() - 1; i >= 0; i--) {
            System.out.println(listaProdutos.get(i).getNome());
        }
        
        System.out.println("\nProcesso de desempilhamento (remoção da pilha):");
        for (int i = listaProdutos.size() - 1; i >= 0; i--) {
            System.out.println("Pop: " + listaProdutos.get(i).getNome() + " → adicionado à lista invertida");
        }
        
        System.out.println("\nResultado final (lista invertida):");
        for (int i = listaProdutos.size() - 1; i >= 0; i--) {
            System.out.println("Posição " + (listaProdutos.size() - 1 - i) + ": " + listaProdutos.get(i).getNome());
        }
        System.out.println();
    }
    
    // Classe interna para representar um produto
    private static class Produto {
        private String nome;
        private double preco;
        
        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }
        
        public String getNome() {
            return nome;
        }
        
        @Override
        public String toString() {
            return nome + " - R$ " + String.format("%.2f", preco);
        }
    }
    
    public static void main(String[] args) {
        Ex05InversorListaProdutos inversor = new Ex05InversorListaProdutos();
        inversor.iniciar();
    }
}
