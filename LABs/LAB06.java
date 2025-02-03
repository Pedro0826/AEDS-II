
import java.util.Random;

public class LAB06{

    public static void swap(int i, int j) {
		int temp = i;
		i = j;
		j = temp;
	}

    public static void copiar(int array[], int array1[], int n){
        for(int i = 0; i < n; i++){
            array1[i] = array[i];
        }
    }

    public static void mediano(int array[]){

        int n = array.length;
        int a = array[0];
        int b = array[n/2];
        int c = array[n-1];

        if(a > b)
            swap(a,b);

        if(a > c)
            swap(a,c);

        if(b > c)
            swap(c,c); 
    }

    public static void quickSort1Pivo(int array[], int esq, int dir){

        int i = esq, j = dir;
        int pivo = array[esq];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quickSort1Pivo(array, esq, j);
        if (i < dir)  quickSort1Pivo(array, i, dir);
    }

    public static void quickSortUltPivo(int array[], int esq, int dir){

        int i = esq, j = dir;
        int pivo = array[dir];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quickSortUltPivo(array, esq, j);
        if (i < dir)  quickSortUltPivo(array, i, dir);
    }
    
    public static void quickSortRandPivo(int array[], int esq, int dir){

        int i = esq, j = dir;
        Random rand = new Random();
        int pivoIndex = esq + rand.nextInt(dir - esq + 1);

        int pivo = array[pivoIndex];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quickSortRandPivo(array, esq, j);
        if (i < dir)  quickSortRandPivo(array, i, dir);
    }
    
    public static void quickSortM3Pivo(int array[], int esq, int dir){

        int i = esq, j = dir;
        mediano(array);
        int pivo = array[(dir+esq) / 2];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quickSortM3Pivo(array, esq, j);
        if (i < dir)  quickSortM3Pivo(array, i, dir);
    }

    public static void arrayCrescente(){

        int array100[] = new int[100];
        int array1000[] = new int[1000];
        int array10000[] = new int[10000];

        int array1001[] = new int[100];
        int array10001[] = new int[1000];
        int array100001[] = new int[10000];

        int array1002[] = new int[100];
        int array10002[] = new int[1000];
        int array100002[] = new int[10000];

        int array1003[] = new int[100];
        int array10003[] = new int[1000];
        int array100003[] = new int[10000];


        for(int i = 0; i < 100; i++){
            array100[i] = i;
        }

        for(int i = 0; i < 1000; i++){
            array1000[i] = i;
        }

        for(int i = 0; i < 10000; i++){
            array10000[i] = i;
        }

        copiar(array100, array1001, 100);
        copiar(array100, array1002, 100);
        copiar(array100, array1003, 100);

        copiar(array1000, array10001, 1000);
        copiar(array1000, array10002, 1000);
        copiar(array1000, array10003, 1000);

        copiar(array10000, array100001, 10000);
        copiar(array10000, array100002, 10000);
        copiar(array10000, array100003, 10000);
        
        long inicio24 = System.nanoTime();
quickSort1Pivo(array100, 0, 99);
long fim24 = System.nanoTime();
double tempoDecorrido24 = (fim24 - inicio24) / 1000000.0;
System.out.printf("Tempo decorrido: 1Pivo 100 %.3f ms%n", tempoDecorrido24);

long inicio25 = System.nanoTime();
quickSort1Pivo(array1000, 0, 999);
long fim25 = System.nanoTime();
double tempoDecorrido25 = (fim25 - inicio25) / 1000000.0;
System.out.printf("Tempo decorrido: 1Pivo 1000 %.3f ms%n", tempoDecorrido25);

long inicio26 = System.nanoTime();
quickSort1Pivo(array10000, 0, 9999);
long fim26 = System.nanoTime();
double tempoDecorrido26 = (fim26 - inicio26) / 1000000.0;
System.out.printf("Tempo decorrido: 1Pivo 10000 %.3f ms%n", tempoDecorrido26);

long inicio27 = System.nanoTime();
quickSortUltPivo(array1001, 0, 99);
long fim27 = System.nanoTime();
double tempoDecorrido27 = (fim27 - inicio27) / 1000000.0;
System.out.printf("Tempo decorrido: UltPivo 100 %.3f ms%n", tempoDecorrido27);

long inicio28 = System.nanoTime();
quickSortUltPivo(array10001, 0, 999);
long fim28 = System.nanoTime();
double tempoDecorrido28 = (fim28 - inicio28) / 1000000.0;
System.out.printf("Tempo decorrido: UltPivo 1000 %.3f ms%n", tempoDecorrido28);

long inicio29 = System.nanoTime();
quickSortUltPivo(array100001, 0, 9999);
long fim29 = System.nanoTime();
double tempoDecorrido29 = (fim29 - inicio29) / 1000000.0;
System.out.printf("Tempo decorrido: UltPivo 10000 %.3f ms%n", tempoDecorrido29);

long inicio30 = System.nanoTime();
quickSortRandPivo(array1002, 0, 99);
long fim30 = System.nanoTime();
double tempoDecorrido30 = (fim30 - inicio30) / 1000000.0;
System.out.printf("Tempo decorrido: PivoRand 100 %.3f ms%n", tempoDecorrido30);

long inicio31 = System.nanoTime();
quickSortRandPivo(array10002, 0, 999);
long fim31 = System.nanoTime();
double tempoDecorrido31 = (fim31 - inicio31) / 1000000.0;
System.out.printf("Tempo decorrido: PivoRand 1000 %.3f ms%n", tempoDecorrido31);

long inicio32 = System.nanoTime();
quickSortRandPivo(array100002, 0, 9999);
long fim32 = System.nanoTime();
double tempoDecorrido32 = (fim32 - inicio32) / 1000000.0;
System.out.printf("Tempo decorrido: PivoRand 10000 %.3f ms%n", tempoDecorrido32);

long inicio33 = System.nanoTime();
quickSortM3Pivo(array1003, 0, 99);
long fim33 = System.nanoTime();
double tempoDecorrido33 = (fim33 - inicio33) / 1000000.0;
System.out.printf("Tempo decorrido: PivoMed 100 %.3f ms%n", tempoDecorrido33);

long inicio34 = System.nanoTime();
quickSortM3Pivo(array10003, 0, 999);
long fim34 = System.nanoTime();
double tempoDecorrido34 = (fim34 - inicio34) / 1000000.0;
System.out.printf("Tempo decorrido: PivoMed 1000 %.3f ms%n", tempoDecorrido34);

long inicio35 = System.nanoTime();
quickSortM3Pivo(array100003, 0, 9999);
long fim35 = System.nanoTime();
double tempoDecorrido35 = (fim35 - inicio35) / 1000000.0;
System.out.printf("Tempo decorrido: PivoMed 10000 %.3f ms%n", tempoDecorrido35);
        
    }

