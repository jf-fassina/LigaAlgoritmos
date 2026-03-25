import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LigaDeAlgoritmos {

    public static void main(String[] args) {
        System.out.println("\nMaçãs para o Rei I");
        macasParaOReiI();
        System.out.println("\nMaçãs para o Rei II");
        macasParaOReiII();
        System.out.println("\nTrincas");
        trincas();
        System.out.println("\nBolinhas de Gude");
        bolinhasDeGude();
    }

    private static void macasParaOReiI() {
        int[] macas = { 8, 6, 1, 3, 4 };

        int boas = 0;
        for (int i : macas) {
            boas = (i % 2 == 0) ? boas + 1 : boas + 0;
            // System.out.printf((i % 2 == 0) ? "Ruim\t%d\n" : "Boa\t%d\n", i);
        }
        System.out.println(boas);
    }

    private static void macasParaOReiII() {
        String macas = "746321095467504376567038576861430785761356704135623678495236752793485672934856787496156487135544798"
                +
                "4195637894185539859125725611987588562118902781988961310751878521500745819727521976883212020187568899824152127908027"
                +
                "2126087010610945781825598802552128451845180142187519829388010612495927505024515575105852562685490445423576982345623"
                +
                "4785762348567293845647385764235763457682435672873816457196457139859463587169959978345693476541685961346571374519637"
                +
                "4563495136547435613459817346517893456183458934651397485763498563847578643576893476576345761346758879346567365789645"
                +
                "7671365713649856734657146375965767834956781436758674356734675637945673457693467563745761346757364563476596437519406"
                +
                "757143567143500043561345036745016547613657348563567430567357346576345614037561034560713465710347651";

        long boas = macas.chars()
                .filter(i -> Character.getNumericValue(i) % 2 != 0)
                .count();
        System.out.println(boas);
        // macas.chars().forEach(i -> System.out.printf((Character.getNumericValue(i) %
        // 2 == 0) ? "Ruim\t%c\n" : "Boa\t%c\n", i));

    }

    private static void trincas() {
        Scanner in = new Scanner(System.in);

        String[] inp = new String[3];
        byte i = 0;

        do {
            if (i > 2)
                break;
            System.out.println("Digite o valor numérico da carta, após o naipe(1,2,3,4)");
            inp[i] = in.nextLine();
            if (inp[i].matches("\\d+\\s+\\d+"))
                i++;
        } while (true);

        System.out.println(
                Arrays.stream(inp)
                        .map(c -> new String[] { c.substring(0, c.indexOf(" ")), c.substring(c.lastIndexOf(" ")-15 ) })
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
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
        Scanner in = new Scanner(System.in);
        String inp;
        // 150 G 300 M 550 P = max
        //in.nextLine();
        do {
            System.out.println("Digite o número de bolinhas Pequenas, Médias e Grandes, respectivamente");
            inp = in.nextLine();
        } while (!inp.matches("\\d+\\s+\\d+\\s+\\d+"));

        int[] nums = {
                Integer.parseInt(inp.substring(0, inp.indexOf(" "))),
                Integer.parseInt(inp.substring(inp.indexOf(" "), inp.lastIndexOf(" "))),
                Integer.parseInt(inp.substring(inp.lastIndexOf(" ")))
        };

        Arrays.stream(nums).forEachOrdered(n -> System.out.println(n));

        in.close();
    }

}