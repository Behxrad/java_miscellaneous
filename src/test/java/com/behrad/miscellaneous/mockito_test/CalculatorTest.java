package com.behrad.miscellaneous.mockito_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.util.stream.Stream;

import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorTest {

    @Mock
    Calculator calculatorMocked;

    MockedStatic<Calculator> calculatorMockedStatic;

    @BeforeAll
    void setup() {
        calculatorMockedStatic = Mockito.mockStatic(Calculator.class);
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("generateNumbers")
    void inlineMockAdd(int number1, int number2, int result) {
        Calculator mock = Mockito.mock(Calculator.class);
        Mockito.when(mock.add(1, 2)).thenReturn(3);
        Assertions.assertEquals(result, mock.add(number1, number2));
    }

    @ParameterizedTest
    @MethodSource("generateNumbers")
    void mockAdd(int number1, int number2, int result) {
        Mockito.when(calculatorMocked.add(1, 2)).thenReturn(3);
        Assertions.assertEquals(result, calculatorMocked.add(number1, number2));
    }

    @ParameterizedTest
    @MethodSource("generateNumbers")
    void mockStaticAdd(int number1, int number2, int result) {
        calculatorMockedStatic.when(() -> Calculator.staticAdd(1, 2)).thenReturn(3);
        int r = Calculator.staticAdd(number1, number2);
        Assertions.assertEquals(result, r);
        Assumptions.assumeTrue(result == r);
        calculatorMockedStatic.verify(() -> Calculator.staticAdd(1, 2));
    }

    @ParameterizedTest
    @MethodSource("generateNumbers")
    void spyAdd(int number1, int number2, int result) {
        Calculator mock = Mockito.spy(calculatorMocked);
        Mockito.when(mock.add(1, 2)).thenReturn(3);
        Assertions.assertEquals(result, mock.add(number1, number2));
    }

    static Stream<Arguments> generateNumbers() {
        return Stream.of(Arguments.of(1, 2, 3),
                Arguments.of(2, 2, 4),
                Arguments.of(2, 5, 7),
                Arguments.of(3, 3, 6));
    }
}