    public static void arrayDecrescente(){

        int array100[] = new int[100];
        int array1000[] = new int[1000];
        int array10000[] = new int[10000];

        int array1001[] = new int[100];
        int array10001[] = new int[1000];
        int array100001[] = new int[10000];

        int array1002[] = new int[100];
        int array10002[] = new int[1000];
        int array100002[] = new int[10000];

        int array1003[] = new int[100];
        int array10003[] = new int[1000];
        int array100003[] = new int[10000];


        for (int i = 0; i < 100; i++) {
            array100[i] = 100 - i;
        }
    
        for (int i = 0; i < 1000; i++) {
            array1000[i] = 1000 - i;
        }
    
        for (int i = 0; i < 10000; i++) {
            array10000[i] = 10000 - i;
        }

        copiar(array100, array1001, 100);
        copiar(array100, array1002, 100);
        copiar(array100, array1003, 100);

        copiar(array1000, array10001, 1000);
        copiar(array1000, array10002, 1000);
        copiar(array1000, array10003, 1000);

        copiar(array10000, array100001, 10000);
        copiar(array10000, array100002, 10000);
        copiar(array10000, array100003, 10000);
        
        long inicio24 = System.currentTimeMillis();
        quickSort1Pivo(array100, 0, 99);
        long fim24 = System.currentTimeMillis();
        long tempoDecorrido24 = fim24 - inicio24;
        System.out.println("Tempo decorrido: 1Pivo 100 " + tempoDecorrido24 + " ms");

        long inicio25 = System.currentTimeMillis();
        quickSort1Pivo(array1000, 0, 999);
        long fim25 = System.currentTimeMillis();
        long tempoDecorrido25 = fim25 - inicio25;
        System.out.println("Tempo decorrido: 1Pivo 1000 " + tempoDecorrido25 + " ms");

        long inicio26 = System.currentTimeMillis();
        quickSort1Pivo(array10000, 0, 9999);
        long fim26 = System.currentTimeMillis();
        long tempoDecorrido26 = fim26 - inicio26;
        System.out.println("Tempo decorrido: 1Pivo 10000 " + tempoDecorrido26 + " ms");

        long inicio27 = System.currentTimeMillis();
        quickSortUltPivo(array1001, 0, 99);
        long fim27 = System.currentTimeMillis();
        long tempoDecorrido27 = fim27 - inicio27;
        System.out.println("Tempo decorrido: UltPivo 100" + tempoDecorrido27 + " ms");

        long inicio28 = System.currentTimeMillis();
        quickSortUltPivo(array10001, 0, 999);
        long fim28 = System.currentTimeMillis();
        long tempoDecorrido28 = fim28 - inicio28;
        System.out.println("Tempo decorrido: UltPivo 1000" + tempoDecorrido28 + " ms");

        long inicio29 = System.currentTimeMillis();
        quickSortUltPivo(array100001, 0, 9999);
        long fim29 = System.currentTimeMillis();
        long tempoDecorrido29 = fim29 - inicio29;
        System.out.println("Tempo decorrido: UltPivo 10000" + tempoDecorrido29 + " ms");

        long inicio30 = System.currentTimeMillis();
        quickSortRandPivo(array1002, 0, 99);
        long fim30 = System.currentTimeMillis();
        long tempoDecorrido30 = fim30 - inicio30;
        System.out.println("Tempo decorrido: PivoRand 100" + tempoDecorrido30 + " ms");

        long inicio31 = System.currentTimeMillis();
        quickSortRandPivo(array10002, 0, 999);
        long fim31 = System.currentTimeMillis();
        long tempoDecorrido31 = fim31 - inicio31;
        System.out.println("Tempo decorrido: PivoRand 1000" + tempoDecorrido31 + " ms");

        long inicio32 = System.currentTimeMillis();
        quickSortRandPivo(array100002, 0, 9999);
        long fim32 = System.currentTimeMillis();
        long tempoDecorrido32 = fim32 - inicio32;
        System.out.println("Tempo decorrido: PivoRand 10000" + tempoDecorrido32 + " ms");

        long inicio33 = System.currentTimeMillis();
        quickSortM3Pivo(array1003, 0, 99);
        long fim33 = System.currentTimeMillis();
        long tempoDecorrido33 = fim33 - inicio33;
        System.out.println("Tempo decorrido: PivoMed 100" + tempoDecorrido33 + " ms");

        long inicio34 = System.currentTimeMillis();
        quickSortM3Pivo(array10003, 0, 999);
        long fim34 = System.currentTimeMillis();
        long tempoDecorrido34 = fim34 - inicio34;
        System.out.println("Tempo decorrido: PivoMed 1000" + tempoDecorrido34 + " ms");

        long inicio35 = System.currentTimeMillis();
        quickSortM3Pivo(array100003, 0, 9999);
        long fim35 = System.currentTimeMillis();
        long tempoDecorrido35 = fim35 - inicio35;
        System.out.println("Tempo decorrido: PivoMed 10000" + tempoDecorrido35 + " ms");
    }

