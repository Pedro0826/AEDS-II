#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <ctype.h>

#define MAX_POKEMONS 802
#define MAX_STRING 256
#define MAX_TIPOS 2
#define MAX_HABILIDADES 4
#define MATRICULA "842843"

typedef struct {
    int id;
    int geracao;
    char nome[MAX_STRING];
    char descricao[MAX_STRING];
    char tipo[MAX_TIPOS][MAX_STRING];
    int num_tipos;
    char habilidade[MAX_HABILIDADES][MAX_STRING];
    int num_habilidades;
    double peso;
    double altura;
    int taxaCaptura;
    bool eLendario;
    struct tm dataCaptura;
} Pokemon;

long long num_comparacoes = 0;
long long num_movimentacoes = 0;

void trim(char *str) {
    char *start = str;
    char *end = str + strlen(str) - 1;
    while (*start && (isspace((unsigned char)*start) || *start == '"' || *start == '\'')) start++;
    while (end > start && (isspace((unsigned char)*end) || *end == '"' || *end == '\'')) end--;
    *(end + 1) = '\0';
    memmove(str, start, end - start + 2);
}

char* getfield(char** line, char* buffer) {
    char* start = *line;
    bool in_quotes = false;
    char* buf = buffer;

    while (**line) {
        if (**line == '"') {
            in_quotes = !in_quotes;
        } else if (**line == ',' && !in_quotes) {
            break;
        } else {
            *buf++ = **line;
        }
        (*line)++;
    }
    *buf = 0;
    if (**line == ',') (*line)++;
    return start;
}

void parse_list(char* token, char arr[][MAX_STRING], int* count, int max_count) {
    char* item = strtok(token, "[]");
    while (item != NULL && *count < max_count) {
        trim(item);
        if (strlen(item) > 0) {
            strncpy(arr[(*count)++], item, MAX_STRING - 1);
        }
        item = strtok(NULL, ",");
    }
}

void parse_csv_line(char* line, Pokemon* p) {
    char buffer[1024];
    int field = 0;

    while (*line) {
        getfield(&line, buffer);
        trim(buffer);

        switch(field) {
            case 0: p->id = atoi(buffer); break;
            case 1: p->geracao = atoi(buffer); break;
            case 2: strncpy(p->nome, buffer, MAX_STRING - 1); break;
            case 3: strncpy(p->descricao, buffer, MAX_STRING - 1); break;
            case 4: 
            case 5: parse_list(buffer, p->tipo, &p->num_tipos, MAX_TIPOS); break;
            case 6: parse_list(buffer, p->habilidade, &p->num_habilidades, MAX_HABILIDADES); break;
            case 7: p->peso = atof(buffer); break;
            case 8: p->altura = atof(buffer); break;
            case 9: p->taxaCaptura = atoi(buffer); break;
            case 10: p->eLendario = (strcmp(buffer, "1") == 0); break;
            case 11: {
                int dia, mes, ano;
                sscanf(buffer, "%d/%d/%d", &dia, &mes, &ano);
                p->dataCaptura.tm_mday = dia;
                p->dataCaptura.tm_mon = mes - 1;
                p->dataCaptura.tm_year = ano - 1900;
                break;
            }
        }
        field++;
    }
}

void imprimir(Pokemon *p) {
    char data_str[11];
    strftime(data_str, sizeof(data_str), "%d/%m/%Y", &p->dataCaptura);
    
    printf("[#%d -> %s: %s - [", p->id, p->nome, p->descricao);
    for (int i = 0; i < p->num_tipos; i++) {
        printf("'%s'", p->tipo[i]);
        if (i < p->num_tipos - 1) printf(", ");
    }
    printf("] - [");
    for (int i = 0; i < p->num_habilidades; i++) {
        printf("'%s'", p->habilidade[i]);
        if (i < p->num_habilidades - 1) printf(", ");
    }
    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
           p->peso, p->altura, p->taxaCaptura, p->eLendario ? "true" : "false",
           p->geracao, data_str);
}

