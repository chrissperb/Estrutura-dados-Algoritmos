/*
 * Inversor de Lista de Tarefas 
 * Este programa simula um gerenciador de tarefas, onde o usuário pode adicionar tarefas e exibi-las em ordem normal ou inversa.
 *  
 * Autor: Christian Sperb
 * Data: 21/05/2025
 */


package Atividade02;

import java.util.ArrayList;
import java.util.Scanner;

public class Ex01InversorListaTarefas {
    
    private ArrayList<String> tarefas;
    private Scanner scanner;
    
    public Ex01InversorListaTarefas() {
        tarefas = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    // Método para adicionar uma nova tarefa à lista
    public void adicionarTarefa(String tarefa) {
        tarefas.add(tarefa);
    }
    
    // Método para obter a lista de tarefas em ordem inversa, sem usar a função reverse da biblioteca Collections
    public ArrayList<String> obterTarefasInvertidas() {
        ArrayList<String> tarefasInvertidas = new ArrayList<>();
        
        for (int i = tarefas.size() - 1; i >= 0; i--) {
            tarefasInvertidas.add(tarefas.get(i));
        }
        
        return tarefasInvertidas;
    }
    
    // Método para exibir a lista de tarefas na ordem em que foi cadastrada
    // Se a lista estiver vazia, exibe uma mensagem informando que não há tarefas cadastradas
    public void exibirTarefas(ArrayList<String> listaTarefas) {
        if (listaTarefas.isEmpty()) {
            System.out.println("Não há tarefas cadastradas.");
            return;
        }
        
        System.out.println("\nLista de Tarefas:");
        for (int i = 0; i < listaTarefas.size(); i++) {
            System.out.println((i + 1) + ". " + listaTarefas.get(i));
        }
        System.out.println();
    }
    
    public void iniciar() {
        int opcao = 0;
        
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 4);
        
        scanner.close();
    }
    
    private void exibirMenu() {
        System.out.println("===== GERENCIADOR DE TAREFAS =====");
        System.out.println("1. Adicionar tarefa");
        System.out.println("2. Exibir tarefas em ordem normal");
        System.out.println("3. Exibir tarefas em ordem inversa");
        System.out.println("4. Sair");
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
                System.out.print("Digite a nova tarefa: ");
                String novaTarefa = scanner.nextLine();
                adicionarTarefa(novaTarefa);
                System.out.println("Tarefa adicionada com sucesso!\n");
                break;
                
            case 2:
                exibirTarefas(tarefas);
                break;
                
            case 3:
                ArrayList<String> tarefasInvertidas = obterTarefasInvertidas();
                exibirTarefas(tarefasInvertidas);
                break;
                
            case 4:
                System.out.println("Encerrando o programa. Até mais!");
                break;
                
            default:
                System.out.println("Opção inválida! Tente novamente.\n");
        }
    }
    
    public static void main(String[] args) {
        Ex01InversorListaTarefas aplicativo = new Ex01InversorListaTarefas();
        aplicativo.iniciar();
    }
}