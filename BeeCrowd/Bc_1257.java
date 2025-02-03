import java.util.Scanner;

public class Bc_1257 {
    
    public static void main(String [] args){

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 0; i < n; i++){

            int l = sc.nextInt();
            sc.nextLine();
            
            int soma = 0;
            
            for(int j = 0; j < l; j++){

                String str = sc.nextLine();

                for(int h = 0; h < str.length(); h++){
                    char letra = str.charAt(h);
                    soma = soma + ( letra - 'A') + h + j;
                }
            }

            System.out.println(soma);
        }

        sc.close();
    }
}
