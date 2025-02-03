
public class tem {
    
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
}
