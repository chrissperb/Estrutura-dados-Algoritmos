/*
 * Sistema para Contar Palavras 
 * Este programa conta a frequência de palavras em um texto fornecido pelo usuário.
 *  
 * Autor: Christian Sperb
 * Data: 22/05/2025
 */

package Atividade02;

import java.util.*;
import java.util.stream.Collectors;

public class Ex08ContadorPalavras {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        exibirCabecalho();
        
        while (true) {
            String texto = obterTextoDoUsuario();
            
            if (texto.equalsIgnoreCase("sair")) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("Obrigado por usar o Contador de Palavras!");
                break;
            }
            
            Map<String, Integer> frequenciaPalavras = contarPalavras(texto);
            exibirResultados(frequenciaPalavras, texto);
            
            System.out.println("\n" + "-".repeat(50));
        }
        
        scanner.close();
    }
    
    private static void exibirCabecalho() {
        System.out.println("=".repeat(60));
        System.out.println("           CONTADOR DE FREQUÊNCIA DE PALAVRAS");
        System.out.println("=".repeat(60));
        System.out.println("Este programa analisa a frequência de palavras em um texto.");
        System.out.println("Digite 'sair' para encerrar o programa.");
        System.out.println("=".repeat(60));
    }
    
    private static String obterTextoDoUsuario() {
        System.out.print("\nDigite o texto para análise: ");
        return scanner.nextLine().trim();
    }
    
    private static Map<String, Integer> contarPalavras(String texto) {
        Map<String, Integer> contadorPalavras = new HashMap<>();
        
        String[] palavras = texto.toLowerCase()
                                .replaceAll("[^a-záàâãéêíóôõúç\\s]", "")
                                .split("\\s+");
        
        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                contadorPalavras.put(palavra, contadorPalavras.getOrDefault(palavra, 0) + 1);
            }
        }
        
        return contadorPalavras;
    }
    
    private static void exibirResultados(Map<String, Integer> frequenciaPalavras, String textoOriginal) {
        if (frequenciaPalavras.isEmpty()) {
            System.out.println("\nNenhuma palavra válida encontrada no texto.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESULTADOS DA ANÁLISE");
        System.out.println("=".repeat(50));
        
        exibirEstatisticasGerais(frequenciaPalavras, textoOriginal);
        exibirFrequenciaOrdenada(frequenciaPalavras);
        exibirPalavrasMaisComuns(frequenciaPalavras);
    }
    
    private static void exibirEstatisticasGerais(Map<String, Integer> frequenciaPalavras, String textoOriginal) {
        int totalPalavras = frequenciaPalavras.values().stream().mapToInt(Integer::intValue).sum();
        int palavrasUnicas = frequenciaPalavras.size();
        
        System.out.printf("Total de palavras: %d%n", totalPalavras);
        System.out.printf("Palavras únicas: %d%n", palavrasUnicas);
        System.out.printf("Caracteres no texto: %d%n", textoOriginal.length());
        System.out.println("-".repeat(50));
    }
    
    private static void exibirFrequenciaOrdenada(Map<String, Integer> frequenciaPalavras) {
        System.out.println("\nFREQUÊNCIA DE TODAS AS PALAVRAS:");
        System.out.printf("%-20s | %s%n", "Palavra", "Frequência");
        System.out.println("-".repeat(35));
        
        frequenciaPalavras.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry -> System.out.printf("%-20s | %d%n", entry.getKey(), entry.getValue()));
    }
    
    private static void exibirPalavrasMaisComuns(Map<String, Integer> frequenciaPalavras) {
        System.out.println("\nTOP 5 PALAVRAS MAIS COMUNS:");
        System.out.println("-".repeat(35));
        
        List<Map.Entry<String, Integer>> topPalavras = frequenciaPalavras.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toList());
        
        for (int i = 0; i < topPalavras.size(); i++) {
            Map.Entry<String, Integer> entry = topPalavras.get(i);
            System.out.printf("%d. %-15s (%d ocorrências)%n", 
                            i + 1, entry.getKey(), entry.getValue());
        }
    }
}
