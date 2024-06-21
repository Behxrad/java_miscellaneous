package com.behrad.miscellaneous.mockito_test;

public class Calculator {

    private Display display;

    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public static int staticAdd(int num1, int num2) {
        return num1 + num2;
    }

    public static class Display {

    }
}
