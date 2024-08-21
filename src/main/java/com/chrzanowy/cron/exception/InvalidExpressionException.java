package com.chrzanowy.cron.exception;

import com.chrzanowy.cron.elements.Element;

public class InvalidExpressionException extends ParsingException {

    private InvalidExpressionException(String message) {
        super(message);
    }

    public static InvalidExpressionException empty() {
        return new InvalidExpressionException("Empty expression");
    }

    public static InvalidExpressionException ofRange(int leftRange, int rightRange, Element element) {
        return new InvalidExpressionException("Left range %d cannot be greater than right range %d for %s".formatted(leftRange, rightRange, element.name()));
    }

    public static InvalidExpressionException ofZeroStep(Element element) {
        return new InvalidExpressionException("Step cannot be zero for %s".formatted(element.name()));
    }

    public static InvalidExpressionException ofAny(String expressionString) {
        return new InvalidExpressionException("Any operator cannot be user in %s".formatted(expressionString));
    }
}
