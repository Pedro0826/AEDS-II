import java.util.Scanner;

public class IsRec{

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

    public static boolean isVogal(String str, int indice){ //Função para saber se a string é composta só por vogal.

        boolean r = true;

        if(indice < str.length()){

            char letra = str.charAt(indice);

            if(temOuNao("aeiouAeiou", letra) == false || temOuNao("0123456789", letra) == true)
            r = false; 

            else
            r = isVogal(str, indice + 1);
        }

        return r;
    }
    
    public static boolean isConsoant(String str, int indice){//Função para saber se a string é composta só por Consoante.

        boolean r = true;

        if(indice < str.length()){

            char letra = str.charAt(indice);

            if(temOuNao("aeiouAeiou", letra) == true || temOuNao("0123456789", letra) == true)
                r = false;
            else
            r = isConsoant(str, indice + 1);
        }

        return r;
    }

    public static boolean isIntNumber(String str, int indice){ //Função para saber se a string é composta só por inteiros.

        boolean r = true;

        if(indice < str.length()){

            char letra = str.charAt(indice);

            if(temOuNao("0123456789", letra) == false || (letra == '.' || letra == ','))
                r = false;
            else
            r = isIntNumber(str, indice + 1);
        }

        return r;
    }

    public static boolean isRealNumber(String str, int indice){//Função para saber se a string é composta só por numeros reais.

        boolean r = true;
        boolean ponto = false;

        if(indice < str.length()){

            char letra = str.charAt(indice);
            
            if(letra == '.' || letra == ','){
                
                if(ponto == true){//Condição de se já tiver ponto, mandar falso como resposta.
                    ponto = false;
                    r = false;
                }

                ponto = true;

            }   else if (temOuNao("0123456789", letra) == false || (letra == '.' || letra == ','))
                    r = false;
                else
                    r = isRealNumber(str, indice + 1);
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

            resposta[0] = isVogal(str, 0);
            resposta[1] = isConsoant(str, 0);
            resposta[2] = isIntNumber(str, 0);
            resposta[3] = isRealNumber(str, 0);

            //Printando a resposta.
            System.out.println((resposta[0]? "SIM" : "NAO") + " " +(resposta[1]? "SIM" : "NAO") + " " +(resposta[2]? "SIM" : "NAO") + " " +(resposta[3]? "SIM" : "NAO"));
        } 

     } while(!strcmp2("FIM", str));

     sc.close();
}
}