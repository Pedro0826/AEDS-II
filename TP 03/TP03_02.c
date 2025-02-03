#define _POSIX_C_SOURCE 200809L

#include <ctype.h>
#include <errno.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// De onde ler o CSV se não receber nenhum parâmetro na linha de comando.
#define DEFAULT_DB "/tmp/pokemon.csv"

/// Definições dos tipos de dados. ////////////////////////////////////////////

// Tipos possíveis de Pokémon.
enum PokeType {
    NO_TYPE = 0,
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER
};

// Definição do tipo de inteiro que armazena o tipo do Pokémon. Deve ter bits
// suficientes para todos os tipos.
typedef uint8_t PokeType;

// Lista de habilidades de um Pokémon.
typedef struct {
    char **list; // Lista dinâmica de strings dinâmicas.
    uint8_t num; // Quantidade de habilidades.
} PokeAbilities;

// Data.
typedef struct {
    uint16_t y; // Ano.
    uint8_t m; // Mês.
    uint8_t d; // Dia.
} Date;

// O Pokémon em si. Usamos tipos numéricos rígidos para economizar memória.
typedef struct {
    // Ordenamos os membros de maior (8 bytes) para menor (1 byte) para
    // melhorar o uso de memória, diminuindo o espaço vazio entre os
    // membros.

    // Tipos de 64 bits.
    double weight; // Peso em quilogramas.
    double height; // Altura em metros.

    // Ponteiros de 32 ou 64 bits, dependendo da máquina.
    char *name; // String dinâmica para o nome.
    char *description; // String dinâmica para a descrição.

    // Tipos de 32 bits.
    Date capture_date; // Data de captura.

    // Tipos de 16 bits.
    PokeType type[2]; // Tipos do Pokémon.
    uint16_t id; // Chave: inteiro não-negativo de 16 bits.
    uint16_t capture_rate; // Determinante da probabilidade de captura.

    // Tipos de 8 bits.
    uint8_t generation; // Geração: inteiro não-negativo de 8 bits.
    bool is_legendary; // Se é ou não um Pokémon lendário.

    // Tipo de tamamho irregular (72 bits) no final evita a introdução de
    // preenchimento no meio da struct.
    PokeAbilities abilities; // Lista dinâmica das habilidades.
} Pokemon;

/// Declarações de todas as funções. //////////////////////////////////////////

int main(int argc, char **argv);
void ler(Pokemon *restrict p, char *str);
void imprimir(Pokemon *restrict const p);
Pokemon *pokemon_from_str(char *str);
Pokemon *pokemon_from_params(uint16_t id, uint8_t generation, const char *name,
                 const char *description, const PokeType type[2],
                 const PokeAbilities *abilities, double weight_kg,
                 double height_m, uint16_t capture_rate,
                 bool is_legendary, Date capture_date);
Pokemon *pokemon_clone(const Pokemon *p);
static inline Pokemon *pokemon_new(void);
void pokemon_free(Pokemon *restrict p);
static PokeAbilities abilities_from_string(char *str);
static PokeType type_from_string(const char *str);
static const char *type_to_string(PokeType type);

/// Métodos que operam nos nossos tipos de dados. /////////////////////////////

