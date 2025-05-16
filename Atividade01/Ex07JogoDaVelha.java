package Atividade01;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Jogo da Velha (Tic-Tac-Toe)
 * Este programa implementa o clássico Jogo da Velha para dois jogadores,
 * utilizando uma matriz para representar o tabuleiro e interface via terminal.
 *  
 * Autor: [Seu Nome]
 * Data: 16/05/2025
 */

public class Ex07JogoDaVelha {
    // Definição de constantes para representar os jogadores e espaços vazios
    private static final char JOGADOR_X = 'X';
    private static final char JOGADOR_O = 'O';
    private static final char VAZIO = ' ';
    
    // Matriz que representa o tabuleiro 3x3
    private static char[][] tabuleiro = new char[3][3];
    
    // Scanner para entrada de dados
    private static Scanner scanner = new Scanner(System.in);
    
    // Armazenar nomes dos jogadores
    private static String nomeJogador1;
    private static String nomeJogador2;
    
    public static void main(String[] args) {
        // Obtenção da data atual formatada
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = formatador.format(new Date());
        
        // Exibição do cabeçalho e instruções
        exibirCabecalho();
        
        // Solicitar os nomes dos jogadores
        System.out.print("Digite o nome do Jogador 1 (X): ");
        nomeJogador1 = scanner.nextLine();
        
        System.out.print("Digite o nome do Jogador 2 (O): ");
        nomeJogador2 = scanner.nextLine();
        
        // Inicializar o tabuleiro (todos os espaços vazios)
        inicializarTabuleiro();
        
        // Jogador atual (X começa)
        char jogadorAtual = JOGADOR_X;
        String nomeJogadorAtual = nomeJogador1;
        
        // Contador de jogadas
        int jogadas = 0;
        
        // Loop principal do jogo
        boolean jogoAtivo = true;
        while (jogoAtivo) {
            // Limpar a tela (simulado com várias quebras de linha)
            limparTela();
            
            // Exibir o tabuleiro atual
            exibirTabuleiro();
            
            // Verificar se há vencedor ou empate
            if (verificarVencedor(JOGADOR_X)) {
                System.out.println("\n══════════════════════════════════════════════════");
                System.out.println("   🎉 PARABÉNS! " + nomeJogador1 + " (X) VENCEU! 🎉");
                System.out.println("══════════════════════════════════════════════════");
                jogoAtivo = false;
                continue;
            } else if (verificarVencedor(JOGADOR_O)) {
                System.out.println("\n══════════════════════════════════════════════════");
                System.out.println("   🎉 PARABÉNS! " + nomeJogador2 + " (O) VENCEU! 🎉");
                System.out.println("══════════════════════════════════════════════════");
                jogoAtivo = false;
                continue;
            } else if (jogadas == 9) {
                System.out.println("\n══════════════════════════════════════════════════");
                System.out.println("        🤝 EMPATE! O JOGO DEU VELHA! 🤝");
                System.out.println("══════════════════════════════════════════════════");
                jogoAtivo = false;
                continue;
            }
            
            // Exibir vez do jogador atual
            System.out.println("\n══════════════════════════════════════════════════");
            System.out.println("   VEZ DE " + nomeJogadorAtual + " (" + jogadorAtual + ")");
            System.out.println("══════════════════════════════════════════════════");
            
            // Solicitar jogada
            boolean jogadaValida = false;
            int linha = 0, coluna = 0;
            
            while (!jogadaValida) {
                System.out.print("Digite a posição (linha [1-3] e coluna [1-3], ex: 1 2): ");
                
                try {
                    linha = scanner.nextInt() - 1; // Ajustando para índice de matriz (0-2)
                    coluna = scanner.nextInt() - 1; // Ajustando para índice de matriz (0-2)
                    
                    // Verificar se a posição é válida
                    if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3) {
                        // Verificar se a posição está vazia
                        if (tabuleiro[linha][coluna] == VAZIO) {
                            jogadaValida = true;
                        } else {
                            System.out.println("Posição já ocupada! Tente novamente.");
                        }
                    } else {
                        System.out.println("Posição inválida! Use valores entre 1 e 3.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida! Digite dois números.");
                    scanner.nextLine(); // Limpar buffer
                }
            }
            
            // Registrar a jogada
            tabuleiro[linha][coluna] = jogadorAtual;
            jogadas++;
            
            // Alternar jogador
            if (jogadorAtual == JOGADOR_X) {
                jogadorAtual = JOGADOR_O;
                nomeJogadorAtual = nomeJogador2;
            } else {
                jogadorAtual = JOGADOR_X;
                nomeJogadorAtual = nomeJogador1;
            }
        }
        
        // Exibir o tabuleiro final
        exibirTabuleiro();
        
        // Perguntar se deseja jogar novamente
        System.out.print("\nDeseja jogar novamente? (S/N): ");
        scanner.nextLine(); // Limpar buffer
        String resposta = scanner.nextLine().toUpperCase();
        
        if (resposta.equals("S")) {
            // Reiniciar o jogo chamando o método main novamente
            main(null);
        } else {
            // Exibir créditos finais
            System.out.println("\n══════════════════════════════════════════════════");
            System.out.println("           OBRIGADO POR JOGAR!");
            System.out.println("══════════════════════════════════════════════════");
            System.out.println(" Jogo da Velha v1.0");
            System.out.println(" Data: " + dataAtual);
            System.out.println("══════════════════════════════════════════════════");
        }
        
        // Fechar o scanner ao final
        scanner.close();
    }
    
