package Atividade01;

/*
 * Sistema de Contagem de Clientes
 * Este programa registra e conta clientes que entram na loja, usando a hora real do sistema.
 * 
 * Autor: Christian Sperb
 * Data: 15/05/2025
 */

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Ex03SistemaContagemClientes {
    
    // Armazena os dados de entrada de um cliente
    private static class RegistroCliente {
        private int numero;
        private LocalDateTime horaEntrada;
        
        public RegistroCliente(int numero, LocalDateTime horaEntrada) {
            this.numero = numero;
            this.horaEntrada = horaEntrada;
        }
        
        public int getNumero() {
            return numero;
        }
        
        public String getHoraFormatada() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return horaEntrada.format(formatter);
        }
    }
    
    public static void main(String[] args) {
        // Configuração do scanner para entrada do usuário, como mostrado na aula
        Scanner scanner = new Scanner(System.in);
        
        // Declaração das variáveis
        String nomeLoja;
        int totalClientes = 0;
        final int META_CLIENTES = 10; // Sem camelcase para identificar constante que, contudo, pode ser alterada de acordo com a necessidade
        
        // Lista para armazenar os registros de entrada
        List<RegistroCliente> registros = new ArrayList<>();
        
        // Obtém a data e hora atual do sistema
        LocalDateTime dataHoraInicio = LocalDateTime.now();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String dataAtual = dataHoraInicio.format(formatterData);
        
        // Atribuição de valores
        nomeLoja = "SuperMart Departamentos";
        
        // Cabeçalho inicial
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("               " + nomeLoja);
        System.out.println("           SISTEMA DE CONTAGEM DE CLIENTES");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" DATA: " + dataAtual + "     HORA INICIAL: " + dataHoraInicio.format(formatterHora));
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" BEM-VINDO AO SISTEMA DE CONTAGEM!");
        System.out.println(" Este sistema irá registrar a entrada de clientes usando");
        System.out.println(" a hora real do sistema para cada registro.");
        System.out.println("");
        System.out.println(" Pressione ENTER para iniciar o sistema de contagem...");
        scanner.nextLine(); // Aguarda o usuário pressionar Enter para iniciar
        
        boolean continuar = true;
        LocalDateTime horaFim = null;
        
        // Loop para registrar os clientes
        while (continuar && totalClientes < META_CLIENTES) {
            // Limpa o console (tentativa para sistemas compatíveis)
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            // Exibe o cabeçalho atual
            System.out.println("══════════════════════════════════════════════════");
            System.out.println("               " + nomeLoja);
            System.out.println("           SISTEMA DE CONTAGEM DE CLIENTES");
            System.out.println("══════════════════════════════════════════════════");
            System.out.println(" DATA: " + dataAtual + "     HORA INICIAL: " + dataHoraInicio.format(formatterHora));
            System.out.println("══════════════════════════════════════════════════");
            System.out.println(" REGISTRO DE ENTRADA DE CLIENTES:");
            System.out.println("");
            
            // Exibe todos os registros anteriores
            for (RegistroCliente registro : registros) {
                System.out.println(" Cliente #" + registro.getNumero() + 
                                 " - Entrada registrada às " + registro.getHoraFormatada());
                
                // Adiciona uma mensagem especial para o 5º cliente
                if (registro.getNumero() == 5) {
                    System.out.println(" ** Cliente recebeu cupom promocional **");
                }
                
                // Adiciona uma mensagem especial para o 10º cliente
                if (registro.getNumero() == 10) {
                    System.out.println(" ** Cliente nº 10! Meta da hora atingida! **");
                }
            }
            
            System.out.println("");
            System.out.println(" Total atual: " + totalClientes + " / " + META_CLIENTES + " clientes");
            System.out.println("");
            System.out.println("--------------------------------------------------");
            System.out.println(" Digite ENTER quando um cliente entrar na loja ou");
            System.out.println(" digite 'fim' para encerrar a contagem: ");
            String entrada = scanner.nextLine();
            
            if (entrada.equalsIgnoreCase("fim")) {
                horaFim = LocalDateTime.now();
                continuar = false;
            } else {
                // Incrementa o contador e registra a entrada com a hora atual
                totalClientes++;
                LocalDateTime horaEntrada = LocalDateTime.now();
                RegistroCliente novoRegistro = new RegistroCliente(totalClientes, horaEntrada);
                registros.add(novoRegistro);
                
                if (totalClientes == META_CLIENTES) {
                    horaFim = horaEntrada;
                }
            }
        }
        
        // Se não foi definido um horário de fim (saiu antes de atingir a meta)
        if (horaFim == null) {
            horaFim = LocalDateTime.now();
        }
        
        // Calcula a diferença de tempo em minutos
        long minutosTotais = ChronoUnit.MINUTES.between(dataHoraInicio, horaFim);
        if (minutosTotais <= 0) minutosTotais = 1; // Para evitar divisão por zero
        double mediaEntrada = (double) minutosTotais / totalClientes;
        
        // Limpa o console (tentativa para sistemas compatíveis)
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        // Relatório final
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("               " + nomeLoja);
        System.out.println("           SISTEMA DE CONTAGEM DE CLIENTES");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" DATA: " + dataAtual);
        System.out.println(" PERÍODO: " + dataHoraInicio.format(formatterHora) + " - " + horaFim.format(formatterHora));
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" REGISTRO DE ENTRADA DE CLIENTES:");
        System.out.println("");
        
        // Exibe todos os registros
        for (RegistroCliente registro : registros) {
            System.out.println(" Cliente #" + registro.getNumero() + 
                             " - Entrada registrada às " + registro.getHoraFormatada());
            
            // Adiciona uma mensagem especial para o 5º cliente
            if (registro.getNumero() == 5) {
                System.out.println(" ** Cliente deve receber cupom promocional!! **");
            }
            
            // Adiciona uma mensagem especial para o 10º cliente
            if (registro.getNumero() == 10) {
                System.out.println(" ** Cliente nº 10! Meta da hora atingida! **");
            }
        }
        
        System.out.println("");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" RESUMO DO PERÍODO:");
        System.out.println(" Total de clientes registrados: " + totalClientes);
        System.out.println(" Duração total da contagem: " + minutosTotais + " minutos");
        System.out.println(String.format(" Média de entrada: 1 cliente a cada %.1f minutos", mediaEntrada));
        
        // Adiciona uma mensagem sobre o status da meta
        if (totalClientes >= META_CLIENTES) {
            System.out.println(" ✓ META ATINGIDA!");
        } else {
            System.out.println(" ⚠ Meta não alcançada: " + totalClientes + "/" + META_CLIENTES + " clientes");
        }
        
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Relatório gerado automaticamente");
        System.out.println(" Sistema de Contagem v1.0");
        System.out.println("══════════════════════════════════════════════════");
        
        // Fecha o scanner
        scanner.close();
    }
}