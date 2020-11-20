package ro.ubbcluj.services;

import ro.ubbcluj.domain.Automata.FiniteAutomata;
import ro.ubbcluj.domain.Symbol.SymbolTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class LexicalAnalyzer {
    private File program;
    private File lexic;
    private File syntax;
    private File tokens;
    private SymbolTable symbolTable;
    private ArrayList<Pair<Integer, Integer>> PIF = new ArrayList<>();
    private HashMap<String, Integer> tokensMap;
    private FiniteAutomata FA_identifier = new FiniteAutomata("fa_1.txt");
    private FiniteAutomata FA_integer = new FiniteAutomata("fa_2.txt");

    public LexicalAnalyzer(String program, String lexic, String syntax, String tokens, SymbolTable symbolTable){
        this.program = new File("D:\\FCLD\\Lab2\\src\\main\\resources\\" + program);
        this.lexic = new File("D:\\FCLD\\Lab2\\src\\main\\resources\\" + lexic);
        this.syntax = new File("D:\\FCLD\\Lab2\\src\\main\\resources\\" + syntax);
        this.tokens = new File("D:\\FCLD\\Lab2\\src\\main\\resources\\" + tokens);
        this.symbolTable = symbolTable;
        this.tokensMap = this.readTokens();
    }

    public void scan(){
        try {
            Scanner programFileScanner = new Scanner(this.program);
            int lineIndex = 0;

            while (programFileScanner.hasNext()){
                String line = programFileScanner.nextLine();
                lineIndex++;

                List<String> tokens = this.detectToken(line);

                for (String token : tokens) {
                    if (this.isOperatorSeparator(token) || this.isReservedWord(token)){
                        if (!token.contains(" \t\n")){
                            this.generatePIF(this.tokensMap.get(token), -1);
                        }
                    }
                    else if(this.isID(token) || this.isConstant(token)){
                        this.symbolTable.insert(token);
                        int index = this.symbolTable.search(token);
                        if (this.isID(token)){
                            this.generatePIF(this.tokensMap.get("identifiers"), index);
                        } else {
                            this.generatePIF(this.tokensMap.get("constants"), index);
                        }
                    }
                    else {
                        System.out.println("Lexical error! line: " + lineIndex + " - " + token);
                    }
                }
            }

            System.out.println(this.PIF.toString());
            System.out.println(this.symbolTable.getInorder());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isOperatorSeparator(String token) {
        List<String> possible = Arrays.asList(
                "+", "-", "*", "/", "%","=", "==", "!=", "<=", ">=", "<", ">", "^", "&", "|", "!",
                "(", ")", "[", "]", "{", "}", ":", ";", ",", " ", "\t", "\n");
        return possible.contains(token);
    }

    private boolean isReservedWord(String token) {
        List<String> possible = Arrays.asList(
                "fun", "if", "else", "while", "for", "from", "to", "console.write", "console.read",
                "int", "boolean", "real", "char", "const", "let", "string", "true", "false");
        return possible.contains(token);
    }

    private boolean isID (String token) {
        return this.FA_identifier.checkSequence(token);
        //return token.matches("^([a-zA-Z]|_[a-zA-Z])([_a-zA-Z]|[0-9])*$");
    }

    private boolean isConstant (String token) {
        //  return token.matches("^(0|([-]?|[+]?)[1-9][0-9]*)$") ||
        return this.FA_integer.checkSequence(token) ||
                token.matches("^\"([a-zA-Z0-9 ])*\"$") ||
                token.matches("^'([a-zA-Z0-9])'$");
    }

    private void generatePIF(int token, int i) {
        this.PIF.add(new Pair<>(token, i));
    }

    private HashMap<String, Integer> readTokens() {
        HashMap<String, Integer> map = new HashMap<>();
        File tokensFile = new File("D:\\FCLD\\Lab2\\src\\main\\resources\\definedTokens.txt");
        Scanner tokensFileScanner = null;
        try {
            tokensFileScanner = new Scanner(tokensFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert tokensFileScanner != null;
        String line = tokensFileScanner.nextLine();
        while (tokensFileScanner.hasNext()) {
            String[] l = line.strip().split(" ");
            map.put(l[0], Integer.parseInt(l[1]));
            line = tokensFileScanner.nextLine();
        }
        String[] l = line.strip().split(" ");
        map.put(l[0], Integer.parseInt(l[1]));

        return map;
    }

    private String getString(String[] l, int i, int j){
        StringBuilder toReturn = new StringBuilder();
        for (int k = i; k < j; k++){
            toReturn.append(l[k]);
        }

        return toReturn.toString();
    }

    private List<String> detectToken(String line) {
        List<String> tokens = new ArrayList<>();
        String[] l = line.strip().split("");
        final int length = line.strip().length();
        int i = 0;
        int j = 0;

        while (j < length){
            if (l[j].equals("\"")){
                tokens.add(this.getString(l, i, j));
                i = j;
                j++;
                if (!this.getString(l, i+1, length - 1).contains("\"")){
                    j = length - 1;
                } else {
                    while (!l[j].equals("\"") && j < length){
                        j++;
                    }
                    j++;
                }
            }
            if (this.isOperatorSeparator(l[j])){
                tokens.add(this.getString(l, i, j));
                if ("=!<>".contains(l[j]) && l[j + 1].equals("=")){
                    tokens.add(this.getString(l, j, j + 2));
                    i = j+2;
                    j++;
                } else {
                    tokens.add(l[j]);
                    i = j+1;
                }
            }
            j++;
        }
        tokens = tokens.stream().filter(a -> !" ,\n\t".contains(a)).collect(Collectors.toList());
        return  tokens;
    }
}
