import java.util.Scanner;
import java.util.Date;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;

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

class Leitura {

    private Pokemon[] pokemons = new Pokemon[802];

    public void carregarPokemon(String caminhoArquivo) {
    
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
        }

    public Pokemon[] getLeitura(){
            return pokemons;
        }
}

class Celula
{
    Pokemon pok;
    Celula ant;
    Celula prox;

    public Celula ( )
    {
        this( null );
    } 

    public Celula ( Pokemon pokemon )
    {
        this.pok = pokemon;
        this.ant = this.prox = null;
    } 
} 

class Lista
{
    Celula primeiro;
    Celula ultimo;
    int tamanho;

    public Lista (  )
    {
        primeiro = new Celula( null );
        ultimo = primeiro;
        tamanho = 0;
    } // end Lista ( )

    public void inserirFim ( Pokemon personagem )
    {
        ultimo.prox = new Celula( personagem );
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
        tamanho++;
    } // end inserirFim ( )

    public void mostrar ( )
    {
        for( Celula i = primeiro.prox; i != null; i = i.prox ) 
        {
            i.pok.imprimir( );
        } 
    } 
} 

public class TP03_10{
    
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


        public static void swap( Celula i, Celula j) 
    {
        Pokemon temp = i.pok;
        i.pok = j.pok;
        j.pok = temp;
    } 

        public static boolean exist ( Celula i, Celula j )
    {
        boolean result = false;
        while( i != null && !result )
        {
            if( i == j ) {
                result = true;
            } 
            i = i.prox;
        } 
        return ( result );
    } 

        public static void quickSort( Celula esq, Celula dir) 
    {
        if( esq != dir && esq != dir.prox )
        {
            Celula i = esq, j = dir;
            int pivo = esq.pok.getGeracao( );
            while( exist( i, j ) ) 
            {
                while ( i.pok.getGeracao() < pivo) 
                { 
                    i = i.prox; 
                } 
                while ( j.pok.getGeracao() > pivo ) 
                { 
                    j = j.ant; 
                } 
                if( exist( i, j ) ) 
                { 
                    swap( i, j); 
                    i = i.prox; 
                    j = j.ant; 
                } 
            } 
            quickSort( esq, j);
            quickSort( i, dir);
        }
    } 

    public static void sortByName( Lista lista)
    {
        for( Celula i = lista.primeiro.prox; i != null; i = i.prox )
        {
            Celula menor = i;
            for( Celula j = i.prox; j != null; j = j.prox )
            {
                Pokemon p1 = menor.pok;
                Pokemon p2 = j.pok;

                if(p1.getGeracao() == p2.getGeracao()) 
                { // Casas são iguais, então compara os nomes
                    if( p1.getNome( ).compareTo( p2.getNome( ) ) > 0 ) 
                    {
                        menor = j;
                    } // end if
                } 
                else if( p1.getGeracao() > p2.getGeracao() ) 
                { // p1 vem depois de p2
                    menor = j;
                } // end if
            } // end for
            swap( menor, i);
        } // end for
    } 
    // end sortByName ( )
    public static void callQuick( Lista lista) 
    {
        quickSort( lista.primeiro.prox, lista.ultimo);
        sortByName(lista);
    } 

    public static void main(String[] args) throws Exception {
        
        Leitura pk = new Leitura();
        pk.carregarPokemon("/tmp/pokemon.csv");

        Pokemon[] pokemon = pk.getLeitura();
        Lista pokemons = new Lista();

        //
        Scanner sc = new Scanner(System.in);
        String n = "oi";

        while(!strcmp2(n, "FIM")){
        
        n = sc.nextLine();

        if(!strcmp2(n, "FIM")){
        int num = Integer.parseInt(n);
        pokemons.inserirFim(pokemon[num-1]);
        }
        }

        callQuick(pokemons);

        pokemons.mostrar();

        sc.close();
    }
}

