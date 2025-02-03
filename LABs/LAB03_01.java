import java.util.Scanner;

public class LAB03_01
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (strcmp(input, "FIM") != 0) {
            System.out.println(parensCorreto(input) ? "correto" : "incorreto");
            input = sc.nextLine();
        }

        sc.close();
    }

    public static boolean parensCorreto(String expr)
    {
        int eq = 0; // Equilíbrio de parênteses.

        // Itera até terminar a string, ou até fechar parênteses inexistentes.
        for (int i = 0; i < expr.length() && eq >= 0; ++i) {
            switch (expr.charAt(i)) {
            case '(':
                ++eq;
                break;
            case ')':
                --eq;
                break;
            }
        }

        return eq == 0; // Retorna se `eq` for zero.
    }

    public static int strcmp(String str, String cmp)
    {
        int ret = 0;
        int min_len = (str.length() > cmp.length()) ? cmp.length() :
                                                      str.length();

        for (int i = min_len - 1; i >= 0; --i)
            if (str.charAt(i) != cmp.charAt(i))
                ret = str.charAt(i) - cmp.charAt(i);

        return ret;
    }
}