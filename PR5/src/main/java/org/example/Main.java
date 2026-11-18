package org.example;

public class Main {
    public static void main(String[] args) {
        StateMachine machine = new StateMachine();

        String[] samples = {"abcTESTabc", "abcTES", "TTEST", "TETEST", "hello"};
        for (String sample : samples) {
            System.out.println("\"" + sample + "\" -> " + machine.processString(sample));
        }
    }
}
