package ru.mephi22.markov;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Rule {
    private final String left;
    private final String right;

    @Override
    public String toString() {
        return left + " -> " + right;
    }
}