package Atividade01;

/*
 * Sistema de Reserva de Hotel
 * Este programa simula um sistema de reserva de hotel, exibindo um comprovante de reserva com os dados do hóspede e informações da estadia.
 * 
 * Autor: Christian Sperb
 * Data: 15/05/2025
 */

 public class Ex03SistemaContagemClientes {
    public static void main(String[] args) {
        // Declaração das variáveis
        String nomeLoja;
        String data;
        String horario;
        int totalClientes;
        
        // Atribuição de valores de exemplo
        nomeLoja = "SuperMart Departamentos";
        data = "15/05/2025";
        horario = "14:00 - 15:00";
        totalClientes = 10;
        
        // Exibição do cabeçalho
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("               " + nomeLoja);
        System.out.println("           SISTEMA DE CONTAGEM DE CLIENTES");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" DATA: " + data + "           HORÁRIO: " + horario);
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" REGISTRO DE ENTRADA DE CLIENTES:");
        System.out.println("");
        
        // Loop para simular a contagem de clientes (1 a 10)
        for (int i = 1; i <= totalClientes; i++) {
            // Calcula um horário fictício para cada cliente
            int minutos = (int)(58.0 / totalClientes * i);
            String horaEntrada = String.format("14:%02d", minutos);
            
            // Exibe informação do cliente
            System.out.println(" Cliente #" + i + " - Entrada registrada às " + horaEntrada);
            
            // Adiciona uma mensagem especial para o 5º cliente
            if (i == 5) {
                System.out.println(" ** Cliente recebeu cupom promocional **");
            }
            
            // Adiciona uma mensagem especial para o 10º cliente
            if (i == 10) {
                System.out.println(" ** Cliente nº 10! Meta da hora atingida! **");
            }
        }
        
        System.out.println("");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" RESUMO DO PERÍODO:");
        System.out.println(" Total de clientes na hora: " + totalClientes);
        System.out.println(" Média de entrada: 1 cliente a cada " + (60 / totalClientes) + " minutos");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Relatório gerado automaticamente");
        System.out.println(" Sistema de Contagem v1.0");
        System.out.println("══════════════════════════════════════════════════");
    }
}