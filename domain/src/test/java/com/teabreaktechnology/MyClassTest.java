
package com.teabreaktechnology;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kishorekpendyala on 12/13/15.
 */
public class MyClassTest {

    @Test
    public void test() {
        assertEquals(9, new MyClass().myworldmychoice(5, 4));
        assertEquals(4, new MyClass().myworldmychoice(0, 4));
        assertEquals(4, new MyClass().myworldmychoice(1, 3));
        System.out.println("value = " + new MyClass().myworldmychoice(5, 4));
    }

}