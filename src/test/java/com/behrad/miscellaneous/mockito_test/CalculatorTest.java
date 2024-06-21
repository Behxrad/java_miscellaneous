package com.behrad.miscellaneous.mockito_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorTest {

    @Mock
    Calculator.Display display;

    @InjectMocks
    Calculator calculator;

    MockedStatic<Calculator> calculatorMockedStatic;

    @BeforeAll
    void setup() {
        calculatorMockedStatic = Mockito.mockStatic(Calculator.class);
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("generateNumbers")
    void add(int number1, int number2, int result) {
        Calculator mock = Mockito.mock(Calculator.class);
        Mockito.when(mock.add(1, 2)).thenReturn(3);
        Assertions.assertEquals(result, mock.add(number1, number2));
        Mockito.verify(mock).add(1, 2);
    }

    @ParameterizedTest
    @MethodSource("generateNumbers")
    void staticAdd(int number1, int number2, int result) {
        calculatorMockedStatic.when(() -> Calculator.staticAdd(1, 2)).thenReturn(3);
        Assertions.assertEquals(result, Calculator.staticAdd(number1, number2));
        calculatorMockedStatic.verify(() -> Calculator.staticAdd(1, 2));
    }

    static Stream<Arguments> generateNumbers() {
        return Stream.of(Arguments.of(1, 2, 3),
                Arguments.of(2, 2, 4),
                Arguments.of(2, 5, 7),
                Arguments.of(3, 3, 6));
    }
}