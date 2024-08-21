package com.chrzanowy.cron.expressions;

import com.chrzanowy.cron.elements.Element;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class Step implements Expression {

    Expression range;

    Expression step;

    @Getter
    Element element;

    @Override
    public Set<Integer> evaluate() {
        Set<Integer> leftSide = range.evaluate();
        int limit = leftSide.stream().max(Integer::compare).orElse(0);
        int offset = leftSide.stream().min(Integer::compare).orElse(0);
        Set<Integer> rightSide = step.evaluate();
        Set<Integer> evaluatedValues = new HashSet<>();

        evaluatedValues.add(offset);
        for (Integer stepValue : rightSide) {
            for (int i = 1; i <= (limit / stepValue); i++) {
                int calculatedValue = offset + (stepValue * i);
                if (calculatedValue > limit) {
                    break;
                }
                evaluatedValues.add(calculatedValue);
            }
        }
        return evaluatedValues;
    }
}
