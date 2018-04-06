package test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import nAlgorithm.Algorithm;
import nAlgorithm.Rule;
import nAlgorithm.RuleList;
import nAlgorithm.interfaces.RuleStore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test
{
  public Test() {}
  
  public static void main(String[] argv) throws IOException, JSONException{
    byte[] encoded = Files.readAllBytes(Paths.get(argv[0], new String[0]));
    String data = new String(encoded, Charset.forName("UTF-8"));
    JSONObject obj = new JSONObject(data);
    String tape = obj.getString("str");
    JSONArray arr = obj.getJSONArray("rule");
    RuleStore rs = new RuleList();
    
    for (int i = 0; i < arr.length(); i++) {
      Rule rule = new Rule();
      JSONObject tmp = arr.getJSONObject(i);
      rule.left = tmp.getString("src");
      rule.right = tmp.getString("dst");
      rs.addRule(rule);
    }

    Algorithm machine = new Algorithm(rs, tape);
    machine.run();
  }
}