package com.chrzanowy.cron.expressions;

import static com.chrzanowy.cron.elements.Element.DAY;
import static com.chrzanowy.cron.elements.Element.DAY_OF_WEEK;
import static com.chrzanowy.cron.elements.Element.HOUR;
import static com.chrzanowy.cron.elements.Element.MINUTE;
import static com.chrzanowy.cron.elements.Element.MONTH;
import static org.assertj.core.api.Assertions.assertThat;

import com.chrzanowy.cron.elements.Element;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StepTest {

    public static Stream<Arguments> rangeElements() {
        return Stream.of(
            //Minutes
            Arguments.of(MINUTE, Range.of(0, 59, MINUTE), Range.of(10, 10, MINUTE), Set.of(0, 10, 20, 30, 40, 50)),
            Arguments.of(MINUTE, Range.of(5, 59, MINUTE), Range.of(13, 13, MINUTE), Set.of(5, 18, 31, 44, 57)),
            //Hours
            Arguments.of(HOUR, Range.of(0, 23, HOUR), Range.of(13, 13, HOUR), Set.of(0, 13)),
            Arguments.of(HOUR, Range.of(5, 12, HOUR), Range.of(2, 2, HOUR), Set.of(5, 7, 9, 11)),
            //Month
            Arguments.of(MONTH, Range.of(1, 12, MONTH), Range.of(2, 2, MONTH), Set.of(1, 3, 5, 7, 9, 11)),
            Arguments.of(MONTH, Range.of(2, 8, MONTH), Range.of(3, 3, MONTH), Set.of(2, 5, 8)),
            //Day
            Arguments.of(DAY, Range.of(1, 31, DAY), Range.of(10, 10, DAY), Set.of(1, 11, 21, 31)),
            Arguments.of(DAY, Range.of(20, 27, DAY), Range.of(5, 5, DAY), Set.of(20, 25)),
            //Day of week
            Arguments.of(DAY_OF_WEEK, Range.of(1, 7, DAY_OF_WEEK), Range.of(3, 3, DAY_OF_WEEK), Set.of(1, 4, 7)),
            Arguments.of(DAY_OF_WEEK, Range.of(1, 3, DAY_OF_WEEK), Range.of(1, 1, DAY_OF_WEEK), Set.of(1, 2, 3)),
            //Day not standard
            Arguments.of(DAY, Range.of(1, 7, DAY), Range.of(2, 3, DAY), Set.of(1, 3, 5, 7, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("rangeElements")
    void shouldEvaluateStepForElement(Element element, Range range, Range step, Set<Integer> expectedResult) {
        //given
        //when
        var evaluatedResult = Step.of(range, step,element).evaluate();

        //then
        assertThat(expectedResult)
            .hasSameElementsAs(evaluatedResult);
    }
}