    public static void arrayAleatorio(){

        int array100[] = new int[100];
        int array1000[] = new int[1000];
        int array10000[] = new int[10000];

        int array1001[] = new int[100];
        int array10001[] = new int[1000];
        int array100001[] = new int[10000];

        int array1002[] = new int[100];
        int array10002[] = new int[1000];
        int array100002[] = new int[10000];

        int array1003[] = new int[100];
        int array10003[] = new int[1000];
        int array100003[] = new int[10000];

        Random rand = new Random();

		for(int i = 0; i < 100; i++){
            array100[i] = i;
        }
		for (int i = 0; i < 100; i++) {
			swap(array100[i], Math.abs(rand.nextInt()) % 100);
		}

        for(int i = 0; i < 1000; i++){
            array1000[i] = i;
        }
		for (int i = 0; i < 1000; i++) {
			swap(array1000[i], Math.abs(rand.nextInt()) % 1000);
		}

        for(int i = 0; i < 10000; i++){
            array10000[i] = i;
        }
		for (int i = 0; i < 10000; i++) {
			swap(array10000[i], Math.abs(rand.nextInt()) % 10000);
		}

        copiar(array100, array1001, 100);
        copiar(array100, array1002, 100);
        copiar(array100, array1003, 100);

        copiar(array1000, array10001, 1000);
        copiar(array1000, array10002, 1000);
        copiar(array1000, array10003, 1000);

        copiar(array10000, array100001, 10000);
        copiar(array10000, array100002, 10000);
        copiar(array10000, array100003, 10000);
        
        long inicio24 = System.nanoTime();
        quickSort1Pivo(array100, 0, 99);
        long fim24 = System.nanoTime();
        double tempoDecorrido24 = (fim24 - inicio24) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 100 %.3f ms%n", tempoDecorrido24);
        
        long inicio25 = System.nanoTime();
        quickSort1Pivo(array1000, 0, 999);
        long fim25 = System.nanoTime();
        double tempoDecorrido25 = (fim25 - inicio25) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 1000 %.3f ms%n", tempoDecorrido25);
        
        long inicio26 = System.nanoTime();
        quickSort1Pivo(array10000, 0, 9999);
        long fim26 = System.nanoTime();
        double tempoDecorrido26 = (fim26 - inicio26) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 10000 %.3f ms%n", tempoDecorrido26);
        
        long inicio27 = System.nanoTime();
        quickSortUltPivo(array1001, 0, 99);
        long fim27 = System.nanoTime();
        double tempoDecorrido27 = (fim27 - inicio27) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 100 %.3f ms%n", tempoDecorrido27);
        
        long inicio28 = System.nanoTime();
        quickSortUltPivo(array10001, 0, 999);
        long fim28 = System.nanoTime();
        double tempoDecorrido28 = (fim28 - inicio28) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 1000 %.3f ms%n", tempoDecorrido28);
        
        long inicio29 = System.nanoTime();
        quickSortUltPivo(array100001, 0, 9999);
        long fim29 = System.nanoTime();
        double tempoDecorrido29 = (fim29 - inicio29) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 10000 %.3f ms%n", tempoDecorrido29);
        
        long inicio30 = System.nanoTime();
        quickSortRandPivo(array1002, 0, 99);
        long fim30 = System.nanoTime();
        double tempoDecorrido30 = (fim30 - inicio30) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 100 %.3f ms%n", tempoDecorrido30);
        
        long inicio31 = System.nanoTime();
        quickSortRandPivo(array10002, 0, 999);
        long fim31 = System.nanoTime();
        double tempoDecorrido31 = (fim31 - inicio31) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 1000 %.3f ms%n", tempoDecorrido31);
        
        long inicio32 = System.nanoTime();
        quickSortRandPivo(array100002, 0, 9999);
        long fim32 = System.nanoTime();
        double tempoDecorrido32 = (fim32 - inicio32) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 10000 %.3f ms%n", tempoDecorrido32);
        
        long inicio33 = System.nanoTime();
        quickSortM3Pivo(array1003, 0, 99);
        long fim33 = System.nanoTime();
        double tempoDecorrido33 = (fim33 - inicio33) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 100 %.3f ms%n", tempoDecorrido33);
        
        long inicio34 = System.nanoTime();
        quickSortM3Pivo(array10003, 0, 999);
        long fim34 = System.nanoTime();
        double tempoDecorrido34 = (fim34 - inicio34) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 1000 %.3f ms%n", tempoDecorrido34);
        
        long inicio35 = System.nanoTime();
        quickSortM3Pivo(array100003, 0, 9999);
        long fim35 = System.nanoTime();
        double tempoDecorrido35 = (fim35 - inicio35) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 10000 %.3f ms%n", tempoDecorrido35);
    }

