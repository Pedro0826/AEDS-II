#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <ctype.h>

#define MAX_STRING 256
#define MAX_TIPOS 2
#define MAX_HABILIDADES 4

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

typedef struct Node {
    Pokemon pokemon;
    struct Node* prev;
    struct Node* next;
} Node;

void trim(char *str);
char* getfield(char** line, char* buffer);
void parse_list(char* token, char arr[][MAX_STRING], int* count, int max_count);
void parse_csv_line(char* line, Pokemon* p);
void imprimir(Pokemon *p);
Node* carregarPokemons(const char* caminhoArquivo);
void quicksort(Node* left, Node* right, int *nComp, int *nMov);
int compararPokemon(const Pokemon *p1, const Pokemon *p2);
Node* buscarPokemonPorId(int id, Node* head);
void adicionarNo(Node** head, Node** tail, Pokemon pokemon);
void liberarLista(Node* head);

void trim(char *str) {
    char *start = str;
    char *end = str + strlen(str) - 1;
    while(*start && (isspace((unsigned char)*start) || *start == '"' || *start == '\'')) start++;
    while(end > start && (isspace((unsigned char)*end) || *end == '"' || *end == '\'')) end--;
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

    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %02d/%02d/%d\n",
           p->peso, p->altura, p->taxaCaptura,
           p->eLendario ? "true" : "false",
           p->geracao, 
           p->dataCaptura.tm_mday, 
           p->dataCaptura.tm_mon + 1, 
           p->dataCaptura.tm_year + 1900);
}

Node* carregarPokemons(const char* caminhoArquivo) {
    FILE *file = fopen(caminhoArquivo, "r");
    if (file == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return NULL;
    }

    Node* head = NULL;
    Node* tail = NULL;
    char line[1024];

    if (fgets(line, sizeof(line), file) == NULL) {
        printf("Arquivo vazio ou erro na leitura.\n");
        fclose(file);
        return NULL;
    }

    while (fgets(line, sizeof(line), file)) {
        Pokemon pokemon;
        memset(&pokemon, 0, sizeof(Pokemon));
        parse_csv_line(line, &pokemon);
        adicionarNo(&head, &tail, pokemon);
    }

    fclose(file);
    return head;
}

void adicionarNo(Node** head, Node** tail, Pokemon pokemon) {
    Node* new_node = (Node*)malloc(sizeof(Node));
    if (new_node == NULL) {
        printf("Erro de alocação de memória.\n");
        return;
    }
    new_node->pokemon = pokemon;
    new_node->next = NULL;
    new_node->prev = *tail;

    if (*tail) {
        (*tail)->next = new_node;
    } else {
        *head = new_node;
    }
    *tail = new_node;
}

Node* buscarPokemonPorId(int id, Node* head) {
    Node* current = head;
    while (current != NULL) {
        if (current->pokemon.id == id) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

void quicksort(Node* left, Node* right, int* nComp, int* nMov) {
    if (left && right && left != right && left != right->next) {
        Node *i = left->prev, *j = left;
        Pokemon pivot = right->pokemon;

        
        while (j != right) {
            (*nComp)++; 
            if (compararPokemon(&j->pokemon, &pivot) < 0) {
                i = (i == NULL) ? left : i->next;

               
                Pokemon temp = i->pokemon;
                i->pokemon = j->pokemon;
                j->pokemon = temp;
                (*nMov)++; 
            }
            j = j->next;
        }

        i = (i == NULL) ? left : i->next;

        // Coloca o pivot na posição correta
        Pokemon temp = i->pokemon;
        i->pokemon = right->pokemon;
        right->pokemon = temp;
        (*nMov)++; // Incrementa o número de movimentações

        // Chamadas recursivas
        quicksort(left, i->prev, nComp, nMov);
        quicksort(i->next, right, nComp, nMov);
    }
}

int compararPokemon(const Pokemon *p1, const Pokemon *p2) {
    if (p1->geracao < p2->geracao) return -1;
    if (p1->geracao > p2->geracao) return 1;
    return strcmp(p1->nome, p2->nome);
}

void liberarLista(Node* head) {
    Node* temp;
    while (head != NULL) {
        temp = head;
        head = head->next;
        free(temp);
    }
}

int main() {

    struct timespec inicio, fim;

    int nComp = 0, nMov = 0;
    
    Node* todosPokemons = carregarPokemons("/tmp/pokemon.csv");
    if (todosPokemons == NULL) {
        printf("Nenhum Pokémon foi carregado.\n");
        return 1;
    }

    Node* pokemonsSelecionados = NULL;
    Node* tailSelecionados = NULL;

    char entrada[MAX_STRING];
    while (true) {
        fgets(entrada, sizeof(entrada), stdin);
        trim(entrada);

        if (strcmp(entrada, "FIM") == 0) {
            break;
        }

        int id = atoi(entrada);
        Node* encontrado = buscarPokemonPorId(id, todosPokemons);
        if (encontrado != NULL) {
            adicionarNo(&pokemonsSelecionados, &tailSelecionados, encontrado->pokemon);
        }
    }
    clock_gettime(CLOCK_MONOTONIC, &inicio);
    quicksort(pokemonsSelecionados, tailSelecionados, &nComp, &nMov);
    clock_gettime(CLOCK_MONOTONIC, &fim);

    double tempoExecucao = (fim.tv_sec - inicio.tv_sec) * 1e9 + (fim.tv_nsec - inicio.tv_nsec);

    Node* current = pokemonsSelecionados;
    while (current != NULL) {
        imprimir(&current->pokemon);
        current = current->next;
    }

    liberarLista(todosPokemons);
    liberarLista(pokemonsSelecionados);

    FILE *log = fopen("842843_quicksort2.txt", "w");
    fprintf(log, "842843 |t %d|t %d|t %.4f\n", nComp, nMov, tempoExecucao);
    fclose(log);

    return 0;
}