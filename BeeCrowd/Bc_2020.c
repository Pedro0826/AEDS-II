#include <stdio.h>
#include <string.h>

typedef struct{
    char nome[50];
    float preco;
    int pref;
} Presente;

int main(){

    char pessoa[50] = "ola";
    int q = 0;

    while(scanf("%s %d", pessoa, &q) != EOF){

    Presente gift[q];

    for(int i = 0; i < q; i++){
        scanf(" %[^\n]", gift[i].nome);
        scanf("%f", &gift[i].preco);
        scanf("%d", &gift[i].pref);
    }

    for(int i = 0; i < q; i++){
        int ind = i;
        for(int j = i + 1; j < q; j++){

            if(gift[j].pref > gift[ind].pref){
                ind = j;
            }
            else if(gift[j].pref == gift[ind].pref){
                if(strcmp(gift[j].nome , gift[ind].nome) < 0){
                    ind = j;
                }
            }
        }
        Presente tmp = gift[i];
        gift[i] = gift[ind];
        gift[ind] = tmp;
    }

    printf("Lista de %s\n", pessoa);

    for(int i = 0; i < q; i++){
        printf("%s - R$%.2f\n", gift[i].nome, gift[i].preco);
    }

    printf("\n");

    }

    return 0;
}