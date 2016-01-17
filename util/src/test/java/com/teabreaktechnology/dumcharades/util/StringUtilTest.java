package com.teabreaktechnology.dumcharades.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kishorekpendyala on 12/20/15.
 */
public class StringUtilTest {

    @Test
    public void splitParamsTest() {

        assertEquals("string1-value,string2,string3", StringUtil.strArrayToStr(StringUtil.split("'string1-value','string2','string3'", ',', '\'')));
        assertEquals("string1,value,string2,string3", StringUtil.strArrayToStr(StringUtil.split("'string1,value','string2','string3'", ',', '\'')));
        assertEquals("string1|value,string2,string3", StringUtil.strArrayToStr(StringUtil.split("'string1|value'|'string2'|'string3'", '|', '\'')));

        assertEquals("string1,value,string2,string3", StringUtil.strArrayToStr(StringUtil.split("|string1,value|,|string2|,|string3|", ',', '|')));

        assertEquals("string1,value,string2,string3", StringUtil.strArrayToStr(StringUtil.split("|string1,value|,string2,string3", ',', '|')));
        assertEquals("", StringUtil.strArrayToStr(StringUtil.split("", ',', '|')));
        assertEquals("abc", StringUtil.strArrayToStr(StringUtil.split("|abc|", ',', '|')));

    }


    @Test
    public void testSplit() throws Exception {

        assertEquals("abc", StringUtil.strArrayToStr(StringUtil.split("abc")));
        assertEquals("abc,hello", StringUtil.strArrayToStr(StringUtil.split("abc,hello")));
        assertEquals("abc,test,hello", StringUtil.strArrayToStr(StringUtil.split("\"abc,test\",hello")));
    }

    @Test
    public void testIsValidStringWithNonZeroLength() throws Exception {

        assertTrue(StringUtil.isValidStringWithNonZeroLength("") == false);
        assertTrue(StringUtil.isValidStringWithNonZeroLength("avinash") == true);
        assertTrue(StringUtil.isValidStringWithNonZeroLength(null) == false);

    }

    @Test
    public void testStrArrayToStr() throws Exception {

    }
}
