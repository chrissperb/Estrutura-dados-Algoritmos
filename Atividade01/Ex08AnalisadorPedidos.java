package Atividade01;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Sistema de Análise de Pedidos
 * Este programa analisa uma lista de pedidos de um cliente e identifica
 * qual item é mais frequentemente solicitado.
 *  
 * Autor: [Seu Nome]
 * Data: 16/05/2025
 */

public class Ex08AnalisadorPedidos {
    public static void main(String[] args) {
        // Criação do objeto Scanner para entrada de dados
        Scanner scanner = new Scanner(System.in);
        
        // Obtenção da data atual formatada
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = formatador.format(new Date());
        
        // HashMap para armazenar a contagem de cada código de pedido
        Map<Integer, Integer> contagemPedidos = new HashMap<>();
        
        // HashMap para associar códigos a nomes de produtos (exemplo)
        Map<Integer, String> nomeProdutos = new HashMap<>();
        nomeProdutos.put(1, "Hambúrguer Clássico");
        nomeProdutos.put(2, "Batata Frita");
        nomeProdutos.put(3, "Refrigerante");
        nomeProdutos.put(4, "Milk-shake");
        nomeProdutos.put(5, "Sundae");
        nomeProdutos.put(6, "Salada");
        nomeProdutos.put(7, "Nuggets");
        nomeProdutos.put(8, "Café");
        nomeProdutos.put(9, "Torta");
        nomeProdutos.put(10, "Água Mineral");
        
        // Exibição do cabeçalho
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("          SISTEMA DE ANÁLISE DE PEDIDOS");
        System.out.println("══════════════════════════════════════════════════");
        
        // Exibir o menu de produtos
        System.out.println(" MENU DE PRODUTOS DISPONÍVEIS:");
        System.out.println("──────────────────────────────────────────────────");
        for (Map.Entry<Integer, String> produto : nomeProdutos.entrySet()) {
            System.out.printf(" %2d - %s\n", produto.getKey(), produto.getValue());
        }
        System.out.println("──────────────────────────────────────────────────");
        
        // Solicitar entrada do usuário
        System.out.println("\nDigite os códigos dos pedidos realizados pelo cliente.");
        System.out.println("Digite os números separados por espaço ou um em cada linha.");
        System.out.println("Digite 0 para finalizar a entrada de dados.");
        
        System.out.println("\nDigite os códigos dos pedidos: ");
        
        int codigo;
        int totalPedidos = 0;
        
        // Loop para ler os códigos de pedido até que o usuário digite 0
        while (true) {
            try {
                codigo = scanner.nextInt();
                
                // Verificar se o usuário deseja encerrar a entrada
                if (codigo == 0) {
                    break;
                }
                
                // Verificar se o código é válido
                if (codigo < 0 || !nomeProdutos.containsKey(codigo)) {
                    System.out.println("Código de produto inválido! Use os códigos do menu.");
                    continue;
                }
                
                // Incrementar a contagem para o código atual
                contagemPedidos.put(codigo, contagemPedidos.getOrDefault(codigo, 0) + 1);
                totalPedidos++;
                
                System.out.println("Pedido registrado: " + nomeProdutos.get(codigo) + " (Código: " + codigo + ")");
                
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.nextLine(); // Limpar buffer
            }
        }
        
        // Verificar se há pedidos para analisar
        if (totalPedidos == 0) {
            System.out.println("\nNenhum pedido foi registrado para análise.");
            scanner.close();
            return;
        }
        
        // Encontrar o item mais pedido
        int codigoMaisPedido = -1;
        int quantidadeMaisPedida = 0;
        
        for (Map.Entry<Integer, Integer> entrada : contagemPedidos.entrySet()) {
            if (entrada.getValue() > quantidadeMaisPedida) {
                codigoMaisPedido = entrada.getKey();
                quantidadeMaisPedida = entrada.getValue();
            }
        }
        
        // Verificar se há mais de um item com a mesma contagem máxima
        boolean empate = false;
        StringBuilder itemsEmpatados = new StringBuilder();
        
        for (Map.Entry<Integer, Integer> entrada : contagemPedidos.entrySet()) {
            if (entrada.getValue() == quantidadeMaisPedida && entrada.getKey() != codigoMaisPedido) {
                empate = true;
                itemsEmpatados.append(nomeProdutos.get(entrada.getKey())).append(" (Código: ")
                          .append(entrada.getKey()).append("), ");
            }
        }
        
        // Remover a vírgula e espaço finais se houver
        if (empate && itemsEmpatados.length() > 2) {
            itemsEmpatados.setLength(itemsEmpatados.length() - 2);
        }
        
        // Limpar a tela (simulado com várias quebras de linha)
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
        
        // Exibição dos resultados
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("          RELATÓRIO DE ANÁLISE DE PEDIDOS");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" DATA DA ANÁLISE: " + dataAtual);
        System.out.println(" TOTAL DE ITENS PEDIDOS: " + totalPedidos);
        System.out.println(" VARIEDADE DE ITENS: " + contagemPedidos.size() + " produtos diferentes");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" DETALHAMENTO DOS PEDIDOS:");
        
        // Exibir a contagem de cada item
        for (Map.Entry<Integer, Integer> entrada : contagemPedidos.entrySet()) {
            int codigoProduto = entrada.getKey();
            int quantidade = entrada.getValue();
            double percentual = (quantidade * 100.0) / totalPedidos;
            
            System.out.printf(" • %s (Código: %d): %d pedidos (%.1f%%)\n", 
                    nomeProdutos.get(codigoProduto), codigoProduto, quantidade, percentual);
        }
        
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" RESULTADO DA ANÁLISE:");
        
        if (empate) {
            System.out.println(" HOUVE EMPATE! Os itens mais pedidos foram:");
            System.out.println(" • " + nomeProdutos.get(codigoMaisPedido) + " (Código: " + codigoMaisPedido + ")");
            System.out.println(" • " + itemsEmpatados.toString());
            System.out.println(" Cada um com " + quantidadeMaisPedida + " pedidos.");
        } else {
            System.out.println(" ITEM MAIS PEDIDO: " + nomeProdutos.get(codigoMaisPedido));
            System.out.println(" Código do produto: " + codigoMaisPedido);
            System.out.println(" Quantidade: " + quantidadeMaisPedida + " pedidos");
            System.out.printf(" Percentual: %.1f%% do total\n", (quantidadeMaisPedida * 100.0) / totalPedidos);
        }
        
        // Recomendações baseadas na análise
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" RECOMENDAÇÕES:");
        System.out.println(" • Manter estoque adequado do item mais pedido");
        System.out.println(" • Considerar promoções para itens menos solicitados");
        System.out.println(" • Verificar sazonalidade dos pedidos mais frequentes");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Sistema de Análise de Pedidos v1.0");
        System.out.println(" Data: " + dataAtual);
        System.out.println("══════════════════════════════════════════════════");
        
        // Fechar o scanner
        scanner.close();
    }
}