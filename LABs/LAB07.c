#include <stdio.h>
#include <stdlib.h>

int main (){

    //Lendo N quantidade de pilotos.
    int n;

    while(scanf("%d", &n) != EOF){

    //Criação da linha de largada e chegada.
    int *inicio = malloc((n +1) * sizeof(int));
    int *chegada = malloc((n + 1) * sizeof(int));
    int *aux = malloc((n + 1) * sizeof(int));

    //Variavel de Ultrapassagens.
    int ultp = 0;

    //Lendo a ordem do início da corrida.
    for(int i = 1; i <= n; i++){
        scanf("%d", &inicio[i]);
    }
    
    //Lendo a ordem do final da corrida e colocando também no auxiliar.
    for(int i = 1; i <= n; i++){
        int num;
        scanf("%d", &num);
        chegada[i] = num;
        aux[chegada[i]] = i;
    }

    //Algoritimo para contar as ultrapassagens.

    /*Este algoritimo ele irá ver a posição em que o PRIMEIRO do inicio e comparar por aonde ele terminou, em resumo a quantidade de quedas de posição, seria as ultrapassagens, assim comparando o primeiro lugar com o 2,3,4...
      Ou seja, precisaremos de dois for de complexidade (n -1 + 1) = (n), para percorrer todos os participantes e suas ultrapassagens, então: (n) * (n) = Θ(n²).
      Esse é o jeito mais simples de fazer, mas se quiser melhorar esta complexidade para um log(n) por exemplo, vamos precisar mudar o algoritimo para algo bem mais detalhado com um MergeSort.
    */
    for(int i = 1; i <= n; i++){
        for(int j = i + 1; j <= n; j++){
            if(aux[inicio[i]] > aux[inicio[j]]){
                ultp++;
            }
        }
    }

    printf("%d\n", ultp);

    //Liberando espaço na memória.
    free(inicio);
    free(chegada);
    free(aux);
    
    }

    return 0;
}