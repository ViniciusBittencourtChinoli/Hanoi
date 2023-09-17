/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hanoi;

/**
 *
 * @author itzvi
 */

import java.util.*;

public class Hanoi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o tamanho das torres: ");
        int tamanhoTorres = scanner.nextInt();

        // Criar três pilhas (torres) para o jogo.
        Stack<Integer> torre1 = criarTorreAleatoria(tamanhoTorres);
        Stack<Integer> torre2 = new Stack<>();
        Stack<Integer> torre3 = new Stack<>();

        int contadorJogadas = 0;

        while (true) {
            // Imprimir o estado atual das torres.
            imprimirTorres(torre1, torre2, torre3);
            System.out.println("Menu do jogo:");
            System.out.println("0 - Sair do jogo");
            System.out.println("1 - Movimentar");
            System.out.println("2 - Solução automática");
            int opcao = scanner.nextInt();

            if (opcao == 0) {
                System.out.println("Jogo encerrado.");
                break;
            } else if (opcao == 1) {
                System.out.print("De qual torre deseja mover (1, 2 ou 3): ");
                int origem = scanner.nextInt();
                System.out.print("Para qual torre deseja mover (1, 2 ou 3): ");
                int destino = scanner.nextInt();
                // Realizar a movimentação entre as torres.
                mover(origem, destino, torre1, torre2, torre3);
                contadorJogadas++;
            } else if (opcao == 2) {
                // Encontrar uma solução automática para o jogo.
                contadorJogadas = solucaoAutomatica(tamanhoTorres, torre1, torre2, torre3);
                imprimirTorres(torre1, torre2, torre3);
                System.out.println("Solução automática encontrada em " + contadorJogadas + " jogadas.");
                break;
            } else {
                System.out.println("Opção inválida.");
            }

            // Verificação de vitória - se a Torre 3 está completamente ordenada.
            if (verificarVitoria(torre3, tamanhoTorres)) {
                System.out.println("Você venceu o jogo!");
                break;
            }
        }

        scanner.close();
    }

    private static Stack<Integer> criarTorreAleatoria(int tamanho) {
        Stack<Integer> torre = new Stack<>();
        Random random = new Random();
        for (int i = 0; i < tamanho; i++) {
            torre.push(random.nextInt(100) + 1);
        }
        return torre;
    }

    private static void imprimirTorres(Stack<Integer> torre1, Stack<Integer> torre2, Stack<Integer> torre3) {
        System.out.println("Torre 1: " + torre1);
        System.out.println("Torre 2: " + torre2);
        System.out.println("Torre 3: " + torre3);
    }

    private static void mover(int origem, int destino, Stack<Integer> torre1, Stack<Integer> torre2, Stack<Integer> torre3) {
        Stack<Integer> origemTorre, destinoTorre;

        if (origem == 1) {
            origemTorre = torre1;
        } else if (origem == 2) {
            origemTorre = torre2;
        } else {
            origemTorre = torre3;
        }

        if (destino == 1) {
            destinoTorre = torre1;
        } else if (destino == 2) {
            destinoTorre = torre2;
        } else {
            destinoTorre = torre3;
        }

        if (!origemTorre.isEmpty() && (destinoTorre.isEmpty() || origemTorre.peek() < destinoTorre.peek())) {
            destinoTorre.push(origemTorre.pop());
        } else {
            System.out.println("Movimento inválido.");
        }
    }

    private static int solucaoAutomatica(int n, Stack<Integer> origem, Stack<Integer> auxiliar, Stack<Integer> destino) {
        if (n == 1) {
            mover(1, 3, origem, auxiliar, destino);
            return 1;
        }

        int movimentos1 = solucaoAutomatica(n - 1, origem, destino, auxiliar);
        mover(1, 3, origem, auxiliar, destino);
        int movimentos2 = solucaoAutomatica(n - 1, auxiliar, origem, destino);

        return movimentos1 + 1 + movimentos2;
    }

    private static boolean verificarVitoria(Stack<Integer> torre3, int tamanhoTorres) {
        if (torre3.size() == tamanhoTorres) {
            int expected = 1;
            Stack<Integer> tempStack = new Stack<>();

            while (!torre3.isEmpty()) {
                int top = torre3.pop();
                if (top != expected) {
                    // A torre não está completamente ordenada
                    return false;
                }
                tempStack.push(top);
                expected++;
            }

            // Restaurar a Torre 3
            while (!tempStack.isEmpty()) {
                torre3.push(tempStack.pop());
            }

            // Se chegamos aqui, a Torre 3 está completamente ordenada
            return true;
        }
        return false;
    }
}