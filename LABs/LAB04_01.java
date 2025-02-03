import java.util.Scanner;

public class LAB04_01 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int mod = sc.nextInt();

        while (n != 0 && mod != 0) {
            int[] array = new int[n];

            // Leitura do array
            for (int i = 0; i < n; i++) {
                array[i] = sc.nextInt();
            }

            // Ordenação baseada no módulo e outras regras
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {

                    int modI = array[i] % mod;
                    int modJ = array[j] % mod;

                    // Ajuste para números negativos conforme a regra da linguagem C
                    if (modI < 0) modI += mod;
                    if (modJ < 0) modJ += mod;

                    if (modI == modJ) {
                        // Se os módulos são iguais, priorizar ímpares sobre pares
                        if (array[i] % 2 == 0 && array[j] % 2 != 0) {
                            // Troca para colocar o ímpar antes do par
                            int temp = array[i];
                            array[i] = array[j];
                            array[j] = temp;
                        } else if (array[i] % 2 != 0 && array[j] % 2 != 0) {
                            // Ambos ímpares: colocar o maior antes
                            if (array[i] < array[j]) {
                                int temp = array[i];
                                array[i] = array[j];
                                array[j] = temp;
                            }
                        } else if (array[i] % 2 == 0 && array[j] % 2 == 0) {
                            // Ambos pares: colocar o menor antes
                            if (array[i] > array[j]) {
                                int temp = array[i];
                                array[i] = array[j];
                                array[j] = temp;
                            }
                        }
                    } else if (modI > modJ) {
                        // Se os módulos são diferentes, comparar diretamente
                        int temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
            }

            // Exibir o array ordenado
            System.out.println(n + " " + mod);
            for (int i = 0; i < n; i++) {
                System.out.println(array[i]);
            }

            // Ler novos valores de n e mod
            n = sc.nextInt();
            mod = sc.nextInt();
        }

        System.out.println(n + " " + mod);
        sc.close();
    }
}