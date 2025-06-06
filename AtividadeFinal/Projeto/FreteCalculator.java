package Projeto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FreteCalculator {
    private static final BigDecimal TAXA_BASE = new BigDecimal("5.00");
    private static final BigDecimal TAXA_POR_KM = new BigDecimal("1.50");
    private static final BigDecimal TAXA_POR_PIZZA = new BigDecimal("2.00");
    private static final BigDecimal TAXA_HORARIO_PICO = new BigDecimal("3.00");
    private static final int DISTANCIA_MAXIMA = 20;

    public static class FreteInfo {
        private final BigDecimal valorFrete;
        private final int tempoPrevisto;
        private final String detalhes;

        public FreteInfo(BigDecimal valorFrete, int tempoPrevisto, String detalhes) {
            this.valorFrete = valorFrete;
            this.tempoPrevisto = tempoPrevisto;
            this.detalhes = detalhes;
        }

        @Override
        public String toString() {
            return String.format("""
                === Detalhes do Frete ===
                Valor: R$ %.2f
                Tempo previsto: %d minutos
                %s""", valorFrete, tempoPrevisto, detalhes);
        }
    }

    public static FreteInfo calcularFrete(Pedido pedido, double distanciaKm, int hora) {
        if (distanciaKm > DISTANCIA_MAXIMA) {
            throw new IllegalArgumentException(
                    "Distância máxima para entrega é de " + DISTANCIA_MAXIMA + "km");
        }

        StringBuilder detalhes = new StringBuilder();
        BigDecimal valorTotal = TAXA_BASE;
        detalhes.append("Taxa base: R$ ").append(TAXA_BASE).append("\n");

        // Cálculo baseado na distância
        BigDecimal taxaDistancia = TAXA_POR_KM.multiply(BigDecimal.valueOf(distanciaKm));
        valorTotal = valorTotal.add(taxaDistancia);
        detalhes.append("Taxa por distância: R$ ").append(taxaDistancia).append("\n");

        // Cálculo baseado na quantidade de pizzas
        int qtdPizzas = pedido.getPizzas().size();
        BigDecimal taxaPizzas = TAXA_POR_PIZZA.multiply(BigDecimal.valueOf(qtdPizzas));
        valorTotal = valorTotal.add(taxaPizzas);
        detalhes.append("Taxa por quantidade de pizzas: R$ ").append(taxaPizzas).append("\n");

        // Taxa adicional para horário de pico (18h às 21h)
        if (hora >= 18 && hora <= 21) {
            valorTotal = valorTotal.add(TAXA_HORARIO_PICO);
            detalhes.append("Taxa de horário de pico: R$ ").append(TAXA_HORARIO_PICO).append("\n");
        }

        // Cálculo do tempo estimado de entrega
        int tempoPrevisto = calcularTempoPrevisto(distanciaKm, qtdPizzas, hora);

        return new FreteInfo(
                valorTotal.setScale(2, RoundingMode.HALF_UP),
                tempoPrevisto,
                detalhes.toString()
        );
    }

    private static int calcularTempoPrevisto(double distanciaKm, int qtdPizzas, int hora) {
        int tempoBase = 20; // Tempo base em minutos
        int tempoPorKm = 2; // Minutos adicionais por km
        int tempoPorPizza = 5; // Minutos adicionais por pizza

        int tempoTotal = tempoBase;
        tempoTotal += (int) (distanciaKm * tempoPorKm);
        tempoTotal += (qtdPizzas - 1) * tempoPorPizza;

        // Adiciona tempo extra em horário de pico
        if (hora >= 18 && hora <= 21) {
            tempoTotal = (int) (tempoTotal * 1.5);
        }

        return tempoTotal;
    }
}
