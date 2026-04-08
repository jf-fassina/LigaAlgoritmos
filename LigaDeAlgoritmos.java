import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LigaDeAlgoritmos {

    public static void main(String[] args) {
        // TODO--> GUI
        String caminhoParc = "src/main/resources/";
        // System.out.println("\nMaçãs para o Rei I");
        // macasParaOReiI();
        // System.out.println("\nMaçãs para o Rei II");
        // macasParaOReiII( "macas_para_o_rei_2.txt");
        // System.out.println("\nTrincas");
        // trincas();
        // System.out.println("\nBolinhas de Gude");
        // bolinhasDeGude();
        // System.out.println("\nCódigo Real");
        // codigoReal(lerInput(caminho + "codigo_real.txt"));
        //System.out.println("\nElixir Sagrado");
        //elixirSagrado(lerInput(/*caminho + */"elixir_sagrado.txt"));
        //System.out.println("\nO Enigma da Piramide");
        //enigmaPiramide("n=342");
        aHorda(lerInput(/*caminho + */"a_horda_input.txt"));
    }

    private static String lerInput(String caminho) {

        StringBuilder input = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(caminho);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String linha = null;

            while ((linha = br.readLine()) != null) {
                input.append(linha).append("\n");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return input.toString();
    }

    private static List<String> quebraLinha(String inp) {
        List<String> str = new ArrayList<String>();
        String buffer = "";
        for (int i = 0; i < inp.length(); i++) {
            if (inp.charAt(i) != '\n')
                buffer += inp.charAt(i);
            else {
                str.add(buffer);
                buffer = "";
            }
        }

        return str;
    }

    private static void macasParaOReiI() {
        int[] macas = { 8, 6, 1, 3, 4 };
        int boas = 0;
        for (int i : macas)
            boas += i % 2;
        System.out.println(boas);
    }

    private static void macasParaOReiII(String inp) {

        String macas = lerInput(inp).toString();

        System.out.println(macas.chars().filter(i -> Character.getNumericValue(i) % 2 != 0).count());
        // macas.chars().forEach(i -> System.out.printf((Character.getNumericValue(i) %
        // 2 == 0) ? "Ruim\t%c\n" : "Boa\t%c\n", i));

    }

    private static void trincas() {
        Scanner in = new Scanner(System.in);
        String[] inp = new String[3];

        byte i = 0;
        while (i <= 2) {
            System.out.println("Digite o valor numérico da carta, após o naipe(1,2,3,4)");
            inp[i] = in.nextLine();
            if (inp[i].matches("\\d+\\s+\\d+"))
                i++;
        }

        System.out.println(Arrays.stream(inp)
                .map(c -> new String[] { c.substring(0, c.indexOf(" ")), c.substring(c.lastIndexOf(" ") - 1) })
                .collect(Collectors.collectingAndThen(Collectors.<String[]>toList(), list -> {
                    // retira {num} iguais e conta +1 se n remover
                    long numDistinct = list.stream().map(arr -> arr[0]).distinct().count();
                    // retira {naipe} iguais e conta +1 se n remover
                    long naipeDistinct = list.stream().map(arr -> arr[1]).distinct().count();
                    return numDistinct == 1 && naipeDistinct == list.size();
                    // true se nums unicos = 1 e naipes = 3 (todos)
                })) ? "É Trinca" : "Não é trinca");
        in.close();
    }

    private static void bolinhasDeGude() {
        String inp;
        // 150 G 300 M 550 P = max
        do {
            System.out.println("Digite o número de bolinhas Pequenas, Médias e Grandes, respectivamente");
            inp = new java.util.Scanner(System.in).nextLine();
        } while (!inp.matches("\\d+\\s+\\d+\\s+\\d+"));

        int[] nums = { Integer.parseInt(inp.substring(0, inp.indexOf(" ")).trim()),
                Integer.parseInt(inp.substring(inp.indexOf(" "), inp.lastIndexOf(" ")).trim()),
                Integer.parseInt(inp.substring(inp.lastIndexOf(" ")).trim()) };

        AtomicInteger caixas = new AtomicInteger(0);
        AtomicInteger counter = new AtomicInteger(0);
        Arrays.stream(nums).forEachOrdered(n -> {
            int[] capacidades = { 550, 300, 150 };
            int idCaixa = counter.incrementAndGet();
            System.out.printf("Bolinhas: %d\t\tValor: %d\n", n, idCaixa);

            int necessarias = (int) Math.ceil((double) n / capacidades[idCaixa - 1]);
            caixas.set(Math.max(caixas.get(), necessarias));
        });

        System.out.printf("Serão necessárias %d caixas\n", caixas.get());

    }

    private static void codigoReal(String inp) {

        Map<Character, Integer> tabela = new HashMap<>();
        tabela.put('!', 1);
        tabela.put('@', 5);
        tabela.put('#', 10);
        tabela.put('$', 50);
        tabela.put('%', 100);
        tabela.put('&', 500);
        tabela.put('*', 1000);
        // &%%%##@

        List<String> cod = quebraLinha(inp);
        long[] num = new long[cod.size()];
        int k = 0;

        for (String i : cod) {
            System.out.println("-----------------------------------------------------");
            for (int j = 0; j < i.length(); j++) {
                if (tabela.containsKey(i.charAt(j))) {
                    int atual = tabela.get(i.charAt(j));
                    // System.out.println(atual);

                    if (j != (i.length() - 1)) {
                        int prox = tabela.get(i.charAt(j + 1));
                        if (prox > atual) {
                            num[k] -= atual;
                        } else {
                            num[k] += atual;
                        }
                    } else {
                        num[k] += atual;
                    }
                    System.out.println("Linha " + (k + 1) + ": " + num[k]);

                }
                // System.out.println(num[j]);
            }
            k++;
        }
        long resultado = 0;
        for (long i : num) {
            resultado += i;
        }

        System.out.println(resultado);
    }

    private static void elixirSagrado(String inp) {

        /*
         * Mexer em sentido horário (addx): Adicionando sal ou açúcar enquanto mexe,
         * aumentando ou diminuindo a DENSIDADE. O número após o addx representa o valor
         * que você deve adicionar à densidade (positivo para sal, negativo para
         * açúcar). Esta ação demora 2 ciclos.
         *
         * Mexer em sentido anti-horário (noop): Sem adicionar sal ou açúcar. Esta ação
         * demora 1 ciclo.
         *
         * Cálculo do Sinal: Para verificar se o processo está correto, você deve
         * calcular o "sinal" em ciclos específicos: 20, 60, 100, 140, 180 e 220. O
         * sinal de um ciclo é o resultado da multiplicação do número do ciclo pelo
         * valor da densidade no INÍCIO desse ciclo. Ao final, some todos esses valores.
         *
         * Exemplo de validação:
         *
         * Ciclo 20: Densidade 21 -> 20 * 21 = 420
         *
         * Ciclo 60: Densidade 19 -> 60 * 19 = 1140
         *
         * Ciclo 100: Densidade 18 -> 100 * 18 = 1800
         *
         * Ciclo 140: Densidade 21 -> 140 * 21 = 2940
         *
         * Ciclo 180: Densidade 16 -> 180 * 16 = 2880
         *
         * Ciclo 220: Densidade 18 -> 220 * 18 = 3960
         *
         * Soma total: 13140.
         *
         */

        String[] cod = quebraLinha(inp).toArray(new String[0]);

        AtomicInteger dens = new AtomicInteger(1);
        AtomicInteger ciclo = new AtomicInteger(1);
        AtomicInteger soma = new AtomicInteger(0);

        Arrays.stream(cod).forEachOrdered(c -> {

            for (int i = 0; i < 2;){
                if (ciclo.get() == 20 || ciclo.get() == 60 || ciclo.get() == 100 || ciclo.get() == 140
                        || ciclo.get() == 180 || ciclo.get() == 220) {
                    soma.addAndGet(ciclo.get() * dens.get());
                }

                if (c.contains("noop")) {
                    System.out.printf("\nCiclo %d \tAção: noop", ciclo.getAndAdd(1));
                    i = 2;
                } else {
                    System.out.printf("\nCiclo %d \tAção: addx", ciclo.getAndAdd(1));

                    if(i == 1)System.out.printf(" %d", dens.addAndGet(Integer.parseInt(c.substring(c.indexOf(" ") + 1))));

                    i++;
                }
            }

        });
        System.out.printf("\nResultado da soma eh %d", soma.get());
    }

    private static void enigmaPiramide(String inp){
        //n=342
    int h = Integer.parseInt(inp.substring(inp.lastIndexOf("=")+1).trim()) ;
    int n = 1;
    int soma =0;
    String prt = "";
    //1
        //22
        // 333

        for (int i = 0; i < h; i++){
            for (int j = 0; j < n; j++){
                prt += String.valueOf(n) + " ";

            }
            System.out.println(prt + " soma: " + n*n);
            soma += n*n;
            prt = "";
            n++;
        }
        System.out.println(soma);

        //soma dos números de cada nível e imprimir esse resultado exatamente ao lado da linha correspondente.
    }

    private static void aHorda(String inp){
        String[] tabela = Arrays.stream(inp.split(" "))
                .toArray(String[]::new);
/*
* DMAYP
CABSE
KBLGW
DZAVS
NRXQJ
RVUTQ
AOGFJ
HAXOW
KCVIG
KCVLE
* */
        for(String s : tabela){
            System.out.println(s);
        }

    }

}
