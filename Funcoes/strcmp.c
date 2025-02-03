
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