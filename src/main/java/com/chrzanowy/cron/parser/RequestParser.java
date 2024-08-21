package com.chrzanowy.cron.parser;

import com.chrzanowy.cron.exception.InvalidCronElementException;
import com.chrzanowy.cron.exception.InvalidCronException;
import com.chrzanowy.cron.exception.ParsingException;
import com.chrzanowy.cron.operator.Operator;
import com.chrzanowy.cron.elements.CronElement;
import com.chrzanowy.cron.elements.Element;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestParser {

    private final static String PRE_FILTER_REGEXP = "(\\*)|((((\\*)|(\\d{1,2}))(\\,|\\-|\\/))*(\\/)?(\\d{1,2}))";

    public static List<CronElement> parseCronElements(String[] cronElements) throws ParsingException {
        return List.of(
            parseCronElement(cronElements[0], Element.MINUTE),
            parseCronElement(cronElements[1], Element.HOUR),
            parseCronElement(cronElements[2], Element.DAY),
            parseCronElement(cronElements[3], Element.MONTH),
            parseCronElement(cronElements[4], Element.DAY_OF_WEEK)
        );
    }

    public static CronElement parseCronElement(String rawCronElementValue, Element element)
        throws ParsingException {
        if (!Pattern.compile(PRE_FILTER_REGEXP).matcher(rawCronElementValue).matches()) {
            throw InvalidCronException.ofCron(rawCronElementValue, element);
        }
        StringBuilder cronExpression = new StringBuilder();
        char[] charArray = rawCronElementValue.toCharArray();
        StringBuilder processedDigit = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char processedChar = charArray[i];
            if (i == 0) {
                if (processedChar != Operator.ANY.getOperatorSign() && !Character.isDigit(processedChar)) {
                    throw InvalidCronElementException.invalidFirstChar(processedChar);
                }
            }
            if (Character.isDigit(processedChar)) {
                processedDigit.append(processedChar);
                if (!element.isInValidRange(Integer.parseInt(processedDigit.toString()))) {
                    throw InvalidCronElementException.invalidRange(processedDigit.toString(), i, element.name());
                }
            } else if (!Operator.supports(processedChar)) {
                throw InvalidCronElementException.unsupportedChar(processedChar, i);
            } else {
                processedDigit = new StringBuilder();
            }
            if (i > 0 && Operator.supports(processedChar) && Operator.supports(charArray[i - 1])) {
                Operator currentOperator = Operator.parse(processedChar);
                Operator previousOperator = Operator.parse(charArray[i - 1]);
                if (!previousOperator.equals(Operator.ANY) && !currentOperator.equals(Operator.STEP)) {
                    throw InvalidCronElementException.invalidOperator(currentOperator.getOperatorSign(), previousOperator.getOperatorSign());
                }
            }
            cronExpression.append(processedChar);
        }
        return new CronElement(element, cronExpression.toString());
    }
}

