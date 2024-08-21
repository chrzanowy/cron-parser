package com.chrzanowy.cron;

import com.chrzanowy.cron.elements.CronElement;
import com.chrzanowy.cron.exception.ParsingException;
import com.chrzanowy.cron.expressions.Expression;
import com.chrzanowy.cron.parser.ElementParser;
import com.chrzanowy.cron.parser.RequestParser;
import com.chrzanowy.cron.printer.PrettyPrinter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            log.error("No arguments provided");
            return;
        }
        if (args[0].equals("help") || args[0].equals("-h") || args[0].equals("--help")) {
            System.out.println("Usage: java -jar cron-parser.jar <minute> <hour> <day> <month> <day of week> <command>");
            return;
        }
        if (args.length > 6) {
            log.error("Too many arguments provided");
            return;
        }
        if (args.length < 6) {
            log.error("Too few arguments provided");
            return;
        }
        try {
            List<String> evaluatedExpressions = new ArrayList<>();
            for (CronElement cronElement : RequestParser.parseCronElements(Arrays.copyOf(args, 5))) {
                Expression expression = ElementParser.parseCronElement(cronElement);
                String stringExpression = PrettyPrinter.prepareExpression(expression);
                evaluatedExpressions.add(stringExpression);
            }
            System.out.println(PrettyPrinter.prettyString(evaluatedExpressions, args[5]));
        } catch (ParsingException e) {
            log.error("Invalid cron expression provided", e);
        }
    }
}