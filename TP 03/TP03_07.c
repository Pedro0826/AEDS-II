#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

#define MAX_STRING_LENGTH 1000
#define MAX_ABILITIES 10
#define MAX_LINE 2000
#define MAX 5


// Structure to store date
typedef struct {
    int day;
    int month;
    int year;
} Date;

// Structure to store Pokemon data
typedef struct {
    int id;
    int generation;
    char name[MAX_STRING_LENGTH];
    char description[MAX_STRING_LENGTH];
    char type1[MAX_STRING_LENGTH];
    char type2[MAX_STRING_LENGTH];
    char abilities[MAX_ABILITIES][MAX_STRING_LENGTH];
    int abilitiesCount;
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    Date captureDate;
} Pokemon;

typedef struct Celula {
    Pokemon elemento;
    struct Celula *prox;
} Celula;

typedef struct Fila {
    struct Celula *primeiro;
    struct Celula *ultimo;
    int tamanho;
} Fila;

// Function prototypes
void readPokemon(Pokemon* pokemon, char* id);
char** split(char* str, char delimiter, int* count);
void parseAbilities(char* abilitiesStr, Pokemon* pokemon);
void trim(char* str);
void removeQuotes(char* str);
char* capitalizeFirstLetter(char* str);
void printPokemon(Pokemon pokemon, int index);
char* parseCsvField(char** cursor);
Pokemon removerFila(Fila *fila);


Fila*iniciarFila() {
    Fila *fila = malloc(sizeof(Fila));
    fila -> primeiro = (Celula*)malloc(sizeof(Celula));
    fila -> primeiro -> prox = NULL;
    fila -> ultimo = fila -> primeiro;
    fila->tamanho = 0;
    return fila;
}

int tamanho(Fila *fila) {
    return fila->tamanho;
}

void inserirFila(Fila *fila, Pokemon x) {
    if (tamanho(fila) >= MAX) {
        removerFila(fila);
    }

    Celula *novaCelula = (Celula*)malloc(sizeof(Celula));
    novaCelula->elemento = x;
    novaCelula->prox = NULL;

    fila->ultimo->prox = novaCelula;
    fila->ultimo = novaCelula;
    fila->tamanho++; 

    media(fila); 
}

void media(Fila *fila) {
    int soma = 0;
    Celula *i = fila->primeiro->prox;
    int cont = 0;

    while (i != NULL) {
        soma += i->elemento.captureRate;
        i = i->prox;
        cont++;
    }
    float media = (float)soma / cont;
    int mediaArredondada = (int)ceil(media);
    printf("Média: %d\n", mediaArredondada);
}

Pokemon removerFila(Fila *fila) {
    if (fila->primeiro == fila->ultimo) {
        printf("FILA VAZIA\n");
        exit(1);
    }

    Celula *temp = fila->primeiro->prox;
    Pokemon elemento = temp->elemento;
    fila->primeiro->prox = temp->prox;

    if (fila->ultimo == temp) {
        fila->ultimo = fila->primeiro;
    }

    free(temp);
    fila->tamanho--; 

    return elemento;
}

// Read Pokemon data from CSV file
void readPokemon(Pokemon* pokemon, char* id) {
    FILE* file = fopen("/tmp/pokemon.csv", "r");
    if (file == NULL) {
        printf("ERROR: File Not Found.\n");
        return;
    }

    char line[MAX_LINE];
    fgets(line, sizeof(line), file); 

    bool found = false;
    while (fgets(line, sizeof(line), file)) {
        char* cursor = line;
        char* currentId = parseCsvField(&cursor);
        
        if (strcmp(currentId, id) == 0) {
            found = true;
            pokemon->id = atoi(currentId);
            pokemon->generation = atoi(parseCsvField(&cursor));
            
            char* name = parseCsvField(&cursor);
            strcpy(pokemon->name, name);
            
            char* desc = parseCsvField(&cursor);
            strcpy(pokemon->description, desc);
            
            char* type1 = parseCsvField(&cursor);
            strcpy(pokemon->type1, type1);
            
            char* type2 = parseCsvField(&cursor);
            strcpy(pokemon->type2, type2);
            
            char* abilities = parseCsvField(&cursor);
            parseAbilities(abilities, pokemon);
            
            pokemon->weight = atof(parseCsvField(&cursor));
            pokemon->height = atof(parseCsvField(&cursor));
            pokemon->captureRate = atoi(parseCsvField(&cursor));
            pokemon->isLegendary = atoi(parseCsvField(&cursor)) > 0;
            
            char* dateStr = parseCsvField(&cursor);
            if (dateStr[0] != '\0') {
                sscanf(dateStr, "%d/%d/%d", 
                    &pokemon->captureDate.day,
                    &pokemon->captureDate.month,
                    &pokemon->captureDate.year);
            }
            break;
        }
        
        // Skip to next line
        while (*cursor != '\0' && *cursor != '\n') cursor++;
    }

    if (!found) {
        printf("Pokémon não encontrado.\n");
    }

    fclose(file);
}

