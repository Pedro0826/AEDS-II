#include <stdio.h>
#include <stdlib.h>

int main() {

    FILE *arq;
    
    arq = fopen("arquivo.txt", "wb+"); //Abrindo o arquivo para adicionar n numeros.

    int n;

    scanf("%d", &n);

    for(int i = 0; i < n; i++){

        double num;
        scanf("%lf", &num);

        fwrite(&num, sizeof(double), 1, arq);
    }

    fclose(arq);

    //Abrindo o arquivo para ler ele de trás para frente e printando.
    arq = fopen("arquivo.txt", "rb");

    for(int i = n - 1; i >= 0; i--){
        double num;
        fseek(arq, i * sizeof(double), SEEK_SET);
        fread(&num, sizeof(double), 1, arq);
        printf("%g\n", num);
    }

    fclose(arq);

    return 0;
}