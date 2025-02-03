#include <stdio.h>
#include <string.h>

int strcmp2(char str1[], char str2[]){

int i = 0;
int resp = 1;

    while (str1[i] != '\0' || str2[i] != '\0') {
        if (str1[i] != str2[i]) {
            resp = 0; // Strings são diferentes
        }
        i++;
    }

    return resp;
}

int palindromo(char str[], int inicio, int fim){ //Criando a função palindromo.

    int resp;

        if(str[inicio] != str[fim]){//Método de parada.
            resp = 0;
        }
        else if (fim <= inicio )//Metodo de parada.
            resp = 1;

        else
           resp = palindromo(str, inicio + 1, fim - 1);// Recursividade

    return resp;
}

int main(){

    char str[1000];

    do{

    scanf(" %[^\n]", str); 

    if(strcmp2(str, "FIM") == 0){//If para ver se continua ou para o código.

       int resp = palindromo(str, 0, strlen(str) - 1);//Usando a função.

        if(resp == 1)
            printf("SIM\n");
        else
            printf("NAO\n");
    }
    } while(strcmp2(str, "FIM") == 0);

    return 0;
}