// Parse CSV field handling quotes
char* parseCsvField(char** cursor) {
    static char field[MAX_STRING_LENGTH];
    int i = 0;
    bool inQuotes = false;
    
    
    while (**cursor == ' ' || **cursor == '\t') (*cursor)++;
    
    
    if (**cursor == '"') {
        inQuotes = true;
        (*cursor)++;
    }
    
    // Copy field content
    while (**cursor != '\0' && **cursor != '\n' &&
           (inQuotes || **cursor != ',')) {
        if (**cursor == '"' && *(*cursor + 1) == '"') {
            
            field[i++] = '"';
            (*cursor) += 2;
        } else if (**cursor == '"') {
            inQuotes = !inQuotes;
            (*cursor)++;
        } else {
            field[i++] = **cursor;
            (*cursor)++;
        }
    }
    
    
    if (**cursor == ',') (*cursor)++;
    
    field[i] = '\0';
    trim(field);
    return field;
}

// Parse abilities string into array
void parseAbilities(char* abilitiesStr, Pokemon* pokemon) {
    removeQuotes(abilitiesStr);
    if (abilitiesStr[0] == '[') abilitiesStr++;
    if (abilitiesStr[strlen(abilitiesStr)-1] == ']') 
        abilitiesStr[strlen(abilitiesStr)-1] = '\0';
    
    pokemon->abilitiesCount = 0;
    char* ability = strtok(abilitiesStr, ",");
    while (ability != NULL && pokemon->abilitiesCount < MAX_ABILITIES) {
        trim(ability);
        strcpy(pokemon->abilities[pokemon->abilitiesCount++], ability);
        ability = strtok(NULL, ",");
    }
}

// Print Pokemon information
void printPokemon(Pokemon pokemon, int index) {
    printf("[%d] [#%d -> %s: %s - ['%s%s%s'] - [",
           index, pokemon.id, pokemon.name, pokemon.description,
           pokemon.type1,
           (strlen(pokemon.type2) > 0 ? "', '" : ""),
           (strlen(pokemon.type2) > 0 ? pokemon.type2 : ""));

    for (int i = 0; i < pokemon.abilitiesCount; i++) {
        printf("%s%s", pokemon.abilities[i],
               (i < pokemon.abilitiesCount - 1 ? ", " : ""));
    }

    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen]",
           pokemon.weight, pokemon.height, pokemon.captureRate,
           pokemon.isLegendary ? "true" : "false", pokemon.generation);

    if (pokemon.captureDate.year != 0) {
        printf(" - %02d/%02d/%04d",
               pokemon.captureDate.day,
               pokemon.captureDate.month,
               pokemon.captureDate.year);
    }

}

// Utility functions
void trim(char* str) {
    char* start = str;
    char* end = str + strlen(str) - 1;
    
    while(isspace((unsigned char)*start)) start++;
    while(end > start && isspace((unsigned char)*end)) end--;
    
    size_t len = (end - start) + 1;
    memmove(str, start, len);
    str[len] = '\0';
}

void removeQuotes(char* str) {
    if (str[0] == '"') {
        memmove(str, str + 1, strlen(str));
    }
    int len = strlen(str);
    if (len > 0 && str[len-1] == '"') {
        str[len-1] = '\0';
    }
}

char* capitalizeFirstLetter(char* str) {
    static char result[MAX_STRING_LENGTH];
    strcpy(result, str);
    if (strlen(result) > 0) {
        result[0] = toupper(result[0]);
    }
    return result;
}

void printFila(Fila *fila) {
    int index = 0;  

    for (Celula *i = fila->primeiro->prox; i != NULL; i = i->prox) {
        printf("\n");
        printPokemon(i->elemento, index++); 
    }
}

int main() {
    Fila *fila = iniciarFila();
    char input[MAX_STRING_LENGTH];
    
    // Read Pokemon IDs
    while (1) {
        fgets(input, sizeof(input), stdin);
        input[strcspn(input, "\n")] = 0;  
        
        if (strcmp(input, "FIM") == 0) break;
        
        Pokemon pokemon = {0};
        readPokemon(&pokemon, input);
        inserirFila(fila, pokemon);
    }
    
    // Read number of commands
    fgets(input, sizeof(input), stdin);
    int n = atoi(input);
    
    // Process commands
    for (int i = 0; i < n; i++) {
        fgets(input, sizeof(input), stdin);
        input[strcspn(input, "\n")] = 0; 
        
        char command[2];
        strncpy(command, input, 1);
        command[1] = '\0';
        
        if (strcmp(command, "I") == 0) {
            Pokemon pokemon = {0};
            readPokemon(&pokemon, input + 2);
            inserirFila(fila, pokemon);
        }
    
        else if (strcmp(command, "R") == 0) {
            Pokemon removed = removerFila(fila);
            printf("(R) %s\n",removed.name);
        }
    }
    
    // Print final fila
    printFila(fila);
    
    return 0;
}