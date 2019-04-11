package ru.mephi22.markov.interfaces;

import ru.mephi22.markov.Rule;

public interface RuleStore {

    Rule next();

    void reset();

    boolean hasNext();
}