import java.util.Scanner;

public class AlgebraBoleanaRec {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                break;
            }

            try {
                // Avalia a expressão booleana e imprime o resultado
                boolean resultado = avaliarExpressao(entrada);
                System.out.println(resultado ? "1" : "0");
            } catch (Exception e) {
                System.out.println("0"); // Caso ocorra qualquer erro na avaliação
            }
        }

        scanner.close();
    }

    public static boolean avaliarExpressao(String entrada) {
        String[] partes = entrada.split(" ", 2);

        if (partes.length < 2) {
            throw new IllegalArgumentException("Formato de entrada inválido.");
        }

        int n;
        try {
            n = Integer.parseInt(partes[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número de variáveis inválido: " + partes[0]);
        }

        String[] valores = partes[1].split(" ", n + 1);

        if (valores.length < n + 1) {
            throw new IllegalArgumentException("Número de valores ou expressão booleana inválidos.");
        }

        String[] binarios = new String[n];
        for (int i = 0; i < n; i++) {
            binarios[i] = valores[i];
        }

        String expressao = valores[n];

        // Substitui as variáveis A, B, C, etc. pelos valores binários correspondentes
        for (int i = 0; i < n; i++) {
            char variavel = (char) ('A' + i);
            expressao = expressao.replace(String.valueOf(variavel), binarios[i]);
        }

        // Avalia a expressão booleana
        return avaliarExpressaoBooleana(expressao);
    }

    public static boolean avaliarExpressaoBooleana(String expressao) {
        expressao = expressao.replace(" ", "");
        return avaliar(expressao);
    }

    public static boolean avaliar(String expressao) {
        if (expressao.startsWith("not(")) {
            return !avaliar(expressao.substring(4, expressao.length() - 1));
        } else if (expressao.startsWith("and(")) {
            return avaliarComOperador(expressao, 4, "and");
        } else if (expressao.startsWith("or(")) {
            return avaliarComOperador(expressao, 3, "or");
        } else {
            return expressao.equals("1");  // Se for "1", é true; se for "0", é false
        }
    }

    public static boolean avaliarComOperador(String expressao, int inicio, String operador) {
        int commaIndex = encontrarVirgula(expressao.substring(inicio));
        boolean left = avaliar(expressao.substring(inicio, inicio + commaIndex));
        boolean right = avaliar(expressao.substring(inicio + commaIndex + 1, expressao.length() - 1));
        return operador.equals("and") ? (left && right) : (left || right);
    }

    public static int encontrarVirgula(String expressao) {
        return encontrarVirgulaHelper(expressao, 0, 0);
    }

    public static int encontrarVirgulaHelper(String expressao, int index, int nivel) {
        if (index >= expressao.length()) {
            return -1;
        }
        char atual = expressao.charAt(index);
        if (atual == '(') nivel++;
        if (atual == ')') nivel--;
        if (atual == ',' && nivel == 0) return index;
        return encontrarVirgulaHelper(expressao, index + 1, nivel);}
}

