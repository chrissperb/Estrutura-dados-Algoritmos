package Atividade01;

public class Ex2VerificadorCaracteristicasProduto {
    public static void main(String[] args) {
        // Declaração das variáveis
        String nomeLoja;
        int codigoProduto;
        String nomeProduto;
        String categoria;
        double precoProduto;
        
        // Atribuição de valores de exemplo
        nomeLoja = "SuperMart Departamentos";
        codigoProduto = 3748;
        nomeProduto = "Monitor LED 24 polegadas";
        categoria = "Eletrônicos";
        precoProduto = 799.90;
        
        // Verificação se o código é par ou ímpar
        String tipoCodigoProduto = (codigoProduto % 2 == 0) ? "PAR" : "ÍMPAR"; // Usei o operador ternário (vulgo Elvis operator) para verificar se o código é par ou ímpar
        
        // Exibição dos valores na tela com frisos horizontais apenas
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("               " + nomeLoja);
        System.out.println("                SISTEMA DE INVENTÁRIO");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" IDENTIFICAÇÃO DO PRODUTO");
        System.out.println(" Nome: " + nomeProduto);
        System.out.println(" Categoria: " + categoria);
        System.out.println(" Código: " + codigoProduto);
        System.out.println(" Preço: R$ " + String.format("%.2f", precoProduto));
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" CLASSIFICAÇÃO PARA INVENTÁRIO");
        System.out.println("");
        System.out.println(" O código " + codigoProduto + " é um número " + tipoCodigoProduto);
        System.out.println("");
        System.out.println(" Produtos com código " + tipoCodigoProduto + " devem ser");
        System.out.println(" armazenados no SETOR " + (tipoCodigoProduto.equals("PAR") ? "A" : "B")); // Usei o operador ternário novamente para determinar o setor
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" INSTRUÇÕES:");
        System.out.println(" • Produtos com código PAR: Inventário mensal");
        System.out.println(" • Produtos com código ÍMPAR: Inventário trimestral");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Sistema de Inventário v1.0");
        System.out.println(" Data: 15/05/2025");
        System.out.println("══════════════════════════════════════════════════");
    }
}