package Projeto;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal getPrecoJusto(List<String> sabores){
        List<String> saboresEncontrados = new ArrayList<>();
        BigDecimal precoTotal = BigDecimal.ZERO;
        int totalSabores = 0;
        for (String sabor : sabores) {
            if (cardapio.containsKey(sabor)) {
                saboresEncontrados.add(sabor);
                totalSabores++;
            }else{
                System.out.println("Pizza "+sabor+" não encontrado!");
            }
        
            precoTotal = precoTotal.add(
                BigDecimal.valueOf(cardapio.get(sabor))
                    .divide(BigDecimal.valueOf(totalSabores), RoundingMode.HALF_UP)
            );
        }

        return precoTotal;
    }
}
