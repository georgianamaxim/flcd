package ro.ubbcluj.domain.grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
        Grammar file will be constructed as follows:
        line 1: non-terminals separated by space
        line 2: terminals separated by space
        line 3: start symbol
        line 4+: productions on each line following this rule:
            non-terminal separated by space from symbols
            and the symbols separated by "#"
            ex: S a#b#S
*/

public class Grammar {
    private File file;
    private ArrayList<String[]> grossProductions;
    private ArrayList<String[]> grammar;
    private String[] nonTerminals;
    private String[] terminals;
    private HashMap<String, ArrayList<String[]>> productions;
    private String startSymbol;

    public Grammar(String file){
        this.file =  new File("D:\\FCLD\\Lab2\\src\\main\\resources\\" + file);
        this.grammar = readGrammar();
        this.nonTerminals = this.grammar.get(0);
        this.terminals = this.grammar.get(1);
        this.productions = this.representProductions(this.grossProductions);
        this.startSymbol = this.grammar.get(2)[0];
    }

    private ArrayList<String[]> readGrammar(){
        ArrayList<String[]> grammar = new ArrayList<>();

        try {
            Scanner programFileScanner = new Scanner(this.file);

            // get the set of  non-terminal symbols
            String line = programFileScanner.nextLine();
            grammar.add(line.strip().split(" "));

            // get the alphabet (set of terminal symbols)
            line = programFileScanner.nextLine();
            grammar.add(line.strip().split(" "));

            // get the start symbol
            line = programFileScanner.nextLine();
            grammar.add(line.strip().split(" "));

            // get the finite set of productions
            ArrayList<String[]> p = new ArrayList<>();
            while (programFileScanner.hasNext()){
                line = programFileScanner.nextLine();
                p.add(line.strip().split(" "));
            }
            this.grossProductions = p;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return grammar;
    }

    private HashMap<String, ArrayList<String[]>> representProductions (ArrayList<String[]> productions){
        HashMap<String, ArrayList<String[]>> representation = new HashMap<>();

        for (String[] p : productions){
            if (!representation.containsKey(p[0])) {
                representation.put(p[0], new ArrayList<>());
            }
            representation.get(p[0]).add( p[1].split("#"));
        }

        return representation;
    }

    public String[] getNonTerminals() {
        return nonTerminals;
    }

    public String[] getTerminals() {
        return terminals;
    }

    public HashMap<String, ArrayList<String[]>> getProductions() {
        return productions;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public ArrayList<String[]> getProductionsByNonTerminal(String nonTerminal){
        return this.productions.get(nonTerminal);
    }
}
