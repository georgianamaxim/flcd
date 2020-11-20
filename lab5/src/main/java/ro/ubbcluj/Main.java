package ro.ubbcluj;

import ro.ubbcluj.domain.Automata.FiniteAutomata;
import ro.ubbcluj.domain.grammar.Grammar;
import ro.ubbcluj.services.LexicalAnalyzer;
import ro.ubbcluj.domain.Symbol.SymbolTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static void testSymbolTable(){
        SymbolTable symbolTable = new SymbolTable();

        System.out.println("\nINSERTION");
        System.out.println(symbolTable.insert("buna"));
        System.out.println(symbolTable.insert("ziua"));
        System.out.println(symbolTable.insert("seara"));
        System.out.println(symbolTable.insert("ziua"));
        System.out.println(symbolTable.insert("merge"));
        System.out.println(symbolTable.insert("acum"));
        System.out.println(symbolTable.insert("gata"));

        System.out.println("\nINORDER");
        System.out.println(symbolTable.getInorder());

        System.out.println("\nFIND");
        System.out.println(symbolTable.search("salut"));
        System.out.println(symbolTable.search("ziua"));
        System.out.println(symbolTable.search("merge"));

        System.out.println("\nFIND BY ID");
        System.out.println(symbolTable.searchById(1));
        System.out.println(symbolTable.searchById(0));
    }

    private static void testScanner() {
        SymbolTable symbolTable = new SymbolTable();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("Problem4.txt", "Lexic.txt", "Syntax.txt", "Token.txt", symbolTable);
        lexicalAnalyzer.scan();
    }

    private static void testFiniteAutomata() {
        FiniteAutomata fa = new FiniteAutomata("fa_1.txt");

        while (true){
            try {
                System.out.println("" +
                        "\nCommands: " +
                        "\n1 - finite set of states " +
                        "\n2 - the alphabet " +
                        "\n3 - the transition function " +
                        "\n4 - the start state " +
                        "\n5 - the final states " +
                        "\n6 - check sequence " +
                        "\n0 - exit." +
                        "\n>> ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(br.readLine());

                if (option == 1)
                    System.out.println(Arrays.toString(fa.getFA_Q()));
                else if (option == 2)
                    System.out.println(Arrays.toString(fa.getFA_E()));
                else if (option == 3)
                    System.out.println(fa.getFA_d().toString());
                else if (option == 4)
                    System.out.println(fa.getFA_q0());
                else if (option == 5)
                    System.out.println(Arrays.toString(fa.getFA_F()));
                else if (option == 6) {
                    String sequence = br.readLine();
                    if (fa.checkSequence(sequence))
                        System.out.println(" Sequence accepted");
                    else
                        System.out.println(" Sequence not accepted");
                }
                else
                    return;
            } catch (Exception e){
                System.out.println("ERROR!");
            }
        }
    }

    private static void testGrammar() {
        Grammar grammar = new Grammar("g1.txt");

        while (true){
            try {
                System.out.println("" +
                        "\nCommands: " +
                        "\n1 - set of non-terminal symbols " +
                        "\n2 - the alphabet (set of terminal symbols) " +
                        "\n3 - the start symbol " +
                        "\n4 - productions " +
                        "\n5 - production for a given non-terminal " +
                        "\n0 - exit." +
                        "\n>> ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(br.readLine());

                if (option == 1)
                    System.out.println(Arrays.toString(grammar.getNonTerminals()));
                else if (option == 2)
                    System.out.println(Arrays.toString(grammar.getTerminals()));
                else if (option == 3)
                    System.out.println(grammar.getStartSymbol());
                else if (option == 4) {
                    for (String key : grammar.getProductions().keySet()) {
                        System.out.print(key + " -");
                        for (String[] e : grammar.getProductions().get(key)) {
                            System.out.print(" (");
                            for (String p : e)
                                System.out.print(p + " ");
                            System.out.print("\b)");
                        }
                        System.out.println();
                    }

                }
                else if (option == 5) {
                    String nonTerminal = br.readLine();
                    System.out.print(nonTerminal + " -");
                    for (String[] e : grammar.getProductionsByNonTerminal(nonTerminal)) {
                        System.out.print(" (");
                        for (String p : e)
                            System.out.print(p + " ");
                        System.out.print("\b)");
                    }
                    System.out.println();
                }
                else
                    return;
            } catch (Exception e){
                System.out.println("ERROR!");
            }
        }
    }

    public static void main(String[] args) {
//        System.out.println("\n SYMBOL TABLE \n");
//        testSymbolTable();

//        System.out.println("\n SCANNER \n");
//        testScanner();

//        System.out.println("\n FINITE AUTOMATA \n");
//        testFiniteAutomata();

        System.out.println("\n GRAMMAR \n");
        testGrammar();
    }
}
