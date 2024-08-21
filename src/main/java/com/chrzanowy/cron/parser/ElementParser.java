package com.chrzanowy.cron.parser;

import com.chrzanowy.cron.elements.CronElement;
import com.chrzanowy.cron.elements.Element;
import com.chrzanowy.cron.exception.InvalidExpressionException;
import com.chrzanowy.cron.expressions.Expression;
import com.chrzanowy.cron.expressions.Range;
import com.chrzanowy.cron.expressions.RangeList;
import com.chrzanowy.cron.expressions.Step;
import com.chrzanowy.cron.operator.Operator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ElementParser {

    public static Expression parseCronElement(CronElement cronElement) throws InvalidExpressionException {
        return parseSignleExpression(cronElement.elementCron(), cronElement.element());
    }

    private static Expression parseSignleExpression(String expressionString, Element element) throws InvalidExpressionException {
        char[] charArray = expressionString.toCharArray();
        if (charArray.length == 0) {
            throw InvalidExpressionException.empty();
        }
        if (expressionString.contains(String.valueOf(Operator.STEP.getOperatorSign()))) {
            return divideIntoTwoParts(element, expressionString);
        }
        return multipleElementExpression(element, expressionString);
    }

    private static List<Integer> findAllSeparators(String expressionString) {
        List<Integer> indexesOfSeparators = new ArrayList<>();
        int index = expressionString.indexOf(Operator.SEPARATOR.getOperatorSign());
        while (index >= 0) {
            indexesOfSeparators.add(index);
            index = expressionString.indexOf(Operator.SEPARATOR.getOperatorSign(), index + 1);
        }
        return indexesOfSeparators;
    }

    private static Expression multipleElementExpression(Element element, String expressionString) throws InvalidExpressionException {
        List<Integer> allSeparators = findAllSeparators(expressionString);
        if (allSeparators.isEmpty()) {
            return rangeElementExpression(element, expressionString);
        }
        List<Expression> expressions = new ArrayList<>();
        int previousIndex = 0;
        for (Integer separatorIndex : allSeparators) {
            String substring = expressionString.substring(previousIndex, separatorIndex);
            expressions.add(rangeElementExpression(element, substring));
            previousIndex = separatorIndex + 1;
        }
        expressions.add(rangeElementExpression(element, expressionString.substring(previousIndex)));
        return RangeList.of(expressions, element);
    }

    private static Expression divideIntoTwoParts(Element element, String expressionString) throws InvalidExpressionException {
        int stepOperatorIndex = expressionString.indexOf(Operator.STEP.getOperatorSign());
        String leftSideExpression = expressionString.substring(0, stepOperatorIndex);
        String rightSideExpression = expressionString.substring(stepOperatorIndex + 1);
        if (leftSideExpression.equals(String.valueOf(Operator.ANY.getOperatorSign()))
            && rightSideExpression.equals(String.valueOf(Operator.ANY.getOperatorSign()))) {
            throw InvalidExpressionException.ofAny(expressionString);
        }
        if (rightSideExpression.equals("0")) {
            throw InvalidExpressionException.ofZeroStep(element);
        }
        return Step.of(parseSignleExpression(leftSideExpression, element), parseSignleExpression(rightSideExpression, element), element);
    }

    private static List<Integer> findOperators(String string) {
        return Arrays.stream(Operator.values())
            .filter(op -> op != Operator.ANY)
            .map(Operator::getOperatorSign)
            .map(string::indexOf)
            .filter(index -> index != -1)
            .toList();
    }

    private static Expression rangeElementExpression(Element element, String expressionString) throws InvalidExpressionException {
        if (findOperators(expressionString).isEmpty()) {
            return singleElementExpression(element, expressionString);
        }
        int indexOfOperator = expressionString.indexOf(Operator.RANGE.getOperatorSign());
        String leftElement = expressionString.substring(0, indexOfOperator);
        String rightElement = expressionString.substring(indexOfOperator + 1);
        if (String.valueOf(Operator.ANY.getOperatorSign()).equals(leftElement) || String.valueOf(Operator.ANY.getOperatorSign()).equals(rightElement)) {
            throw InvalidExpressionException.ofAny(expressionString);
        }
        int leftRange = Integer.parseInt(leftElement);
        int rightRange = Integer.parseInt(rightElement);

        if (leftRange > rightRange) {
            throw InvalidExpressionException.ofRange(leftRange, rightRange, element);
        }

        return Range.of(leftRange, rightRange, element);
    }

    private static Range singleElementExpression(Element element, String expressionString) {
        if (expressionString.length() == 1 && Operator.ANY.getOperatorSign() == expressionString.toCharArray()[0]) {
            return Range.of(element.getLowerBoundary(), element.getTopBoundary(), element);
        }
        int parsedInt = Integer.parseInt(expressionString);
        return Range.of(parsedInt, parsedInt, element);
    }
}
