package Projeto;

import java.util.ArrayList;
import java.util.List;

public class CardapioService {
    
    private Cardapio cardapio;

    public CardapioService() {
        this.cardapio = new Cardapio();
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public double getPrecoJusto(List<String> sabores){
        List<String> saboresEncontrados = new ArrayList<>();
        double precoTotal = 0.0;
        int totalSabores = 0;
        for (String sabor : sabores) {
            if (cardapio.containsKey(sabor)) {
                saboresEncontrados.add(sabor);
                totalSabores++;
            }else{
                System.out.println("Pizza "+sabor+" não encontrado!");
            }
        }

        for (String sabor : saboresEncontrados) {
            precoTotal += cardapio.get(sabor)/totalSabores;
        }

        return precoTotal;
    }
}
