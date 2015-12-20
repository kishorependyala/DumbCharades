package com.teabreaktechnology;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("test");
    }

    public int myworldmychoice(int a, int b) {
        int returnValue = a + b;

        if (a > 0 && b > 0 && returnValue < 0) {
            throw new RuntimeException("addition of " + a + " " + b + " produced invalid out of bound result");
        }
        if (returnValue > Integer.MIN_VALUE)
            return returnValue;
        return returnValue;
    }
}

