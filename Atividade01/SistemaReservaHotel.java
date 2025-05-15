package Atividade01;
/*
 * Sistema de Reserva de Hotel
 * Este programa simula um sistema de reserva de hotel, exibindo um comprovante de reserva com os dados do hóspede e informações da estadia.
 * 
 * Autor: Christian Sperb
 * Data: 15/05/2025
 */
public class SistemaReservaHotel {
    public static void main(String[] args) {
        // Declaração das variáveis
        int numeroQuarto;
        double valorDiaria;
        String nomeHospede;
        String nomeHotel;
        String dataCheckIn;
        String dataCheckOut;
        
        // Atribuição de valores de exemplo
        nomeHotel = "Grand Palace Hotel";
        numeroQuarto = 404;
        valorDiaria = 189.90;
        nomeHospede = "Christian Sperb";
        dataCheckIn = "15/05/2025";
        dataCheckOut = "18/05/2025";
        
        // Cálculo da estadia
        int diasEstadia = 3;
        double valorTotal = valorDiaria * diasEstadia;
        
        // Exibição dos valores na tela com frisos e detalhes visuais (as barras da direita estão alinhadas para as variáveis de exemplo) 
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                " + nomeHotel + "                ║");
        System.out.println("║               COMPROVANTE DE RESERVA             ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ DADOS DO HÓSPEDE                                 ║");
        System.out.println("║ Nome: " + nomeHospede + "                            ║");          
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ INFORMAÇÕES DA RESERVA                           ║");
        System.out.println("║ Número do Quarto: " + numeroQuarto + "                            ║");
        System.out.println("║ Tipo de Acomodação: Luxo King" + "                    ║");
        System.out.println("║ Check-in: " + dataCheckIn + "                             ║");
        System.out.println("║ Check-out: " + dataCheckOut + "                            ║");
        System.out.println("║ Duração da Estadia: " + diasEstadia + " dias                       ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ VALORES                                          ║");
        System.out.println("║ Valor da Diária: R$ " + String.format("%.2f", valorDiaria)    + "                       ║");
        System.out.println("║ Valor Total: R$ " + String.format("%.2f", valorTotal) + "                           ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ Agradecemos a preferência!                       ║");
        System.out.println("║ Contato: reservas@grandpalace.com.br             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}