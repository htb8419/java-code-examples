package org.examples.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "student course ranking")
public class CourseRankingRule {
    @Condition
    public boolean when(@Fact("rate")Integer rate){
        return rate>90;
    }
    @Action
    public void then(){
        System.out.println("excellent");
    }
    @Action
    public void saveResult(){
        System.out.println("save ranking");
    }
}
