package ro.ubbcluj.domain.Automata;

import ro.ubbcluj.utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FiniteAutomata {
    private File file;
    private ArrayList<String[]> FA;
    private ArrayList<String[]> transitions;
    private String[] FA_Q;
    private String[] FA_E;
    private HashMap<Pair<String,String>, ArrayList<String>> FA_d;
    private String FA_q0;
    private String[] FA_F;

    public FiniteAutomata (String file){
        this.file =  new File("D:\\FCLD\\Lab2\\src\\main\\resources\\" + file);
        this.FA = this.readFA();
        this.FA_Q = this.FA.get(0);
        this.FA_E = this.FA.get(1);
        this.FA_d = this.representTransitions(this.transitions);
        this.FA_q0 = this.FA.get(2)[0];
        this.FA_F = this.FA.get(3);
    }

    private ArrayList<String[]> readFA() {
        ArrayList<String[]> fa = new ArrayList<>();

        try {
            Scanner programFileScanner = new Scanner(this.file);

            // get the finite set of states
            String line = programFileScanner.nextLine();
            fa.add(line.strip().split(" "));

            // get the alphabet
            line = programFileScanner.nextLine();
            fa.add(line.strip().split(" "));

            // get the start state
            line = programFileScanner.nextLine();
            fa.add(line.strip().split(" "));

            // get the final states
            line = programFileScanner.nextLine();
            fa.add(line.strip().split(" "));

            // get the transition function
            ArrayList<String[]> d = new ArrayList<>();
            while (programFileScanner.hasNext()){
                line = programFileScanner.nextLine();
                d.add(line.strip().split(","));
            }

            this.transitions = d;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fa;
    }

    private HashMap<Pair<String,String>, ArrayList<String>> representTransitions(ArrayList<String[]> transitions){
        HashMap<Pair<String,String>, ArrayList<String>> representation = new HashMap<>();

        for (String[] t : transitions){
            Pair<String, String> s = new Pair<>(t[0], t[1]);
            if (!representation.containsKey(s)) {
                representation.put(s, new ArrayList<>());
            }
            representation.get(s).add(t[2]);
        }

        return representation;
    }

    private boolean isDeterministic() {
        for (ArrayList<String> element : this.getFA_d().values()){
            if (element.size() > 1)
                return false;
        }
        return true;
    }

    public boolean checkSequence(String sequence) {
        if (! this.isDeterministic()){
            System.out.println("The FA is not deterministic");
            return false;
        }

        String currentState = this.getFA_q0();

        for (String i : sequence.strip().split("")) {
            if (!this.FA_d.containsKey(new Pair<>(currentState, i))){
                return false;
            }
            currentState = this.FA_d.get(new Pair<>(currentState, i)).get(0);
        }
        for (String element : this.FA_F){
            if (currentState.equals(element))
                return true;
        }
        return false;
    }


    public String[] getFA_Q() {
        return FA_Q;
    }

    public String[] getFA_E() {
        return FA_E;
    }

    public String getFA_q0() {
        return FA_q0;
    }

    public String[] getFA_F() {
        return FA_F;
    }

    public HashMap<Pair<String,String>, ArrayList<String>> getFA_d() {
        return FA_d;
    }
}
