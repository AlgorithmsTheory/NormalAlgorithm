package ru.mephi22.markov.interfaces;

import ru.mephi22.markov.Rule;

public interface RuleStore {

    Rule nextElem();

    void reset();

    boolean hasNext();
}