/*Crie um método iterativo que recebe uma string como parâmetro e retorna true se essa é um ``Palíndromo''. 
Na saída padrão, para cada linha de entrada, escreva uma linha de saída com SIM/NÃO indicando se a linha é um palíndromo. 
Destaca-se que uma linha de entrada pode ter caracteres não letras */

import java.util.Scanner;

public class Palindromo{

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

    public static void classificarP(String palavra){ //Criação da função para saber se é palindromo ou não.

        int n = palavra.length();

    for (int i = 0, j = n - 1; i <= n / 2; i++, j--){ //For para percorrer a primeira letra com a ultima até chegar ao meio da palavra.


        if (palavra.charAt(i) != palavra.charAt(j)){
            System.out.println("NAO");
            i = n;
        }
        else if (j <= i)
            System.out.println("SIM");
    }

    }

    public static void main(String[] args){

        String palavra;
        Scanner sc = new Scanner(System.in);

        do{
        palavra = sc.nextLine(); //Lendo a string.

        if(!strcmp2(palavra, "FIM")) //Condição para ver se usa a função ou para o código.
            classificarP(palavra);

        } while(!strcmp2(palavra, "FIM"));

        sc.close();
    }
}