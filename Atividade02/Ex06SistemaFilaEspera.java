/*
 * Sistema de Fila de Espera 
 * Este programa permite gerenciar uma fila de espera para pacientes em uma clínica médica.
 * Os pacientes podem ser adicionados à fila com diferentes níveis de prioridade (alta, média, baixa).
 *  
 * Autor: Christian Sperb
 * Data: 21/05/2025
 */

package Atividade02;

import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class Ex06SistemaFilaEspera {
    
    private LinkedList<Paciente> filaPacientes;  // Alterado para LinkedList para facilitar remoção
    private Scanner scanner;
    private int proximoNumeroAtendimento;
    private int totalAtendidos;
    
    public Ex06SistemaFilaEspera() {
        filaPacientes = new LinkedList<>();
        scanner = new Scanner(System.in);
        proximoNumeroAtendimento = 1;
        totalAtendidos = 0;
    }
    
    public void adicionarPaciente(String nome, String prioridade) {
        int nivelPrioridade = converterNivelPrioridade(prioridade);
        Paciente novoPaciente = new Paciente(nome, nivelPrioridade, proximoNumeroAtendimento++);
        filaPacientes.add(novoPaciente);
        System.out.println("Paciente " + nome + " adicionado à fila.");
        System.out.println("Senha de atendimento: " + novoPaciente.getNumeroAtendimento());
        System.out.println("Posição atual na fila: " + filaPacientes.size());
    }
    
    private int converterNivelPrioridade(String prioridade) {
        switch (prioridade.toLowerCase()) {
            case "alta":
                return 1;
            case "média":
                return 2;
            case "baixa":
                return 3;
            default:
                return 3; // Padrão é baixa prioridade
        }
    }
    
    public Paciente chamarProximoPaciente() {
        if (filaPacientes.isEmpty()) {
            System.out.println("A fila de espera está vazia.");
            return null;
        }
        
        Paciente pacienteChamado = filaPacientes.poll();
        pacienteChamado.setHorarioChamada(LocalDateTime.now());
        totalAtendidos++;
        
        System.out.println("\n=== PRÓXIMO PACIENTE ===");
        System.out.println("Senha: " + pacienteChamado.getNumeroAtendimento());
        System.out.println("Nome: " + pacienteChamado.getNome());
        System.out.println("Prioridade: " + converterPrioridadeParaTexto(pacienteChamado.getPrioridade()));
        System.out.println("Hora da chamada: " + pacienteChamado.getHorarioChamadaFormatado());
        
        return pacienteChamado;
    }
    
    // Novo método para remover paciente da fila
    public boolean removerPaciente(int numeroAtendimento) {
        if (filaPacientes.isEmpty()) {
            System.out.println("A fila de espera está vazia.");
            return false;
        }
        
        Iterator<Paciente> iterator = filaPacientes.iterator();
        while (iterator.hasNext()) {
            Paciente paciente = iterator.next();
            if (paciente.getNumeroAtendimento() == numeroAtendimento) {
                iterator.remove();
                System.out.println("Paciente " + paciente.getNome() + 
                                  " (Senha: " + paciente.getNumeroAtendimento() + 
                                  ") removido da fila com sucesso.");
                return true;
            }
        }
        
        System.out.println("Paciente com senha " + numeroAtendimento + " não encontrado na fila.");
        return false;
    }
    
    private String converterPrioridadeParaTexto(int prioridade) {
        switch (prioridade) {
            case 1:
                return "Alta";
            case 2:
                return "Média";
            case 3:
                return "Baixa";
            default:
                return "Desconhecida";
        }
    }
    
    public void exibirFila() {
        if (filaPacientes.isEmpty()) {
            System.out.println("A fila de espera está vazia.");
            return;
        }
        
        System.out.println("\n===== FILA DE ESPERA =====");
        System.out.println("Total de pacientes aguardando: " + filaPacientes.size());
        
        int posicao = 1;
        for (Paciente paciente : filaPacientes) {
            System.out.println(posicao + ". Senha " + paciente.getNumeroAtendimento() + 
                              " - " + paciente.getNome() + 
                              " (Prioridade: " + converterPrioridadeParaTexto(paciente.getPrioridade()) + ")");
            posicao++;
        }
        System.out.println();
    }
    
    public void exibirEstatisticas() {
        System.out.println("\n===== ESTATÍSTICAS DE ATENDIMENTO =====");
        System.out.println("Total de pacientes atendidos: " + totalAtendidos);
        System.out.println("Total de pacientes na fila: " + filaPacientes.size());
        System.out.println("Próxima senha a ser distribuída: " + proximoNumeroAtendimento);
        System.out.println();
    }
    
    public void iniciar() {
        int opcao = 0;
        
        System.out.println("===== SISTEMA DE FILA DE ESPERA - CLÍNICA MÉDICA =====");
        System.out.println("Inicializando sistema...");
        
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 7);  // Alterado para 7 devido ao novo item de menu
        
        scanner.close();
    }
    
    private void exibirMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Adicionar paciente à fila");
        System.out.println("2. Chamar próximo paciente");
        System.out.println("3. Visualizar fila de espera");
        System.out.println("4. Remover paciente da fila");  // Nova opção
        System.out.println("5. Estatísticas de atendimento");
        System.out.println("6. Carregar pacientes de exemplo");
        System.out.println("7. Sair");  // Alterado para 7
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
                cadastrarNovoPaciente();
                break;
                
            case 2:
                chamarProximoPaciente();
                break;
                
            case 3:
                exibirFila();
                break;
                
            case 4:  // Nova opção para remover paciente
                removerPacientePorSenha();
                break;
                
            case 5:  // Alterado para 5
                exibirEstatisticas();
                break;
                
            case 6:  // Alterado para 6
                carregarPacientesExemplo();
                break;
                
            case 7:  // Alterado para 7
                System.out.println("Encerrando o sistema de fila de espera. Até mais!");
                break;
                
            default:
                System.out.println("Opção inválida! Tente novamente.\n");
        }
    }
    
    // Novo método para processar a remoção de paciente
    private void removerPacientePorSenha() {
        System.out.println("\n===== REMOVER PACIENTE DA FILA =====");
        
        // Exibe a fila antes da remoção para facilitar a visualização
        exibirFila();
        
        if (filaPacientes.isEmpty()) {
            return;  // A exibirFila já mostra mensagem de fila vazia
        }
        
        System.out.print("Digite o número da senha do paciente a ser removido: ");
        try {
            int senha = Integer.parseInt(scanner.nextLine());
            removerPaciente(senha);
        } catch (NumberFormatException e) {
            System.out.println("Senha inválida! A senha deve ser um número.");
        }
    }
    
    private void cadastrarNovoPaciente() {
        System.out.println("\n===== CADASTRO DE PACIENTE =====");
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        
        System.out.println("Prioridade de atendimento:");
        System.out.println("1. Alta");
        System.out.println("2. Média");
        System.out.println("3. Baixa");
        System.out.print("Escolha a prioridade: ");
        
        String prioridade;
        try {
            int opcaoPrioridade = Integer.parseInt(scanner.nextLine());
            switch (opcaoPrioridade) {
                case 1:
                    prioridade = "alta";
                    break;
                case 2:
                    prioridade = "média";
                    break;
                default:
                    prioridade = "baixa";
                    break;
            }
        } catch (NumberFormatException e) {
            prioridade = "baixa";
            System.out.println("Opção inválida! Definindo prioridade como baixa.");
        }
        
        adicionarPaciente(nome, prioridade);
    }
    
    private void carregarPacientesExemplo() {
        adicionarPaciente("Carlos Silva", "alta");
        adicionarPaciente("Maria Oliveira", "média");
        adicionarPaciente("João Pereira", "baixa");
        adicionarPaciente("Ana Santos", "alta");
        adicionarPaciente("Pedro Costa", "média");
        
        System.out.println("Pacientes de exemplo adicionados com sucesso!");
    }
    
    // Classe interna para representar um paciente
    private static class Paciente {
        private String nome;
        private int prioridade;  // 1 = Alta, 2 = Média, 3 = Baixa
        private int numeroAtendimento;
        private LocalDateTime horarioChamada;
        
        public Paciente(String nome, int prioridade, int numeroAtendimento) {
            this.nome = nome;
            this.prioridade = prioridade;
            this.numeroAtendimento = numeroAtendimento;
        }
        
        public String getNome() {
            return nome;
        }
        
        public int getPrioridade() {
            return prioridade;
        }
        
        public int getNumeroAtendimento() {
            return numeroAtendimento;
        }
        
        public void setHorarioChamada(LocalDateTime horarioChamada) {
            this.horarioChamada = horarioChamada;
        }
        
        public String getHorarioChamadaFormatado() {
            if (horarioChamada == null) {
                return "Ainda não chamado";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return horarioChamada.format(formatter);
        }
    }
    
    public static void main(String[] args) {
        Ex06SistemaFilaEspera sistema = new Ex06SistemaFilaEspera();
        sistema.iniciar();
    }
}