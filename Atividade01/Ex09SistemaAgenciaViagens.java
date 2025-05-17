package Atividade01;

/*
 * Sistema de Cadastro de Clientes para Agência de Viagens
 * Este programa permite cadastrar e gerenciar clientes da agência, registrando seus dados pessoais.
 * 
 * Autor: Christian Sperb
 * Data: 17/05/2025
 */

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ex09SistemaAgenciaViagens {
    
    // Classe Pessoa para armazenar os dados dos clientes
    private static class Pessoa {
        private String nome;
        private int idade;
        private String endereco;
        private String email;
        private String telefone;
        private LocalDateTime dataCadastro;
        
        public Pessoa(String nome, int idade, String endereco, String email, String telefone) {
            this.nome = nome;
            this.idade = idade;
            this.endereco = endereco;
            this.email = email;
            this.telefone = telefone;
            this.dataCadastro = LocalDateTime.now();
        }
        
        public String getNome() {
            return nome;
        }
        
        public int getIdade() {
            return idade;
        }
        
        public String getEndereco() {
            return endereco;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getTelefone() {
            return telefone;
        }
        
        public String getDataCadastroFormatada() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return dataCadastro.format(formatter);
        }
    }
    
    public static void main(String[] args) {
        // Configuração do scanner para entrada do usuário
        Scanner scanner = new Scanner(System.in);
        
        // Declaração das variáveis
        String nomeAgencia;
        List<Pessoa> clientes = new ArrayList<>();
        
        // Obtém a data e hora atual do sistema
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String dataAtual = dataHoraAtual.format(formatterData);
        String horaAtual = dataHoraAtual.format(formatterHora);
        
        // Atribuição de valores
        nomeAgencia = "TravelWorld Agência de Viagens";
        
        // Cabeçalho inicial
        exibirCabecalho(nomeAgencia, dataAtual, horaAtual);
        System.out.println(" BEM-VINDO AO SISTEMA DE CADASTRO DE CLIENTES!");
        System.out.println(" Este sistema irá ajudá-lo a gerenciar os clientes da agência.");
        System.out.println("");
        System.out.println(" Pressione ENTER para iniciar o sistema...");
        scanner.nextLine(); // Aguarda o usuário pressionar Enter para iniciar
        
        boolean continuar = true;
        
        // Loop principal do menu
        while (continuar) {
            // Limpa o console
            limparConsole();
            
            // Exibe o menu
            exibirCabecalho(nomeAgencia, dataAtual, horaAtual);
            System.out.println(" MENU PRINCIPAL:");
            System.out.println("");
            System.out.println(" 1. Cadastrar novo cliente");
            System.out.println(" 2. Listar todos os clientes");
            System.out.println(" 3. Buscar cliente por nome");
            System.out.println(" 4. Exibir estatísticas");
            System.out.println(" 5. Sair do sistema");
            System.out.println("");
            System.out.println("--------------------------------------------------");
            System.out.print(" Digite a opção desejada: ");
            
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1":
                    cadastrarCliente(scanner, clientes);
                    break;
                case "2":
                    listarClientes(clientes);
                    break;
                case "3":
                    buscarClientePorNome(scanner, clientes);
                    break;
                case "4":
                    exibirEstatisticas(clientes);
                    break;
                case "5":
                    continuar = false;
                    break;
                default:
                    System.out.println(" Opção inválida! Pressione ENTER para continuar...");
                    scanner.nextLine();
                    break;
            }
        }
        
        // Relatório final
        limparConsole();
        exibirCabecalho(nomeAgencia, dataAtual, horaAtual);
        System.out.println(" RELATÓRIO FINAL DE SISTEMA:");
        System.out.println("");
        System.out.println(" Total de clientes cadastrados: " + clientes.size());
        
        // Calcula a média de idade
        double mediaIdade = 0;
        if (!clientes.isEmpty()) {
            int somaIdades = 0;
            for (Pessoa cliente : clientes) {
                somaIdades += cliente.getIdade();
            }
            mediaIdade = (double) somaIdades / clientes.size();
        }
        
        System.out.println(String.format(" Média de idade dos clientes: %.1f anos", mediaIdade));
        System.out.println("");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Relatório gerado automaticamente");
        System.out.println(" Sistema de Cadastro de Clientes v1.0");
        System.out.println("══════════════════════════════════════════════════");
        
        System.out.println(" Obrigado por utilizar nosso sistema!");
        System.out.println(" Pressione ENTER para encerrar...");
        scanner.nextLine();
        
        // Fecha o scanner
        scanner.close();
    }
    
    // Método para cadastrar um novo cliente
    private static void cadastrarCliente(Scanner scanner, List<Pessoa> clientes) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             CADASTRO DE NOVO CLIENTE");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        System.out.print(" Nome completo: ");
        String nome = scanner.nextLine();
        
        int idade = 0;
        boolean idadeValida = false;
        while (!idadeValida) {
            System.out.print(" Idade: ");
            try {
                idade = Integer.parseInt(scanner.nextLine());
                idadeValida = true;
            } catch (NumberFormatException e) {
                System.out.println(" Idade inválida. Por favor, digite um número.");
            }
        }
        
        System.out.print(" Endereço completo: ");
        String endereco = scanner.nextLine();
        
        System.out.print(" E-mail: ");
        String email = scanner.nextLine();
        
        System.out.print(" Telefone: ");
        String telefone = scanner.nextLine();
        
        // Cria e adiciona o novo cliente à lista
        Pessoa novoCliente = new Pessoa(nome, idade, endereco, email, telefone);
        clientes.add(novoCliente);
        
        System.out.println("");
        System.out.println(" Cliente cadastrado com sucesso!");
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para listar todos os clientes
    private static void listarClientes(List<Pessoa> clientes) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             LISTAGEM DE CLIENTES");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (clientes.isEmpty()) {
            System.out.println(" Nenhum cliente cadastrado no sistema.");
        } else {
            System.out.println(" TOTAL DE CLIENTES: " + clientes.size());
            System.out.println("");
            
            int contador = 1;
            for (Pessoa cliente : clientes) {
                System.out.println(" CLIENTE #" + contador);
                System.out.println(" Nome: " + cliente.getNome());
                System.out.println(" Idade: " + cliente.getIdade() + " anos");
                System.out.println(" Endereço: " + cliente.getEndereco());
                System.out.println(" E-mail: " + cliente.getEmail());
                System.out.println(" Telefone: " + cliente.getTelefone());
                System.out.println(" Data de Cadastro: " + cliente.getDataCadastroFormatada());
                System.out.println(" ------------------------------------------------");
                contador++;
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }
    
    // Método para buscar cliente por nome
    private static void buscarClientePorNome(Scanner scanner, List<Pessoa> clientes) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             BUSCA DE CLIENTE POR NOME");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (clientes.isEmpty()) {
            System.out.println(" Nenhum cliente cadastrado no sistema.");
        } else {
            System.out.print(" Digite o nome para busca: ");
            String nomeBusca = scanner.nextLine().toLowerCase();
            
            System.out.println("");
            System.out.println(" RESULTADOS DA BUSCA:");
            System.out.println("");
            
            boolean encontrou = false;
            int contador = 1;
            
            for (Pessoa cliente : clientes) {
                if (cliente.getNome().toLowerCase().contains(nomeBusca)) {
                    System.out.println(" CLIENTE #" + contador);
                    System.out.println(" Nome: " + cliente.getNome());
                    System.out.println(" Idade: " + cliente.getIdade() + " anos");
                    System.out.println(" Endereço: " + cliente.getEndereco());
                    System.out.println(" E-mail: " + cliente.getEmail());
                    System.out.println(" Telefone: " + cliente.getTelefone());
                    System.out.println(" Data de Cadastro: " + cliente.getDataCadastroFormatada());
                    System.out.println(" ------------------------------------------------");
                    contador++;
                    encontrou = true;
                }
            }
            
            if (!encontrou) {
                System.out.println(" Nenhum cliente encontrado com esse nome.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para exibir estatísticas
    private static void exibirEstatisticas(List<Pessoa> clientes) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             ESTATÍSTICAS DO SISTEMA");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (clientes.isEmpty()) {
            System.out.println(" Nenhum cliente cadastrado no sistema.");
        } else {
            System.out.println(" Total de clientes cadastrados: " + clientes.size());
            
            // Calcula a média de idade
            int somaIdades = 0;
            int clienteMaisJovem = Integer.MAX_VALUE;
            int clienteMaisVelho = 0;
            
            for (Pessoa cliente : clientes) {
                int idade = cliente.getIdade();
                somaIdades += idade;
                
                if (idade < clienteMaisJovem) {
                    clienteMaisJovem = idade;
                }
                
                if (idade > clienteMaisVelho) {
                    clienteMaisVelho = idade;
                }
            }
            
            double mediaIdade = (double) somaIdades / clientes.size();
            
            System.out.println(String.format(" Média de idade dos clientes: %.1f anos", mediaIdade));
            System.out.println(" Cliente mais jovem: " + clienteMaisJovem + " anos");
            System.out.println(" Cliente mais velho: " + clienteMaisVelho + " anos");
            
            // Contagem por faixa etária
            int menores18 = 0;
            int entre18e30 = 0;
            int entre31e50 = 0;
            int maiores50 = 0;
            
            for (Pessoa cliente : clientes) {
                int idade = cliente.getIdade();
                
                if (idade < 18) {
                    menores18++;
                } else if (idade <= 30) {
                    entre18e30++;
                } else if (idade <= 50) {
                    entre31e50++;
                } else {
                    maiores50++;
                }
            }
            
            System.out.println("");
            System.out.println(" DISTRIBUIÇÃO POR FAIXA ETÁRIA:");
            System.out.println(" Menores de 18 anos: " + menores18 + " (" + String.format("%.1f", (menores18 * 100.0) / clientes.size()) + "%)");
            System.out.println(" Entre 18 e 30 anos: " + entre18e30 + " (" + String.format("%.1f", (entre18e30 * 100.0) / clientes.size()) + "%)");
            System.out.println(" Entre 31 e 50 anos: " + entre31e50 + " (" + String.format("%.1f", (entre31e50 * 100.0) / clientes.size()) + "%)");
            System.out.println(" Acima de 50 anos: " + maiores50 + " (" + String.format("%.1f", (maiores50 * 100.0) / clientes.size()) + "%)");
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }
    
    // Método para exibir o cabeçalho padrão
    private static void exibirCabecalho(String nomeAgencia, String data, String hora) {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("               " + nomeAgencia);
        System.out.println("           SISTEMA DE CADASTRO DE CLIENTES");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" DATA: " + data + "     HORA: " + hora);
        System.out.println("══════════════════════════════════════════════════");
    }
    
    // Método para limpar o console
    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}