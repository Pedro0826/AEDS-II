/*Crie um método iterativo que recebe uma string e retorna true se a mesma é composta somente por vogais. 
Crie outro método iterativo que recebe uma string e retorna true se a mesma é composta somente por consoantes. 
Crie um terceiro método iterativo que recebe uma string e retorna true se a mesma corresponde a um número inteiro. 
Crie um quarto método iterativo que recebe uma string e retorna true se a mesma corresponde a um número real. 
Na saída padrão, para cada linha de entrada, escreva outra de saída da seguinte forma X1 X2 X3 X4 onde cada X$i$ é um booleano indicando se a é entrada é: 
composta somente por vogais (X1); composta somente somente por consoantes (X2); um número inteiro (X3); um número real (X4). Se X$i$ for verdadeiro, 
seu valor será SIM, caso contrário, NÃO. */

import java.util.Scanner;

public class Is {

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

    public static boolean temOuNao (String str1, char str2){
        
        int tam1 = str1.length();
        boolean resp = false;

        for(int i = 0; i < tam1; i++){
                if(str1.charAt(i) == str2){
                    resp = true;
                    i = tam1;  
            }
        }

        return resp;
    }

    public static boolean isVogal(String str){ //Função para saber se a string é composta só por vogal.

        boolean r = true;

        int n = str.length();

        for(int i = 0; i < n; i++){
            
            char letra = str.charAt(i);

            if(temOuNao("aeiouAeiou", letra) == false || temOuNao("0123456789", letra) == true){
            i = n;
            r = false; 
            }       
        }

        return r;
    }
    
    public static boolean isConsoant(String str){//Função para saber se a string é composta só por Consoante.

        boolean r = true;

        int n = str.length();

        for (int i = 0; i < n; i++){

            char letra = str.charAt(i);

            if(temOuNao("aeiouAeiou", letra) == true || temOuNao("0123456789", letra) == true){
                i = n;
                r = false;
            }
        }

        return r;
    }

    public static boolean isIntNumber(String str){ //Função para saber se a string é composta só por inteiros.

        boolean r = true;

        int n = str.length();

        for(int i = 0; i < n; i++){

            char letra = str.charAt(i);

            if(temOuNao("0123456789", letra) == false || (letra == '.' || letra == ',')){
                i = n;
                r = false;
            }
        }

        return r;
    }

    public static boolean isRealNumber(String str){//Função para saber se a string é composta só por numeros reais.

        boolean r = true;

        int n = str.length();
        boolean ponto = false;

        for(int i = 0; i < n; i++){

            char letra = str.charAt(i);
            
            if(letra == '.' || letra == ','){
                
                if(ponto == true){//Condição de se já tiver ponto, mandar falso como resposta.
                    ponto = false;
                    r = false;
                }

                ponto = true;

            }   else if(temOuNao("0123456789", letra) == false || (letra == '.' || letra == ',')){
                    i = n;
                    r = false;
                }
            }

        return r;
     }
    
    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);
        String str;

        do{

        str = sc.nextLine();

        if(!strcmp2("FIM", str)){ //Condição para continuar ou não o código.

            boolean[] resposta = new boolean[4];

            resposta[0] = isVogal(str);
            resposta[1] = isConsoant(str);
            resposta[2] = isIntNumber(str);
            resposta[3] = isRealNumber(str);

            //Printando a resposta.
            System.out.println((resposta[0]? "SIM" : "NAO") + " " +(resposta[1]? "SIM" : "NAO") + " " +(resposta[2]? "SIM" : "NAO") + " " +(resposta[3]? "SIM" : "NAO"));
        } 

     } while(!strcmp2("FIM", str));

     sc.close();
}

}