// Lê um Pokémon a partir de uma string. A string é modificada.
void ler(Pokemon *restrict p, char *str)
{
    // Posição inicial dos termos após a lista de habilidades. Necessária
    // porque o método `abilities_from_string()` invalidará a string `str`
    // antes dessa posição.
    char *const post_list = strstr(str, "']\",") + 3;
    char *tok = NULL; // Ponteiro temporário para as substrings (tokens).
    char *sav = NULL; // Ponteiro auxiliar para o estado de `strtok_r()`.
    int tok_count = 0; // Contador auxiliar de tokens.

    // Verifica erro ao buscar a substring.
    if (!post_list) {
        int errsv = errno;
        perror("Tentei criar Pokémon com uma string mal formada");
        exit(errsv);
    }

    // Lê a chave (id) e a geração.
    p->id = atoi(strtok_r(str, ",", &sav));
    p->generation = atoi(strtok_r(NULL, ",", &sav));

    // Lê o nome.
    tok = strtok_r(NULL, ",", &sav);
    p->name = strdup(tok);

    // Lê a descrição.
    tok = strtok_r(NULL, ",", &sav);
    p->description = strdup(tok);

    // Lê o primeiro tipo.
    p->type[0] = type_from_string(strtok_r(NULL, ",", &sav));

    // Lẽ o segundo tipo, se existir.
    tok = strtok_r(NULL, "[,", &sav);
    p->type[1] = (*tok == '"') ? NO_TYPE : type_from_string(tok);

    // Lê a lista de habilidades.
    p->abilities = abilities_from_string(strtok_r(NULL, "]", &sav));
    str = post_list; // Avança para após a lista de habilidades.
    sav = NULL; // Reseta o ponteiro de `strtok_r()`.

    // Leia peso e altura, se existirem (alguns Pokémon no CSV não têm, mas
    // todos que têm peso também têm altura, e vice-versa). Por isso,
    // determina quantos campos não vazios restam.
    for (int i = 0; str[i]; ++i)
        // Vírgulas não-consecutivas indicam campo não vazio.
        if (str[i] == ',' && str[i + 1] != ',')
            ++tok_count;

    // Leia peso e altura, se existirem (alguns Pokémon no CSV não têm, mas
    // todos que têm peso também têm altura, e vice-versa).
    if (tok_count == 5) { // Se restam 5 itens, o peso e a altura existem.
        p->weight = atof(strtok_r(str, ",", &sav));
        p->height = atof(strtok_r(NULL, ",", &sav));
    } else {
        p->height = p->weight = 0; // Atribui um peso inválido.
    }

    // Lê o determinante da probabilidade de captura e se é lendário ou não.
    p->capture_rate = atoi(strtok_r(sav ? NULL : str, ",", &sav));
    p->is_legendary = atoi(strtok_r(NULL, ",", &sav));

    // Lê a data de captura.
    p->capture_date.d = atoi(strtok_r(NULL, "/", &sav));
    p->capture_date.m = atoi(strtok_r(NULL, "/", &sav));
    p->capture_date.y = atoi(strtok_r(NULL, "/\n\r", &sav));
}

// Printa um Pokémon recebido por referência em `stdout`.
void imprimir(Pokemon *restrict const p)
{
    printf("[#%d -> %s: %s - ['%s'", p->id, p->name, p->description,
           type_to_string(p->type[0]));

    if (p->type[1] != NO_TYPE)
        printf(", '%s'", type_to_string(p->type[1]));

    printf("] - ['%s'", p->abilities.list[0]);
    for (int i = 1; i < p->abilities.num; ++i)
        printf(", '%s'", p->abilities.list[i]);

    printf("] - %0.1lfkg - %0.1lfm - %u%% - %s - %u gen] - %02u/%02u/%04u\n",
           p->weight, p->height, p->capture_rate,
           p->is_legendary ? "true" : "false", p->generation,
           p->capture_date.d, p->capture_date.m, p->capture_date.y);
}

// Aloca um Pokémon a partir de uma string.
Pokemon *pokemon_from_str(char *str)
{
    Pokemon *res = pokemon_new();
    ler(res, str);
    return res;
}

// Aloca um Pokémon a partir de parâmetros.
Pokemon *pokemon_from_params(uint16_t id, uint8_t generation, const char *name,
                 const char *description, const PokeType type[2],
                 const PokeAbilities *abilities, double weight_kg,
                 double height_m, uint16_t capture_rate,
                 bool is_legendary, Date capture_date)
{
    Pokemon *res = pokemon_new();

    // Precisamos criar uma cópia profunda da lista de habilidades.
    PokeAbilities ablist_clone = { .num = abilities->num };
    ablist_clone.list = malloc(ablist_clone.num * sizeof(char *));
    for (int i = 0; i < ablist_clone.num; ++i)
        ablist_clone.list[i] = strdup(abilities->list[i]);

    *res = (Pokemon){ .id = id,
              .generation = generation,
              .name = strdup(name),
              .description = strdup(description),
              .type[0] = type[0],
              .type[1] = type[1],
              .abilities = ablist_clone,
              .weight = weight_kg,
              .height = height_m,
              .capture_rate = capture_rate,
              .is_legendary = is_legendary,
              .capture_date = capture_date };
    return res;
}

