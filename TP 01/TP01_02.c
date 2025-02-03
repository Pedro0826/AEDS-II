/*Crie um método iterativo que recebe uma string como parâmetro e retorna true se essa é um ``Palíndromo''. 
Na saída padrão, para cada linha de entrada, escreva uma linha de saída com SIM/NÃO indicando se a linha é um palíndromo. 
Destaca-se que uma linha de entrada pode ter caracteres não letras */

#include <stdio.h>

int strlen2(char str[]) {

  int tam = 0, i = 0;

  while (str[i] != '\0') {
    tam++;
    i++;
  }

  return tam;
}

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

void palindromo(char str[]){ //Criação da função para saber se é palindromo ou não.

    int n = strlen2(str);

    for(int i = 0, j = n-1; i <= n/2; i++, j--){ //For para percorrer a primeira letra com a ultima até chegar ao meio da palavra.

        if(str[i] != str[j]){
            printf("NAO\n");
            i = n;
        }
        else if (j <= i )
            printf("SIM\n");
    }
}

int main(){

    char str[1000];
    
    do{
    
    scanf(" %[^\n]", str); //Lendo a string.

    if(strcmp2(str, "FIM") == 0) //Condição para ver se usa a função ou para o código.
        palindromo(str);

    } while(strcmp2(str, "FIM") == 0);

    return 0;
}