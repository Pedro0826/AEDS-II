import java.util.Scanner;

public class PalindromoRec{

    public static boolean strcmp2(String str1, String str2) {

        boolean resp = true;

        if (str1.length() != str2.length()) {
            resp = false;
        }

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                resp = false;
                i = str1.length();
            }
        }

        return resp;
    }

    public static boolean palindromo(String str, int ini, int fim){
        //Fiz o return já com as comparações, dependo do que for falso ou verdadeiro retorna true ou false.
        return (fim <= ini) || (str.charAt(ini) == str.charAt(fim) && palindromo(str, ini + 1, fim - 1));
    }

    public static void main(String[] args){

        String palavra;
        Scanner sc = new Scanner(System.in);
        
        do{
            palavra = sc.nextLine();

            int n = palavra.length();

            if(!strcmp2(palavra, "FIM")){ //Condição para ver se continua ou para o código.

                boolean resp = palindromo(palavra, 0, n -1); //Usando a função.

                if(resp == true)
                    System.out.println("SIM");
                else
                    System.out.println("NAO");

            }

        } while(!strcmp2(palavra, "FIM"));

        sc.close();
    }
}