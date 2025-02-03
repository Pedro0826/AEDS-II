int strlen2(char str[]) {

  int tam = 0, i = 0;

  while (str[i] != '\0') {
    tam++;
    i++;
  }

  return tam;
}