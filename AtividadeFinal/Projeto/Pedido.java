package Projeto;

import java.math.BigDecimal;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private BigDecimal valorTotal;

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, BigDecimal valorTotal){
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
    }

}
