package com.chrzanowy.cron.printer;

import static org.assertj.core.api.Assertions.assertThat;

import com.chrzanowy.cron.elements.Element;
import com.chrzanowy.cron.expressions.Expression;
import com.chrzanowy.cron.expressions.Range;
import com.chrzanowy.cron.expressions.RangeList;
import com.chrzanowy.cron.expressions.Step;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PrettyPrinterTest {

    public static Stream<Arguments> expressions() {
        return Stream.of(
            Arguments.of(Range.of(1, 5, Element.MINUTE), "minute         1 2 3 4 5"),
            Arguments.of(Range.of(1, 5, Element.DAY_OF_WEEK), "day of week    1 2 3 4 5"),
            Arguments.of(Range.of(12, 12, Element.MONTH), "month          12"),
            Arguments.of(RangeList.of(List.of(Range.of(1, 1, Element.HOUR)), Element.HOUR), "hour           1"),
            Arguments.of(Step.of(Range.of(1, 3, Element.DAY), Range.of(3, 3, Element.DAY), Element.DAY), "day of month   1")
        );
    }

    @ParameterizedTest
    @MethodSource("expressions")
    void shouldPrepareExpressionString(Expression expression, String expectedResult) {
        //given
        //when
        var result = PrettyPrinter.prepareExpression(expression);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

}