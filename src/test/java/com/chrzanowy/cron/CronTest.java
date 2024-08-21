package com.chrzanowy.cron;

import static org.assertj.core.api.Assertions.assertThat;

import com.chrzanowy.cron.exception.ParsingException;
import com.chrzanowy.cron.parser.RequestParser;
import com.chrzanowy.cron.elements.CronElement;
import com.chrzanowy.cron.parser.ElementParser;
import com.chrzanowy.cron.printer.PrettyPrinter;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CronTest {

    public static Stream<Arguments> cronsWithSolution() {
        return Stream.of(
            Arguments.of(
                new String[]{"*/15", "0", "1,15", "*", "1-5"}, """                                    
                    minute         0 15 30 45
                    hour           0
                    day of month   1 15
                    month          1 2 3 4 5 6 7 8 9 10 11 12
                    day of week    1 2 3 4 5
                    command        /usr/bin/app"""),
            Arguments.of(
                new String[]{"23", "0-20/2", "*", "*", "*"}, """                                   
                    minute         23
                    hour           0 2 4 6 8 10 12 14 16 18 20
                    day of month   1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
                    month          1 2 3 4 5 6 7 8 9 10 11 12
                    day of week    1 2 3 4 5 6 7
                    command        /usr/bin/app"""),
            Arguments.of(
                new String[]{"0", "0,12", "1", "*/2", "*"}, """             
                    minute         0
                    hour           0 12
                    day of month   1
                    month          1 3 5 7 9 11
                    day of week    1 2 3 4 5 6 7
                    command        /usr/bin/app""")
        );
    }

    @ParameterizedTest
    @MethodSource("cronsWithSolution")
    void shouldPrintParsedCronExpression(String[] cronArray, String expectedOutput) throws ParsingException {
        //given
        var evaluatedExpressions = new ArrayList<String>();
        for (CronElement cronElement : RequestParser.parseCronElements(cronArray)) {
            var expression = ElementParser.parseCronElement(cronElement);
            var stringExpression = PrettyPrinter.prepareExpression(expression);
            evaluatedExpressions.add(stringExpression);
        }
        //when
        var output = PrettyPrinter.prettyString(evaluatedExpressions, "/usr/bin/app");

        //then
        assertThat(output).isEqualTo(expectedOutput);
    }

}
