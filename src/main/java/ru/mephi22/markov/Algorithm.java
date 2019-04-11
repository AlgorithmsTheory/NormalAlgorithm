package ru.mephi22.markov;

import java.nio.charset.Charset;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.mephi22.markov.interfaces.RuleStore;

@AllArgsConstructor
class Algorithm {
    private static final int MAX_STEP = 10_000;
    private static final String STOP = "H";
    private static final String WIPE_OFF = "_";
    private static final char LAMBDA = 955;

    private final RuleStore rules;
    private String tape;

    void run() {
        int step = 0;
        rules.reset();
        JSONArray logs = new JSONArray();
        boolean isFinished = false;

        while ((rules.hasNext()) && (step++ < MAX_STEP) && !isFinished) {
            Rule rule = rules.next();
            String left = rule.getLeft();
            String right = rule.getRight();

            if (tape.contains(left)) {
                if (right.equals(WIPE_OFF)) {
                    tape = tape.replaceFirst(left, "");
                } else if (right.equals(WIPE_OFF + STOP)) {
                    tape = tape.replaceFirst(left, "");
                    isFinished = true;
                } else if (right.endsWith(STOP)) {
                    right = right.replace(STOP, "");
                    tape = tape.replaceFirst(left, right);
                    isFinished = true;
                } else {
                    tape = tape.replaceFirst(left, right);
                }

                logs.put(tape);
                rules.reset();
            }
        }

        JSONObject report = new JSONObject();
        if (step >= MAX_STEP) {
            report.put("error", "too many steps");
        } else {
            report.put("error", "ok");
        }
        tape = tape.replaceAll(String.valueOf(LAMBDA), "");
        report.put("result", tape);
        report.put("logs", logs);
        report.put("cycle", step);

        byte[] res = report.toString().getBytes(Charset.forName("UTF-8"));

        System.out.write(res, 0, res.length);
    }
}