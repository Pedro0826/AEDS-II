import java.util.Scanner;

public class Bc_2312 {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        String[] paises = new String[500];
        int[] ouro = new int[10000];
        int[] prata = new int[10000];
        int[] bronze = new int[10000];

        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 0; i < n; i++){
            paises[i] = sc.nextLine();
            ouro[i] = sc.nextInt();
            prata[i] = sc.nextInt();
            bronze[i] = sc.nextInt();
            sc.nextLine();
        }
        
        for(int i = 0; i < n; i++){
            int ind = i;

            for(int j = i + 1; j < n; j++){

                if(ouro[ind] < ouro[j])
                    ind = j;

                else if(ouro[ind] == ouro[j])
                    if(prata[ind] < prata[j])
                        ind = j;

                    else if(prata[ind] == prata[j])
                        if(bronze[ind] < bronze[j])
                            ind = j;
            }

            String tempS = paises[i];
            paises[i] = paises[ind];
            paises[ind] = tempS;

            int temp1 = ouro[i];
            ouro[i] = ouro[ind];
            ouro[ind] = temp1;

            int temp2 = prata[i];
            prata[i] = prata[ind];
            prata[ind] = temp2;

            int temp3 = bronze[i];
            bronze[i] = bronze[ind];
            bronze[ind] = temp3;
        }

        for(int i = 0; i < n; i++){
            System.out.println(paises[i] + " " + ouro[i] + " " + prata[i] + " " + bronze[i]);
        }

        sc.close();
    }
}
