package ru.mephi22.markov;

import ru.mephi22.markov.interfaces.RuleStore;

import java.util.List;
import java.util.ListIterator;

public class RuleList implements RuleStore {
    private final List<Rule> rules;
    private ListIterator<Rule> iter;

    RuleList(List<Rule> rules) {
        this.rules = rules;
        reset();
    }

    @Override
    public Rule next() {
        return iter.next();
    }

    @Override
    public void reset() {
        iter = rules.listIterator();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }
}
