package ru.mephi22.markov;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Markov {

    public static void main(String[] argv) throws IOException, JSONException {
        byte[] encoded = Files.readAllBytes(Paths.get(argv[0]));
        String data = new String(encoded, Charset.forName("UTF-8"));
        JSONObject obj = new JSONObject(data);
        String tape = obj.getString("str");
        JSONArray arr = obj.getJSONArray("rule");
        RuleList ruleList = buildRules(arr);

        Algorithm machine = new Algorithm(ruleList, tape);
        machine.run();
    }

    private static RuleList buildRules(JSONArray arr) {
        List<Rule> rules = new LinkedList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject tmp = arr.getJSONObject(i);
            String left = tmp.getString("src");
            String right = tmp.getString("dst");
            Rule rule = new Rule(left, right);
            rules.add(rule);
        }
        return new RuleList(rules);
    }
}
