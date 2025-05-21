package Atividade02;

import java.util.Scanner;
import java.util.Stack;

public class Ex04VerificadorExpressoes {
    
    private Scanner scanner;
    
    public Ex04VerificadorExpressoes() {
        scanner = new Scanner(System.in);
    }
    
    public boolean verificarBalanceamento(String expressao) {
        Stack<Character> pilha = new Stack<>();
        
        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);
            
            if (caractere == '(' || caractere == '[' || caractere == '{') {
                pilha.push(caractere);
            } else if (caractere == ')' || caractere == ']' || caractere == '}') {
                if (pilha.isEmpty()) {
                    return false; // Caractere de fechamento sem abertura correspondente
                }
                
                char topo = pilha.pop();
                
                // Verifica se o par de delimitadores é válido
                if (!delimitadoresCorrespondem(topo, caractere)) {
                    return false;
                }
            }
        }
        
        // Expressão balanceada se a pilha estiver vazia no final
        return pilha.isEmpty();
    }
    
    private boolean delimitadoresCorrespondem(char abertura, char fechamento) {
        return (abertura == '(' && fechamento == ')') ||
               (abertura == '[' && fechamento == ']') ||
               (abertura == '{' && fechamento == '}');
    }
    
    public void verificarExpressao(String expressao) {
        System.out.println("\nExpressão: " + expressao);
        
        if (verificarBalanceamento(expressao)) {
            System.out.println("✓ Expressão com delimitadores corretamente balanceados.");
        } else {
            System.out.println("✗ Expressão com erro de balanceamento nos delimitadores.");
        }
        
        identificarErrosExpressao(expressao);
    }
    
    public void identificarErrosExpressao(String expressao) {
        Stack<ParentesesInfo> pilha = new Stack<>();
        boolean temErro = false;
        
        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            
            if (c == '(' || c == '[' || c == '{') {
                pilha.push(new ParentesesInfo(c, i));
            } else if (c == ')' || c == ']' || c == '}') {
                if (pilha.isEmpty()) {
                    System.out.println("Erro: Caractere de fechamento '" + c + "' na posição " + (i + 1) + " sem abertura correspondente.");
                    temErro = true;
                } else {
                    ParentesesInfo info = pilha.pop();
                    if (!delimitadoresCorrespondem(info.caractere, c)) {
                        System.out.println("Erro: Delimitador de abertura '" + info.caractere + "' na posição " + 
                                          (info.posicao + 1) + " não corresponde ao fechamento '" + c + "' na posição " + (i + 1) + ".");
                        temErro = true;
                    }
                }
            }
        }
        
        while (!pilha.isEmpty()) {
            ParentesesInfo info = pilha.pop();
            System.out.println("Erro: Delimitador de abertura '" + info.caractere + "' na posição " + 
                              (info.posicao + 1) + " sem fechamento correspondente.");
            temErro = true;
        }
        
        if (!temErro && verificarBalanceamento(expressao)) {
            System.out.println("A expressão está sintaticamente correta quanto aos delimitadores.");
        }
    }
    
    public void avaliarExpressaoMatematica(String expressao) {
        // Verificação básica de sintaxe da expressão matemática
        // Além do balanceamento de parênteses
        boolean valida = true;
        
        // Verifica operadores consecutivos
        if (expressao.contains("++") || expressao.contains("--") || 
            expressao.contains("**") || expressao.contains("//") ||
            expressao.contains("+-") || expressao.contains("-+") ||
            expressao.contains("*/") || expressao.contains("/*")) {
            System.out.println("Aviso: Expressão contém operadores consecutivos, o que pode ser um erro.");
            valida = false;
        }
        
        // Verifica operadores no início ou fim
        if (expressao.length() > 0) {
            char primeiro = expressao.charAt(0);
            char ultimo = expressao.charAt(expressao.length() - 1);
            
            if ((primeiro == '+' || primeiro == '*' || primeiro == '/')) {
                System.out.println("Aviso: Expressão começa com um operador inadequado.");
                valida = false;
            }
            
            if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
                System.out.println("Aviso: Expressão termina com um operador, o que é inválido.");
                valida = false;
            }
        }
        
        if (valida && verificarBalanceamento(expressao)) {
            System.out.println("A expressão matemática parece estar bem formada.");
        }
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
        System.out.println("\n===== VERIFICADOR DE EXPRESSÕES MATEMÁTICAS =====");
        System.out.println("1. Verificar expressão");
        System.out.println("2. Testar expressões de exemplo");
        System.out.println("3. Sobre o verificador");
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
                System.out.print("Digite a expressão matemática: ");
                String expressao = scanner.nextLine();
                verificarExpressao(expressao);
                avaliarExpressaoMatematica(expressao);
                break;
                
            case 2:
                testarExpressoesExemplo();
                break;
                
            case 3:
                exibirSobreVerificador();
                break;
                
            case 4:
                System.out.println("Encerrando o programa. Até mais!");
                break;
                
            default:
                System.out.println("Opção inválida! Tente novamente.\n");
        }
    }
    
    private void testarExpressoesExemplo() {
        System.out.println("\n===== TESTANDO EXPRESSÕES DE EXEMPLO =====");
        
        String[] expressoes = {
            "(2 + 3) * 4",
            "5 + (8 - 2) / 4",
            "((3 + 4) * 2",
            "5 + 7) * 2",
            "(5 + [3 * 2)]",
            "{2 * (3 + 4) - 5}",
            "(2 + 3)) * ((4 - 1)",
            "2 + 3 * 4 - 5",
            "(2+3)*[4-1]/{5+2}"
        };
        
        for (String exp : expressoes) {
            verificarExpressao(exp);
            System.out.println("---------------------------------");
        }
    }
    
    private void exibirSobreVerificador() {
        System.out.println("\n===== SOBRE O VERIFICADOR =====");
        System.out.println("Este programa utiliza o conceito de pilhas para verificar");
        System.out.println("o balanceamento de delimitadores em expressões matemáticas.");
        System.out.println("\nDelimitadores suportados:");
        System.out.println("- Parênteses: ()");
        System.out.println("- Colchetes: []");
        System.out.println("- Chaves: {}");
        System.out.println("\nO programa identifica os seguintes erros:");
        System.out.println("1. Delimitador de fechamento sem abertura correspondente");
        System.out.println("2. Delimitador de abertura sem fechamento correspondente");
        System.out.println("3. Delimitadores que não correspondem (ex: abre com '(' e fecha com ']')");
        System.out.println("\nAlém disso, o programa realiza verificações adicionais da sintaxe,");
        System.out.println("como operadores consecutivos ou no início/fim da expressão.");
    }
    
    // Classe interna para armazenar informações sobre parênteses
    private static class ParentesesInfo {
        char caractere;
        int posicao;
        
        public ParentesesInfo(char caractere, int posicao) {
            this.caractere = caractere;
            this.posicao = posicao;
        }
    }
    
    public static void main(String[] args) {
        Ex04VerificadorExpressoes verificador = new Ex04VerificadorExpressoes();
        verificador.iniciar();
    }
}
