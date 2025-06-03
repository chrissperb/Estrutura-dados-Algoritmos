package Projeto;

import java.math.BigDecimal;
import java.util.List;

public class Pizza {
    private List<String> sabores;
    private BigDecimal preco;
    private final TamanhoPizza tamanho;

    public enum TamanhoPizza {
        BROTO,
        GRANDE,
        GIGA;

        public static TamanhoPizza getByIndex(int index) {
            TamanhoPizza[] tamanhos = TamanhoPizza.values();
            if (index >= 0 && index < tamanhos.length) {
                return tamanhos[index];
            } else {
                throw new IllegalArgumentException("Posição incorreta do index");
            }
        }
    }

    public Pizza(List<String> sabores, BigDecimal preco, TamanhoPizza tamanho) {
        this.sabores = sabores;
        this.preco = preco;
        this.tamanho = tamanho;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setSabores(List<String> sabores) {
        this.sabores = sabores;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "sabores=" + sabores +
                ", preco=" + preco +
                ", tamanho=" + tamanho +
                '}';
    }

}
