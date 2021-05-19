package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {
    private String path;
    private String w;
    private String initS;

    private ArrayList<String> terminals = new ArrayList<>();
    private ArrayList<String> nonTerminals = new ArrayList<>();

    private HashMap<String, ArrayList<String>> g = new HashMap<>();

    public Controller(String path, String w, String initS) {
        this.path = path;
        this.w = w;
        this.initS = initS;
    }

    public void setupGrammar() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        ArrayList<String> tmp = new ArrayList<>();

        tmp.addAll(Arrays.asList(toArray(scanner.nextLine())));
        terminals.addAll(tmp);
        tmp.clear();
        tmp.addAll(Arrays.asList(toArray(scanner.nextLine())));
        nonTerminals.addAll(tmp);
        tmp.clear();

        while(scanner.hasNextLine()) {
            tmp.addAll(Arrays.asList(toArray(scanner.nextLine())));
            String l = tmp.get(0);
            tmp.remove(0);
            ArrayList arr = new ArrayList();
            arr.addAll(tmp);
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
        for (int i = 1;i < table.length;i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = "";
            }
        }
        return table;
    }
}
