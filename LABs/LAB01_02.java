/*Crie um método iterativo em Java que receba como parâmetro uma string e 
retorne seu número de caracteres maiúsculos. 
Em seguida, teste o método anterior usando redirecionamento de entrada e saída. 
A entrada padrão é composta por várias linhas sendo que a última contém a palavra FIM.
A saída padrão contém um número inteiro para cada linha de entrada. */

import java.util.Scanner;

public class LAB01_02{

    public static int Maiusculo(String str, int indice){

        return (indice == str.length() ? 0 : ((str.charAt(indice) >= 65 && str.charAt(indice) <= 90) ? 1 : 0) + Maiusculo(str, indice + 1));
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        String str;

        do{
            str = sc.nextLine();
            if(!str.equals("FIM")){
                int qmaiusculo = Maiusculo(str, 0);
                System.out.println("" + qmaiusculo);
            }

        } while(!str.equals("FIM"));

        sc.close();
    }
}