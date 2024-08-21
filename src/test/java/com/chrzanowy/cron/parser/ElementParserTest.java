package com.chrzanowy.cron.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.chrzanowy.cron.elements.CronElement;
import com.chrzanowy.cron.elements.Element;
import com.chrzanowy.cron.exception.InvalidExpressionException;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElementParserTest {

    public static Stream<Arguments> validCronElements() {
        return Stream.of(
            Arguments.of("*", Set.of(1, 2, 3, 4, 5, 6, 7), Element.DAY_OF_WEEK),
            Arguments.of("9", Set.of(9), Element.MINUTE),
            Arguments.of("1-15/3", Set.of(1, 4, 7, 10, 13), Element.MINUTE),
            Arguments.of("10-20/2", Set.of(10, 12, 14, 16, 18, 20), Element.HOUR),
            Arguments.of("1,2,7,10", Set.of(1, 2, 7, 10), Element.DAY),
            Arguments.of("1,2-5,7,10", Set.of(1, 2, 3, 4, 5, 7, 10), Element.MONTH),
            Arguments.of("1,2-5,7,10/3", Set.of(1, 4, 7, 10), Element.DAY),
            Arguments.of("1-5,6-7", Set.of(1, 2, 3, 4, 5, 6, 7), Element.DAY_OF_WEEK)
        );
    }

    public static Stream<Arguments> invalidCronElements() {
        return Stream.of(
            Arguments.of("", "Empty expression", Element.MINUTE),
            Arguments.of("1-10/0", "Step cannot be zero for MINUTE", Element.MINUTE),
            Arguments.of("10-5", "Left range 10 cannot be greater than right range 5 for MINUTE", Element.MINUTE),
            Arguments.of("10-5", "Left range 10 cannot be greater than right range 5 for DAY", Element.DAY),
            Arguments.of("*-*", "Any operator cannot be user in *-*", Element.DAY),
            Arguments.of("*/*", "Any operator cannot be user in */*", Element.MONTH)
        );
    }

    @ParameterizedTest
    @MethodSource("validCronElements")
    void shouldParseElement(String cronElementString, Set<Integer> expectedResult, Element element) throws InvalidExpressionException {
        //given
        var cronElement = new CronElement(element, cronElementString);
        //when
        var evaluatedCron = ElementParser.parseCronElement(cronElement)
            .evaluate();

        //then
        Assertions.assertThat(evaluatedCron)
            .hasSameElementsAs(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("invalidCronElements")
    void shouldThrowExceptionIfCronIsInvalid(String cronElementString, String expectedException, Element element) {
        //given
        var cronElement = new CronElement(element, cronElementString);
        //when
        //then
        assertThatThrownBy(() -> ElementParser.parseCronElement(cronElement))
            .isInstanceOf(InvalidExpressionException.class)
            .hasMessageContaining(expectedException);
    }

}