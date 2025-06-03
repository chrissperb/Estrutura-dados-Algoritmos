package Projeto;

import java.math.BigDecimal;
import java.util.List;

public class Pedido {
    private final int id;
    private final Cliente cliente;
    private final List<Pizza> pizzas;
    private BigDecimal valorTotal;

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, BigDecimal valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " - Cliente: " + cliente.getNome() +
                " - Qtd Pizzas: " + pizzas.size() +
                " - Valor Total: R$" + valorTotal;
    }

}
