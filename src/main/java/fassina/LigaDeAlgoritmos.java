
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        //aHorda(lerInput(/*caminho + */"a_horda_input.txt"));
        peguemOPombo(lerInput("peguem_o_pombo_input.txt"));
    }

    private static String lerInput(String caminho) {

        StringBuilder input = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(caminho); InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8); BufferedReader br = new BufferedReader(isr)) {
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
            if (inp.charAt(i) != '\n') buffer += inp.charAt(i);
            else {
                str.add(buffer);
                buffer = "";
            }
        }

        return str;
    }

    private static void macasParaOReiI() {
        int[] macas = {8, 6, 1, 3, 4};
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
            if (inp[i].matches("\\d+\\s+\\d+")) i++;
        }

        System.out.println(Arrays.stream(inp).map(c -> new String[]{c.substring(0, c.indexOf(" ")), c.substring(c.lastIndexOf(" ") - 1)}).collect(Collectors.collectingAndThen(Collectors.<String[]>toList(), list -> {
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

        int[] nums = {Integer.parseInt(inp.substring(0, inp.indexOf(" ")).trim()), Integer.parseInt(inp.substring(inp.indexOf(" "), inp.lastIndexOf(" ")).trim()), Integer.parseInt(inp.substring(inp.lastIndexOf(" ")).trim())};

        AtomicInteger caixas = new AtomicInteger(0);
        AtomicInteger counter = new AtomicInteger(0);
        Arrays.stream(nums).forEachOrdered(n -> {
                    int[] capacidades = {550, 300, 150};
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

                    for (int i = 0; i < 2; ) {
                        if (ciclo.get() == 20 || ciclo.get() == 60 || ciclo.get() == 100 || ciclo.get() == 140 || ciclo.get() == 180 || ciclo.get() == 220) {
                            soma.addAndGet(ciclo.get() * dens.get());
                        }

                        if (c.contains("noop")) {
                            System.out.printf("\nCiclo %d \tAção: noop", ciclo.getAndAdd(1));
                            i = 2;
                        } else {
                            System.out.printf("\nCiclo %d \tAção: addx", ciclo.getAndAdd(1));

                            if (i == 1)
                                System.out.printf(" %d", dens.addAndGet(Integer.parseInt(c.substring(c.indexOf(" ") + 1))));

                            i++;
                        }
                    }

            });
        System.out.printf("\nResultado da soma eh %d", soma.get());
    }

    private static void enigmaPiramide(String inp) {
        //n=342
        int h = Integer.parseInt(inp.substring(inp.lastIndexOf("=") + 1).trim());
        int n = 1;
        int soma = 0;
        String prt = "";
        //1
        //22
        // 333

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < n; j++) {
                prt += String.valueOf(n) + " ";

            }
            System.out.println(prt + " soma: " + n * n);
            soma += n * n;
            prt = "";
            n++;
        }
        System.out.println(soma);

        //soma dos números de cada nível e imprimir esse resultado exatamente ao lado da linha correspondente.
    }

    private static void aHorda(String inp) {
        String[] tabela = Arrays.stream(inp.split(" ")).toArray(String[]::new);
        //DMAYP CABSE KBLGW DZAVS NRXQJ RVUTQ AOGFJ HAXOW KCVIG KCVLE --> error?
        for (String s : tabela) {
            System.out.println(s);
        }

    }

    private static void peguemOPombo(String inp) {
        /*
        30 minutos pelo Pombo
        válvulas (que liberariam a pressão do gás para a parte externa)
        Cada válvula possui uma certa quantidade de fluxo de ULPG

        Por exemplo, se no papel tivesse escrito:

        Válvula AA tem fluxo de 0 ulpg/min; passagens secretas para válvulas DD, II, BB
        Válvula BB tem fluxo de 13 ulpg/min; passagens secretas para válvulas CC, AA
        Válvula CC tem fluxo de 2 ulpg/min; passagens secretas para válvulas DD, BB
        Válvula DD tem fluxo de 20 ulpg/min; passagens secretas para válvulas CC, AA, EE
        Válvula EE tem fluxo de 3 ulpg/min; passagens secretas para válvulas FF, DD
        Válvula FF tem fluxo de 0 ulpg/min; passagens secretas para válvulas EE, GG
        Válvula GG tem fluxo de 0 ulpg/min; passagens secretas para válvulas FF, HH
        Válvula HH tem fluxo de 22 ulpg/min; passagem secreta pra válvula GG
        Válvula II tem fluxo de 0 ulpg/min; passagens secretas para válvulas AA, JJ
        Válvula JJ tem fluxo de 21 ulpg/min; passagem secreta pra válvula II

        Todas as válvulas começam inicialmente fechadas

        leva 1 minuto para atravessar uma passagem) e ir para a válvula BB, e abrí-la (também leva 1 minuto para abrir uma válvula).
        Agora a válvula BB estará aberta pelos próximos 28 minutos, resultando em uma liberação de 364ulpg (28 * 13 = 364).
        Dick agora tem alguma esperança de que seja possível escapar da armadilha do Pombo, porém ele não faz a menor ideia de como ele pode fazer isso nos 30 minutos da melhor forma possível.

        Dick agora tem alguma esperança de que seja possível escapar da armadilha do Pombo, porém ele não faz a menor ideia de como ele pode fazer isso nos 30 minutos da melhor forma possível.

        Nessa caso, a melhor sequência de decisões seria:

        == Minuto 1 ==
        Nenhuma válvulas está aberta.
        Dick se move para válvula DD.
        == Minuto 2 ==
        Nenhuma válvula está aberta.
        Dick abre a válvula DD.
        == Minuto 3 ==
        Válvula DD está aberta, liberando 20 ulpg.
        Dick se move para válvula CC.
        == Minuto 4 ==
        Válvula DD está aberta, liberando 20 ulpg.
        Dick se move para válvula BB.
        == Minuto 5 ==
        Válvula DD está aberta, liberando 20 ulpg.
        Dick abre a válvula BB.
        == Minuto 6 ==
        Válvulas BB e DD estão abertas, liberando 33 ulpg.
        Dick se move para válvula AA.
        == Minuto 7 ==
        Válvulas BB e DD estão abertas, liberando 33 ulpg.
        Dick se move para válvula II.
        == Minuto 8 ==
        Válvulas BB e DD estão abertas, liberando 33 ulpg.
        Dick se move para válvula JJ.
        == Minuto 9 ==
        Válvulas BB e DD estão abertas, liberando 33 ulpg.
        Dick abre a válvula JJ.
        == Minuto 10 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula II.
        == Minuto 11 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula AA.
        == Minuto 12 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula DD.
        == Minuto 13 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula EE.
        == Minuto 14 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula FF.
        == Minuto 15 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula GG.
        == Minuto 16 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick se move para válvula HH.
        == Minuto 17 ==
        Válvulas BB, DD, e JJ estão abertas, liberando 54 ulpg.
        Dick abre a válvula HH.
        == Minuto 18 ==
        Válvulas BB, DD, HH, e JJ estão abertas, liberando 76 ulpg.
        Dick se move para válvula GG.
        == Minuto 19 ==
        Válvulas BB, DD, HH, e JJ estão abertas, liberando 76 ulpg.
        Dick se move para válvula FF.
        == Minuto 20 ==
        Válvulas BB, DD, HH, e JJ estão abertas, liberando 76 ulpg.
        Dick se move para válvula EE.
        == Minuto 21 ==
        Válvulas BB, DD, HH, e JJ estão abertas, liberando 76 ulpg.
        Dick abre a válvula EE.
        == Minuto 22 ==
        Válvulas BB, DD, EE, HH, e JJ estão abertas, liberando 79 ulpg.
        Dick se move para válvula DD.
        == Minuto 23 ==
        Válvulas BB, DD, EE, HH, e JJ estão abertas, liberando 79 ulpg.
        Dick se move para válvula CC.
        == Minuto 24 ==
        Válvulas BB, DD, EE, HH, e JJ estão abertas, liberando 79 ulpg.
        Dick abre a válvula CC.
        == Minuto 25 ==
        Válvulas BB, CC, DD, EE, HH, e JJ estão abertas, liberando 81 ulpg.
        == Minuto 26 ==
        Válvulas BB, CC, DD, EE, HH, e JJ estão abertas, liberando 81 ulpg.
        == Minuto 27 ==
        Válvulas BB, CC, DD, EE, HH, e JJ estão abertas, liberando 81 ulpg.
        == Minuto 28 ==
        Válvulas BB, CC, DD, EE, HH, e JJ estão abertas, liberando 81 ulpg.
        == Minuto 29 ==
        Válvulas BB, CC, DD, EE, HH, e JJ estão abertas, liberando 81 ulpg.
        == Minuto 30 ==
        Válvulas BB, CC, DD, EE, HH, e JJ estão abertas, liberando 81 ulpg.

        Essa estratégia é a que maximiza a quantidade de ulpg liberado nesses 30 minutos, 1651!!!

        Ajude Dick Vigarista e Muttley a sobreviver, e descubra qual é a melhor estratégia para abrir as válvulas!
        Seguindo a melhor estratégia possível, quantos ulpg serão liberados?

         */

        String[] linhas = Arrays.stream(inp.split("\n")).toArray(String[]::new);

        long ulpg;

        var valv = new ArrayList<Valvula>();

        //salav tudo em um array list
        for(String i : linhas){
            String atual;
            int fluxo;
            var passagens = new ArrayList<String>();

            atual = i.substring(i.indexOf(" ")+1, i.indexOf(" ") + 3);
            fluxo = Integer.parseInt(i.replaceAll("\\D+", ""));

            Pattern p = Pattern.compile("[A-Z]{2}");
            Matcher m = p.matcher(i);

            while (m.find()) {
                if(!m.group().equals(atual))
                passagens.add(m.group());
            }

            System.out.println(atual + " || " + fluxo + " || " + passagens);
            valv.add(new Valvula(atual, fluxo, passagens, false));
        }
        System.out.println(valv);
        
        var importancia = new ArrayList<Valvula>();
        
        for (Valvula v : valv){
            if (v.fluxo > 0){
                importancia.add(v);
            }
        }
        
        //salvar todos os que tem fluxo >0 --> os que precisa abrir
        //ordenar de maior para menor, ou seja, importancia para abrir as valvulas
        //porem mapear os mais pertos e maior fluxo
        
        
        /*
           caminhos possiveis -> ocmpara melhor
           se n tem fluxo -> comparar os dois ate achar o maior fluxo & se ja no esta aberta a valvula
           if(valv.contains(v));
           */
        
    }

    //Para uso em Pombo
    private record Valvula(String atual, int fluxo, ArrayList<String> passagens, boolean aberta) {}

}
