package Atividade01;

/*
 * Sistema de Gerenciamento de Estoque para Loja
 * Este programa permite adicionar, remover e atualizar itens no estoque, gerenciando produtos disponíveis para venda.
 * 
 * Autor: Christian Sperb
 * Data: 17/05/2025
 */

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ex10SistemaGerenciamentoEstoque {
    
    // Classe Produto para armazenar os dados dos itens no estoque
    private static class Produto {
        private String codigo;
        private String nome;
        private String categoria;
        private double preco;
        private int quantidade;
        private String fornecedor;
        private LocalDateTime dataCadastro;
        private LocalDateTime dataAtualizacao;
        
        public Produto(String codigo, String nome, String categoria, double preco, int quantidade, String fornecedor) {
            this.codigo = codigo;
            this.nome = nome;
            this.categoria = categoria;
            this.preco = preco;
            this.quantidade = quantidade;
            this.fornecedor = fornecedor;
            this.dataCadastro = LocalDateTime.now();
            this.dataAtualizacao = LocalDateTime.now();
        }
        
        public String getCodigo() {
            return codigo;
        }
        
        public String getNome() {
            return nome;
        }
        
        public void setNome(String nome) {
            this.nome = nome;
            this.dataAtualizacao = LocalDateTime.now();
        }
        
        public String getCategoria() {
            return categoria;
        }
        
        public void setCategoria(String categoria) {
            this.categoria = categoria;
            this.dataAtualizacao = LocalDateTime.now();
        }
        
        public double getPreco() {
            return preco;
        }
        
        public void setPreco(double preco) {
            this.preco = preco;
            this.dataAtualizacao = LocalDateTime.now();
        }
        
        public int getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
            this.dataAtualizacao = LocalDateTime.now();
        }
        
        public String getFornecedor() {
            return fornecedor;
        }
        
        public void setFornecedor(String fornecedor) {
            this.fornecedor = fornecedor;
            this.dataAtualizacao = LocalDateTime.now();
        }
        
        public String getDataCadastroFormatada() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return dataCadastro.format(formatter);
        }
        
        public String getDataAtualizacaoFormatada() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return dataAtualizacao.format(formatter);
        }
        
        public double getValorEmEstoque() {
            return preco * quantidade;
        }
    }
    
    public static void main(String[] args) {
        // Configuração do scanner para entrada do usuário
        Scanner scanner = new Scanner(System.in);
        
        // Declaração das variáveis
        String nomeLoja;
        List<Produto> estoque = new ArrayList<>();
        
        // Obtém a data e hora atual do sistema
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String dataAtual = dataHoraAtual.format(formatterData);
        String horaAtual = dataHoraAtual.format(formatterHora);
        
        // Atribuição de valores
        nomeLoja = "SuperMercado Central";
        
        // Cabeçalho inicial
        exibirCabecalho(nomeLoja, dataAtual, horaAtual);
        System.out.println(" BEM-VINDO AO SISTEMA DE GERENCIAMENTO DE ESTOQUE!");
        System.out.println(" Este sistema irá ajudá-lo a gerenciar os produtos disponíveis para venda.");
        System.out.println("");
        System.out.println(" Pressione ENTER para iniciar o sistema...");
        scanner.nextLine(); // Aguarda o usuário pressionar Enter para iniciar
        
        // Adicionando alguns produtos de exemplo ao estoque
        estoque.add(new Produto("P001", "Arroz 5kg", "Alimentos", 22.90, 50, "Distribuidora Grãos"));
        estoque.add(new Produto("P002", "Feijão 1kg", "Alimentos", 7.50, 30, "Distribuidora Grãos"));
        estoque.add(new Produto("P003", "Sabonete", "Higiene", 2.25, 100, "Higiênica Ltda"));
        estoque.add(new Produto("P004", "Refrigerante 2L", "Bebidas", 8.90, 40, "Refrigerantes Brasil"));
        
        boolean continuar = true;
        
        // Loop principal do menu
        while (continuar) {
            // Limpa o console
            limparConsole();
            
            // Exibe o menu
            exibirCabecalho(nomeLoja, dataAtual, horaAtual);
            System.out.println(" MENU PRINCIPAL:");
            System.out.println("");
            System.out.println(" 1. Adicionar novo produto");
            System.out.println(" 2. Listar todos os produtos");
            System.out.println(" 3. Buscar produto por código");
            System.out.println(" 4. Buscar produto por nome");
            System.out.println(" 5. Atualizar produto");
            System.out.println(" 6. Remover produto");
            System.out.println(" 7. Adicionar quantidade ao estoque");
            System.out.println(" 8. Remover quantidade do estoque");
            System.out.println(" 9. Exibir estatísticas do estoque");
            System.out.println(" 10. Sair do sistema");
            System.out.println("");
            System.out.println("--------------------------------------------------");
            System.out.print(" Digite a opção desejada: ");
            
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1":
                    adicionarProduto(scanner, estoque);
                    break;
                case "2":
                    listarProdutos(estoque);
                    break;
                case "3":
                    buscarProdutoPorCodigo(scanner, estoque);
                    break;
                case "4":
                    buscarProdutoPorNome(scanner, estoque);
                    break;
                case "5":
                    atualizarProduto(scanner, estoque);
                    break;
                case "6":
                    removerProduto(scanner, estoque);
                    break;
                case "7":
                    adicionarQuantidade(scanner, estoque);
                    break;
                case "8":
                    removerQuantidade(scanner, estoque);
                    break;
                case "9":
                    exibirEstatisticas(estoque);
                    break;
                case "10":
                    continuar = false;
                    break;
                default:
                    System.out.println(" Opção inválida! Pressione ENTER para continuar...");
                    scanner.nextLine();
                    break;
            }
        }
        
        // Relatório final
        limparConsole();
        exibirCabecalho(nomeLoja, dataAtual, horaAtual);
        System.out.println(" RELATÓRIO FINAL DO SISTEMA:");
        System.out.println("");
        System.out.println(" Total de produtos cadastrados: " + estoque.size());
        
        // Calcula o valor total em estoque
        double valorTotalEstoque = 0;
        for (Produto produto : estoque) {
            valorTotalEstoque += produto.getValorEmEstoque();
        }
        
        System.out.println(String.format(" Valor total em estoque: R$ %.2f", valorTotalEstoque));
        System.out.println("");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Relatório gerado automaticamente");
        System.out.println(" Sistema de Gerenciamento de Estoque v1.0");
        System.out.println("══════════════════════════════════════════════════");
        
        System.out.println(" Obrigado por utilizar nosso sistema!");
        System.out.println(" Pressione ENTER para encerrar...");
        scanner.nextLine();
        
        // Fecha o scanner
        scanner.close();
    }
    
    // Método para adicionar um novo produto
    private static void adicionarProduto(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             CADASTRO DE NOVO PRODUTO");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        String codigo = "";
        boolean codigoValido = false;
        
        while (!codigoValido) {
            System.out.print(" Código do produto: ");
            codigo = scanner.nextLine();
            
            // Verifica se o código já existe
            boolean codigoExistente = false;
            for (Produto produto : estoque) {
                if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                    codigoExistente = true;
                    break;
                }
            }
            
            if (codigoExistente) {
                System.out.println(" Código já existe! Por favor, use outro código.");
            } else {
                codigoValido = true;
            }
        }
        
        System.out.print(" Nome do produto: ");
        String nome = scanner.nextLine();
        
        System.out.print(" Categoria: ");
        String categoria = scanner.nextLine();
        
        double preco = 0;
        boolean precoValido = false;
        while (!precoValido) {
            System.out.print(" Preço unitário (R$): ");
            try {
                preco = Double.parseDouble(scanner.nextLine().replace(",", "."));
                if (preco >= 0) {
                    precoValido = true;
                } else {
                    System.out.println(" O preço não pode ser negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Preço inválido. Por favor, digite um número válido.");
            }
        }
        
        int quantidade = 0;
        boolean quantidadeValida = false;
        while (!quantidadeValida) {
            System.out.print(" Quantidade em estoque: ");
            try {
                quantidade = Integer.parseInt(scanner.nextLine());
                if (quantidade >= 0) {
                    quantidadeValida = true;
                } else {
                    System.out.println(" A quantidade não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Quantidade inválida. Por favor, digite um número inteiro.");
            }
        }
        
        System.out.print(" Fornecedor: ");
        String fornecedor = scanner.nextLine();
        
        // Cria e adiciona o novo produto à lista
        Produto novoProduto = new Produto(codigo, nome, categoria, preco, quantidade, fornecedor);
        estoque.add(novoProduto);
        
        System.out.println("");
        System.out.println(" Produto cadastrado com sucesso!");
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para listar todos os produtos
    private static void listarProdutos(List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             LISTAGEM DE PRODUTOS");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.println(" TOTAL DE PRODUTOS: " + estoque.size());
            System.out.println("");
            
            System.out.println(" CÓDIGO | PRODUTO                 | CATEGORIA      | PREÇO (R$) | QTD | VALOR TOTAL (R$)");
            System.out.println("--------|-------------------------|----------------|------------|-----|----------------");
            
            double valorTotal = 0;
            
            for (Produto produto : estoque) {
                String codigoFormatado = String.format("%-6s", produto.getCodigo());
                String nomeFormatado = produto.getNome();
                if (nomeFormatado.length() > 23) {
                    nomeFormatado = nomeFormatado.substring(0, 20) + "...";
                } else {
                    nomeFormatado = String.format("%-23s", nomeFormatado);
                }
                
                String categoriaFormatada = produto.getCategoria();
                if (categoriaFormatada.length() > 14) {
                    categoriaFormatada = categoriaFormatada.substring(0, 11) + "...";
                } else {
                    categoriaFormatada = String.format("%-14s", categoriaFormatada);
                }
                
                String precoFormatado = String.format("%10.2f", produto.getPreco());
                String quantidadeFormatada = String.format("%3d", produto.getQuantidade());
                String valorEmEstoqueFormatado = String.format("%14.2f", produto.getValorEmEstoque());
                
                System.out.println(" " + codigoFormatado + " | " + nomeFormatado + " | " + 
                                   categoriaFormatada + " | " + precoFormatado + " | " + 
                                   quantidadeFormatada + " | " + valorEmEstoqueFormatado);
                
                valorTotal += produto.getValorEmEstoque();
            }
            
            System.out.println("--------|-------------------------|----------------|------------|-----|----------------");
            System.out.println(String.format(" VALOR TOTAL EM ESTOQUE: R$ %.2f", valorTotal));
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }
    
    // Método para buscar produto por código
    private static void buscarProdutoPorCodigo(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             BUSCA DE PRODUTO POR CÓDIGO");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.print(" Digite o código para busca: ");
            String codigoBusca = scanner.nextLine();
            
            System.out.println("");
            System.out.println(" RESULTADOS DA BUSCA:");
            System.out.println("");
            
            boolean encontrou = false;
            
            for (Produto produto : estoque) {
                if (produto.getCodigo().equalsIgnoreCase(codigoBusca)) {
                    exibirDetalheProduto(produto);
                    encontrou = true;
                    break;
                }
            }
            
            if (!encontrou) {
                System.out.println(" Nenhum produto encontrado com esse código.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para buscar produto por nome
    private static void buscarProdutoPorNome(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             BUSCA DE PRODUTO POR NOME");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.print(" Digite o nome para busca: ");
            String nomeBusca = scanner.nextLine().toLowerCase();
            
            System.out.println("");
            System.out.println(" RESULTADOS DA BUSCA:");
            System.out.println("");
            
            boolean encontrou = false;
            int contador = 1;
            
            for (Produto produto : estoque) {
                if (produto.getNome().toLowerCase().contains(nomeBusca)) {
                    System.out.println(" PRODUTO #" + contador);
                    exibirDetalheProduto(produto);
                    System.out.println(" ------------------------------------------------");
                    contador++;
                    encontrou = true;
                }
            }
            
            if (!encontrou) {
                System.out.println(" Nenhum produto encontrado com esse nome.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para atualizar um produto
    private static void atualizarProduto(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             ATUALIZAÇÃO DE PRODUTO");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.print(" Digite o código do produto a ser atualizado: ");
            String codigoBusca = scanner.nextLine();
            
            boolean encontrou = false;
            Produto produtoEncontrado = null;
            
            for (Produto produto : estoque) {
                if (produto.getCodigo().equalsIgnoreCase(codigoBusca)) {
                    produtoEncontrado = produto;
                    encontrou = true;
                    break;
                }
            }
            
            if (encontrou) {
                System.out.println("");
                System.out.println(" PRODUTO ENCONTRADO:");
                exibirDetalheProduto(produtoEncontrado);
                System.out.println("");
                System.out.println(" Informe os novos dados (deixe em branco para manter o valor atual):");
                
                System.out.print(" Nome do produto [" + produtoEncontrado.getNome() + "]: ");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) {
                    produtoEncontrado.setNome(nome);
                }
                
                System.out.print(" Categoria [" + produtoEncontrado.getCategoria() + "]: ");
                String categoria = scanner.nextLine();
                if (!categoria.isEmpty()) {
                    produtoEncontrado.setCategoria(categoria);
                }
                
                System.out.print(" Preço unitário (R$) [" + String.format("%.2f", produtoEncontrado.getPreco()) + "]: ");
                String precoStr = scanner.nextLine();
                if (!precoStr.isEmpty()) {
                    try {
                        double preco = Double.parseDouble(precoStr.replace(",", "."));
                        if (preco >= 0) {
                            produtoEncontrado.setPreco(preco);
                        } else {
                            System.out.println(" O preço não pode ser negativo. Mantendo o valor atual.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Preço inválido. Mantendo o valor atual.");
                    }
                }
                
                System.out.print(" Fornecedor [" + produtoEncontrado.getFornecedor() + "]: ");
                String fornecedor = scanner.nextLine();
                if (!fornecedor.isEmpty()) {
                    produtoEncontrado.setFornecedor(fornecedor);
                }
                
                System.out.println("");
                System.out.println(" Produto atualizado com sucesso!");
            } else {
                System.out.println("");
                System.out.println(" Nenhum produto encontrado com esse código.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para remover um produto
    private static void removerProduto(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             REMOÇÃO DE PRODUTO");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.print(" Digite o código do produto a ser removido: ");
            String codigoBusca = scanner.nextLine();
            
            boolean encontrou = false;
            Produto produtoRemover = null;
            
            for (Produto produto : estoque) {
                if (produto.getCodigo().equalsIgnoreCase(codigoBusca)) {
                    produtoRemover = produto;
                    encontrou = true;
                    break;
                }
            }
            
            if (encontrou) {
                System.out.println("");
                System.out.println(" PRODUTO ENCONTRADO:");
                exibirDetalheProduto(produtoRemover);
                System.out.println("");
                System.out.print(" Tem certeza que deseja remover este produto? (S/N): ");
                String confirmacao = scanner.nextLine();
                
                if (confirmacao.equalsIgnoreCase("S")) {
                    estoque.remove(produtoRemover);
                    System.out.println("");
                    System.out.println(" Produto removido com sucesso!");
                } else {
                    System.out.println("");
                    System.out.println(" Operação cancelada pelo usuário.");
                }
            } else {
                System.out.println("");
                System.out.println(" Nenhum produto encontrado com esse código.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para adicionar quantidade ao estoque
    private static void adicionarQuantidade(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             ADICIONAR QUANTIDADE AO ESTOQUE");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.print(" Digite o código do produto: ");
            String codigoBusca = scanner.nextLine();
            
            boolean encontrou = false;
            Produto produtoEncontrado = null;
            
            for (Produto produto : estoque) {
                if (produto.getCodigo().equalsIgnoreCase(codigoBusca)) {
                    produtoEncontrado = produto;
                    encontrou = true;
                    break;
                }
            }
            
            if (encontrou) {
                System.out.println("");
                System.out.println(" PRODUTO ENCONTRADO:");
                System.out.println(" Código: " + produtoEncontrado.getCodigo());
                System.out.println(" Nome: " + produtoEncontrado.getNome());
                System.out.println(" Quantidade atual: " + produtoEncontrado.getQuantidade());
                System.out.println("");
                
                boolean quantidadeValida = false;
                while (!quantidadeValida) {
                    System.out.print(" Quantidade a adicionar: ");
                    try {
                        int quantidade = Integer.parseInt(scanner.nextLine());
                        if (quantidade > 0) {
                            produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade() + quantidade);
                            System.out.println("");
                            System.out.println(" Quantidade atualizada com sucesso!");
                            System.out.println(" Nova quantidade: " + produtoEncontrado.getQuantidade());
                            quantidadeValida = true;
                        } else {
                            System.out.println(" A quantidade deve ser maior que zero.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Quantidade inválida. Por favor, digite um número inteiro.");
                    }
                }
            } else {
                System.out.println("");
                System.out.println(" Nenhum produto encontrado com esse código.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para remover quantidade do estoque
    private static void removerQuantidade(Scanner scanner, List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             REMOVER QUANTIDADE DO ESTOQUE");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.print(" Digite o código do produto: ");
            String codigoBusca = scanner.nextLine();
            
            boolean encontrou = false;
            Produto produtoEncontrado = null;
            
            for (Produto produto : estoque) {
                if (produto.getCodigo().equalsIgnoreCase(codigoBusca)) {
                    produtoEncontrado = produto;
                    encontrou = true;
                    break;
                }
            }
            
            if (encontrou) {
                System.out.println("");
                System.out.println(" PRODUTO ENCONTRADO:");
                System.out.println(" Código: " + produtoEncontrado.getCodigo());
                System.out.println(" Nome: " + produtoEncontrado.getNome());
                System.out.println(" Quantidade atual: " + produtoEncontrado.getQuantidade());
                System.out.println("");
                
                boolean quantidadeValida = false;
                while (!quantidadeValida) {
                    System.out.print(" Quantidade a remover: ");
                    try {
                        int quantidade = Integer.parseInt(scanner.nextLine());
                        if (quantidade > 0) {
                            if (quantidade <= produtoEncontrado.getQuantidade()) {
                                produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade() - quantidade);
                                System.out.println("");
                                System.out.println(" Quantidade atualizada com sucesso!");
                                System.out.println(" Nova quantidade: " + produtoEncontrado.getQuantidade());
                                quantidadeValida = true;
                            } else {
                                System.out.println(" Quantidade insuficiente em estoque.");
                                System.out.println(" Quantidade atual: " + produtoEncontrado.getQuantidade());
                            }
                        } else {
                            System.out.println(" A quantidade deve ser maior que zero.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Quantidade inválida. Por favor, digite um número inteiro.");
                    }
                }
            } else {
                System.out.println("");
                System.out.println(" Nenhum produto encontrado com esse código.");
            }
        }
        
        System.out.println("");
        System.out.println(" Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    // Método para exibir estatísticas do estoque
    private static void exibirEstatisticas(List<Produto> estoque) {
        limparConsole();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("             ESTATÍSTICAS DO ESTOQUE");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
        
        if (estoque.isEmpty()) {
            System.out.println(" Nenhum produto cadastrado no sistema.");
        } else {
            System.out.println(" Total de produtos cadastrados: " + estoque.size());
            
            // Calcula o valor total em estoque
            double valorTotalEstoque = 0;
            int quantidadeTotalItens = 0;
            double precoMedioProdutos = 0;
            double maiorPreco = 0;
            String produtoMaiorPreco = "";
            double menorPreco = Double.MAX_VALUE;
            String produtoMenorPreco = "";
            int maiorQuantidade = 0;
            String produtoMaiorQuantidade = "";
            int menorQuantidade = Integer.MAX_VALUE;
            String produtoMenorQuantidade = "";
            double maiorValorEmEstoque = 0;
            String produtoMaiorValorEmEstoque = "";
            
            // Contador de produtos por categoria
            java.util.Map<String, Integer> categorias = new java.util.HashMap<>();
            
            for (Produto produto : estoque) {
                double valorEmEstoque = produto.getValorEmEstoque();
                valorTotalEstoque += valorEmEstoque;
                quantidadeTotalItens += produto.getQuantidade();
                
                // Atualiza produto com maior e menor preço
                if (produto.getPreco() > maiorPreco) {
                    maiorPreco = produto.getPreco();
                    produtoMaiorPreco = produto.getNome();
                }
                
                if (produto.getPreco() < menorPreco) {
                    menorPreco = produto.getPreco();
                    produtoMenorPreco = produto.getNome();
                }
                
                // Atualiza produto com maior e menor quantidade
                if (produto.getQuantidade() > maiorQuantidade) {
                    maiorQuantidade = produto.getQuantidade();
                    produtoMaiorQuantidade = produto.getNome();
                }
                
                if (produto.getQuantidade() < menorQuantidade) {
                    menorQuantidade = produto.getQuantidade();
                    produtoMenorQuantidade = produto.getNome();
                }
                
                // Atualiza produto com maior valor em estoque
                if (valorEmEstoque > maiorValorEmEstoque) {
                    maiorValorEmEstoque = valorEmEstoque;
                    produtoMaiorValorEmEstoque = produto.getNome();
                }
                
                // Contagem de produtos por categoria
                String categoria = produto.getCategoria();
                categorias.put(categoria, categorias.getOrDefault(categoria, 0) + 1);
            }
            
            // Calcula o preço médio dos produtos
            precoMedioProdutos = valorTotalEstoque / quantidadeTotalItens;
            
            System.out.println(String.format(" Valor total em estoque: R$ %.2f", valorTotalEstoque));
            System.out.println(String.format(" Quantidade total de itens: %d", quantidadeTotalItens));
            System.out.println(String.format(" Preço médio dos produtos: R$ %.2f", precoMedioProdutos));
            System.out.println("");
            
            System.out.println(" INFORMAÇÕES DE DESTAQUE:");
            System.out.println(String.format(" Produto com maior preço: %s (R$ %.2f)", produtoMaiorPreco, maiorPreco));
            System.out.println(String.format(" Produto com menor preço: %s (R$ %.2f)", produtoMenorPreco, menorPreco));
            System.out.println(String.format(" Produto com maior quantidade: %s (%d unidades)", produtoMaiorQuantidade, maiorQuantidade));
            System.out.println(String.format(" Produto com menor quantidade: %s (%d unidades)", produtoMenorQuantidade, menorQuantidade));
            System.out.println(String.format(" Produto com maior valor em estoque: %s (R$ %.2f)", produtoMaiorValorEmEstoque, maiorValorEmEstoque));
            
            System.out.println("");
            System.out.println(" DISTRIBUIÇÃO POR CATEGORIA:");
            for (java.util.Map.Entry<String, Integer> entry : categorias.entrySet()) {
                System.out.println(String.format(" %s: %d produtos (%.1f%%)", 
                    entry.getKey(), 
                    entry.getValue(), 
                    (entry.getValue() * 100.0) / estoque.size()));
            }
            
            // Alerta de produtos com estoque baixo (menos de 5 unidades)
            System.out.println("");
            System.out.println(" ALERTA DE ESTOQUE BAIXO (MENOS DE 5 UNIDADES):");
            boolean temEstoqueBaixo = false;
            for (Produto produto : estoque) {
                if (produto.getQuantidade() < 5) {
                    System.out.println(String.format(" - %s: %d unidades", produto.getNome(), produto.getQuantidade()));
                    temEstoqueBaixo = true;
                }
            }
            
            if (!temEstoqueBaixo) {
                System.out.println(" Nenhum produto com estoque baixo.");
            }
        }
    }

        // Método para exibir o cabeçalho das telas
    private static void exibirCabecalho(String nomeLoja, String data, String hora) {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" " + nomeLoja.toUpperCase());
        System.out.println(" Data: " + data + " - Hora: " + hora);
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("");
    }
    
    // Método para limpar o console (simulado)
    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    // Método para exibir os detalhes de um produto
    private static void exibirDetalheProduto(Produto produto) {
        System.out.println(" ------------------------------------------------");
        System.out.println(" Código: " + produto.getCodigo());
        System.out.println(" Nome: " + produto.getNome());
        System.out.println(" Categoria: " + produto.getCategoria());
        System.out.println(" Preço unitário: R$ " + String.format("%.2f", produto.getPreco()));
        System.out.println(" Quantidade em estoque: " + produto.getQuantidade());
        System.out.println(" Valor em estoque: R$ " + String.format("%.2f", produto.getValorEmEstoque()));
        System.out.println(" Fornecedor: " + produto.getFornecedor());
        System.out.println(" Data de cadastro: " + produto.getDataCadastroFormatada());
        System.out.println(" Última atualização: " + produto.getDataAtualizacaoFormatada());
        System.out.println(" ------------------------------------------------");
    }

}