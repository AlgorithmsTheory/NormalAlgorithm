package nAlgorithm;

public class Rule
{
  public String left;
  public String right;
  
  public Rule()
  {
    left = "";
    right = "";
  }
  
  public Rule(String l, String r){
      left = l;
      right = r;
  }
  
  public String getLeft()
  {
    return left;
  }
  
  public String getRight() {
    return right;
  }
  
  public void setLeftRule(String left) {
    this.left = left;
  }
  
  public void setRightRule(String right) {
    this.right = right;
  }
  
  public String ruleToString() {
    return left.toString() + "->" + right.toString();
  }
}