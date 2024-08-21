package com.chrzanowy.cron.exception;

import com.chrzanowy.cron.elements.Element;

public class InvalidCronException extends ParsingException {

    private InvalidCronException(String message) {
        super(message);
    }

    public static InvalidCronException ofCron(String cron, Element element) {
        return new InvalidCronException("Invalid expression %s for %s".formatted(cron, element.name()));
    }

}
