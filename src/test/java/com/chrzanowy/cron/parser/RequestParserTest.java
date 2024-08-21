package com.chrzanowy.cron.parser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.chrzanowy.cron.elements.Element;
import com.chrzanowy.cron.exception.InvalidCronException;
import com.chrzanowy.cron.exception.ParsingException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RequestParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"1-15/3", "1-3", "1-3/13", "1,2,3,4,5,6,7,8,9,10-12"})
    void shouldParseMinutes(String expression) throws ParsingException {
        //given
        //when
        var cronElement = RequestParser.parseCronElement(expression, Element.MINUTE);

        //then
        AssertionsForClassTypes.assertThat(cronElement.elementCron()).isEqualTo(expression);
        AssertionsForClassTypes.assertThat(cronElement.element()).isEqualTo(Element.MINUTE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1--3/*", "hello", "+1/1", "?", "0 0 0 0", "*/*", "1/111", "111/1", "111,1", "111-4"})
    void shouldReturnValidationErrorsForInvalidCron(String expression) {
        //given
        //when
        //then
        assertThatThrownBy(() -> RequestParser.parseCronElement(expression, Element.MINUTE))
            .isInstanceOf(InvalidCronException.class)
            .hasMessageContaining("Invalid expression %s for %s".formatted(expression, Element.MINUTE.name()));
    }


}