Pokemon* carregarPokemon(const char* caminhoArquivo) {
    FILE *file = fopen(caminhoArquivo, "r");
    if (file == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return NULL;
    }

    Pokemon *pokemons = malloc(MAX_POKEMONS * sizeof(Pokemon));
    if (pokemons == NULL) {
        printf("Erro de alocação de memória.\n");
        fclose(file);
        return NULL;
    }

    char line[1024];
    int index = 0;
    
    if (fgets(line, sizeof(line), file) == NULL) {
        printf("Arquivo vazio ou erro na leitura.\n");
        fclose(file);
        free(pokemons);
        return NULL;
    }

    while (fgets(line, sizeof(line), file) && index < MAX_POKEMONS) {
        memset(&pokemons[index], 0, sizeof(Pokemon));
        parse_csv_line(line, &pokemons[index]);
        index++;
    }

    fclose(file);
    return pokemons;
}

bool strcmp2(const char *str1, const char *str2) {
    return strcmp(str1, str2) == 0;
}

void heapify(Pokemon *pokemons, int *ids, int n, int i) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    num_comparacoes++;
    if (left < n && (pokemons[ids[left] - 1].altura > pokemons[ids[largest] - 1].altura ||
        (pokemons[ids[left] - 1].altura == pokemons[ids[largest] - 1].altura && 
         strcmp(pokemons[ids[left] - 1].nome, pokemons[ids[largest] - 1].nome) > 0))) {
        largest = left;
    }

    num_comparacoes++;
    if (right < n && (pokemons[ids[right] - 1].altura > pokemons[ids[largest] - 1].altura ||
        (pokemons[ids[right] - 1].altura == pokemons[ids[largest] - 1].altura && 
         strcmp(pokemons[ids[right] - 1].nome, pokemons[ids[largest] - 1].nome) > 0))) {
        largest = right;
    }

    if (largest != i) {
        num_movimentacoes++;
        int temp = ids[i];
        ids[i] = ids[largest];
        ids[largest] = temp;

        heapify(pokemons, ids, n, largest);
    }
}

void heapSort(Pokemon *pokemons, int *ids, int count) {
    for (int i = count / 2 - 1; i >= 0; i--) {
        heapify(pokemons, ids, count, i);
    }

    for (int i = count - 1; i >= 0; i--) {
        num_movimentacoes++;
        int temp = ids[0];
        ids[0] = ids[i];
        ids[i] = temp;

        heapify(pokemons, ids, i, 0);
    }
}

int main() {
    clock_t inicio = clock();

    Pokemon *pokemons = carregarPokemon("/tmp/pokemon.csv");
    if (pokemons == NULL) {
        printf("Nenhum pokémon foi carregado.\n");
        return 1;
    }

    char input[MAX_STRING];
    int ids[MAX_POKEMONS]; 
    int count = 0;

    while (1) {
        if (fgets(input, sizeof(input), stdin) == NULL) {
            break;
        }
        input[strcspn(input, "\n")] = 0;  

        if (strcmp2(input, "FIM")) {
            break;
        }

        int num = atoi(input);
        if (num >= 1 && num <= MAX_POKEMONS) {
            ids[count++] = num;
        }
    }

    heapSort(pokemons, ids, count);

    int limit = count < 10 ? count : 10;
    for (int i = 0; i < limit; i++) {
        imprimir(&pokemons[ids[i] - 1]);
    }

    clock_t fim = clock();
    double tempo_execucao = (double)(fim - inicio) / CLOCKS_PER_SEC;

    FILE *log_file = fopen(MATRICULA "_HeapsortParcial.txt", "w");
    if (log_file) {
        fprintf(log_file, "%s\t%lld\t%lld\t%.6f\n", MATRICULA, num_comparacoes, num_movimentacoes, tempo_execucao);
        fclose(log_file);
    }

    free(pokemons);
    return 0;
}