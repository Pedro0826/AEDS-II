import java.util.Scanner;

class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;
 
    public Celula() {
        this(0);
    }
 
    public Celula(int elemento) {
        this(elemento, null, null, null, null);
    }
 
    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir) {
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
}
 
class Matriz {
    Celula inicio;
    private int linha, coluna;
 
    public Matriz() {
        this(3, 3);
    }
 
    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        inicializarMatriz();
    }
 
    private void inicializarMatriz() {
        Celula el = new Celula();
        inicio = el;
        Celula el2 = inicio;

        for (int j = 1; j < coluna; j++) {
            el.dir = new Celula();
            el.dir.esq = el;
            el = el.dir;
        }

        for (int i = 1; i < linha; i++) {
            el2.inf = new Celula();
            el2.inf.sup = el2;
            el2 = el2.inf;
            el = el2;

            for (int j = 1; j < coluna; j++) {
                el.dir = new Celula();
                el.dir.esq = el;
                el = el.dir;
                el.sup = el.esq.sup.dir;
                el.sup.inf = el;
            }
        }
    }
 
    public Matriz soma(Matriz m) {
        Matriz resp = null;
 
        if (this.linha == m.linha && this.coluna == m.coluna) {
            resp = new Matriz(this.linha, this.coluna);
            Celula c = resp.inicio, a = this.inicio, b = m.inicio;
 
            for (int i = 0; i < linha; i++) {
                Celula cTemp = c, aTemp = a, bTemp = b;
                for (int j = 0; j < coluna; j++) {
                    cTemp.elemento = aTemp.elemento + bTemp.elemento;
                    cTemp = cTemp.dir;
                    aTemp = aTemp.dir;
                    bTemp = bTemp.dir;
                }
                c = c.inf;
                a = a.inf;
                b = b.inf;
            }
        }
        return resp;
    }
 
    public Matriz multiplicacao(Matriz m2) {
        // Verifica se a multiplicação é possível
        if (this.coluna != m2.linha) {
            return null; // A multiplicação é inválida se o número de colunas de 'this' não igualar o número de linhas de 'm2'
        }
    
        // Array temporário para armazenar os elementos da matriz resultante
        int[] tmp = new int[this.linha * m2.coluna];
        Celula y1, y2, u1, u2;
        y1 = u1 = this.inicio;
        y2 = u2 = m2.inicio;
        int index = 0;
    
        for (int i = 0; i < this.linha; i++) {
            for (int t = 0; t < m2.coluna; t++) {
                for (int j = 0; j < this.coluna; j++) {
                    tmp[index] += u1.elemento * y2.elemento;
                    u1 = u1.dir;
                    y2 = y2.inf;
                }
                index++;
                u1 = y1;
                u2 = u2.dir;
                y2 = u2;
            }
            y1 = y1.inf;
            u1 = y1;
            u2 = y2 = m2.inicio;
        }
    
        // Criação da matriz resultante e inserção dos valores calculados
        Matriz multi = new Matriz(this.linha, m2.coluna);
        multi.inicializarMatriz();
        multi.inserir(tmp);
        return multi;
    }
 
    public void inserir(int array[]) {
        Celula el1, u1;
        el1 = u1 = inicio;
        int index = 0;

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                u1.elemento = array[index++];
                u1 = u1.dir;
            }
            el1 = el1.inf;
            u1 = el1;
        }
    }

    public boolean isQuadrada() {
        return this.linha == this.coluna;
    }
 
    public void mostrarDiagonalPrincipal() {
        if (isQuadrada()) {
            Celula atual = inicio;
            for (int i = 0; i < linha; i++) {
                System.out.print(atual.elemento + " ");
                if (atual.inf != null && atual.dir != null) {
                    atual = atual.inf.dir;
                }
            }
            System.out.println();
        }
    }
 
    public void mostrarDiagonalSecundaria() {
        if (isQuadrada()) {
            Celula atual = inicio;
            while (atual.dir != null) {
                atual = atual.dir;
            }
            for (int i = 0; i < linha; i++) {
                System.out.print(atual.elemento + " ");
                if (atual.inf != null && atual.esq != null) {
                    atual = atual.inf.esq;
                }
            }
            System.out.println();
        }
    }
}

public class TP03_11{

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int casos = sc.nextInt();
        
        for(int i = 0; i < casos; i++){

            int l1 = sc.nextInt();
            int c1 = sc.nextInt(); 
            
            Matriz matriz = new Matriz(l1,c1);

            Celula linhaAtual = matriz.inicio;
            
            for(int l = 0; l < l1; l++){     
                Celula colunaAtual = linhaAtual;

                for(int j = 0; j < c1; j++){
                    int valor = sc.nextInt();
                    colunaAtual.elemento = valor;
                    colunaAtual = colunaAtual.dir;
                }

                linhaAtual = linhaAtual.inf;
            }

            int l2 = sc.nextInt();
            int c2 = sc.nextInt(); 
            
            Matriz matriz1 = new Matriz(l2,c2);

            Celula linhaAtual1 = matriz1.inicio;
            
            for(int l = 0; l < l2; l++){     
                Celula colunaAtual1 = linhaAtual1;

                for(int j = 0; j < c2; j++){
                    int valor = sc.nextInt();
                    colunaAtual1.elemento = valor;
                    colunaAtual1 = colunaAtual1.dir;
                }

                linhaAtual1 = linhaAtual1.inf;
            }

            matriz.mostrarDiagonalPrincipal();
            matriz.mostrarDiagonalSecundaria();

            Matriz resultadoSoma = matriz.soma(matriz1);
            Celula linhaResultado = resultadoSoma.inicio;
                for (int l = 0; l < l1; l++) {
                    Celula colunaResultado = linhaResultado;
                    for (int j = 0; j < c1; j++) {
                        System.out.print(colunaResultado.elemento + " ");
                        colunaResultado = colunaResultado.dir;
                    }
                    System.out.println();
                    linhaResultado = linhaResultado.inf;
                }

               Matriz resultadoMult = matriz.multiplicacao(matriz1);
                if (resultadoMult != null) {  // Verifica se a multiplicação foi possível
                    Celula linhaResultado1 = resultadoMult.inicio;
                    for (int l = 0; l < l1; l++) {
                        Celula colunaResultado = linhaResultado1;
                        for (int j = 0; j < c2; j++) {
                            System.out.print(colunaResultado.elemento + " ");
                            colunaResultado = colunaResultado.dir;
                        }
                        System.out.println();
                        linhaResultado1 = linhaResultado1.inf;
                    }
                }
        }
        
        sc.close();
    }
}