package Projeto;

public class ClienteService {
            
    private Cliente cliente;

    public ClienteService(String nome, String endereco, String telefone, String email) {
        this.cliente = new Cliente(nome, endereco, telefone, email);
    }

    public Cliente getCliente() {
        return cliente;
    }

}
