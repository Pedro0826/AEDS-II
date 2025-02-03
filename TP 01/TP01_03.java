/*O Imperador Júlio César foi um dos principais nomes do Império Romano. Entre suas contribuições, temos um algoritmo de criptografia chamado ``Ciframento de César''. 
Segundo os historiadores, César utilizava esse algoritmo para criptografar as mensagens que enviava aos seus generais durante as batalhas. 
A ideia básica é um simples deslocamento de caracteres. Assim, por exemplo, se a chave utilizada para criptografar as mensagens for 3, 
todas as ocorrências do caractere 'a' são substituídas pelo caractere 'd', as do 'b' por 'e', e assim sucessivamente.

Crie um método iterativo que recebe uma string como parâmetro e retorna outra contendo a entrada de forma cifrada. 
Neste exercício, suponha a chave de ciframento três. Na saída padrão, para cada linha de entrada, escreva uma linha com a mensagem criptografada.*/

import java.util.Scanner;

public class Cesar {

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

    public static String cesarCode(String palavra){ //Criação da função para criptografar.

        String codigo = "";
        int n = palavra.length();

        for(int i = 0; i < n; i++){ // For para pecorrer a string inteira.

            char letra = palavra.charAt(i);

            if(letra <= 127){

            letra += 3; //Acrescentando 3 na tabela ASCII
            }
            
            codigo += letra;
        }

        return codigo;
    }

    public static void main (String[] args){

        String palavra;
        Scanner sc = new Scanner(System.in);
        
        do
        {
            palavra = sc.nextLine(); // Lendo a string.

            if(!strcmp2(palavra, "FIM")) //Condição para continuar o código
                System.out.println(cesarCode(palavra)); //Mostrando o resultado da função.

        } while(!strcmp2(palavra, "FIM"));

        sc.close();
    }
}
