import java.util.Scanner;

public class AlgebraBoleana{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // Lê o número de variáveis.

        while (n != 0) {
            // Lê as variáveis booleanas.
            int[] entradas = new int[n];
            for (int i = 0; i < n; ++i)
                entradas[i] = sc.nextInt();

            // Lê a expressão booleanas.
            String expr = sc.nextLine();

            // for (int i : entradas)
            //     System.out.print(i + " ");
            // System.out.println();
            // System.out.println(expr);

            expr = tokeniza(expr); // Preprocessa a expressão.
            expr = insere(expr, entradas); // Insere as entradas.

            // System.out.println(expr);

            String fila = shuntingYard(expr); // Constrói a fila, como em RPN.

            boolean res = avaliaRPN(fila); // Avalia a fila RPN booleana.

            // System.out.println(fila);

            System.out.println(res ? 1 : 0);

            // System.out.println();

            n = sc.nextInt(); // Lê o próximo número de variáveis.
        }

        sc.close();
    }

    // Recebe uma expressão e a converte para ser fácil de iterar.
    public static String tokeniza(String in)
    {
        String out = new String();

        for (int i = 0; i < in.length(); ++i) {
            switch (in.charAt(i)) {
            // Tokens que correspondem às operações booleanas.
            case 'o': // Or.
                out += 'v';
                i += 1; // Pula o 'r'.
                break;
            case 'a': // And.
                out += '^';
                i += 2; // Pula o 'nd'.
                break;
            case 'n': // Not.
                out += '~'; // Pula o 'ot'.
                i += 2;
                break;

            // Caracteres que serão ignorados.
            case ' ':
                break;

            // Comportamento padrão: copie os demais caracteres.
            default:
                out += in.charAt(i);
                break;
            }
        }

        return out;
    }

    // Recebe a expressão tokenizada e a lista de valores binários e retorna a
    // expressão tokenizada com os valores inseridos.
    public static String insere(String expr, int[] vals)
    {
        String out = new String();

        for (int i = 0; i < expr.length(); ++i) {
            char tok = expr.charAt(i);

            if (tok >= 'A' && tok <= 'Z')
                out += vals[tok - 'A'];
            else
                out += tok;
        }

        return out;
    }

    // Implementação do algoritmo Shunting Yard para esse problema.
    // De <https://en.wikipedia.org/wiki/Shunting_yard_algorithm>
    public static String shuntingYard(String expr)
    {
        // Não consegui descobrir como implementar essa Stack como um array.
        String out = new String(); // Fila de output.
        char[] op = new char[expr.length()]; // Stack de operadores.
        int ind = -1; // Índice da stack.

        for (int i = 0; i < expr.length(); ++i) { // Enquanto houver tokens...
            char tok = expr.charAt(i); // ...leia um token.

            switch (tok) { // Se o token for...
            case '0', '1': // ...um número:
                out += tok; // coloca-o na fila de saída.
                break;
            case '^', 'v', '~': // ...uma função:
                op[++ind] = tok; // apende-o à stack de operadores.
                break;
            case ',': // ...um separador de parâmetros:
                // enquanto o operador no topo da stack não for `(`:
                while (op[ind] != '(')
                    out += op[ind--]; // insere-o daí à fila de saída.
                break;
            case '(': // ...abertura de parênteses:
                op[++ind] = tok; // apende-o à stack de operadores.
                break;
            case ')': // ...fechamento de parênteses:
                // enquanto o operador no topo da stack não for `(`:
                while (op[ind] != '(') {
                    assert ind >= 0 : "Erro de parênteses!";
                    out += op[ind--]; // insere-o daí à fila de saída.
                }

                assert op[ind] == '(';
                --ind; // Descarta o `(` no topo da stack.

                // Se há uma função no topo da stack:
                switch (op[ind]) {
                case '^', 'v', '~':
                    // insere-a daí à fila de saída.
                    out += op[ind--];
                    break;
                }
                break;
            }
        }

        // Terminados os tokens, apende os itens restantes na stack à fila.
        while (ind >= 0) {
            assert op[ind] != '(' : "Erro de parênteses!";
            out += op[ind--];
        }

        return out.toString(); // Converte vetor de char para String.
    }

    public static boolean avaliaRPN(String fila)
    {
        boolean[] ops = new boolean[fila.length()]; // Nossa Stack™ da Shopee.
        int ind = -1; // O “índice” da nossa “Stack”.

        // Itera sobre cada item na fila.
        for (int i = 0; i < fila.length(); ++i) {
            char tok = fila.charAt(i); // Extrai token da fila.

            if (tok == '0' || tok == '1') { // Se for valor, insere na Stack.
                ops[++ind] = (tok == '1') ? true : false;
            } else if (tok == '~') { // Caso contrário, se for operador NOT...
                ops[ind] = (ops[ind]) ? false : true; // Nega o valor no topo.
            } else { // Caso contrário, é operador binário.
                // Extrai os dois primeiros operandos na Stack™.
                boolean a = ops[ind--];
                boolean b = ops[ind--];
                boolean res; // Resultado da operação em `a` e `b`.

                // Efetua a operação correta.
                switch (tok) {
                case '^':
                    res = a && b;
                    break;
                case 'v':
                    res = a || b;
                    break;
                default:
                    throw new IllegalArgumentException("Operador inexiste!");
                }

                // Insere o resultado na Stack™.
                ops[++ind] = res;
            }
        }

        return ops[0]; // O elemento que sobra na “Stack” é o resultado.
    }
}