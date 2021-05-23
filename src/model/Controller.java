package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Controller {
    private final String path;
    private final String w;
    private final String initS;

    private final ArrayList<String> terminals;
    private final ArrayList<String> nonTerminals;

    private final HashMap<String, ArrayList<String>> g = new HashMap<>();

    public Controller(String path, String w, String initS, ArrayList<String> ter, ArrayList<String> nonter) {
        this.path = path;
        this.w = w;
        this.initS = initS;
        terminals = ter;
        nonTerminals = nonter;
    }

    public String getInitS() {
        return initS;
    }

    public String[][] doit() throws FileNotFoundException {
        setupGrammar();
        return cyk(createCYKTable());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupGrammar() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));

        ArrayList<String> tmp = new ArrayList<>();

        while (scanner.hasNextLine()) {
            tmp.addAll(Arrays.asList(toArray(scanner.nextLine())));
            String l = tmp.get(0);
            tmp.remove(0);
            ArrayList arr = new ArrayList(tmp);
            g.put(l, arr);
            tmp.clear();
        }
        scanner.close();
    }

    private String[] toArray(String nextLine) {
        return nextLine.split("\\s");
    }

    private String[][] createCYKTable() {
        int length = w.length();
        String[][] table = new String[length + 1][];
        table[0] = new String[length];
        for (int i = 1; i < table.length; i++) {
            table[i] = new String[length - (i - 1)];
        }
        for (int i = 1; i < table.length; i++) {
            Arrays.fill(table[i], "");
        }
        return table;
    }

    private String[][] cyk(String[][] table) {
        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = w.charAt(i) + "";
        }
        for (int i = 0; i < table[1].length; i++) {
            String[] comb = productions(new String[]{table[0][i]});
            table[1][i] = Arrays.toString(comb).replaceAll("[\\[\\],]", "");
        }
        if (w.length() > 1) {
            for (int i = 0; i < table[2].length; i++) {
                String[] down = toArray(table[1][i]);
                String[] diag = toArray(table[1][i + 1]);
                String[] aCombs = getAllCombs(down, diag);
                String[] comb = productions(aCombs);
                table[2][i] = Arrays.toString(comb).replaceAll("[\\[\\],]", "");
            }
            if (w.length() > 2){
                restOps(table);
            }
        }
        return table;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void restOps(String[][] table) {
        for (int i = 3; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                for (int k = 1; k < i; k++) {
                    String[] down = table[k][j].split("\\s");
                    String[] diag = table[i-k][j+k].split("\\s");
                    String[] aCombs = getAllCombs(down, diag);
                    String[] comb = productions(aCombs);
                    if (table[i][j].isEmpty()) {
                        table[i][j] = Arrays.toString(comb).replaceAll("[\\[\\],]", "");
                    } else {
                        ArrayList newVal = new ArrayList();
                        newVal.addAll(Arrays.asList(toArray(table[i][j])));
                        newVal.addAll(Arrays.asList(comb));
                        HashSet<String> currentV = new HashSet<>(newVal);
                        String[] res = currentV.toArray(new String[0]);
                        table[i][j] = Arrays.toString(res).replaceAll("[\\[\\],]", "");
                    }
                }
            }
        }
    }

    private String[] getAllCombs(String[] down, String[] diag) {
        int n = down.length * diag.length;
        String[] combs = new String[n];
        int cont = 0;
        for (String s : down) {
            for (String s1 : diag) {
                combs[cont] = s + s1;
                cont++;
            }
        }
        return combs;
    }

    private String[] productions(String[] strings) {
        ArrayList<String> tmp = new ArrayList<>();
        for (String s : g.keySet()) {
            for (String tc : strings) {
                if (g.get(s).contains(tc)) {
                    tmp.add(s);
                }
            }
        }
        return tmp.toArray(new String[0]);
    }

}