// Duplica um Pokemón.
Pokemon *pokemon_clone(const Pokemon *p)
{
    return pokemon_from_params(p->id, p->generation, p->name,
                   p->description, p->type, &p->abilities,
                   p->weight, p->height, p->capture_rate,
                   p->is_legendary, p->capture_date);
}

// Aloca um Pokémon vazio dinamicamente.
static inline Pokemon *pokemon_new(void)
{
    Pokemon *res = calloc(1, sizeof(Pokemon));
    if (!res) {
        int errsv = errno;
        perror("Impossível alocar memória para Pokémon");
        exit(errsv);
    }
    return res;
}

// Libera um Pokémon alocado dinamicamente.
void pokemon_free(Pokemon *restrict p)
{
    if (p != NULL) {
        free(p->name);
        free(p->description);
        for (int i = 0; i < p->abilities.num; ++i)
            free(p->abilities.list[i]);
        free(p->abilities.list);
        free(p);
    }
}

// Cria uma lista dinâmica de habilidades a partir de uma representação textual.
static PokeAbilities abilities_from_string(char *str)
{
    PokeAbilities res = { .num = 1 }; // Há no mínimo uma habilidade.
    char *sav = NULL;

    // Conta o número de habilidades a partir das vírgulas na string.
    for (int i = 0; str[i] && str[i] != ']'; ++i)
        if (str[i] == ',')
            ++res.num;
    // Aloca memória para a lista dinâmica.
    res.list = malloc(res.num * sizeof(char *));
    if (!res.list) {
        int errsv = errno;
        perror("Impossível alocar memória para lista de habilidades");
        exit(errsv);
    }

    // Lê cada uma das habilidades.
    for (int i = 0; i < res.num; ++i) {
        char *ability; // Ponteiro temporário para a substring (token).
        int token_len = 0; // Contador to tamanho do token `ability`.

        // Extrai um token da lista.
        ability = strtok_r(i ? NULL : str, ",]", &sav);
        if (!ability) {
            int errsv = errno;
            for (int j = 0; j < i; ++j) {
                free(res.list[j]);
            }
            free(res.list);
            res.list = NULL;
            res.num = 0;
            perror("Erro ao extrair habilidade da string");
            exit(errsv);
        }

        // Remove quaisquer caracteres exceto letras, números e espaços.
        for (int j = 0; ability[j]; ++j)
            if (isalnum(ability[j]) || isspace(ability[j]))
                ability[token_len++] = ability[j];
        ability[token_len] = '\0'; // Termina o token.

        // Remove espaços e aspas iniciais.
        while (*ability == ' ' || *ability == '\'') {
            ++ability;
            --token_len;
        }

        // Remove espaços e aspas finais.
        while (token_len > 0 && (ability[token_len - 1] == ' ' ||
                     ability[token_len - 1] == '\''))
            ability[--token_len] = '\0';

        // Aloca a memória para a habilidade e a salva no struct.
        res.list[i] = strdup(ability);
        if (!res.list[i]) {
            int errsv = errno;
            for (int j = 0; j < i; ++j) {
                free(res.list[j]);
            }
            free(res.list);
            res.list = NULL;
            res.num = 0;
            perror("Impossível alocar memória para habilidade");
            exit(errsv);
        }
    }

    return res;
}

// Converte a representação textual do tipo em um dado PokeType.
static PokeType type_from_string(const char *str)
{
    enum PokeType res;

    if (!strcmp(str, "bug"))
        res = BUG;
    else if (!strcmp(str, "dark"))
        res = DARK;
    else if (!strcmp(str, "dragon"))
        res = DRAGON;
    else if (!strcmp(str, "electric"))
        res = ELECTRIC;
    else if (!strcmp(str, "fairy"))
        res = FAIRY;
    else if (!strcmp(str, "fighting"))
        res = FIGHTING;
    else if (!strcmp(str, "fire"))
        res = FIRE;
    else if (!strcmp(str, "flying"))
        res = FLYING;
    else if (!strcmp(str, "ghost"))
        res = GHOST;
    else if (!strcmp(str, "grass"))
        res = GRASS;
    else if (!strcmp(str, "ground"))
        res = GROUND;
    else if (!strcmp(str, "ice"))
        res = ICE;
    else if (!strcmp(str, "normal"))
        res = NORMAL;
    else if (!strcmp(str, "poison"))
        res = POISON;
    else if (!strcmp(str, "psychic"))
        res = PSYCHIC;
    else if (!strcmp(str, "rock"))
        res = ROCK;
    else if (!strcmp(str, "steel"))
        res = STEEL;
    else if (!strcmp(str, "water"))
        res = WATER;
    else
        res = NO_TYPE;

    return res;
}

