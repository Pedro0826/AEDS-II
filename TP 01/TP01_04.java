/*Crie um método iterativo que recebe uma string, sorteia duas letras minúsculas aleatórias (código ASCII > 'a' e < 'z'), 
substitui todas as ocorrências da primeira letra na string pela segunda e retorna a string com as alterações efetuadas. 
Na saída padrão, para cada linha de entrada, execute o método desenvolvido nesta questão e mostre a string retornada como uma linha de saída. 
Abaixo, observamos um exemplo de entrada supondo que para a primeira linha as letras sorteados foram o 'a' e o 'q'. Para a segunda linha, foram o 'e' e o 'k'.

A classe Random do Java gera números (ou letras) aleatórios e o exemplo abaixo mostra uma letra minúscula na tela. 
Em especial, destacamos que: i) \textit{seed} é a semente para geração de números aleatórios; ii) nesta questão, por causa da correção automática, 
a \textit{seed} será quatro; iii) a disciplina de Estatística e Probabilidade faz uma discussão sobre ``aleatório''.

Random gerador = new Random();
gerador.setSeed(4);
System.out.println((char)('a' + (Math.abs(gerador.nextInt()) % 26))); */

import java.util.Random;
import java.util.Scanner;


public class Aleatoria {

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

    public static final Random gerador = new Random();
    
    public static char charAle(){
        
        return ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
    }

    public static String funcao(String str){ //Criando a funçao.

        int n = str.length();

        char a = charAle();
        char b = charAle();

        String strNova = "";

        for(int i = 0; i < n; i++){ //For para percorrer a string.
           
            char letra = str.charAt(i);

            if(str.charAt(i) == a){ //Condição para ver se troca ou não a letra.
                strNova += b;
            } else
                strNova += letra;
        }

        return strNova;
    }
    public static void main(String[] args){

        String str;

        Scanner sc = new Scanner(System.in); 

        gerador.setSeed(4); //Gerando semente pedida.

        do{

            str = sc.nextLine();

            if(!strcmp2(str, "FIM")) //Fazendo um if para ver ser para ou continua o programa.
                System.out.println(funcao(str));

        } while(!strcmp2(str, "FIM"));

        sc.close();
    }

}
