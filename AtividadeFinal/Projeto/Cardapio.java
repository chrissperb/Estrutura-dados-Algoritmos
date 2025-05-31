package Projeto;

import java.util.HashMap;
import java.util.Map;

public class Cardapio {
    private Map<String, Double> cardapio;

    public Cardapio() {
        this.cardapio = new HashMap<>();
        inicializarCardapio();
    }

    private void inicializarCardapio() {
        cardapio.put("Margherita", 30.00);
        cardapio.put("Pepperoni", 35.00);
        cardapio.put("Quatro queijos", 37.00);
        cardapio.put("Calabresa", 33.00);
        cardapio.put("Frango com Catupiry", 36.50);
        cardapio.put("Portuguesa", 33.40);
        cardapio.put("Mussarela", 28.00);
        cardapio.put("Atum", 38.70);
        cardapio.put("Vegetariana", 34.30);
        cardapio.put("Especial da casa", 42.20);
    }

    public Map<String, Double> getCardapio(){
        return cardapio;
    }

    public boolean containsKey(String sabor) {
        return cardapio.containsKey(sabor);
    }

    public double get(String sabor) {
        return cardapio.getOrDefault(sabor, 0.0);
    }

    public String[] keySet() {
        return cardapio.keySet().toArray(new String[0]);
    }

}
    
