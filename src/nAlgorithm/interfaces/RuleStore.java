package nAlgorithm.interfaces;

import nAlgorithm.Rule;

public abstract interface RuleStore
{
  public abstract void addRule(Rule paramRule);
  
  public abstract Rule nextElem();
  
  public abstract void reset();
  
  public abstract boolean hasNext();
}