// Converte um dado PokeType em sua representação textual.
static const char *type_to_string(PokeType type)
{
    const char *res = NULL;
    switch (type) {
    case BUG:
        res = "bug";
        break;
    case DARK:
        res = "dark";
        break;
    case DRAGON:
        res = "dragon";
        break;
    case ELECTRIC:
        res = "electric";
        break;
    case FAIRY:
        res = "fairy";
        break;
    case FIGHTING:
        res = "fighting";
        break;
    case FIRE:
        res = "fire";
        break;
    case FLYING:
        res = "flying";
        break;
    case GHOST:
        res = "ghost";
        break;
    case GRASS:
        res = "grass";
        break;
    case GROUND:
        res = "ground";
        break;
    case ICE:
        res = "ice";
        break;
    case NORMAL:
        res = "normal";
        break;
    case POISON:
        res = "poison";
        break;
    case PSYCHIC:
        res = "psychic";
        break;
    case ROCK:
        res = "rock";
        break;
    case STEEL:
        res = "steel";
        break;
    case WATER:
        res = "water";
        break;
    default:
        fputs("FATAL: Pokémon tem um tipo desconhecido!\n", stderr);
        exit(EXIT_FAILURE);
    }

    return res;
}

typedef struct {
    Pokemon *pokemons[801];
    int n;
} Lista;

// Inicializar lista
void inicializar(Lista *lista) {
    lista->n = 0;
}

void inserirInicio(Lista *lista, Pokemon x) {
    if (lista->n >= 801) {
        printf("Erro ao Inserir!\n");
        exit(1);
    }

    for (int i = lista->n; i > 0; i--) {
        lista->pokemons[i] = lista->pokemons[i - 1];
    }

    lista->pokemons[0] = malloc(sizeof(Pokemon));  // Aloca memória para um novo Pokemon
    *(lista->pokemons[0]) = x;  // Copia o conteúdo de x para a nova posição
    lista->n++;
}

void inserirFim(Lista *lista, Pokemon x) {
    if (lista->n >= 801) {
        printf("Erro ao Inserir!\n");
        exit(1);
    }

    lista->pokemons[lista->n] = malloc(sizeof(Pokemon));  // Aloca memória para um novo Pokemon
    *(lista->pokemons[lista->n]) = x;  // Copia o conteúdo de x
    lista->n++;
}

void inserir(Lista *lista, Pokemon x, int pos) {
    if (lista->n >= 801 || pos < 0 || pos > lista->n) {
        printf("Erro ao Inserir!\n");
        exit(1);
    }

    // Desloca os elementos para a direita para abrir espaço
    for (int i = lista->n; i > pos; i--) {
        lista->pokemons[i] = lista->pokemons[i - 1];
    }

    // Aloca um novo espaço de memória para o Pokémon e copia os dados
    lista->pokemons[pos] = malloc(sizeof(Pokemon)); 
    if (lista->pokemons[pos] == NULL) {
        printf("Erro de alocação de memória!\n");
        exit(1);
    }

    *(lista->pokemons[pos]) = x;  // Copia os valores do Pokémon `x` para a posição alocada
    lista->n++;
}

Pokemon removerInicio(Lista *lista) {
    if (lista->n == 0) {
        printf("Erro ao Remover!\n");
        exit(1);
    }

    Pokemon rem = *(lista->pokemons[0]);
    free(lista->pokemons[0]); // Libera a memória do Pokémon removido
    lista->n--;

    for (int i = 0; i < lista->n; i++) {
        lista->pokemons[i] = lista->pokemons[i + 1];
    }

    return rem;
}

Pokemon removerFim(Lista *lista) {
    if (lista->n == 0) {
        printf("Erro ao Remover!\n");
        exit(1);
    }

    Pokemon rem = *(lista->pokemons[lista->n - 1]);
    free(lista->pokemons[lista->n - 1]); // Libera a memória do Pokémon removido
    lista->n--;

    return rem;
}

