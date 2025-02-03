import java.util.Scanner;

public class LAB02_02 {

        public static void espelhar(int inicio, int fim) {
    
            String str = "";
        
            for(int i = inicio; i <= fim; i++){
    
                str += i;
            }
    
            for(int j = str.length() - 1; j >= 0; j--){
    
                str += str.charAt(j);
            }
    
            System.out.println(str);
        }
        public static void main(String[] args) {
    
            Scanner sc = new Scanner(System.in);
    
            while (sc.hasNext()) {
    
                int inicio = sc.nextInt();
                int fim = sc.nextInt();
    
                espelhar(inicio, fim);
            }
    
            sc.close();
        }
}