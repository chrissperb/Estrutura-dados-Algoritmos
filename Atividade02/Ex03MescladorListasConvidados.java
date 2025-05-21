/*
 * Mesclador de Listas de Convidados 
 * Este programa permite adicionar convidados a duas listas separadas (Lista A e Lista B),
 * mesclar as listas intercalando os convidados e exibir as listas resultantes.
 *  
 * Autor: Christian Sperb
 * Data: 21/05/2025
 */

package Atividade02;

import java.util.ArrayList;
import java.util.Scanner;

public class Ex03MescladorListasConvidados {
    
    private ArrayList<String> listaA;
    private ArrayList<String> listaB;
    private Scanner scanner;
    
    public Ex03MescladorListasConvidados() {
        listaA = new ArrayList<>();
        listaB = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    // Método para mesclar as listas A e B intercalando os elementos
    // Se uma lista for maior que a outra, adiciona os elementos restantes da lista maior na sequência original
    public ArrayList<String> mesclarListas() {
        ArrayList<String> listaMesclada = new ArrayList<>();
        int tamanhoMaximo = Math.max(listaA.size(), listaB.size());
        
        for (int i = 0; i < tamanhoMaximo; i++) {
            if (i < listaA.size()) {
                listaMesclada.add(listaA.get(i));
            }
            
            if (i < listaB.size()) {
                listaMesclada.add(listaB.get(i));
            }
        }
        
        return listaMesclada;
    }
    
    // Método para adicionar um convidado a uma lista específica
    public void adicionarConvidado(ArrayList<String> lista, String nome) {
        lista.add(nome);
    }
    
    // Método para exibir a lista de convidados
    // Se a lista estiver vazia, exibe uma mensagem informando que a lista está vazia
    public void exibirLista(ArrayList<String> lista, String titulo) {
        if (lista.isEmpty()) {
            System.out.println("A lista " + titulo + " está vazia.");
            return;
        }
        
        System.out.println("\nLista de Convidados " + titulo + ":");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        System.out.println("Total: " + lista.size() + " convidado(s)");
        System.out.println();
    }
    
    // Método para carregar listas de exemplo
    public void carregarListasExemplo() {
        // Lista A
        listaA.add("João Silva");
        listaA.add("Maria Oliveira");
        listaA.add("Pedro Santos");
        listaA.add("Ana Costa");
        
        // Lista B
        listaB.add("Carlos Souza");
        listaB.add("Mariana Pereira");
        listaB.add("Lucas Ferreira");
        listaB.add("Julia Almeida");
        listaB.add("Ricardo Gomes");
        
        System.out.println("Listas de exemplo carregadas com sucesso!");
    }
    
    // Método para limpar uma lista específica
    public void limparLista(ArrayList<String> lista) {
        lista.clear();
        System.out.println("Lista limpa com sucesso!");
    }
    
    public void iniciar() {
        int opcao = 0;
        
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 8);
        
        scanner.close();
    }
    
    private void exibirMenu() {
        System.out.println("===== MESCLADOR DE LISTAS DE CONVIDADOS =====");
        System.out.println("1. Adicionar convidado à Lista A");
        System.out.println("2. Adicionar convidado à Lista B");
        System.out.println("3. Exibir Lista A");
        System.out.println("4. Exibir Lista B");
        System.out.println("5. Mesclar e exibir listas intercaladas");
        System.out.println("6. Carregar listas de exemplo");
        System.out.println("7. Limpar listas");
        System.out.println("8. Sair");
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
                System.out.print("Digite o nome do convidado para a Lista A: ");
                String nomeA = scanner.nextLine();
                adicionarConvidado(listaA, nomeA);
                System.out.println("Convidado adicionado à Lista A com sucesso!");
                break;
                
            case 2:
                System.out.print("Digite o nome do convidado para a Lista B: ");
                String nomeB = scanner.nextLine();
                adicionarConvidado(listaB, nomeB);
                System.out.println("Convidado adicionado à Lista B com sucesso!");
                break;
                
            case 3:
                exibirLista(listaA, "A");
                break;
                
            case 4:
                exibirLista(listaB, "B");
                break;
                
            case 5:
                if (listaA.isEmpty() && listaB.isEmpty()) {
                    System.out.println("Ambas as listas estão vazias. Nada para mesclar.");
                    break;
                }
                
                ArrayList<String> listaMesclada = mesclarListas();
                exibirLista(listaMesclada, "Mesclada (A+B intercalados)");
                break;
                
            case 6:
                limparLista(listaA);
                limparLista(listaB);
                carregarListasExemplo();
                break;
                
            case 7:
                System.out.println("Qual lista você deseja limpar?");
                System.out.println("1. Lista A");
                System.out.println("2. Lista B");
                System.out.println("3. Ambas as listas");
                System.out.print("Escolha uma opção: ");
                
                int opcaoLimpar = lerOpcao();
                switch (opcaoLimpar) {
                    case 1:
                        limparLista(listaA);
                        break;
                    case 2:
                        limparLista(listaB);
                        break;
                    case 3:
                        limparLista(listaA);
                        limparLista(listaB);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
                break;
                
            case 8:
                System.out.println("Encerrando o programa. Até mais!");
                break;
                
            default:
                System.out.println("Opção inválida! Tente novamente.\n");
        }
    }
    
    public static void main(String[] args) {
        Ex03MescladorListasConvidados mesclador = new Ex03MescladorListasConvidados();
        mesclador.iniciar();
    }
}