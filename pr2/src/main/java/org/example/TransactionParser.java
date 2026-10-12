package org.example;

import java.util.List;

public interface TransactionParser {
    List<Transaction> parse(List<String> lines);
}
