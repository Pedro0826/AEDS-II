import java.util.Scanner;

public class Bc_2479 {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int comport = 0;
        int nComport = 0;

        int n = sc.nextInt();
        sc.nextLine();

        String[] nomes = new String[n];
        String[] nomesSem = new String[n];

        for(int i = 0; i < n; i++){
            nomes[i] = sc.nextLine();
        }

        // Contar comportados e não comportados
        for (int i = 0; i < n; i++) {
            char letra = nomes[i].charAt(0);
            if (letra == '+') {
                comport++;
            } else {
                nComport++;
            }

            // Remover o sinal e criar nomes sem sinal
            nomesSem[i] = "";
            for (int j = 1; j < nomes[i].length(); j++) {
                nomesSem[i] += nomes[i].charAt(j);
            }
        }

        for(int i = 0; i < n; i++){
            nomesSem[i] = "";
            for(int j = 2; j < nomes[i].length(); j++){
            char letra = nomes[i].charAt(j);
            nomesSem[i] += letra;
            }
        }
        
        for(int i = 0; i < n; i++){
            int ind = i;
        
            for(int j = i + 1; j < n; j++){
                
                if(nomesSem[ind].charAt(0) > nomesSem[j].charAt(0))
                    ind = j;
                else if(nomesSem[ind].charAt(0) == nomesSem[j].charAt(0)){
                    if(nomesSem[ind].charAt(1) > nomesSem[j].charAt(1))
                    ind = j;
                }
            }

            String temp = nomesSem[i];
            nomesSem[i] = nomesSem[ind];
            nomesSem[ind] = temp;
        }

        for(int i = 0; i < n; i++){
            System.out.println(nomesSem[i]);
        }

        System.out.println("Se comportaram: " + comport + " | Nao se comportaram: " + nComport);


        sc.close();
    }
}
