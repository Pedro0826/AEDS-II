import java.util.Scanner;

public class Bc_1238 {

    public static void funcao(String a, String b){

        String resp = "";

        int numa = a.length();
        int numb = b.length();

        int i = 0, j = 0;

        while(i < numa && j < numb){
            resp += a.charAt(i++);
            resp += b.charAt(j++);
        }

        while(i < numa){
            resp += a.charAt(i++);
        }

        while(j < numb){
            resp += b.charAt(j++);
        }


        System.out.println(resp);
    }
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 0; i < n; i++){

            if(sc.hasNextLine()){
            String a = sc.nextLine();
            String b = sc.nextLine();

            funcao(a,b);
            }
        }

        sc.close();
    }
}
