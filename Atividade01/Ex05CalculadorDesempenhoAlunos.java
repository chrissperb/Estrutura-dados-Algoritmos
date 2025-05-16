package Atividade01;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Sistema de Cálculo de Desempenho de Alunos
 * Este programa calcula o desempenho dos alunos em uma avaliação, encontrando a maior nota da turma e exibindo-a.
 * O usuário pode inserir as informações da turma e as notas dos alunos.
 *  
 * Autor: Christian Sperb
 * Data: 16/05/2025
 */

public class Ex05CalculadorDesempenhoAlunos {
    public static void main(String[] args) {
        // Criação do objeto Scanner para entrada de dados
        Scanner scanner = new Scanner(System.in);
        
        // Declaração das variáveis
        String nomeEscola;
        String nomeTurma;
        String disciplina;
        String dataDaAvaliacao;
        double[] notasAlunos;
        int quantidadeAlunos;
        
        // Obtenção da data atual formatada
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = formatador.format(new Date());
        
        // Solicitação de dados ao usuário
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("         SISTEMA DE DESEMPENHO ESCOLAR");
        System.out.println("══════════════════════════════════════════════════");
        
        System.out.print("Digite o nome da escola: ");
        nomeEscola = scanner.nextLine();
        
        System.out.print("Digite o nome da turma: ");
        nomeTurma = scanner.nextLine();
        
        System.out.print("Digite a disciplina: ");
        disciplina = scanner.nextLine();
        
        System.out.print("Digite a data da avaliação (ou deixe em branco para usar a data atual): ");
        dataDaAvaliacao = scanner.nextLine();
        if (dataDaAvaliacao.trim().isEmpty()) {
            dataDaAvaliacao = dataAtual;
        }
        
        System.out.print("Digite a quantidade de alunos na turma: ");
        quantidadeAlunos = scanner.nextInt();
        
        // Inicialização do array com o tamanho informado pelo usuário
        notasAlunos = new double[quantidadeAlunos];
        
        // Solicitação das notas dos alunos
        System.out.println("\nDigite as notas dos alunos:");
        for (int i = 0; i < quantidadeAlunos; i++) {
            System.out.print("Nota do Aluno " + (i+1) + ": ");
            
            // Validação da entrada para garantir que o valor esteja entre 0 e 10
            while (true) {
                try {
                    notasAlunos[i] = scanner.nextDouble();
                    if (notasAlunos[i] >= 0 && notasAlunos[i] <= 10) {
                        break; // Sai do loop se a nota for válida
                    } else {
                        System.out.print("Nota inválida! Digite um valor entre 0 e 10: ");
                    }
                } catch (Exception e) {
                    System.out.print("Entrada inválida! Digite um número: ");
                    scanner.next(); // Limpa o buffer do scanner
                }
            }
        }
        
        // Fechando o scanner após a entrada de dados
        scanner.close();
        
        // Cálculo da maior nota
        double maiorNota = notasAlunos[0]; // Começamos assumindo que a primeira nota é a maior
        int alunoComMaiorNota = 1; // Armazenamos também qual aluno possui a maior nota
        
        // Percorre o array para encontrar a maior nota
        for (int i = 1; i < notasAlunos.length; i++) {
            if (notasAlunos[i] > maiorNota) {
                maiorNota = notasAlunos[i];
                alunoComMaiorNota = i + 1; // +1 porque os índices começam em 0
            }
        }
        
        // Limpando a tela (apenas uma quebra de linha para simular uma limpeza)
        System.out.println("\n\n\n");
        
        // Exibição dos resultados na tela com frisos horizontais
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("               " + nomeEscola);
        System.out.println("            SISTEMA DE DESEMPENHO ESCOLAR");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" INFORMAÇÕES DA AVALIAÇÃO");
        System.out.println(" Turma: " + nomeTurma);
        System.out.println(" Disciplina: " + disciplina);
        System.out.println(" Data da Avaliação: " + dataDaAvaliacao);
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" RELATÓRIO DE NOTAS");
        System.out.println("");
        
        // Exibindo todas as notas
        System.out.println(" Notas dos alunos:");
        for (int i = 0; i < notasAlunos.length; i++) {
            System.out.println(" Aluno " + (i+1) + ": " + String.format("%.1f", notasAlunos[i]));
        }
        
        System.out.println("");
        System.out.println(" Total de alunos avaliados: " + notasAlunos.length);
        System.out.println("");
        System.out.println(" RESULTADO DA ANÁLISE:");
        System.out.println(" A maior nota da turma foi: " + String.format("%.1f", maiorNota));
        System.out.println(" Obtida pelo Aluno " + alunoComMaiorNota);
        
        // Classificação da maior nota
        String classificacao = "";
        if (maiorNota >= 9.0) {
            classificacao = "EXCELENTE";
        } else if (maiorNota >= 8.0) {
            classificacao = "MUITO BOM";
        } else if (maiorNota >= 7.0) {
            classificacao = "BOM";
        } else if (maiorNota >= 6.0) {
            classificacao = "REGULAR";
        } else {
            classificacao = "INSUFICIENTE";
        }
        
        System.out.println(" Classificação: " + classificacao);
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" INFORMAÇÕES ADICIONAIS:");
        System.out.println(" • Notas acima de 9.0: Excelente");
        System.out.println(" • Notas entre 8.0 e 8.9: Muito Bom");
        System.out.println(" • Notas entre 7.0 e 7.9: Bom");
        System.out.println(" • Notas entre 6.0 e 6.9: Regular");
        System.out.println(" • Notas abaixo de 6.0: Insuficiente");
        System.out.println("══════════════════════════════════════════════════");
        System.out.println(" Sistema de Desempenho Escolar v1.0");
        System.out.println(" Data: " + dataAtual);
        System.out.println("══════════════════════════════════════════════════");
    }
}