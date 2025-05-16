package Atividade01;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Sistema de Identificação de Números Primos para Segurança
 * Este programa encontra e exibe todos os números primos entre 1 e 100 para uso em criptografia.
 *  
 * Autor: Christian Sperb
 * Data: 16/05/2025
 */

public class Ex06IdentificadorNumerosPrimos {
    public static void main(String[] args) {
        // Obtenção da data atual formatada
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = formatador.format(new Date());
        
        // Definindo o intervalo para busca de números primos
        final int LIMITE_INFERIOR = 1;
        final int LIMITE_SUPERIOR = 100;
        
        // ArrayList para armazenar os números primos encontrados
        ArrayList<Integer> numerosPrimos = new ArrayList<>();
        
        // Exibição do cabeçalho
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("     SISTEMA DE IDENTIFICAÇÃO DE NÚMEROS PRIMOS");
        System.out.println("              MÓDULO DE SEGURANÇA");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" PROCESSANDO NÚMEROS PRIMOS...");
        
        // Verifica cada número no intervalo
        for (int numero = LIMITE_INFERIOR; numero <= LIMITE_SUPERIOR; numero++) {
            if (ehPrimo(numero)) {
                numerosPrimos.add(numero);
                System.out.print(" " + numero); // Exibe o número primo em tempo real
                
                // Pausa breve para simular processamento (opcional)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Conta quantos números primos foram encontrados
        int totalPrimos = numerosPrimos.size();
        
        // Exibição dos resultados na tela
        System.out.println("\n\n══════════════════════════════════════════════════");
        System.out.println(" RELATÓRIO DE SEGURANÇA - NÚMEROS PRIMOS");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" INTERVALO ANALISADO: " + LIMITE_INFERIOR + " a " + LIMITE_SUPERIOR);
        System.out.println(" TOTAL DE NÚMEROS PRIMOS ENCONTRADOS: " + totalPrimos);
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" LISTAGEM DE NÚMEROS PRIMOS:");
        
        // Exibe os números primos em linhas de 10 números
        int contador = 0;
        System.out.print(" ");
        for (Integer primo : numerosPrimos) {
            System.out.print(String.format("%2d", primo) + " ");
            contador++;
            
            // Quebra de linha a cada 10 números para melhor visualização
            if (contador % 10 == 0) {
                System.out.println();
                System.out.print(" ");
            }
        }
        
        System.out.println("\n══════════════════════════════════════════════════");
        System.out.println(" RECOMENDAÇÕES DE SEGURANÇA:");
        System.out.println(" • Números primos maiores oferecem maior segurança");
        System.out.println(" • Combine dois ou mais primos para chaves complexas");
        System.out.println(" • Evite usar primos comumente conhecidos (ex: 2, 3, 5)");
        System.out.println(" • Números primos de Mersenne são especialmente úteis");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" PROPRIEDADES DOS NÚMEROS PRIMOS:");
        System.out.println(" • Divisíveis apenas por 1 e por eles mesmos");
        System.out.println(" • Base da criptografia de chave pública (RSA)");
        System.out.println(" • Fundamentais para hash e funções de segurança");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Sistema de Segurança v1.0");
        System.out.println(" Data: " + dataAtual);
        System.out.println("══════════════════════════════════════════════════");
    }
    
    /**
     * Método que verifica se um número é primo
     * @param numero O número a ser verificado
     * @return true se o número for primo, false caso contrário
     */
    private static boolean ehPrimo(int numero) {
        // 1 não é primo por definição
        if (numero <= 1) {
            return false;
        }
        
        // 2 e 3 são primos
        if (numero <= 3) {
            return true;
        }
        
        // Números pares maiores que 2 não são primos
        if (numero % 2 == 0) {
            return false;
        }
        
        // Verificação de divisibilidade até a raiz quadrada do número
        // Otimização: verificamos apenas divisores ímpares
        int raiz = (int) Math.sqrt(numero);
        for (int i = 3; i <= raiz; i += 2) {
            if (numero % i == 0) {
                return false;
            }
        }
        
        return true;
    }
}