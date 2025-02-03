import java.util.Scanner;

public class CesarRec {

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

    public static String cesarCode(String palavra, int inicio, int n, String cod){//Criando a função.
        
        if(n > inicio){

            char letra = palavra.charAt(inicio);

            if(letra <= 127){//Avançando +3 naa tabela ASCII a letra.

            letra += 3; 
            }
            
            cod += letra; // Adicionando a letra na string.

            cod = cesarCode(palavra, inicio + 1, n, cod); // Usando a recursividade.
        }

        else if (n == inicio){//Metodo de parada.

            char letra = palavra.charAt(n);

            if(letra <= 127){//Avançando +3 naa tabela ASCII a letra.

            letra += 3; 
            }
            
            cod += letra;// Adicionando a letra na string.
        }
    
        return cod;
    }

    public static void main (String[] args){

        String palavra, codigo;
        Scanner sc = new Scanner(System.in);
        
        do
        {
            palavra = sc.nextLine(); // Lendo a string.
            codigo = "";

            if(!strcmp2(palavra, "FIM"))//Condição para ver se o codigo continua ou para.
                System.out.println(cesarCode(palavra, 0, palavra.length() - 1, codigo)); //Mostrando o resultado da função.

        } while(!strcmp2(palavra, "FIM"));

        sc.close();
    }
}