    public static void arrayPoucoCrescente(){
        
        int array100[] = new int[100];
        int array1000[] = new int[1000];
        int array10000[] = new int[10000];

        int array1001[] = new int[100];
        int array10001[] = new int[1000];
        int array100001[] = new int[10000];

        int array1002[] = new int[100];
        int array10002[] = new int[1000];
        int array100002[] = new int[10000];

        int array1003[] = new int[100];
        int array10003[] = new int[1000];
        int array100003[] = new int[10000];

        int cont100 = 20;
        int cont1000 = 200;
        int cont10000 = 2000;

        for(int i = 0; i < 100; i++){
            array100[i] = i;
        }
        for(int i = 0; i < 100; i++){
            if(100%cont100 == 0){
            swap(array100[i], array100[i+1]);
            cont100 += 20;
            }
        }

        for(int i = 0; i < 1000; i++){
            array1000[i] = i;
        }
        for(int i = 0; i < 1000; i++){
            if(1000%cont1000 == 0){
            swap(array1000[i], array1000[i+1]);
            cont1000 += 200;
            }
        }


        for(int i = 0; i < 10000; i++){
            array10000[i] = i;
        }
        for(int i = 0; i < 10000; i++){
            if(10000%cont10000 == 0){
            swap(array100[i], array10000[i+1]);
            cont10000 += 2000;
            }
        }

        copiar(array100, array1001, 100);
        copiar(array100, array1002, 100);
        copiar(array100, array1003, 100);

        copiar(array1000, array10001, 1000);
        copiar(array1000, array10002, 1000);
        copiar(array1000, array10003, 1000);

        copiar(array10000, array100001, 10000);
        copiar(array10000, array100002, 10000);
        copiar(array10000, array100003, 10000);

        
        long inicio24 = System.nanoTime();
        quickSort1Pivo(array100, 0, 99);
        long fim24 = System.nanoTime();
        double tempoDecorrido24 = (fim24 - inicio24) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 100 %.3f ms%n", tempoDecorrido24);
        
        long inicio25 = System.nanoTime();
        quickSort1Pivo(array1000, 0, 999);
        long fim25 = System.nanoTime();
        double tempoDecorrido25 = (fim25 - inicio25) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 1000 %.3f ms%n", tempoDecorrido25);
        
        long inicio26 = System.nanoTime();
        quickSort1Pivo(array10000, 0, 9999);
        long fim26 = System.nanoTime();
        double tempoDecorrido26 = (fim26 - inicio26) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 10000 %.3f ms%n", tempoDecorrido26);
        
        long inicio27 = System.nanoTime();
        quickSortUltPivo(array1001, 0, 99);
        long fim27 = System.nanoTime();
        double tempoDecorrido27 = (fim27 - inicio27) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 100 %.3f ms%n", tempoDecorrido27);
        
        long inicio28 = System.nanoTime();
        quickSortUltPivo(array10001, 0, 999);
        long fim28 = System.nanoTime();
        double tempoDecorrido28 = (fim28 - inicio28) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 1000 %.3f ms%n", tempoDecorrido28);
        
        long inicio29 = System.nanoTime();
        quickSortUltPivo(array100001, 0, 9999);
        long fim29 = System.nanoTime();
        double tempoDecorrido29 = (fim29 - inicio29) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 10000 %.3f ms%n", tempoDecorrido29);
        
        long inicio30 = System.nanoTime();
        quickSortRandPivo(array1002, 0, 99);
        long fim30 = System.nanoTime();
        double tempoDecorrido30 = (fim30 - inicio30) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 100 %.3f ms%n", tempoDecorrido30);
        
        long inicio31 = System.nanoTime();
        quickSortRandPivo(array10002, 0, 999);
        long fim31 = System.nanoTime();
        double tempoDecorrido31 = (fim31 - inicio31) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 1000 %.3f ms%n", tempoDecorrido31);
        
        long inicio32 = System.nanoTime();
        quickSortRandPivo(array100002, 0, 9999);
        long fim32 = System.nanoTime();
        double tempoDecorrido32 = (fim32 - inicio32) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 10000 %.3f ms%n", tempoDecorrido32);
        
        long inicio33 = System.nanoTime();
        quickSortM3Pivo(array1003, 0, 99);
        long fim33 = System.nanoTime();
        double tempoDecorrido33 = (fim33 - inicio33) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 100 %.3f ms%n", tempoDecorrido33);
        
        long inicio34 = System.nanoTime();
        quickSortM3Pivo(array10003, 0, 999);
        long fim34 = System.nanoTime();
        double tempoDecorrido34 = (fim34 - inicio34) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 1000 %.3f ms%n", tempoDecorrido34);
        
        long inicio35 = System.nanoTime();
        quickSortM3Pivo(array100003, 0, 9999);
        long fim35 = System.nanoTime();
        double tempoDecorrido35 = (fim35 - inicio35) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 10000 %.3f ms%n", tempoDecorrido35);
    }

