package nAlgorithm;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Base64.Encoder;
import nAlgorithm.interfaces.RuleStore;
import org.json.JSONArray;
import org.json.JSONObject;


public class Algorithm
{
  final int max_step;
  RuleStore rlist;
  String tape;
  String stop = "H";
  public static char lambda = 955;
  
  public Algorithm() {
    max_step = 0;
  }
  
  public Algorithm(RuleStore rl, String str) {
    rlist = rl;
    tape = str;
    max_step = 10000;
  }
  
  public void run() throws FileNotFoundException {
    int cstep = 0;
    rlist.reset();
    JSONObject report = new JSONObject();
    JSONArray logs = new JSONArray();
    JSONArray rules = new JSONArray();
    while ((rlist.hasNext()) && (cstep++ < max_step)) {
      Rule current = rlist.nextElem();
      rules.put(current);
      if (tape.contains(current.left)) {
        if ((!current.right.contains(stop)) && (!current.right.contains("_"))) {
          tape = tape.replaceFirst(current.left, current.right);
          logs.put(tape);
          
          rlist.reset();
        } else if ((!current.right.contains(stop)) && (current.right.contains("_"))) {
          tape = tape.replaceFirst(current.left, "");
          logs.put(tape);
          
          rlist.reset();
        } else  if ((current.right.contains(stop)) && (!current.right.contains("_"))){
          String sym = current.right.replace(stop, "");
          System.out.println(sym);
          tape = tape.replaceFirst(current.left, sym);
          logs.put(tape);
          rlist.reset();
          break;
        } 
        else { if ((!current.right.contains(stop)) || (!current.right.contains("_"))) break;
          tape = tape.replaceFirst(current.left, "");
          logs.put(tape);
          
          rlist.reset();
          break;
        }
      }
    }
    try {
      if (cstep >= max_step) {
        report.put("error", "too many steps");
      } else {
        report.put("error", "ok");
      }
      tape  = tape.replaceAll(String.valueOf(lambda), "");
      report.put("result", tape);
      report.put("logs", logs);
      report.put("cycle", cstep);
      
      byte[] res = report.toString().getBytes(Charset.forName("UTF-8"));
      
      PrintWriter out = new PrintWriter("/tmp/out.txt");
      out.println(Base64.getEncoder().encodeToString(res));
      out.close();
      
      System.out.write(res, 0, res.length);
    }
    catch (Exception ex) {
      System.out.println("fatal error " + ex.getMessage());
    }
  }
}