package com.chrzanowy.cron.expressions;

import static org.assertj.core.api.Assertions.assertThat;

import com.chrzanowy.cron.elements.Element;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RangeTest {

    public static Stream<Arguments> rangeElements() {
        return Stream.of(
            //Minutes
            Arguments.of(Element.MINUTE, 1, 6, Set.of(1, 2, 3, 4, 5, 6)),
            Arguments.of(Element.MINUTE, 60, 6, Set.of()),
            Arguments.of(Element.MINUTE, 60, 61, Set.of()),
            Arguments.of(Element.MINUTE, -1, 6, Set.of()),
            //Hours
            Arguments.of(Element.HOUR, 11, 14, Set.of(11, 12, 13, 14)),
            Arguments.of(Element.HOUR, 60, 6, Set.of()),
            Arguments.of(Element.HOUR, 60, 61, Set.of()),
            Arguments.of(Element.HOUR, -1, 6, Set.of()),
            //Days
            Arguments.of(Element.DAY, 28, 31, Set.of(28, 29, 30, 31)),
            Arguments.of(Element.DAY, 33, 6, Set.of()),
            Arguments.of(Element.DAY, 32, 33, Set.of()),
            Arguments.of(Element.DAY, -1, 6, Set.of()),
            //Months
            Arguments.of(Element.MONTH, 7, 8, Set.of(7, 8)),
            Arguments.of(Element.MONTH, 33, 6, Set.of()),
            Arguments.of(Element.MONTH, 32, 33, Set.of()),
            Arguments.of(Element.MONTH, -1, 6, Set.of()),
            //DaysOfWeek
            Arguments.of(Element.DAY_OF_WEEK, 7, 7, Set.of(7)),
            Arguments.of(Element.DAY_OF_WEEK, 33, 6, Set.of()),
            Arguments.of(Element.DAY_OF_WEEK, 32, 33, Set.of()),
            Arguments.of(Element.DAY_OF_WEEK, -1, 6, Set.of())
        );
    }

    @ParameterizedTest
    @MethodSource("rangeElements")
    void shouldEvaluateRangeForElement(Element element, Integer start, Integer end, Set<Integer> expectedResult) {
        //given
        //when
        var evaluatedResult = Range.of(start, end, element).evaluate();

        //then
        assertThat(expectedResult)
            .hasSameElementsAs(evaluatedResult);
    }

}