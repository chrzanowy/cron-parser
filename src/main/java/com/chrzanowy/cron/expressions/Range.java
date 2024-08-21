package com.chrzanowy.cron.expressions;

import com.chrzanowy.cron.elements.Element;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class Range implements Expression {

    private final int start;

    private final int end;

    @Getter
    Element element;

    @Override
    public Set<Integer> evaluate() {
        if (start < element.getLowerBoundary() || start > element.getTopBoundary() || start > end || end > element.getTopBoundary()) {
            return Set.of();
        }
        return IntStream.rangeClosed(start, end)
            .boxed()
            .collect(Collectors.toSet());
    }
}
