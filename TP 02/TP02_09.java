
import java.util.Scanner;
import java.util.Date;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintStream;


class Pokemon{
    
    private int id;
    private int geracao;
    private String nome;
    private String descricao;
    private ArrayList<String> tipo;
    private ArrayList<String> habilidade;
    private double peso;
    private double altura;
    private int taxaCaptura;
    private boolean eLendario;
    private Date dataCaptura;

    public Pokemon() {
        this.tipo = new ArrayList<>();
        this.habilidade = new ArrayList<>();
        this.dataCaptura = new Date();
    }

    public Pokemon(int id, int geracao, String nome, String descricao, ArrayList<String> tipo,
    ArrayList<String> habilidade, double peso, double altura, int taxaCaptura, 
    boolean eLendario, Date dataCaptura) {
        this.id = id;
        this.geracao = geracao;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.habilidade = habilidade;
        this.peso = peso;
        this.altura = altura;
        this.taxaCaptura = taxaCaptura;
        this.eLendario = eLendario;
        this.dataCaptura = dataCaptura;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getGeracao() {return geracao;}

    public void setGeracao(int geracao) {this.geracao = geracao;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public ArrayList<String> getTipo() {return tipo;}

    public void setTipo(ArrayList<String> tipo) {this.tipo = tipo;}

    public ArrayList<String> getHabilidade() {return habilidade;}

    public void setHabilidade(String novaHabilidade) {habilidade.add(novaHabilidade);}

    public double getPeso() {return peso;}

    public void setPeso(double peso) {this.peso = peso;}

    public double getAltura() {return altura;}

    public void setAltura(double altura) {this.altura = altura;}

    public int getTaxaCaptura() {return taxaCaptura;}

    public void setTaxaCaptura(int taxaCaptura) {this.taxaCaptura = taxaCaptura;}

    public boolean getELendario() {return eLendario;}

    public void setELendario(boolean eLendario) {this.eLendario = eLendario;}

    public Date getDataCaptura() {return dataCaptura;}

    public void setDataCaptura(Date dataCaptura) {this.dataCaptura = dataCaptura;}

    @Override
    public Pokemon clone() {
        try {
            return (Pokemon) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void imprimir() {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");  // Para formatar a data de captura
    
        // Formatar os tipos para ficar entre aspas simples e separados por vírgula
        String tiposFormatados = tipo.stream()
                                     .map(t -> "'" + t.trim() + "'")  // Remove espaços em branco
                                     .reduce((t1, t2) -> t1 + ", " + t2)
                                     .orElse("");
    
        // Formatar as habilidades para ficar entre aspas simples e separados por vírgula
        String habilidadesFormatadas = habilidade.stream()
                                                 .map(h -> "'" + h.replace("\"", "").trim() + "'")  // Remove aspas duplas e espaços em branco
                                                 .reduce((h1, h2) -> h1 + ", " + h2)
                                                 .orElse("");
    
        // Exibir os dados do Pokémon formatados corretamente
        System.out.println(String.format("[#%d -> %s: %s - [%s] - [%s] - %.1fkg - %.1fm - %d%% - %b - %d gen] - %s",
                id,                           // ID do Pokémon
                nome,                         // Nome do Pokémon
                descricao,                    // Descrição do Pokémon
                tiposFormatados,              // Tipos formatados com apóstrofos
                habilidadesFormatadas,        // Habilidades formatadas com apóstrofos
                peso,                         // Peso do Pokémon
                altura,                       // Altura do Pokémon
                taxaCaptura,                  // Taxa de captura
                eLendario,                    // Se é lendário ou não
                geracao,                      // Geração do Pokémon
                formatoData.format(dataCaptura)  // Data de captura formatada
        ));
    }

    public void ler(int id, int geracao, String nome, String descricao, ArrayList<String> tipo, 
    ArrayList<String> habilidade, double peso, double altura, int taxaCaptura, 
                    boolean eLendario, Date dataCaptura) {
        this.id = id;
        this.geracao = geracao;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.habilidade = habilidade;
        this.peso = peso;
        this.altura = altura;
        this.taxaCaptura = taxaCaptura;
        this.eLendario = eLendario;
        this.dataCaptura = dataCaptura;
    }
}

public class Heapsort {

        public static Pokemon[] carregarPokemon(String caminhoArquivo) {
            Pokemon[] pokemons = new Pokemon[802];  // Array de 802 Pokémon
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            int indice = 0;  // Índice para preencher o array
    
            try (Scanner scanner = new Scanner(new File(caminhoArquivo))) {
                // Pula a primeira linha de cabeçalho
                if (scanner.hasNextLine()) {
                    scanner.nextLine();  // Ignora o cabeçalho
                }
    
                // Lê as linhas restantes do arquivo CSV
                while (scanner.hasNextLine() && indice < pokemons.length) {
                    String linha = scanner.nextLine();
                    
                    // Verifica se a linha contém dados válidos
                    if (linha.trim().isEmpty()) {
                        continue;  // Pula linhas vazias
                    }
    
                    try (Scanner linhaScanner = new Scanner(linha)) {
                        linhaScanner.useDelimiter(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Trata vírgulas dentro de aspas
    
                        // Parseando os dados do Pokémon com verificações de strings vazias
                        String idStr = linhaScanner.next().trim();
                        int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);  // Se vazio, usa 0
    
                        String geracaoStr = linhaScanner.next().trim();
                        int geracao = geracaoStr.isEmpty() ? 0 : Integer.parseInt(geracaoStr);
    
                        String nome = linhaScanner.next().trim();
                        String descricao = linhaScanner.next().trim();
    
                        // Lidando com os tipos (pode ter um ou dois)
                        ArrayList<String> tipos = new ArrayList<>();
                        tipos.add(linhaScanner.next().trim());
                        String tipo2 = linhaScanner.hasNext() ? linhaScanner.next().trim() : "";
                        if (!tipo2.isEmpty()) {
                            tipos.add(tipo2);
                        }
    
                        // Corrigindo o processamento das habilidades
                        String habilidadesStr = linhaScanner.next().trim();
                        habilidadesStr = habilidadesStr.replace("[", "").replace("]", "").replace("'", "").trim();
                        String[] habilidadesArray = habilidadesStr.split(", ");
                        ArrayList<String> habilidades = new ArrayList<>();
                        for (String habilidade : habilidadesArray) {
                            habilidades.add(habilidade.trim());
                        }
    
                        // Verificando se os campos numéricos não estão vazios
                        String pesoStr = linhaScanner.next().trim();
                        double peso = pesoStr.isEmpty() ? 0 : Double.parseDouble(pesoStr);
    
                        String alturaStr = linhaScanner.next().trim();
                        double altura = alturaStr.isEmpty() ? 0 : Double.parseDouble(alturaStr);
    
                        String taxaCapturaStr = linhaScanner.next().trim();
                        int taxaCaptura = taxaCapturaStr.isEmpty() ? 0 : Integer.parseInt(taxaCapturaStr);
    
                        String eLendarioStr = linhaScanner.next().trim();
                        boolean eLendario = !eLendarioStr.isEmpty() && eLendarioStr.equals("1");
    
                        // Parse da data de captura
                        String dataStr = linhaScanner.next().trim();
                        Date dataCaptura = dataStr.isEmpty() ? new Date() : formatoData.parse(dataStr);
    
                        // Cria o objeto Pokémon e o insere no array
                        pokemons[indice] = new Pokemon(id, geracao, nome, descricao, tipos, habilidades, peso, altura, taxaCaptura, eLendario, dataCaptura);
                        indice++;  // Incrementa o índice para o próximo Pokémon
                    } catch (ParseException e) {
                        System.err.println("Erro ao fazer parse da data: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao fazer parse de número: " + e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    
            return pokemons;
        }
    
        public static boolean strcmp2(String str1, String str2) {

            boolean resp = true;
    
            if (str1.length() != str2.length()) {
                resp = false;
            }
    
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    resp = false;
                    i = str1.length();
                }
            }
    
            return resp;
        }

        public static void swap(Pokemon[] array, int a, int b) {
            Pokemon temp = array[a];
            array[a] = array[b];
            array[b] = temp; 
        }
        
        public static void construir(Pokemon[] array, int tamHeap, int[] nComp, int[] nMov) {
            for (int i = tamHeap; i > 1; i /= 2) {
                nComp[0]++; // Contando a comparação
                if (comparaPokemon(array[i], array[i / 2]) > 0) {
                    swap(array, i, i / 2);
                    nMov[0]++; // Contando as movimentações
                }
            }
        }
        
        public static void reconstruir(Pokemon[] array, int tamHeap, int[] nComp, int[] nMov) {
            int i = 1;
            while (i <= (tamHeap / 2)) {
                int filho = getMaiorFilho(array, i, tamHeap, nComp);
                nComp[0]++; // Contando a comparação
                if (comparaPokemon(array[i], array[filho]) < 0) {
                    swap(array, i, filho);
                    nMov[0]++; // Contando as movimentações
                    i = filho;
                } else {
                    i = tamHeap;
                }
            }
        }
        
        public static int getMaiorFilho(Pokemon[] array, int i, int tamHeap, int[] nComp) {
            int filho;
            if (2 * i == tamHeap || comparaPokemon(array[2 * i], array[2 * i + 1]) > 0) {
                filho = 2 * i;
            } else {
                filho = 2 * i + 1;
            }
            nComp[0]++; // Contando a comparação
            return filho;
        }
        
        // Função para comparar Pokémon por altura e nome
        public static int comparaPokemon(Pokemon p1, Pokemon p2) {
            int alturaComparison = Double.compare(p1.getAltura(), p2.getAltura());
            return alturaComparison != 0 ? alturaComparison : p1.getNome().compareTo(p2.getNome());
        }
        
        public static void main(String[] args) {
            Pokemon[] pokemons = carregarPokemon("/tmp/pokemon.csv");
            Pokemon[] pokemonsSec = new Pokemon[801];
            int a = 0;
        
            Scanner sc = new Scanner(System.in);
            String p = "oi";
            int[] nMov = {0}; // Contagem como array
            int[] nComp = {0}; // Contagem como array
        
            while(!strcmp2(p, "FIM")){
        
                p = sc.nextLine();
        
                if(!strcmp2(p, "FIM")){
                int num = Integer.parseInt(p);
                pokemonsSec[a] = pokemons[num-1];
                a++;
                }
                }
        
            long inicio = System.nanoTime();
        
            // Ajusta o array para o tamanho correto
            Pokemon[] tmp = new Pokemon[a + 1];
            System.arraycopy(pokemonsSec, 0, tmp, 1, a);
            pokemonsSec = tmp;
        
            // Construção do heap
            for (int tamHeap = 2; tamHeap <= a; tamHeap++) {
                construir(pokemonsSec, tamHeap, nComp, nMov);
            }
        
            // Ordenação propriamente dita
            int tamHeap = a;
            while (tamHeap > 1) {
                swap(pokemonsSec, 1, tamHeap--);
                nMov[0]++; // Contando as movimentações
                reconstruir(pokemonsSec, tamHeap, nComp, nMov);
            }
        
            // Remove a posição extra e ajusta o array
            tmp = pokemonsSec;
            pokemonsSec = new Pokemon[a];
            System.arraycopy(tmp, 1, pokemonsSec, 0, a);
        
            long fim = System.nanoTime();
            long tempoExecucao = fim - inicio;
        
            for (int i = 0; i < a; i++) {
                pokemonsSec[i].imprimir();
            }
        
            try (PrintStream log = new PrintStream("842843_insercao.txt")) {
                log.println("842843" + "|t " + nComp[0] + "|t " + nMov[0] + "|t " + tempoExecucao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            sc.close();
        }
        
}
