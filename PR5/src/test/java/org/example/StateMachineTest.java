package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StateMachineTest {

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "abcTESTabc, FINISH",
            "TEST,       FINISH",
            "TESTabc,    FINISH",
            "abcTEST,    FINISH",
            "TESTTEST,   FINISH",
            "abcTES,     THIRD",
            "abcTE,      SECOND",
            "abcT,       FIRST",
            "abc,        START",
            "TEXT,       FIRST",
            "TEXTa,      START",
            "test,       START",
            "TES T,      FIRST",
            "'',         START"
    })
    void shouldReturnExpectedFinalState(String input, State expected) {
        assertEquals(expected, new StateMachine().processString(input));
    }

    @ParameterizedTest(name = "{0} + ''{1}'' -> {2}")
    @CsvSource({
            "START,  T, FIRST",
            "START,  x, START",
            "FIRST,  E, SECOND",
            "FIRST,  x, START",
            "SECOND, S, THIRD",
            "SECOND, x, START",
            "THIRD,  T, FINISH",
            "THIRD,  x, START",
            "FINISH, x, FINISH",
            "FINISH, T, FINISH"
    })
    void shouldFollowTransitionTable(State from, char symbol, State expected) {
        assertEquals(expected, StateMachine.next(from, symbol));
    }

    @Test
    void shouldBeReusableBetweenCalls() {
        StateMachine machine = new StateMachine();
        assertEquals(State.FINISH, machine.processString("TEST"));
        assertEquals(State.START, machine.processString("abc"));
    }

    @Test
    void shouldThrowOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new StateMachine().processString(null));
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "TTEST,    FINISH",
            "TTTEST,   FINISH",
            "TETEST,   FINISH",
            "TETETEST, FINISH",
            "TTE,      SECOND",
            "TET,      FIRST"
    })
    void shouldFindWordAfterFalseStart(String input, State expected) {
        assertEquals(expected, new StateMachine().processString(input));
    }
}
