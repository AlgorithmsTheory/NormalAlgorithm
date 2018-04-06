package nAlgorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import nAlgorithm.interfaces.RuleStore;

public class RuleList implements RuleStore
{
  List<Rule> rlist;
  ListIterator<Rule> iter;
  
  public RuleList()
  {
    rlist = new LinkedList();
    iter = rlist.listIterator();
  }
  
  public void addRule(Rule rule)
  {
    rlist.add(rule);
  }
  
  public Rule nextElem()
  {
    return (Rule)iter.next();
  }
  
  public void reset()
  {
    iter = rlist.listIterator();
  }
  
  public boolean hasNext()
  {
    return iter.hasNext();
  }
}
