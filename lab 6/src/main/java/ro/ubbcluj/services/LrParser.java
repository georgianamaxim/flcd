package ro.ubbcluj.services;

import ro.ubbcluj.domain.grammar.Grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LrParser {
    private Grammar grammar = new Grammar("g1.txt");
    private HashMap<String, ArrayList<ArrayList<String>>> dotted = this.dotMaker();
    private HashMap<String, ArrayList<String>> initialClosure = new HashMap<>();

    public LrParser() {
        this.initialClosure.put("S'", this.dotted.get("S'").get(0));
        this.closure(this.initialClosure, this.dotted, this.dotted.get("S'").get(0));
    }

    private HashMap<String, ArrayList<ArrayList<String>>> dotMaker() {
        HashMap<String, ArrayList<ArrayList<String>>> dottedProductions = new HashMap<>();
        dottedProductions.put("S'", new ArrayList<>());
        dottedProductions.get("S'").add(new ArrayList<>());

        for (String nonTerminal : grammar.getProductions().keySet()){
            dottedProductions.put(nonTerminal, new ArrayList<>());
            for (String[] way : grammar.getProductionsByNonTerminal(nonTerminal)) {
                ArrayList<String> e = (ArrayList<String>) Arrays.asList(way);
                e.add(0, ".");
                dottedProductions.get(nonTerminal).add(e);
            }
        }

        return dottedProductions;
    }

    private void closure(HashMap<String, ArrayList<String>> closureMap,
                         HashMap<String, ArrayList<ArrayList<String>>> transitionMap,
                         ArrayList<String> transitionValue) {
        int dotIndex = transitionValue.indexOf(".");
        if (dotIndex + 1 == transitionValue.size()) {
            return;
        }
        String afterDot = transitionValue.get(dotIndex + 1);
        for (String nonTerminal : this.grammar.getNonTerminals()) {
            if (nonTerminal.equals(afterDot)) {

                if (!closureMap.containsKey(nonTerminal)) {
                    closureMap.put(nonTerminal, transitionMap.get(nonTerminal).get(0));
                } else {
                    closureMap.get(nonTerminal).add(transitionMap.get(nonTerminal).get(0));
                }
                for (ArrayList<String> transition : transitionMap.get(nonTerminal))
                    this.closure(closureMap, transitionMap, transition);
            }
        }
    }

    private boolean shiftable(ArrayList<String> transition) {
        int dotIndex = transition.indexOf(".");
        return transition.size() > dotIndex + 1;
    }

    private ArrayList<String> shiftDot(ArrayList<String> transition) {
        int dotIndex = transition.indexOf(".");
        ArrayList<String> reminder = new ArrayList<>();
        if (!this.shiftable(transition)){
            System.out.println("Shift back?");
        }
        if (transition.size() > dotIndex + 2) {
           for (int i = dotIndex + 2; i < transition.size(); i++)
               reminder.add(transition.get(i));
        }
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i < dotIndex; i++){
            toReturn.add(transition.get(i));
        }
        toReturn.add(transition.get(dotIndex + 1));
        toReturn.add(".");
        toReturn.addAll(reminder);
        return toReturn;
    }

    public void canonicalCollection() {
        // HashMap<> idk = new HashMap();
        // ArrayList<HashMap<String, >>Queue
    }

    private void goToAll(ArrayList<String> state, HashMap<String, ArrayList<String>> initialDotted, int parent, String parentKey) {

    }

    private void goToOne(ArrayList<String> state, HashMap<String, ArrayList<String>> initialDotted, String key, int parent) {

    }

    private HashMap<String, ArrayList<ArrayList<String>>> removeTerminated (HashMap<String, ArrayList<String[]>> productions) {
        return null;
    }

}
