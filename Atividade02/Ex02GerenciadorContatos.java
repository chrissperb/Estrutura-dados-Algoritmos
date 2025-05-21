package Atividade02;

import java.util.ArrayList;
import java.util.Scanner;

public class Ex02GerenciadorContatos {
    
    private ArrayList<Contato> contatos;
    private Scanner scanner;
    
    public Ex02GerenciadorContatos() {
        contatos = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    // Método para adicionar um contato
    public void adicionarContato(String nome, String email) {
        contatos.add(new Contato(nome, email));
        System.out.println("Contato adicionado com sucesso!");
    }
    
    // Método para remover contatos duplicados, considerando nome e e-mail e criando uma nova lista
    public void removerDuplicados() {
        if (contatos.isEmpty()) {
            System.out.println("Lista de contatos vazia.");
            return;
        }
        
        ArrayList<Contato> contatosUnicos = new ArrayList<>();
        
        for (Contato contato : contatos) {
            if (!contatoExisteNaLista(contato, contatosUnicos)) {
                contatosUnicos.add(contato);
            }
        }
        
        int duplicadosRemovidos = contatos.size() - contatosUnicos.size();
        contatos = contatosUnicos;
        
        System.out.println(duplicadosRemovidos + " contato(s) duplicado(s) removido(s).");
    }
    
    // Método auxiliar para verificar se um contato já existe na lista
    private boolean contatoExisteNaLista(Contato contato, ArrayList<Contato> lista) {
        for (Contato item : lista) {
            if (item.equals(contato)) {
                return true;
            }
        }
        return false;
    }
    
    // Método para listar os contatos
    public void listarContatos() {
        if (contatos.isEmpty()) {
            System.out.println("Não há contatos cadastrados.");
            return;
        }
        
        System.out.println("\nLista de Contatos:");
        for (int i = 0; i < contatos.size(); i++) {
            System.out.println((i + 1) + ". " + contatos.get(i));
        }
        System.out.println("Total: " + contatos.size() + " contato(s)");
        System.out.println();
    }
    
    public void iniciar() {
        int opcao = 0;
        
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 5);
        
        scanner.close();
    }
    
    private void exibirMenu() {
        System.out.println("===== GERENCIADOR DE CONTATOS =====");
        System.out.println("1. Adicionar contato");
        System.out.println("2. Adicionar contatos de exemplo (alguns duplicados)");
        System.out.println("3. Remover contatos duplicados");
        System.out.println("4. Listar contatos");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                System.out.print("Digite o nome do contato: ");
                String nome = scanner.nextLine();
                System.out.print("Digite o e-mail do contato: ");
                String email = scanner.nextLine();
                adicionarContato(nome, email);
                break;
                
            case 2:
                adicionarContatosExemplo();
                break;
                
            case 3:
                removerDuplicados();
                break;
                
            case 4:
                listarContatos();
                break;
                
            case 5:
                System.out.println("Encerrando o programa. Até mais!");
                break;
                
            default:
                System.out.println("Opção inválida! Tente novamente.\n");
        }
    }
    
    // Método para adicionar contatos de exemplo, incluindo duplicados
    private void adicionarContatosExemplo() {
        adicionarContato("João Silva", "joao@email.com");
        adicionarContato("Maria Oliveira", "maria@email.com");
        adicionarContato("João Silva", "joao@email.com");
        adicionarContato("Pedro Santos", "pedro@email.com");
        adicionarContato("Ana Costa", "ana@email.com");
        adicionarContato("Maria Oliveira", "maria@email.com");
        adicionarContato("Carlos Souza", "carlos@email.com");
        adicionarContato("Ana Costa", "ana@email.com");
        System.out.println("Contatos de exemplo adicionados (incluindo duplicados).");
    }
    
    public static void main(String[] args) {
        Ex02GerenciadorContatos gerenciador = new Ex02GerenciadorContatos();
        gerenciador.iniciar();
    }
    
    // Classe interna para representar um contato
    private static class Contato {
        private String nome;
        private String email;
        
        public Contato(String nome, String email) {
            this.nome = nome;
            this.email = email;
        }
        
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            Contato outro = (Contato) obj;
            return nome.equalsIgnoreCase(outro.nome) && email.equalsIgnoreCase(outro.email);
        }
        
        @Override
        public String toString() {
            return nome + " (" + email + ")";
        }
    }
}
