#include <stdio.h>

    int Maiusculo(char str[], int indice){

        return (indice == strlen(str) ? 0 : ((str[indice] >= 65 && str[indice] <= 90) ? 1 : 0) 
        + Maiusculo(str, indice + 1));
    }

    int main(){

        char str[100];

        do{
            scanf(" %[^\n]", str);
            if(strcmp(str, "FIM") != 0){
                int qmaiusculo = Maiusculo(str, 0);
                printf("%d\n", qmaiusculo);
            }

        } while(strcmp(str, "FIM") != 0);

        return 0;
}