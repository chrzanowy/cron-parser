package com.chrzanowy.cron.printer;

import com.chrzanowy.cron.elements.Element;
import com.chrzanowy.cron.expressions.Expression;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PrettyPrinter {

    private final static String HEADER_FORMAT = "%1$-14s";

    public static String prepareExpression(Expression expression) {
        String values = expression.evaluate()
            .stream()
            .sorted(Integer::compareTo)
            .map(String::valueOf)
            .collect(Collectors.joining(" "));
        return prepareHeader(expression) + " " + values;
    }

    private static String prepareHeader(Expression expression) {
        return switch (expression.getElement()) {
            case Element.MINUTE -> String.format(HEADER_FORMAT, "minute");
            case Element.HOUR -> String.format(HEADER_FORMAT, "hour");
            case Element.DAY -> String.format(HEADER_FORMAT, "day of month");
            case Element.MONTH -> String.format(HEADER_FORMAT, "month");
            case Element.DAY_OF_WEEK -> String.format(HEADER_FORMAT, "day of week");
        };
    }

    public static String prettyString(List<String> list, String command) {
        return String.join("\n", list) + "\n" + HEADER_FORMAT.formatted("command") + " " + command;
    }
}