    /**
     * Inicializa o tabuleiro com espaços vazios
     */
    private static void inicializarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = VAZIO;
            }
        }
    }
    
    /**
     * Exibe o cabeçalho do jogo
     */
    private static void exibirCabecalho() {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("            🎮 JOGO DA VELHA 🎮");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" INSTRUÇÕES:");
        System.out.println(" • O jogo é jogado em um tabuleiro 3x3");
        System.out.println(" • Jogador 1 usa 'X' e Jogador 2 usa 'O'");
        System.out.println(" • Vence quem completar uma linha, coluna ou diagonal");
        System.out.println(" • Digite a posição como: linha coluna (ex: 1 2)");
        System.out.println("══════════════════════════════════════════════════");
    }
    
    /**
     * Exibe o tabuleiro atual
     */
    private static void exibirTabuleiro() {
        System.out.println("\n        1   2   3  [COLUNAS]");
        System.out.println("      ┌───┬───┬───┐");
        System.out.println("   1  │ " + tabuleiro[0][0] + " │ " + tabuleiro[0][1] + " │ " + tabuleiro[0][2] + " │");
        System.out.println("      ├───┼───┼───┤");
        System.out.println("   2  │ " + tabuleiro[1][0] + " │ " + tabuleiro[1][1] + " │ " + tabuleiro[1][2] + " │");
        System.out.println("      ├───┼───┼───┤");
        System.out.println("   3  │ " + tabuleiro[2][0] + " │ " + tabuleiro[2][1] + " │ " + tabuleiro[2][2] + " │");
        System.out.println("      └───┴───┴───┘");
        System.out.println("   [LINHAS]");
    }
    
    /**
     * Verifica se há um vencedor
     * @param jogador O símbolo do jogador (X ou O)
     * @return true se o jogador venceu, false caso contrário
     */
    private static boolean verificarVencedor(char jogador) {
        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true;
            }
        }
        
        // Verificar colunas
        for (int j = 0; j < 3; j++) {
            if (tabuleiro[0][j] == jogador && tabuleiro[1][j] == jogador && tabuleiro[2][j] == jogador) {
                return true;
            }
        }
        
        // Verificar diagonais
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) {
            return true;
        }
        
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Limpa a tela (simulado com várias quebras de linha)
     */
    private static void limparTela() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}