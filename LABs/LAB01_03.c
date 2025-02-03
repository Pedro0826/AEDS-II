/*Crie um método iterativo em C que receba como parâmetro uma string e 
retorne seu número de caracteres maiúsculos. 
Em seguida, teste o método anterior usando redirecionamento de entrada e saída. 
A entrada padrão é composta por várias linhas sendo que a última contém a palavra FIM.
A saída padrão contém um número inteiro para cada linha de entrada. */

#include <stdio.h>
#include <string.h>

    void Maiusculo(char str[]){

        int n = strlen(str);
        int maiusc = 0;

        for(int i = 0; i < n; i++){

            char letra = str[i];

            if(letra >= 65 && letra <= 90)
                maiusc += 1;
        }

        printf("%d\n", maiusc);
    }

    int main(){

        char str[100];

        do{
            scanf(" %[^\n]", str);
            if(strcmp(str, "FIM") != 0){
                Maiusculo(str);
            }

        } while(strcmp(str, "FIM") != 0);

        return 0;
    }