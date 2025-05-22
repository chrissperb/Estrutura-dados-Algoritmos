/*
 * Sistema de Gerenciamento de Fila de Atendimento 
 * Este programa permite gerenciar uma fila de atendimento de clientes.
 *  
 * Autor: Christian Sperb
 * Data: 21/05/2025
 */


package Atividade02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Ex07GerenciadorFilaAtendimento {

    public static void main(String[] args) {
        Queue<String> filaClientes = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        
        exibirCabecalho();
        gerenciarFila(filaClientes, scanner);
        scanner.close();
    }

    private static void exibirCabecalho() {
        System.out.println("=================================");
        System.out.println("  SISTEMA DE FILA DE ATENDIMENTO  ");
        System.out.println("=================================\n");
    }

    private static void gerenciarFila(Queue<String> fila, Scanner scanner) {
        int opcao;
        
        do {
            exibirMenu();
            opcao = lerOpcao(scanner);
            
            switch(opcao) {
                case 1:
                    adicionarCliente(fila, scanner);
                    break;
                case 2:
                    atenderProximoCliente(fila);
                    break;
                case 3:
                    exibirFilaAtual(fila);
                    break;
                case 4:
                    System.out.println("\nEncerrando o sistema...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }
        } while(opcao != 4);
    }

    private static void exibirMenu() {
        System.out.println("\nMENU PRINCIPAL:");
        System.out.println("1. Adicionar cliente à fila");
        System.out.println("2. Atender próximo cliente");
        System.out.println("3. Visualizar fila atual");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao(Scanner scanner) {
        while(!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void adicionarCliente(Queue<String> fila, Scanner scanner) {
        scanner.nextLine(); // Limpar buffer
        System.out.print("\nDigite o nome do cliente: ");
        String nome = scanner.nextLine();
        fila.add(nome);
        System.out.println(nome + " foi adicionado(a) à fila.");
    }

    private static void atenderProximoCliente(Queue<String> fila) {
        if(fila.isEmpty()) {
            System.out.println("\nA fila está vazia. Nenhum cliente para atender.");
            return;
        }
        String clienteAtendido = fila.remove();
        System.out.println("\nCliente atendido: " + clienteAtendido);
    }

    private static void exibirFilaAtual(Queue<String> fila) {
        if(fila.isEmpty()) {
            System.out.println("\nA fila está vazia no momento.");
            return;
        }
        
        System.out.println("\nFILA DE ATENDIMENTO:");
        int posicao = 1;
        for(String cliente : fila) {
            System.out.println(posicao + "º - " + cliente);
            posicao++;
        }
    }
}