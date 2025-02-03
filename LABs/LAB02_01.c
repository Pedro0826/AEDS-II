#include <stdio.h>

int tamanho(char str[]) {

  int tam = 0, i = 0;

  while (str[i] != '\0') {
    tam++;
    i++;
  }

  return tam;
}

void Combinador(char str1[], char str2[], char str3[]) {

  int n1 = tamanho(str1);
  int n2 = tamanho(str2);

  int i = 0, j = 0, k = 0;

  while (i < n1 && j < n2) {
    str3[k++] = str1[i++];
    str3[k++] = str2[j++];
  }

  while (i < n1) {
    str3[k++] = str1[i++];
  }

  while (j < n2) {
    str3[k++] = str2[j++];
  }

  str3[k] = '\0';
}

int main(void) {

  char str1[1000], str2[1000], str3[1000];

  while (scanf("%s %s", str1, str2) == 2) {
    Combinador(str1, str2, str3);
    printf("%s\n", str3);
  }

  return 0;
}