    public static void arrayPoucoDecrescente(){
        
        int array100[] = new int[100];
        int array1000[] = new int[1000];
        int array10000[] = new int[10000];

        int array1001[] = new int[100];
        int array10001[] = new int[1000];
        int array100001[] = new int[10000];

        int array1002[] = new int[100];
        int array10002[] = new int[1000];
        int array100002[] = new int[10000];

        int array1003[] = new int[100];
        int array10003[] = new int[1000];
        int array100003[] = new int[10000];

        int cont100 = 20;
        int cont1000 = 200;
        int cont10000 = 2000;

        for (int i = 0; i < 100; i++) {
			array100[i] = 100 - 1 - i;
		}
        for(int i = 0; i < 100; i++){
            if(100%cont100 == 0){
            swap(array100[i], array100[i+1]);
            cont100 += 20;
            }
        }

        for (int i = 0; i < 1000; i++) {
			array1000[i] = 1000 - 1 - i;
		}
        for(int i = 0; i < 1000; i++){
            if(1000%cont1000 == 0){
            swap(array100[i], array1000[i+1]);
            cont1000 += 200;
            }
        }

        for (int i = 0; i < 10000; i++) {
			array10000[i] = 10000 - 1 - i;
		}
        for(int i = 0; i < 10000; i++){
            if(10000%cont10000 == 0){
            swap(array100[i], array10000[i+1]);
            cont10000 += 2000;
            }
        }

        copiar(array100, array1001, 100);
        copiar(array100, array1002, 100);
        copiar(array100, array1003, 100);

        copiar(array1000, array10001, 1000);
        copiar(array1000, array10002, 1000);
        copiar(array1000, array10003, 1000);

        copiar(array10000, array100001, 10000);
        copiar(array10000, array100002, 10000);
        copiar(array10000, array100003, 10000);

        long inicio24 = System.nanoTime();
        quickSort1Pivo(array100, 0, 99);
        long fim24 = System.nanoTime();
        double tempoDecorrido24 = (fim24 - inicio24) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 100 %.3f ms%n", tempoDecorrido24);
        
        long inicio25 = System.nanoTime();
        quickSort1Pivo(array1000, 0, 999);
        long fim25 = System.nanoTime();
        double tempoDecorrido25 = (fim25 - inicio25) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 1000 %.3f ms%n", tempoDecorrido25);
        
        long inicio26 = System.nanoTime();
        quickSort1Pivo(array10000, 0, 9999);
        long fim26 = System.nanoTime();
        double tempoDecorrido26 = (fim26 - inicio26) / 1000000.0;
        System.out.printf("Tempo decorrido: 1Pivo 10000 %.3f ms%n", tempoDecorrido26);
        
        long inicio27 = System.nanoTime();
        quickSortUltPivo(array1001, 0, 99);
        long fim27 = System.nanoTime();
        double tempoDecorrido27 = (fim27 - inicio27) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 100 %.3f ms%n", tempoDecorrido27);
        
        long inicio28 = System.nanoTime();
        quickSortUltPivo(array10001, 0, 999);
        long fim28 = System.nanoTime();
        double tempoDecorrido28 = (fim28 - inicio28) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 1000 %.3f ms%n", tempoDecorrido28);
        
        long inicio29 = System.nanoTime();
        quickSortUltPivo(array100001, 0, 9999);
        long fim29 = System.nanoTime();
        double tempoDecorrido29 = (fim29 - inicio29) / 1000000.0;
        System.out.printf("Tempo decorrido: UltPivo 10000 %.3f ms%n", tempoDecorrido29);
        
        long inicio30 = System.nanoTime();
        quickSortRandPivo(array1002, 0, 99);
        long fim30 = System.nanoTime();
        double tempoDecorrido30 = (fim30 - inicio30) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 100 %.3f ms%n", tempoDecorrido30);
        
        long inicio31 = System.nanoTime();
        quickSortRandPivo(array10002, 0, 999);
        long fim31 = System.nanoTime();
        double tempoDecorrido31 = (fim31 - inicio31) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 1000 %.3f ms%n", tempoDecorrido31);
        
        long inicio32 = System.nanoTime();
        quickSortRandPivo(array100002, 0, 9999);
        long fim32 = System.nanoTime();
        double tempoDecorrido32 = (fim32 - inicio32) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoRand 10000 %.3f ms%n", tempoDecorrido32);
        
        long inicio33 = System.nanoTime();
        quickSortM3Pivo(array1003, 0, 99);
        long fim33 = System.nanoTime();
        double tempoDecorrido33 = (fim33 - inicio33) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 100 %.3f ms%n", tempoDecorrido33);
        
        long inicio34 = System.nanoTime();
        quickSortM3Pivo(array10003, 0, 999);
        long fim34 = System.nanoTime();
        double tempoDecorrido34 = (fim34 - inicio34) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 1000 %.3f ms%n", tempoDecorrido34);
        
        long inicio35 = System.nanoTime();
        quickSortM3Pivo(array100003, 0, 9999);
        long fim35 = System.nanoTime();
        double tempoDecorrido35 = (fim35 - inicio35) / 1000000.0;
        System.out.printf("Tempo decorrido: PivoMed 10000 %.3f ms%n", tempoDecorrido35);
    }

    public static void main(String[] args){

        arrayPoucoDecrescente();

    }
}