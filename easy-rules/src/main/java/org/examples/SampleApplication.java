package org.examples;

import org.examples.rules.CourseRankingRule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class SampleApplication {

    public static void main(String[] args) {
        Facts facts = new Facts();
        facts.put("rate", 95);
        // create rules
        Rules rules = new Rules();
        rules.register(new CourseRankingRule());

        // create a rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
