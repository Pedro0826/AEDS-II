/*Crie um método iterativo em Java que receba como parâmetro uma string e 
retorne seu número de caracteres maiúsculos. 
Em seguida, teste o método anterior usando redirecionamento de entrada e saída. 
A entrada padrão é composta por várias linhas sendo que a última contém a palavra FIM.
A saída padrão contém um número inteiro para cada linha de entrada. */

import java.util.Scanner;

public class LAB01_01{

    public static void Maiusculo(String str){

        int n = str.length();
        int maiusc = 0;

        for(int i = 0; i < n; i++){

            char letra = str.charAt(i);

            if(letra >= 65 && letra <= 90)
                maiusc += 1;
        }

        System.out.println("" + maiusc);

    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        String str;

        do{
            str = sc.nextLine();
            if(!str.equals("FIM")){
                Maiusculo(str);
            }

        } while(!str.equals("FIM"));

        sc.close();
    }
}