Pokemon remover(Lista *lista, int pos) {
    if (lista->n == 0 || pos < 0 || pos >= lista->n) {
        printf("Erro ao Remover!\n");
        exit(1);
    }

    Pokemon rem = *(lista->pokemons[pos]);
    free(lista->pokemons[pos]); // Libera a memória do Pokémon removido
    lista->n--;

    for (int i = pos; i < lista->n; i++) {
        lista->pokemons[i] = lista->pokemons[i + 1];
    }

    return rem;
}

void mostrar(Lista *lista) {
    for (int i = 0; i < lista->n; i++) {
        printf("[%d] ", i);
        imprimir(lista->pokemons[i]);
    }
}


/// Programa principal. ///////////////////////////////////////////////////////

#define NUM_PK 801 // Número máximo de Pokémon no CSV.

int main(int argc, char **argv)
{
    // Stream do arquivo CSV.
    FILE *csv = fopen((argc > 1) ? argv[1] : DEFAULT_DB, "r");
    Pokemon *pokemon[NUM_PK] = { NULL };
    Lista *pokemons = (Lista *)malloc(sizeof(Lista)); // Array de Pokémon começa vazio.
    inicializar(pokemons);
    int n = 0; // Número de Pokémon lidos.
    char *input = NULL; // Buffer para as linhas de entrada.
    size_t tam; // Capacidade do buffer de entrada.
    char num[20] = "oi";
    int a = 0;

    // Verifica se houve erro ao abrir o CSV.
    if (!csv) {
        int errsv = errno;
        perror("Falha ao abrir CSV");
        return errsv;
    }

    // Descarta a primeira linha (cabeçalho).
    while (fgetc(csv) != '\n')
        ;

    // Lê os Pokémon do CSV.
    while (n < NUM_PK && getline(&input, &tam, csv) != -1)
        pokemon[n++] = pokemon_from_str(input);
    fclose(csv); // Fecha o arquivo ao terminar.

    // Lê a entrada até encontrar "FIM"
    while (getline(&input, &tam, stdin) != -1 && strcmp(input, "FIM\n") != 0) {
        input[strcspn(input, "\n")] = 0;  // Remove o '\n' da entrada
        int idx = atoi(input) - 1;
        if (idx > -1 && idx < n) {
            inserirFim(pokemons, *pokemon[idx]);
            a++;
        } else {
            puts("Pokémon indisponível.");
        }
    }

    // Leitura de quantidade de comandos e comando de ação
    int q;
    scanf("%d", &q);
    getchar(); // Limpa o buffer após ler q

    for (int i = 0; i < q; i++) {
        int num, pos;
        char acao[3];
        scanf("%2s", acao); // Lê a ação com segurança

        if (strcmp(acao, "II") == 0) {
            int num;
            scanf("%d", &num);
            inserirInicio(pokemons, *pokemon[num-1]);
            
        } else if (strcmp(acao, "I*") == 0) {
            scanf("%d %d", &pos, &num);
            if (num >= 1 && num <= n && pos >= 0 && pos <= pokemons->n) {
                inserir(pokemons, *pokemon[num-1], pos);
            } else {
                printf("Erro: índice de Pokémon ou posição inválida!\n");
            }
            
        } else if (strcmp(acao, "IF") == 0) {
            int num;
            scanf("%d", &num);
            inserirFim(pokemons, *pokemon[num-1]);
            
        } else if (strcmp(acao, "RI") == 0) {
            Pokemon rem = removerInicio(pokemons);
            printf("(R) %s\n", rem.name);
            
        } else if (strcmp(acao, "R*") == 0) {
            int pos;
            scanf("%d", &pos);
            Pokemon rem = remover(pokemons, pos);
            printf("(R) %s\n", rem.name);
            
        } else if (strcmp(acao, "RF") == 0) {
            Pokemon rem = removerFim(pokemons);
            printf("(R) %s\n", rem.name);
        }
    }

    mostrar(pokemons);

    // Libera recursos.
    for (int i = 0; i < n; ++i)
        pokemon_free(pokemon[i]);
    free(input);
    free(pokemons); // Libera a lista de Pokémon

    return EXIT_SUCCESS;
}