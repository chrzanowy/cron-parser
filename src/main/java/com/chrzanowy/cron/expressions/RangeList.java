package com.chrzanowy.cron.expressions;

import com.chrzanowy.cron.elements.Element;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class RangeList implements Expression {

    List<Expression> expressions;

    @Getter
    Element element;

    @Override
    public Set<Integer> evaluate() {
        Set<Integer> result = new HashSet<>();
        for (Expression expression : expressions) {
            result.addAll(expression.evaluate());
        }
        return result;
    }
}
