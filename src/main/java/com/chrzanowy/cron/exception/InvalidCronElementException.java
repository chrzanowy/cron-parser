package com.chrzanowy.cron.exception;

public class InvalidCronElementException extends ParsingException {

    public InvalidCronElementException(String message) {
        super(message);
    }

    public static InvalidCronElementException invalidFirstChar(char processedChar) {
        return new InvalidCronElementException("Expression cannot start with %c".formatted(processedChar));
    }

    public static InvalidCronElementException invalidRange(String processedChar, int position, String elementName) {
        return new InvalidCronElementException("Value %s out of range at %d in %s".formatted(processedChar, position, elementName));
    }

    public static InvalidCronElementException unsupportedChar(char processedChar, int position) {
        return new InvalidCronElementException("Unsupported char %c at %d".formatted(processedChar, position));
    }

    public static InvalidCronElementException invalidOperator(char processedChar, char preProcessedChar) {
        return new InvalidCronElementException("Invalid operator used %c with %c".formatted(processedChar, preProcessedChar));
    }
}
