import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public class LeituraPaginaHtml {
    public static void main(String[] args) throws Exception {
        //Criando um BufferedReader para ler String
        InputStreamReader r=new InputStreamReader(System.in, Charset.forName("UTF-8"));
        BufferedReader br=new BufferedReader(r);
        String[] entrada = new String[1000];
        int numEntrada = 0;	    

        do {
            entrada[numEntrada] = br.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;
        PrintStream out=new PrintStream(System.out, true, "ISO-8859-1");
        for(int i = 0; i < numEntrada; i += 2){
            out.println(saida(entrada[i], entrada[i+1]));
        }
    }

    @SuppressWarnings("deprecation")
    public static String getHtml(String endereco) {
        
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;
    
        try {
            url = new URL(endereco);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
    
        while((line = br.readLine()) != null) {
            resp += line + "\n";
        }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    
        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here
        }
        return resp;
    }

    /**
     * Verifica se o @param s é uma consoante
     */
    public static boolean isConsoante(char c) {
        boolean x2 = true;
        if(!((c >= 'b' && c <= 'd') || (c >= 'f' && c <= 'h') || (c >= 'j' && c <= 'n') || (c >= 'p' && c <= 't') || (c >= 'v' && c <= 'z')) ) {
            x2 = false;
        }
        return x2;
    }

    public static String quantidadeHtml(String html) {
        int x1 = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0;    // Quantidade Vogais
        int x6 = 0, x7 = 0, x8 = 0, x9 = 0, x10 = 0, x11 = 0, x12 = 0, x13 = 0, x14 = 0, x15 = 0, x16 = 0, x17 = 0, x18 = 0, x19 = 0, x20 = 0, x21 = 0, x22 = 0;    // Vogais acentuadas
        int x23 = 0;    // Quantidade Consoante
        int x24 = 0;    // Quantidade <br>
        int x25 = 0;    // Quantidade <table>
        String resp = "";   // Armazenar a saida

        for(int i = 0; i < html.length(); i++) {
            // Pegar as vogais
            if(html.charAt(i) == 'a') {
                x1++;
            } else if(html.charAt(i) == 'e') {
                x2++;
            } else if(html.charAt(i) == 'i') {
                x3++;
            } else if(html.charAt(i) == 'o') {
                x4++;
            } else if(html.charAt(i) == 'u') {
                x5++;
            }

            //  (´)
            if((int)html.charAt(i) == 225) {
                x6++;
            } else if((int)html.charAt(i) == 233) {
                x7++;
            } else if((int)html.charAt(i) == 237) {
                x8++;
            } else if((int)html.charAt(i) == 243) {
                x9++;
            } else if((int)html.charAt(i) == 250) {
                x10++;
            }

            //  (`)
            if((int)html.charAt(i) == 224) {
                x11++;    
            } else if((int)html.charAt(i) == 232) {
                x12++;    
            } else if((int)html.charAt(i) == 236) {
                x13++;
            } else if((int)html.charAt(i) == 242) {
                x14++;
            } else if((int)html.charAt(i) == 249) {
                x15++;
            }

            //  (~)
            if((int)html.charAt(i) == 227) {
                x16++;
            } else if((int)html.charAt(i) == 245) {
                x17++;
            }

            //  (^)
            if((int)html.charAt(i) == 226) {
                x18++;
            } else if((int)html.charAt(i) == 234) {
                x19++;
            } else if((int)html.charAt(i) == 238) {
                x20++;
            } else if((int)html.charAt(i) == 244) {
                x21++;
            } else if((int)html.charAt(i) == 251) {
                x22++;
            }

            // Quantidade de consoantes
            if(isConsoante(html.charAt(i)) == true) {
                x23++;
            }

            // Se achar um < -> Confirmar se é <br>
            if(html.charAt(i) == '<' && html.charAt(i + 1) == 'b' && html.charAt(i + 2) == 'r' && html.charAt(i + 3) == '>') {
                x24++;
            }

            // Se achar um < -> Confirmar se é <table>
            if(html.charAt(i) == '<' && html.charAt(i + 1) == 't' && html.charAt(i + 2) == 'a' && html.charAt(i + 3) == 'b' && html.charAt(i + 4) == 'l' 
            && html.charAt(i + 5) == 'e' && html.charAt(i + 6) == '>') {
                x25++;
            }
        }

        // Quantidade dentro das tags <br> e <table>
        x23 = x23 - ((x24 * 2) + (x25 * 3));
        x1 = x1 - (x25 * 1);
        x2 = x2 - (x25 * 1);

        resp = ("a(" + x1 + ") " + "e(" + x2 + ") " + "i(" + x3 + ") " + "o(" + x4 + ") " + "u(" + x5 + ") " + "á(" + x6 + ") " + "é(" + x7 + ") "
                   + "í(" + x8 + ") " + "ó(" + x9 + ") " + "ú(" + x10 + ") " + "à(" + x11 + ") " + "è(" + x12 + ") " + "ì(" + x13 + ") " + "ò(" + x14 
                   + ") " + "ù(" + x15 + ") " + "ã(" + x16 + ") " + "õ(" + x17 + ") " + "â(" + x18 + ") " + "ê(" + x19 + ") " + "î(" + x20 + ") " 
                   + "ô(" + x21 + ") " + "û(" + x22 + ") " + "consoante(" + x23 + ") " + "<br>(" + x24 + ") " + "<table>(" + x25 + ")");

        return resp;
    }

    public static String saida(String nomepag, String endereco) {
        String html;
        html = getHtml(endereco); // recebe codigo da pagina html
        return(quantidadeHtml(html) + " " + nomepag);
    }

    /**
     * Verifica se o @param s é a palavra "FIM"
     */
    private static boolean isFim(String s) {
        boolean result;
        if (s.length() != 3){
            result = false;
        }else{
            if(s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') result = true;
            else result = false;
        }
        return result;
    }

    
}