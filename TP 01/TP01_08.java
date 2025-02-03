import java.io.File; 
import java.io.IOException; 
import java.io.RandomAccessFile;
import java.util.Scanner; 

public class Arquivo {
    
    public static String removerZeros(double numero) {
        String texto = Double.toString(numero);
        if(texto.indexOf('.') > 0) {
            texto = texto.replaceAll("0*$", "");
            texto = texto.replaceAll("\\.$", ""); 
        }
        return texto;
    }
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        File arquivo = new File("arquivo.txt");

       try(RandomAccessFile arq = new RandomAccessFile(arquivo, "rw")){ //Abrindo o arquivo e adicionando os numeros.

            for(int i = 0; i < n; i++){
                double num = sc.nextDouble();
                arq.writeDouble(num);
            }
        }   catch (IOException e){}

        try(RandomAccessFile arq = new RandomAccessFile(arquivo, "r")){//Lendo o arquivo de trás para frente mostrando na tela o numero.

            for(int i = n - 1; i >= 0; i--){
                arq.seek(i * 8);
                double num = arq.readDouble();
                System.out.println(removerZeros(num));
            }
        }   catch (IOException e){}

        sc.close();
    }  
    }
