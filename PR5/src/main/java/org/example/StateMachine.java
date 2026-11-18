package org.example;

import static org.example.State.FINISH;
import static org.example.State.FIRST;
import static org.example.State.SECOND;
import static org.example.State.START;
import static org.example.State.THIRD;

public class StateMachine {
    private State state = START;

    public State processString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Вхідний рядок не може бути null");
        }

        state = START;
        for (int i = 0; i < input.length() && state != FINISH; i++) {
            switchState(input.charAt(i));
        }
        return state;
    }

    public void switchState(char c) {
        state = next(state, c);
    }

    static State next(State current, char c) {
        return switch (current) {
            case START -> restart(c);
            case FIRST -> c == 'E' ? SECOND : restart(c);
            case SECOND -> c == 'S' ? THIRD : restart(c);
            case THIRD -> c == 'T' ? FINISH : restart(c);
            case FINISH -> FINISH;
        };
    }

    private static State restart(char c) {
        return c == 'T' ? FIRST : START;
    }
}
