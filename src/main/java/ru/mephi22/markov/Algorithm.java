package ru.mephi22.markov;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Base64;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.mephi22.markov.interfaces.RuleStore;

@AllArgsConstructor
class Algorithm {
    private static final int MAX_STEP = 10_000;
    private final String STOP = "H";
    private static final char LAMBDA = 955;

    private final RuleStore rlist;
    private String tape;

    void run() {
        int cstep = 0;
        rlist.reset();
        JSONObject report = new JSONObject();
        JSONArray logs = new JSONArray();
        JSONArray rules = new JSONArray();

        while ((rlist.hasNext()) && (cstep++ < MAX_STEP)) {
            Rule current = rlist.nextElem();
            rules.put(current);
            if (tape.contains(current.getLeft())) {
                if ((!current.getRight().contains(STOP)) && (!current.getRight().contains("_"))) {
                    tape = tape.replaceFirst(current.getLeft(), current.getRight());
                    logs.put(tape);

                    rlist.reset();
                } else if ((!current.getRight().contains(STOP)) && (current.getRight().contains("_"))) {
                    tape = tape.replaceFirst(current.getLeft(), "");
                    logs.put(tape);

                    rlist.reset();
                } else if ((current.getRight().contains(STOP)) && (!current.getRight().contains("_"))) {
                    String sym = current.getRight().replace(STOP, "");
                    System.out.println(sym);
                    tape = tape.replaceFirst(current.getLeft(), sym);
                    logs.put(tape);
                    rlist.reset();
                    break;
                } else {
                    if ((!current.getRight().contains(STOP)) || (!current.getRight().contains("_"))) break;
                    tape = tape.replaceFirst(current.getLeft(), "");
                    logs.put(tape);

                    rlist.reset();
                    break;
                }
            }
        }
        try {
            if (cstep >= MAX_STEP) {
                report.put("error", "too many steps");
            } else {
                report.put("error", "ok");
            }
            tape = tape.replaceAll(String.valueOf(LAMBDA), "");
            report.put("result", tape);
            report.put("logs", logs);
            report.put("cycle", cstep);

            byte[] res = report.toString().getBytes(Charset.forName("UTF-8"));

            PrintWriter out = new PrintWriter("/tmp/out.txt");
            out.println(Base64.getEncoder().encodeToString(res));
            out.close();

            System.out.write(res, 0, res.length);
        } catch (Exception ex) {
            System.out.println("fatal error " + ex.getMessage());
        }
    }
}