package Atividade01;

/*
 * Lista de Espera de Restaurante
 * Este programa simula um sistema de lista de espera para um restaurante, permitindo adicionar e remover clientes da lista.
 * Autor: Christian Sperb
 * Data: 15/05/2025
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ex04SistemaListaEsperaRestaurante {
    
    private static class ListaEspera {
        private String nomeRestaurante;
        private List<Cliente> clientes;
        
        // Construtor
        public ListaEspera(String nomeRestaurante) {
            this.nomeRestaurante = nomeRestaurante;
            this.clientes = new ArrayList<>();
        }
        
        // Getters e Setters - Lombok seria uma boa opção para diminuir o código
        public String getNomeRestaurante() {
            return nomeRestaurante;
        }
        
        public void setNomeRestaurante(String nomeRestaurante) {
            this.nomeRestaurante = nomeRestaurante;
        }
        
        public List<Cliente> getClientes() {
            return clientes;
        }
        
        // Métodos para gerenciar a lista
        public void adicionarCliente(Cliente cliente) {
            clientes.add(cliente);
            cliente.setPosicao(clientes.size());
            exibirLista("CLIENTE ADICIONADO À LISTA DE ESPERA", cliente.getNome());
        }
        
        public void removerCliente(int posicao) {
            if (posicao <= 0 || posicao > clientes.size()) {
                System.out.println("\n Posição inválida! A lista tem " + clientes.size() + " clientes.");
                return;
            }
            
            Cliente clienteRemovido = clientes.remove(posicao - 1);
            
            // Atualiza as posições dos clientes restantes
            for (int i = 0; i < clientes.size(); i++) {
                clientes.get(i).setPosicao(i + 1);
            }
            
            exibirLista("CLIENTE REMOVIDO DA LISTA DE ESPERA", clienteRemovido.getNome());
        }
        
        public void exibirLista(String acao, String nomeCliente) {
            System.out.println("══════════════════════════════════════════════════");
            System.out.println("               " + nomeRestaurante);
            System.out.println("                LISTA DE ESPERA");
            System.out.println("══════════════════════════════════════════════════");
            
            if (acao != null && nomeCliente != null) {
                System.out.println(" " + acao + ": " + nomeCliente);
            }
            
            System.out.println(" Data: 15/05/2025            Hora: 19:30");
            System.out.println("══════════════════════════════════════════════════");
            
            if (clientes.isEmpty()) {
                System.out.println(" Não há clientes na lista de espera.");
            } else {
                System.out.println(" CLIENTES AGUARDANDO:");
                System.out.println("");
                
                for (Cliente cliente : clientes) {
                    System.out.println(" " + cliente.getPosicao() + ". " + cliente.getNome() + 
                                     " - " + cliente.getQuantidadePessoas() + " pessoas" +
                                     " - Contato: " + cliente.getTelefone() +
                                     " - Espera: " + cliente.getTempoEspera() + " min");
                }
            }
            
            System.out.println("");
            System.out.println(" Total de clientes na fila: " + clientes.size());
            System.out.println(" Tempo médio de espera: " + calcularTempoMedioEspera() + " minutos");
            System.out.println("══════════════════════════════════════════════════");
        }
        
        private int calcularTempoMedioEspera() {
            if (clientes.isEmpty()) {
                return 0;
            }
            
            int somaTempos = 0;
            for (Cliente cliente : clientes) {
                somaTempos += cliente.getTempoEspera();
            }
            
            return somaTempos / clientes.size();
        }
    }
    
    private static class Cliente {
        private String nome;
        private String telefone;
        private int quantidadePessoas;
        private int tempoEspera;
        private int posicao;
        
        // Construtor
        public Cliente(String nome, String telefone, int quantidadePessoas, int tempoEspera) {
            this.nome = nome;
            this.telefone = telefone;
            this.quantidadePessoas = quantidadePessoas;
            this.tempoEspera = tempoEspera;
        }
        
        // Getters e Setters
        public String getNome() {
            return nome;
        }
        
        public void setNome(String nome) {
            this.nome = nome;
        }
        
        public String getTelefone() {
            return telefone;
        }
        
        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }
        
        public int getQuantidadePessoas() {
            return quantidadePessoas;
        }
        
        public void setQuantidadePessoas(int quantidadePessoas) {
            this.quantidadePessoas = quantidadePessoas;
        }
        
        public int getTempoEspera() {
            return tempoEspera;
        }
        
        public void setTempoEspera(int tempoEspera) {
            this.tempoEspera = tempoEspera;
        }
        
        public int getPosicao() {
            return posicao;
        }
        
        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
    
    // Método para limpar a tela do console
    private static void limparTela() {
        try {
            // Verifica qual é o sistema operacional sendo utilizado
            String sistemaOperacional = System.getProperty("os.name").toLowerCase();
            
            // Limpa o console de acordo com o sistema operacional
            if (sistemaOperacional.contains("windows")) {
                // Para Windows - usa o comando 'cls'
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Para Unix/Linux/MacOS - usa o comando 'clear'
                new ProcessBuilder("clear").inheritIO().start().waitFor();
                
                // Alternativa: usa caracteres de escape ANSI (funciona em alguns terminais)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Em caso de falha, imprime várias linhas em branco como alternativa
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        // Criação da lista de espera
        ListaEspera lista = new ListaEspera("Sabor & Arte Restaurante");
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        
        // Limpa a tela e exibe a lista inicial (vazia)
        limparTela();
        lista.exibirLista("SISTEMA INICIADO", "Lista vazia");
        
        // Loop principal do programa
        while (executando) {
            System.out.println("\n—————————— MENU DE OPÇÕES ——————————");
            System.out.println(" 1. Adicionar cliente à lista de espera");
            System.out.println(" 2. Remover cliente da lista de espera");
            System.out.println(" 3. Exibir lista de espera atual");
            System.out.println(" 4. Sair do sistema");
            System.out.print("\n Digite sua opção: ");
            
            int opcao = 0;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do scanner
            } catch (Exception e) {
                scanner.nextLine(); // Limpa o buffer do scanner em caso de erro
                System.out.println(" Ops! Opção inválida! Digite um número de 1 a 4.");
                continue;
            }
            
            switch (opcao) {
                case 1:
                    // Limpa a tela antes de adicionar cliente
                    limparTela();
                    
                    // Adicionar cliente
                    System.out.println("\n—————————— ADICIONAR CLIENTE ——————————");
                    
                    System.out.print(" Nome do cliente: ");
                    String nome = scanner.nextLine();
                    
                    System.out.print(" Telefone de contato: ");
                    String telefone = scanner.nextLine();
                    
                    int quantidadePessoas = 0;
                    while (quantidadePessoas <= 0) {
                        try {
                            System.out.print(" Quantidade de pessoas: ");
                            quantidadePessoas = scanner.nextInt();
                            if (quantidadePessoas <= 0) {
                                System.out.println(" Ops! A quantidade deve ser maior que zero.");
                            }
                        } catch (Exception e) {
                            scanner.nextLine(); // Limpa o buffer do scanner
                            System.out.println(" Ops! Por favor, digite um número válido.");
                        }
                    }
                    
                    int tempoEspera = 0;
                    scanner.nextLine(); // Limpa o buffer do scanner
                    while (tempoEspera <= 0) {
                        try {
                            System.out.print(" Tempo estimado de espera (minutos): ");
                            tempoEspera = scanner.nextInt();
                            scanner.nextLine(); // Limpa o buffer do scanner
                            if (tempoEspera <= 0) {
                                System.out.println(" Ops! O tempo deve ser maior que zero.");
                            }
                        } catch (Exception e) {
                            scanner.nextLine(); // Limpa o buffer do scanner
                            System.out.println(" Ops! Por favor, digite um número válido.");
                        }
                    }
                    
                    // Cria e adiciona o cliente à lista
                    Cliente novoCliente = new Cliente(nome, telefone, quantidadePessoas, tempoEspera);
                    
                    // Limpa a tela antes de mostrar a lista atualizada
                    limparTela();
                    lista.adicionarCliente(novoCliente);
                    
                    // Aguarda o usuário pressionar Enter para continuar
                    System.out.print("\n Pressione Enter para continuar...");
                    scanner.nextLine();
                    limparTela();
                    break;
                    
                case 2:
                    // Limpa a tela antes de remover cliente
                    limparTela();
                    
                    // Remover cliente
                    System.out.println("\n—————————— REMOVER CLIENTE ——————————");
                    
                    if (lista.getClientes().isEmpty()) {
                        System.out.println(" ℹ️ A lista de espera está vazia.");
                        // Aguarda o usuário pressionar Enter para continuar
                        System.out.print("\n Pressione Enter para continuar...");
                        scanner.nextLine();
                        limparTela();
                        break;
                    }
                    
                    int posicao = 0;
                    while (posicao <= 0 || posicao > lista.getClientes().size()) {
                        try {
                            System.out.print(" Digite a posição do cliente a ser removido (1-" + 
                                           lista.getClientes().size() + "): ");
                            posicao = scanner.nextInt();
                            scanner.nextLine(); // Limpa o buffer do scanner
                            
                            if (posicao <= 0 || posicao > lista.getClientes().size()) {
                                System.out.println(" Ops! Posição inválida! Digite um número entre 1 e " + 
                                                 lista.getClientes().size() + ".");
                            }
                        } catch (Exception e) {
                            scanner.nextLine(); // Limpa o buffer do scanner
                            System.out.println(" Ops! Por favor, digite um número válido.");
                        }
                    }
                    
                    // Limpa a tela antes de mostrar a lista atualizada
                    limparTela();
                    lista.removerCliente(posicao);
                    
                    // Aguarda o usuário pressionar Enter para continuar
                    System.out.print("\n Pressione Enter para continuar...");
                    scanner.nextLine();
                    limparTela();
                    break;
                    
                case 3:
                    // Limpa a tela antes de exibir a lista
                    limparTela();
                    
                    // Exibir lista atual
                    lista.exibirLista("CONSULTA DE LISTA", "Situação atual");
                    
                    // Aguarda o usuário pressionar Enter para continuar
                    System.out.print("\n Pressione Enter para continuar...");
                    scanner.nextLine();
                    limparTela();
                    break;
                    
                case 4:
                    // Limpa a tela antes de sair
                    limparTela();
                    
                    // Sair do sistema
                    executando = false;
                    System.out.println("\n Encerrando o sistema...");
                    System.out.println(" Obrigado por utilizar o Sistema de Lista de Espera!");
                    break;
                    
                default:
                    System.out.println(" Ops! Opção inválida! Digite um número de 1 a 4.");
                    // Aguarda o usuário pressionar Enter para continuar
                    System.out.print("\n Pressione Enter para continuar...");
                    scanner.nextLine();
                    limparTela();
            }
        }
        
        scanner.close();
    }
}